package com.khahnm04.ecommerce.modules.auth.application.service.role;

import com.khahnm04.ecommerce.modules.auth.application.request.RoleRequest;
import com.khahnm04.ecommerce.modules.auth.application.response.RoleResponse;
import java.util.List;

public interface RoleService {

    RoleResponse createRole(RoleRequest request);
    List<RoleResponse> getAllRoles();
    RoleResponse updateRole(Long id, RoleRequest request);
    void deleteRole(Long id);

}
