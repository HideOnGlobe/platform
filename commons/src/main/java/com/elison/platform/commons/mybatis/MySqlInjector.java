package com.elison.platform.commons.mybatis;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import com.baomidou.mybatisplus.extension.injector.methods.LogicDeleteByIdWithFill;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.commons.mybatis
 * @Description: 自定义方法统一管理
 * @Author: elison
 * @CreateDate: 2020/9/11 23:09
 * @UpdateDate: 2020/9/11 23:09
 **/
@Component
public class MySqlInjector extends DefaultSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        methodList.add(new InsertBatchSomeColumn());
        methodList.add(new LogicDeleteByIdWithFill());
        methodList.add(new LogicBatchDeleteWithFill());
        return methodList;
    }
}
