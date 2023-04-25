package spring.boot_security.service;

import spring.boot_security.models.Role;

import java.util.Set;

public interface RoleService {
    Set<Role> getAllRoles();

    Set<Role> getByName(String name);

}