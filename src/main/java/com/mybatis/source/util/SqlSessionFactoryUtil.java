package com.mybatis.source.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlSessionFactoryUtil {
  private static SqlSessionFactory sqlSessionFactory = null;
  private static final Class CLASS_LOCK = SqlSessionFactoryUtil.class;

  private SqlSessionFactoryUtil() {}

  private static SqlSessionFactory getSqlSessionFactory() {
    InputStream inputStream =null;
    try {
      inputStream = Resources.getResourceAsStream("mybatis_config.xml");
    } catch (IOException e) {
      Logger.getLogger(SqlSessionFactoryUtil.class.getName()).log(Level.SEVERE, null, e);
    }
    synchronized (CLASS_LOCK) {
      if (sqlSessionFactory == null) {
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
      }
    }
    return sqlSessionFactory;
  }

  public static SqlSession openSqlSession() {
    if (sqlSessionFactory == null) {
      getSqlSessionFactory();
    }
    return sqlSessionFactory.openSession();
  }
}
