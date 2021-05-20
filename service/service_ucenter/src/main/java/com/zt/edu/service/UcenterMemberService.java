package com.zt.edu.service;

import com.zt.edu.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zt.edu.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author ZhangTao
 * @since 2021-05-11
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    void register(RegisterVo registerVo);

    UcenterMember getOpenIdMember(String openid);

    Integer ucenterMemberService(String day);
}
