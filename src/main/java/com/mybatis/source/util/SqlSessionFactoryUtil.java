package com.mybatis.source.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;



public class SqlSessionFactoryUtil {
  private static SqlSessionFactory sqlSessionFactory = null;
  private static final Class CLASS_LOCK = SqlSessionFactoryUtil.class;

  private SqlSessionFactoryUtil() {}

  private static SqlSessionFactory getSqlSessionFactory() {
    //配置文件输入流
    InputStream inputStream = null;
    Reader reader = null;
    //数据源
    InputStream prop = null;
    Reader propReader = null;
    Properties properties = null;

    try {
      inputStream = Resources.getResourceAsStream("mybatis_config.xml");
      //加载数据源文件
      reader = new InputStreamReader(inputStream);
      prop = Resources.getResourceAsStream("datasource.properties");
      propReader = new InputStreamReader(prop);
      properties = new Properties();
      properties.load(propReader);
      properties.setProperty("username", properties.getProperty("username"));
      properties.setProperty("password", properties.getProperty("password"));
    } catch (IOException e) {
      Logger.getLogger(SqlSessionFactoryUtil.class.getName()).log(Level.SEVERE, null, e);
    }
    synchronized (CLASS_LOCK) {
      if (sqlSessionFactory == null) {
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, properties);
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
