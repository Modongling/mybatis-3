package com.mybatis.source.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

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
}
