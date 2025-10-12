package com.khahnm04.service;

import com.khahnm04.dto.request.RoleRequest;
import com.khahnm04.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {

    RoleResponse createRole(RoleRequest request);
    List<RoleResponse> getAllRoles();
    void deleteRole(String role);

}
