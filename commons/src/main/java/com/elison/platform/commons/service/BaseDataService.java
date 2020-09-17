package com.elison.platform.commons.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.elison.platform.commons.model.BaseDO;
import com.elison.platform.commons.model.BaseDTO;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.commons.service
 * @Description: -
 * @Author: elison
 * @CreateDate: 2020/9/13 22:55
 * @UpdateDate: 2020/9/13 22:55
 **/
public interface BaseDataService<T extends BaseDTO, R extends BaseDO> {

    /**
     * 批量大小
     */
    static final int BATCH_SIZE = 1024;

    /**
     * 生成通用查询条件
     * @param entity 实体对象
     * @return 查询条件
     */
    QueryWrapper<R> queryWrapper(T entity);

    /**
     * 插入一条记录（选择字段，策略插入）
     *
     * @param entity 实体对象
     * @return 是否新增成功
     */
    boolean insert(T entity);

    /**
     * 插入（批量）
     *
     * @param entityList 实体对象集合
     * @return 是否新增成功
     */
    boolean insertBatch(Collection<T> entityList);


    /**
     * 根据 ID 选择修改
     *
     * @param entity 实体对象
     * @return 是否操作成功
     */
    boolean updateById(T entity);

    /**
     * 根据ID 批量更新
     *
     * @param entityList 实体对象集合
     * @return 是否操作成功
     */
    default boolean updateBatchById(Collection<T> entityList) {
        return (entityList != null && entityList.size() > 0) ?
                (entityList.stream().map(this::updateById).reduce(true, (a, b) -> a && b)) : true;
    }

    /**
     * TableId 注解存在更新记录，否插入一条记录
     *
     * @param entity 实体对象
     * @return 是否操作成功
     */
    default boolean insertOrUpdate(T entity) {
        return updateById(entity) || insert(entity);
    }

    /**
     * 批量修改插入
     *
     * @param entityList 实体对象集合
     * @return 是否操作成功
     */
    default boolean insertOrUpdateBatch(Collection<T> entityList) {
        return (entityList != null && entityList.size() > 0) ?
                (entityList.stream().map(this::insertOrUpdate).reduce(true, (a, b) -> a && b)) : true;
    }


    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     * @return 实体对象
     */
    T getById(Long id);

    /**
     * 根据 实体对象，查询一条记录
     *
     * @param entity 实体对象
     * @return 实体对象
     */
    default T getOne(T entity) {
        List<T> result = list(entity);
        return (result != null && result.size() > 0) ? result.get(0) : null;
    }

    /**
     * 查询列表
     *
     * @return 实体对象列表
     */
    default List<T> list() {
        return list(null);
    }



    /**
     * 查询列表
     *
     * @param entity 实体对象
     * @return 实体对象列表
     */
    List<T> list(T entity);

    /**
     * 翻页查询
     *
     * @param page 翻页对象
     * @return 实体对象列表
     */
    default IPage<T> page(IPage<T> page) {
        return page(page, null);
    }

    /**
     * 翻页查询
     *
     * @param page   翻页对象
     * @param entity 实体对象
     * @return 实体对象列表
     */
    IPage<T> page(IPage<T> page, T entity);

    /**
     * 转化Page查询类
     * @param page
     * @param clazz
     * @return
     */
    default IPage<R> convertPageQuery(IPage<T> page, Class<R> clazz) {
        return page.convert(new Function<T, R>() {
            @Override
            public R apply(T t) {
                return (R) t.convertToDO(clazz);
            }
        });
    }

    /**
     * 转化Page结果类
     * @param page
     * @param clazz
     * @return
     */
    default IPage<T> convertPageResult(IPage<R> page, Class<T> clazz) {
        return page.convert(new Function<R, T>() {
            @Override
            public T apply(R r) {
                return (T) r.convertToDTO(clazz);
            }
        });
    }

    /**
     * 查询总记录数
     *
     * @return 数据量
     */
    default int count() {
        return count(null);
    }

    /**
     * 根据 实体对象 条件，查询总记录数
     *
     * @param entity 实体对象
     * @return 数据量
     */
    int count(T entity);


    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     * @return 是否操作成功
     */
    boolean deleteById(Long id);

    /**
     * 删除所有记录
     *
     * @return 是否操作成功
     */
    default boolean deleteAll() {
        return delete(null);
    }

    /**
     * 根据 entity 条件，删除记录
     *
     * @param entity 实体对象
     * @return 是否操作成功
     */
    boolean delete(T entity);

}
