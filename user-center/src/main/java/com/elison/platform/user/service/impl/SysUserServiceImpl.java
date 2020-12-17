package com.elison.platform.user.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.elison.platform.commons.exception.ResultException;
import com.elison.platform.commons.mybatis.SnowflakeIdGenerator;
import com.elison.platform.commons.result.CodeEnum;
import com.elison.platform.user.constants.PermissionConstant;
import com.elison.platform.user.constants.UserConstant;
import com.elison.platform.user.enums.UserStatusEnum;
import com.elison.platform.user.mapper.SysUserMapper;
import com.elison.platform.user.model.dao.SysUser;
import com.elison.platform.user.model.dto.SysRoleDTO;
import com.elison.platform.user.model.dto.SysUserDTO;
import com.elison.platform.user.redis.UserRedisKeyPrefix;
import com.elison.platform.user.service.*;
import com.elison.platform.user.shiro.ShiroConfig;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.user.service.data.impl
 * @Description: -
 * @Author: elison
 * @CreateDate: 2020/9/9 13:47
 * @UpdateDate: 2020/9/9 13:47
 **/
@Service
public class SysUserServiceImpl implements SysUserService {

    private static final String CACHE_KEY = UserRedisKeyPrefix.USER_DATA_SYS_USER_KEY;

    public static final String DEFAULT_PASSWORD = "123456";

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleService sysRoleService;
    @Lazy
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public SysUserDTO getOneByUserName(String userName) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", userName);
        SysUser sysUserDO = sysUserMapper.selectOne(wrapper);
        return sysUserDO != null ? sysUserDO.convertToDTO(SysUserDTO.class) : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public SysUserDTO getOneByPhone(String phone) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        SysUser sysUserDO = sysUserMapper.selectOne(wrapper);
        return sysUserDO != null ? sysUserDO.convertToDTO(SysUserDTO.class) : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean freezeUserList(List<Long> userIdList) {
        int num = 0;
        for (Long userId : userIdList) {
            SysUser oldSysUser = sysUserMapper.selectById(userId);
            if (ObjectUtil.isNull(oldSysUser)) {
                throw new ResultException(CodeEnum.BUSSINESS_ERROR, "当前用户异常,请稍后重试");
            }
            // 如果用户已被删除则不需要被禁用
            if (ObjectUtil.equal(oldSysUser.getUserStatus(), UserStatusEnum.DELETED)) {
                num += 1;
                continue;
            }
            SysUserDTO sysUserDTO = new SysUserDTO();
            sysUserDTO.setId(userId);
            sysUserDTO.setUserStatus(UserStatusEnum.FREEZE);
            num = num + (sysUserService.updateById(sysUserDTO) ? 1 : 0);
        }
        return ObjectUtil.equal(num, userIdList.size());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteIdSet(Set<Long> idSet) {
        int num = 0;
        for (Long id : idSet) {
            if (deleteById(id)) {
                num++;
            }
        }
        if (num != idSet.size()) {
            throw new ResultException(CodeEnum.DELETE_ERROR);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(SysUserDTO entity) {
        if (entity.getPassword() == null) {
            entity.setPassword(DEFAULT_PASSWORD);
        }
        encryptPassword(entity);
        entity.setUserStatus(UserStatusEnum.NOT_ACTIVE);
        SysUser user = entity.convertToDO(SysUser.class);
        if (sysUserMapper.insert(user) > 0) {
            if (ObjectUtil.isNotEmpty(entity.getRoleSet())) {
                if (sysUserMapper.addUserRoleCollection(user.getId(),
                        entity.getRoleSet().stream().map(SysRoleDTO::getId).collect(Collectors.toList()))
                        != entity.getRoleSet().size()) {
                    throw new ResultException(CodeEnum.ADD_ERROR, "新增角色权限异常，请重新尝试");
                }
            }
            if (sysUserMapper.insertWallet(snowflakeIdGenerator.nextId(null), user.getId()) == 0) {
                throw new ResultException(CodeEnum.ADD_ERROR, "新增钱包异常，请重新尝试");
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CACHE_KEY, key = "#entity.id", condition = "#entity.id != null")
    public boolean updateById(SysUserDTO entity) {
        if (ObjectUtil.isNotEmpty(entity.getUserName()) || ObjectUtil.isNull(entity.getId())) {
            return false;
        }
        boolean checkOld = false;
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotEmpty(entity.getPhone())) {
            if (checkOld) {
                queryWrapper.or();
            }
            queryWrapper.eq("phone", entity.getPhone());
            checkOld = true;
        }
        if (ObjectUtil.isNotEmpty(entity.getEmail())) {
            if (checkOld) {
                queryWrapper.or();
            }
            queryWrapper.eq("email", entity.getEmail());
            checkOld = true;
        }
        if (checkOld) {
            SysUser old = sysUserMapper.selectOne(queryWrapper);
            if (old != null && !old.getId().equals(entity.getId())) {
                throw new ResultException(CodeEnum.REQ_ERROR, "手机号/邮箱已存在");
            }
        }
        // 保证ROOT用户一直是正常状态
        if (ObjectUtil.equal(entity.getId(), UserConstant.ROOT)) {
            entity.setUserStatus(UserStatusEnum.NORMAL);
        }
        if (ObjectUtil.isNotEmpty(entity.getPassword())) {
            encryptPassword(entity);
        }
        if (sysUserMapper.updateById(entity.convertToDO(SysUser.class)) > 0) {
            if (ObjectUtil.isNotEmpty(entity.getRoleSet())) {
                sysUserMapper.deleteUserRole(entity.getId());
                if (sysUserMapper.addUserRoleCollection(entity.getId(),
                        entity.getRoleSet().stream().map(SysRoleDTO::getId).collect(Collectors.toList()))
                        != entity.getRoleSet().size()) {
                    throw new ResultException(CodeEnum.UPDATE_ERROR, "更新角色权限异常，请重新尝试");
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Cacheable(value = CACHE_KEY, key = "#id", condition = "#id != null")
    public SysUserDTO getById(Long id) {
        SysUser sysUserDO = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("id", id));
        return sysUserDO != null ? sysUserDO.convertToDTO(SysUserDTO.class) : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public IPage<SysUserDTO> page(IPage<SysUserDTO> page, SysUserDTO entity, boolean hasAdminPermission) {
        IPage<SysUserDTO> pageResult = convertPageResult(
                sysUserMapper.pageUserByManage(
                        convertPageQuery(page, SysUser.class), entity.convertToDO(SysUser.class),
                        sysRoleService.listRoleIdWithPermission(PermissionConstant.ADMIN, hasAdminPermission)),
                SysUserDTO.class);
//        pageResult.getRecords().forEach(sysUserDTO -> {});
        return pageResult;
    }


    @Override
    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
    @CacheEvict(value = CACHE_KEY, key = "#id", condition = "#id != null")
    public boolean deleteById(Long id) {
        SysUserDTO entity = new SysUserDTO();
        entity.setId(id);
        entity.setUserStatus(UserStatusEnum.DELETED);
        return sysUserService.updateById(entity);
    }

    private void encryptPassword(SysUserDTO user) {
        user.setSalt(RandomUtil.randomString(16));
        user.setPassword(Base64.encode(new SimpleHash(ShiroConfig.HASH_ALGORITHM_NAME, user.getPassword(),
                user.getSalt(), ShiroConfig.HASH_ITERATIONS).getBytes()));
    }
}
