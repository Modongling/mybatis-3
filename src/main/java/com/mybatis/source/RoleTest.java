package com.mybatis.source;

import com.mybatis.source.jdbc.entity.Role;
import com.mybatis.source.mapper.RoleMapper;
import com.mybatis.source.util.MyBatisUtils;
import com.mybatis.source.util.SqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;

/**
 * SqlSessionFactoryBuilder --- SqlSessionFactory --- sqlSession --- SQLMapper --- Result
 */
public class RoleTest {
  public static void main(String[] args) {
    //通过命名空间查找对应的SQL/或者iBatis通过methodName+sqlId找到SQL
    //执行对应的事务操作
    SqlSession sqlSession = null;
    try {
      //sqlSession = MyBatisUtils.getSqlSessionFactory().openSession();
      //sqlSession = MyBatisUtils.getSqlSessionFactoryWithHardCode().openSession();
      sqlSession = SqlSessionFactoryUtil.openSqlSession();
      RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
      Role newRole = new Role();
      newRole.setName("role2");
      newRole.setNote("test");
      roleMapper.insertRole(newRole);
      sqlSession.commit();
      Role role = roleMapper.getRoleById(1L);
      System.out.println(role);
      Role r = roleMapper.findRole("r");
      System.out.println(r);
    } catch (Exception e) {
      System.err.println(e.getMessage());
      if (sqlSession != null) {
        sqlSession.rollback();
      }
    } finally {
      if (sqlSession != null) {
        sqlSession.close();
      }
    }
  }
}
