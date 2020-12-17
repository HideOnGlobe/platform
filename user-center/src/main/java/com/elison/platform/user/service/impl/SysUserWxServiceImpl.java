package com.elison.platform.user.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.elison.platform.commons.exception.ResultException;
import com.elison.platform.commons.result.CodeEnum;
import com.elison.platform.user.mapper.SysUserWxMapper;
import com.elison.platform.user.model.dao.SysUserWx;
import com.elison.platform.user.service.SysUserWxService;
import com.elison.platform.user.shiro.UserUtil;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @ProjectName: platform
 * @Package: com.elison.platform.user.service.impl
 * @Description: -
 * @Author: elison
 * @CreateDate: 2020/12/1 15:58
 * @UpdateDate: 2020/12/1 15:58
 **/
@Service
public class SysUserWxServiceImpl implements SysUserWxService {

    @Autowired
    private SysUserWxMapper sysUserWxMapper;
    @Autowired
    private WxMpService wxMpService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String getOpenIdByCode(String code) {
        Long userId = UserUtil.getCurrentUser().getId();
        QueryWrapper<SysUserWx> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        SysUserWx sysUserWx = sysUserWxMapper.selectOne(queryWrapper);
        if (ObjectUtil.isNull(sysUserWx)) {
            try {
                WxOAuth2AccessToken accessToken = wxMpService.getOAuth2Service().getAccessToken(code);
                sysUserWx = new SysUserWx();
                sysUserWx.setUserId(userId);
                sysUserWx.setOpenId(accessToken.getOpenId());
                sysUserWx.setUnionId(accessToken.getUnionId());
                if (sysUserWxMapper.insert(sysUserWx) == 0) {
                    throw new ResultException(CodeEnum.ADD_ERROR, "记录用户微信账户信息失败");
                }
            } catch (WxErrorException e) {
                throw new ResultException(CodeEnum.BUSSINESS_ERROR, "通过code获取用户微信账户信息失败");
            }
        }
        return sysUserWx.getOpenId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public String getOpenIdByUserId(Long userId) {
        QueryWrapper<SysUserWx> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        SysUserWx sysUserWx = sysUserWxMapper.selectOne(queryWrapper);
        return ObjectUtil.isNotNull(sysUserWx) ? sysUserWx.getOpenId() : null;
    }
}
