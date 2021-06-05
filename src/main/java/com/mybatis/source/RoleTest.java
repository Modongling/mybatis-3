package com.mybatis.source;

import com.mybatis.source.jdbc.entity.Role;
import com.mybatis.source.mapper.RoleMapper;
import com.mybatis.source.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

public class RoleTest {
  public static void main(String[] args) {
    SqlSession sqlSession = null;
    try {
      sqlSession = MyBatisUtils.getSqlSessionFactory().openSession();
      RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
      Role role = roleMapper.getRoleById(1L);
      System.out.println(role);
    } finally {
      sqlSession.close();
    }
  }
}
