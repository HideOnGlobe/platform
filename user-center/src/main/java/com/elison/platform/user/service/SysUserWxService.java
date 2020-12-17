package com.elison.platform.user.service;

import com.elison.platform.commons.service.BaseDataService;
import com.elison.platform.user.model.dao.SysUserWx;
import com.elison.platform.user.model.dto.SysUserWxDTO;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.user.service
 * @Description: -
 * @Author: elison
 * @CreateDate: 2020/12/1 15:53
 * @UpdateDate: 2020/12/1 15:53
 **/
public interface SysUserWxService extends BaseDataService<SysUserWxDTO, SysUserWx> {

    /**
     * 通过临时code获取openId
     * @param code 临时code
     * @return 微信OpenId
     */
    String getOpenIdByCode(String code);

    /**
     * 通过用户获取其OpenId
     * @param userId 用户ID
     * @return 微信OpenId
     */
    String getOpenIdByUserId(Long userId);

}
