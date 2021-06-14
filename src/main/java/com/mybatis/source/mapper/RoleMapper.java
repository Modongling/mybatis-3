package com.mybatis.source.mapper;

import com.mybatis.source.jdbc.entity.Role;

public interface RoleMapper {
  Role getRoleById(Long id);
  int deleteRole(Long id);
  int insertRole(Role role);
  Role findRole(String name);
}
