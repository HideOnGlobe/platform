package com.elison.platform.commons.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


/**
 * @ProjectName: platform
 * @Package: com.elison.platform.commons.mybatis
 * @Description: 通用Mapper
 * @Author: elison
 * @CreateDate: 2020/9/10 22:38
 * @UpdateDate: 2020/9/10 22:38
 **/
public interface MyMapper<T> extends BaseMapper<T> {

    /**
     * @param entityList
     * @return
     */
    int insertBatchSomeColumn(List<T> entityList);

    /**
     * 凭借Id逻辑删除
     * @param t 实体
     * @return 是否成功删除
     */
//    int logicDeleteByIdWithFill(@Param(Constants.ENTITY) T t);

    /**
     * 批量逻辑删除并填充字段
     * @param t 实体
     * @param wrapper 删除条件
     * @return 是否成功删除
     */
//    int logicBatchDeleteWithFill(@Param(Constants.ENTITY) T t, @Param(Constants.WRAPPER)Wrapper<T> wrapper);
}
