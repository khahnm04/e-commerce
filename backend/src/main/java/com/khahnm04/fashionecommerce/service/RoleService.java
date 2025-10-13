package com.khahnm04.fashionecommerce.service;

import com.khahnm04.fashionecommerce.dto.request.RoleRequest;
import com.khahnm04.fashionecommerce.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {

    RoleResponse createRole(RoleRequest request);
    List<RoleResponse> getAllRoles();
    void deleteRole(String role);

}
