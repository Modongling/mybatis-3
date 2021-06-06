package com.mybatis.source.util;

import com.mybatis.source.jdbc.entity.Role;
import com.mybatis.source.mapper.RoleMapper;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisUtils {
  private static SqlSessionFactory sqlSessionFactory = null;
  public static SqlSessionFactory getSqlSessionFactory() {
    InputStream inputStream = null;
    try {
      sqlSessionFactory = new SqlSessionFactoryBuilder()
        .build(Resources.getResourceAsStream("mybatis_config.xml"));
      return sqlSessionFactory;
    } catch (IOException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
    }
    return sqlSessionFactory;
  }

  public static SqlSessionFactory getSqlSessionFactoryWithHardCode() {
    //构建数据库连接池
    PooledDataSource dataSource = new PooledDataSource();
    dataSource.setDriver("com.mysql.cj.jdbc.Driver");
    dataSource.setUrl("jdbc:mysql://localhost:3306/mybatis3");
    dataSource.setUsername("root");
    dataSource.setPassword("root");
    //构建事务对象
    TransactionFactory transactionFactory = new JdbcTransactionFactory();
    //构建环境对象
    Environment environment = new Environment("development", transactionFactory, dataSource);
    //构建Configuration对象
    Configuration configuration = new Configuration(environment);
    //注册别名上下文
    configuration.getTypeAliasRegistry().registerAlias("role", Role.class);
    //注册映射器
    configuration.addMapper(RoleMapper.class);
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    return sqlSessionFactory;
  }
}
