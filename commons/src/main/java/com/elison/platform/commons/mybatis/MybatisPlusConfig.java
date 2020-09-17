package com.elison.platform.commons.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.commons.mybatis
 * @Description: 插件配置
 * @Author: elison
 * @CreateDate: 2020/9/4 12:45
 * @UpdateDate: 2020/9/4 12:45
 **/
@Configuration
@MapperScan("com.elison.platform.*.mapper")
public class MybatisPlusConfig {

    /**
     * 自定义注入语句
     * @return 插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        return new MybatisPlusInterceptor();
    }

    /**
     * 分页插件
     * @return 插件
     */
    @Bean
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        // 数据库类型
        paginationInnerInterceptor.setDbType(DbType.MYSQL);
        // 溢出总页数后是否处理
        paginationInnerInterceptor.setOverflow(true);
        // 单页数据上限
        paginationInnerInterceptor.setMaxLimit(100L);
        return paginationInnerInterceptor;
    }

    /**
     * 乐观锁插件
     * @return 插件
     */
    @Bean
    public OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor()  {
        return new OptimisticLockerInnerInterceptor();
    }

    /**
     * 防止全表更新与删除插件
     * @return 插件
     */
    @Bean
    public BlockAttackInnerInterceptor blockAttackInnerInterceptor()  {
        return new BlockAttackInnerInterceptor();
    }
}
