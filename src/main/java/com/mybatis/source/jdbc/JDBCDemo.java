package com.mybatis.source.jdbc;

import com.mybatis.source.jdbc.entity.Role;

import java.sql.*;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * JDBC例子
 */
public class JDBCDemo {
  private static Connection getConnection() {
    Connection connection = null;
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      String url = "jdbc:mysql://localhost:3306/mybatis3";
      String user = "root";
      String password = "root";
      connection = DriverManager.getConnection(url, user, password);
    } catch (ClassNotFoundException | SQLException e) {
      Logger.getLogger(JDBCDemo.class.getName()).log(Level.SEVERE, null, e);
      return null;
    }
    return connection;
  }

  public static Role getRole(Long id) {
    //获取连接
    Connection connection = getConnection();
    //sql处理器
    PreparedStatement ps = null;
    //结果集
    ResultSet rs = null;
    try {
      ps = connection.prepareStatement("select id, name, note from t_role where id = ?");
      ps.setLong(1, id);
      rs = ps.executeQuery();
      while (rs.next()) {
        Long roleId = rs.getLong("id");
        String name = rs.getString("name");
        String note = rs.getString("note");
        Role role = new Role();
        role.setId(roleId);
        role.setName(name);
        role.setNote(note);
        return role;
      }
    } catch (SQLException e) {
      Logger.getLogger(JDBCDemo.class.getName()).log(Level.SEVERE, null, e);
    } finally {
      close(rs, ps, connection);
    }
    return null;
  }

  private static void close(ResultSet rs, PreparedStatement ps, Connection connection) {
    try {
      if (Objects.nonNull(rs) && ! rs.isClosed()) {
        rs.close();
      }
    } catch (SQLException e) {
      Logger.getLogger(JDBCDemo.class.getName()).log(Level.SEVERE, null, e);
    }

    try {
      if (Objects.nonNull(ps) && ! ps.isClosed()) {
        ps.close();
      }
    } catch (SQLException e) {
      Logger.getLogger(JDBCDemo.class.getName()).log(Level.SEVERE, null, e);
    }

    try {
      if (Objects.nonNull(connection) && ! connection.isClosed()) {
        connection.close();
      }
    } catch (SQLException e) {
      Logger.getLogger(JDBCDemo.class.getName()).log(Level.SEVERE, null, e);
    }
  }

  public static void main(String[] args) {
    Role role = JDBCDemo.getRole(1L);
    System.out.println(role);
  }
}
