package com.elison.platform.user.service.data.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.elison.platform.user.mapper.SysUserMapper;
import com.elison.platform.user.model.dao.SysUserDO;
import com.elison.platform.user.model.dto.SysUserDTO;
import com.elison.platform.user.redis.UserRedisKeyPrefix;
import com.elison.platform.user.service.data.SysUserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public SysUserDTO getOneByUserName(String userName) {
        SysUserDTO sysUserDTO = null;
        QueryWrapper<SysUserDO> wrapper = new QueryWrapper<>();
        wrapper.eq("userName", userName)
                .or().eq("email", userName)
                .or().eq("phone", userName);
        SysUserDO sysUserDO = sysUserMapper.selectOne(wrapper);
        return sysUserDO != null ? sysUserDO.convertToDTO(SysUserDTO.class) : null;
    }

    @Override
    public QueryWrapper<SysUserDO> queryWrapper(SysUserDTO entity) {
        QueryWrapper<SysUserDO> wrapper = new QueryWrapper<>();
        wrapper.eq("id", entity.getId());
        return wrapper;
    }

    @Override
    public boolean insert(SysUserDTO entity) {
        return sysUserMapper.insert(entity.convertToDO(SysUserDO.class)) > 0;
    }

    @Override
    public boolean insertBatch(Collection<SysUserDTO> entityList) {
        return CollectionUtil.isNotEmpty(entityList) &&
                sysUserMapper.insertBatchSomeColumn(new ArrayList<>(entityList.stream().map(entity -> entity.convertToDO(SysUserDO.class)).collect(Collectors.toList())))
                        == entityList.size();
    }

    @Override
    @CacheEvict(value = CACHE_KEY, key = "#entity.id", condition = "#entity.id != null")
    public boolean updateById(SysUserDTO entity) {
        if (ObjectUtil.isNotEmpty(entity.getUserName()) || ObjectUtil.isNotNull(entity.getId())) {
            return false;
        }
        boolean checkOld = false;
        QueryWrapper<SysUserDO> queryWrapper = new QueryWrapper<>();
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
            SysUserDO old = sysUserMapper.selectOne(queryWrapper);
            if (old != null && !old.getId().equals(entity.getId())) {
                return false;
            }
        }
        UpdateWrapper<SysUserDO> updateWrapper = new UpdateWrapper<>();
        // todo 判断那些可以更新
        return sysUserMapper.update(entity.convertToDO(SysUserDO.class), updateWrapper) > 0;
    }

    @Override
    @Cacheable(value = CACHE_KEY, key = "#id", condition = "#id != null")
    public SysUserDTO getById(Long id) {
        return sysUserMapper.selectOne(new QueryWrapper<SysUserDO>().eq("id", id)).convertToDTO(SysUserDTO.class);
    }

    @Override
    public List<SysUserDTO> list(SysUserDTO entity) {
        return CollectionUtil.emptyIfNull(sysUserMapper.selectList(queryWrapper(entity))).stream().map(sysUserDO -> sysUserDO.convertToDTO(SysUserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public IPage<SysUserDTO> page(IPage<SysUserDTO> page, SysUserDTO entity) {
        return convertPageResult(sysUserMapper.selectPage(convertPageQuery(page, SysUserDO.class),
                queryWrapper(entity)), SysUserDTO.class);
    }

    @Override
    public int count(SysUserDTO entity) {
        return 0;
    }

    @Override
    @CacheEvict(value = CACHE_KEY, key = "#id", condition = "#id != null")
    public boolean deleteById(Long id) {
        if (id == null) {
            return false;
        }
        SysUserDTO entity = new SysUserDTO();
        entity.setId(id);
        return delete(entity);
    }

    @Override
    public boolean delete(SysUserDTO entity) {
        return sysUserMapper.logicDeleteByIdWithFill(entity.convertToDO(SysUserDO.class)) > 0;
    }
}
