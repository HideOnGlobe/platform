package com.elison.platform.user.service.data;

import com.elison.platform.commons.service.BaseDataService;
import com.elison.platform.user.model.dao.SysUserDO;
import com.elison.platform.user.model.dto.SysUserDTO;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.user.service
 * @Description: -
 * @Author: elison
 * @CreateDate: 2020/9/8 23:46
 * @UpdateDate: 2020/9/8 23:46
 **/
public interface SysUserService extends BaseDataService<SysUserDTO, SysUserDO> {

    /**
     * 根据登录名(邮箱/手机号)获取对应的信息
     * @param userName 登录名
     * @return 用户信息
     */
    SysUserDTO getOneByUserName(String userName);
}
