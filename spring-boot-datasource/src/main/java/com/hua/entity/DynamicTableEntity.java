package com.hua.entity;

import lombok.Setter;
import tk.mybatis.mapper.entity.IDynamicTableName;

import javax.persistence.Transient;

/**
 * 提供动态表名支持
 */
public abstract class DynamicTableEntity implements IDynamicTableName {

    @Transient
    @Setter
    private String tableName;

    @Override
    public String getDynamicTableName() {
        return tableName;
    }
}
