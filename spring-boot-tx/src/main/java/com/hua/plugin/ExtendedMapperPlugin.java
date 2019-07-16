package com.hua.plugin;

import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.ibatis.type.JdbcType;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import com.google.common.collect.Sets;

import tk.mybatis.mapper.annotation.ColumnType;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.IdentityDialect;
import tk.mybatis.mapper.generator.MapperPlugin;

/**
 * 
 * @type ExtendedMapperPlugin 
 * @description 扩展tk Mapper插件
 * @author qianye.zheng
 */
public class ExtendedMapperPlugin extends MapperPlugin 
{

    /* 数据库方言 */
    private IdentityDialect identityDialect = null;

    /*
     * Java中关键词字段属性过滤，如果数据库字段中含有Java关键词的字段属性名称，
     * 需要进行特殊处理
     */
    private static final Set<String> JAVA_KEYWORDS = Collections.unmodifiableSet(Sets.newHashSet(
            "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else", "enum", "extends", "final", "finally", "float", "for",
            "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "package",
            "private", "protected", "public", "return", "strictfp", "short", "static", "super", "switch", "synchronized",
            "this", "throw", "throws", "transient", "try", "void", "volatile", "while"));

    /**
     * 
     * @description 初始化
     * @param introspectedTable
     * @author qianye.zheng
     */
    @Override
    public void initialized(final IntrospectedTable introspectedTable) {
    	// 遍历表的字段
        introspectedTable.getAllColumns().forEach(introspectedColumn -> {
            String javaProperty = introspectedColumn.getJavaProperty();
            // 属性名和Java关键字相同时，以0结尾重命名
            if (JAVA_KEYWORDS.contains(javaProperty)) {
            	// 以 0 结尾
                javaProperty += "0";
                // 设置属性名
                introspectedColumn.setJavaProperty(javaProperty);
            }
        });
    }

    /**
     * 
     * @description 设置属性
     * @param properties
     * @author qianye.zheng
     */
    @Override
    public void setProperties(final Properties properties) {
        super.setProperties(properties);
        final String database = getProperty("databaseDialect");
        if (null == database) {
            this.identityDialect = IdentityDialect.NULL;
        } else {
            this.identityDialect = IdentityDialect.getDatabaseDialect(database);
        }
    }

    /**
     * 
     * @description 生成基本形状
     * @param topLevelClass 顶层类
     * @param introspectedTable 表
     * @return
     * @author qianye.zheng
     */
    @Override
    public boolean modelBaseRecordClassGenerated(final TopLevelClass topLevelClass, 
    		final IntrospectedTable introspectedTable) {
    	// 导入
        topLevelClass.addImportedType("com.hua.entity.DynamicTableEntity");
        // 父类
        topLevelClass.setSuperClass("DynamicTableEntity");
        // lombok 支持
       // topLevelClass.addImportedType("lombok.Data");
        //topLevelClass.addAnnotation("@Data");       
        topLevelClass.addImportedType("lombok.EqualsAndHashCode");
        topLevelClass.addAnnotation("@EqualsAndHashCode(callSuper = true)");
        
        return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }

    /**
     * 
     * @description 生成主键
     * @param topLevelClass 顶层类
     * @param introspectedTable 表
     * @return
     * @author qianye.zheng
     */
    @Override
    public boolean modelPrimaryKeyClassGenerated(final TopLevelClass topLevelClass, 
    		final IntrospectedTable introspectedTable) {
        
        return super.modelPrimaryKeyClassGenerated(topLevelClass, introspectedTable);
    }

    /**
     * 
     * @description 生成Blob类
     * @param topLevelClass 顶层类
     * @param introspectedTable 表
     * @return
     * @author qianye.zheng
     */
    @Override
    public boolean modelRecordWithBLOBsClassGenerated(final TopLevelClass topLevelClass, 
    		final IntrospectedTable introspectedTable) {
        
        return super.modelRecordWithBLOBsClassGenerated(topLevelClass, introspectedTable);
    }

    /**
     * 
     * @description 生成属性
     * @param field 属性
     * @param topLevelClass 顶层类
     * @param introspectedColumn 列
     * @param introspectedTable 表
     * @param modelClassType 实体类型
     * @return
     * @author qianye.zheng
     */
    @Override
    public boolean modelFieldGenerated(final Field field, final TopLevelClass topLevelClass, 
    		final IntrospectedColumn introspectedColumn,
    		final  IntrospectedTable introspectedTable, final ModelClassType modelClassType) 
    {
    	// import tk.mybatis.mapper.annotation.ColumnType;
        topLevelClass.addImportedType(ColumnType.class.getName());
        // import org.apache.ibatis.type.JdbcType;
        topLevelClass.addImportedType(JdbcType.class.getName());
        
        final List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        if (introspectedColumn.isAutoIncrement()
                || introspectedColumn.isIdentity()
                || (primaryKeyColumns.size() == 1 && primaryKeyColumns.get(0) == introspectedColumn)) 
        {
            if (identityDialect == null || identityDialect == IdentityDialect.DEFAULT 
            		|| identityDialect == IdentityDialect.NULL) {
                field.addAnnotation("@GeneratedValue(strategy = GenerationType.IDENTITY)");
            } else {
            	// import tk.mybatis.mapper.annotation.KeySql;
                topLevelClass.addImportedType(KeySql.class.getName());
                // import tk.mybatis.mapper.code.IdentityDialect;
                topLevelClass.addImportedType(IdentityDialect.class.getName());

                field.addAnnotation("@KeySql(dialect = IdentityDialect." + identityDialect.name() + ")");
            }
        }
        
        JdbcType jdbcType = JdbcType.forCode(introspectedColumn.getJdbcType());
        if (null == jdbcType) {
        	// 默认为 varchar
            jdbcType = JdbcType.VARCHAR;
        }
        field.addAnnotation("@ColumnType(jdbcType = JdbcType." + jdbcType.name() + ")");
        
        return true;
    }
    
    /**
     * @description 生成 setter 方法
     * @param method
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return
     * @author qianye.zheng
     */
    @Override
    public boolean modelSetterMethodGenerated(Method method,
    		TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
    		IntrospectedTable introspectedTable, ModelClassType modelClassType)
    {

    	return true;
    }
    
    /**
     * @description
     * @param method
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return
     * @author qianye.zheng
     */
    @Override
    public boolean modelGetterMethodGenerated(Method method,
    		TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
    		IntrospectedTable introspectedTable, ModelClassType modelClassType)
    {
    
    	return true;
    }

}
