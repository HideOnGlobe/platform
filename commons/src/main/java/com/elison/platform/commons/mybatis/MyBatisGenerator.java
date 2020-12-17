package com.elison.platform.commons.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.commons.mybatis
 * @Description: 代码生成器
 * @Author: elison
 * @CreateDate: 2020/9/4 12:45
 * @UpdateDate: 2020/9/4 12:45
 **/
public class MyBatisGenerator {

    // 当前文件根目录的绝对路径(即.../.../.../src/main/java)
    private static final String dir = "";
    // 当前文件对于根目录的相对路径(即com/xxx/xxx/commons/mybatis/generator)
    private static final String filePath = "";

    // 需要生成的表 用,隔开
    private static final String tableNameList = "";

    public static void main(String[] args) {

        // 创建代码生成器
        AutoGenerator mpg = new AutoGenerator();
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOpen(false);
        gc.setOutputDir(dir);
        gc.setSwagger2(true);
        gc.setDateType(DateType.ONLY_DATE);
        // 是否覆盖已有文件
        gc.setFileOverride(true);
        // XML是否需要BaseResultMap
        gc.setBaseResultMap(true);
        // XML是否显示字段
        gc.setBaseColumnList(true);
        gc.setControllerName("%sController");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setAuthor("elison");
        gc.setIdType(IdType.INPUT);
        mpg.setGlobalConfig(gc);
        //数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/xxxxx?useUnicode=true&characterEncoding=UTF-8&useSSL=true");
        dsc.setUsername("root");
        dsc.setPassword("12345678");
        mpg.setDataSource(dsc);
        //策略配置
        StrategyConfig sc = new StrategyConfig();
        if (tableNameList.length() > 0) {
            sc.setInclude(tableNameList.split(","));
        }
        sc.setEntityBuilderModel(true);
        sc.setRestControllerStyle(true);
        sc.setEntityLombokModel(true);
        // 表名生成策略
        sc.setNaming(NamingStrategy.underline_to_camel);
        // 自定义实体父类
        sc.setSuperEntityClass("BaseDO");
        sc.setSuperEntityColumns("id");
        // 自定义 mapper 父类
        sc.setSuperMapperClass("MyMapper");
        // 自定义 service 父类
        sc.setSuperServiceClass("BaseDataService");
        // 自定义 service 实现类父类
        // sc.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
        // 自定义 controller 父类
        // sc.setSuperControllerClass("com.baomidou.demo.TestController");
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        // sc.setEntityColumnConstant(true);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        // sc.setEntityBuilderModel(true);
        mpg.setStrategy(sc);
        //包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(filePath);
        pc.setEntity("entity");
        pc.setController("controller");
        pc.setService("service");
        pc.setServiceImpl("serviceImpl");
        pc.setMapper("mapper");
        pc.setXml("mapper");
        mpg.setPackageInfo(pc);

        mpg.execute();
    }
}
