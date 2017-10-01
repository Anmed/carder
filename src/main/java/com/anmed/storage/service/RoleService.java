package com.anmed.storage.service;

import com.anmed.storage.model.Role;


public interface RoleService extends GeneralCRUDservice<Role> {
    Role getByName(String roleName);
}
