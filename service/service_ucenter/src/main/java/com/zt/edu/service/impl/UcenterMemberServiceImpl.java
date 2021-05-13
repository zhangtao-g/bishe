package com.zt.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zt.commonutils.JwtUtils;
import com.zt.commonutils.MD5;
import com.zt.edu.entity.UcenterMember;
import com.zt.edu.entity.vo.RegisterVo;
import com.zt.edu.mapper.UcenterMemberMapper;
import com.zt.edu.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zt.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author ZhangTao
 * @since 2021-05-11
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String login(UcenterMember member) {
        //获取手机号和密码
        String mobile =member.getMobile();
        String passwd=member.getPassword();

        //手机号和密码判断非空
        if(StringUtils.isEmpty(member)||StringUtils.isEmpty(passwd)){
            throw new GuliException(20001,"登录失败");
        }


        //判断手机号是否正确

        QueryWrapper<UcenterMember> wrapper=new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember mobilemember=baseMapper.selectOne(wrapper);

        //判断查询对象是否为空
        if(mobilemember==null){//s没有这个手机号
            throw new GuliException(20001,"登录失败");
        }

        //判断密码  MD5加密后再判断
        if(!MD5.encrypt(passwd).equals(mobilemember.getPassword())){
            throw new GuliException(20001,"登录失败");
        }

        //判断用户是否禁用
        if(mobilemember.getIsDisabled()){
            throw new GuliException(20001,"登录失败");
        }

//      登录成功
        //使用JWT生成token字符串
        String token = JwtUtils.getJwtToken(mobilemember.getId(), mobilemember.getNickname());
        return token;


    }


    //注册检验
    @Override
    public void register(RegisterVo registerVo) {
        //获取注册的数据
        String code=registerVo.getCode();
        String mobile=registerVo.getMobile();
        String nickname=registerVo.getNickname();
        String password=registerVo.getPassword();

        //判断非空
        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
            throw new GuliException(20001,"注册失败");
        }

        //判断验证码
        //获取redis验证码
        String redisCode=redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)){
            throw new GuliException(20001,"注册失败");
        }
        //判断手机号是否重复
        QueryWrapper<UcenterMember> wrapper=new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count=baseMapper.selectCount(wrapper);
        if(count>0){
            throw new GuliException(20001,"注册失败");
        }

        //数据添加数据库
        UcenterMember member=new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setIsDeleted(false);//用户不禁用
        member.setAvatar("http://th\n" +
                "irdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtT\n" +
                "LqiaJeanU2Lpg3w/132");
        baseMapper.insert(member);

    }


    //根据openid来判断用户是否重复
    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openid);
        UcenterMember member = baseMapper.selectOne(queryWrapper);
        return member;

    }
}
