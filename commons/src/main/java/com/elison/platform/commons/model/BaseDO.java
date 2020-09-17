package com.elison.platform.commons.model;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.elison.platform.commons.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.commons.model
 * @Description: 数据对象抽象类,与数据库表结构一一对应
 * @Author: elison
 * @CreateDate: 2020/9/3 15:08
 * @UpdateDate: 2020/9/3 15:08
 **/
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseDO implements Serializable {
    /**
     * 主键ID,生成策略看配置文件,目前为手动实现的雪花算法(非MybatisPlus自带的雪花)
     * `id` bigint(11) NOT NULL AUTO_INCREMENT,
     */
    private Long id;

    /**
     * 乐观锁
     * `version` int(11) DEFAULT 0 COMMENT '乐观锁版本号',
     */
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;

    /**
     * 状态
     * `status` smallint(6) DEFAULT 0 NOT NULL COMMENT '状态 0：正常 1：删除 2:禁用',
     */
    @TableLogic
    private StatusEnum status;

    /**
     * 创建时间
     * `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 创建人
     * `create_by` bigint(11) NULL COMMENT '创建人',
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    /**
     * 最终修改时间
     * `update_time` datetime NULL DEFAULT ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 修改人
     * `update_by` bigint(11) NULL COMMENT '修改人',
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    public <T extends BaseDTO> T convertToDTO(Class<T> tClass) {

        return BeanUtil.toBean(this, tClass);
    }
}
