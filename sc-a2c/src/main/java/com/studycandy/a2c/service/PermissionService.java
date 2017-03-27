package com.studycandy.a2c.service;

import com.studycandy.a2c.model.Permission;

import java.util.List;

/**
 * Coding with Intellij IDEA
 * Author: Chenls
 * Time: 2017/3/27
 */
public interface PermissionService {
    Integer addPermission(Permission entity);

    Integer deleteById(Long id);

    Integer updatePermission(Permission entity);

    Permission getPermissionById(Long id);

    List<Permission> getListByUserId(Long userid);
}
