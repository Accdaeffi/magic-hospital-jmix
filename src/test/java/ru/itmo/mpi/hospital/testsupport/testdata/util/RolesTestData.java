package ru.itmo.mpi.hospital.testsupport.testdata.util;

import io.jmix.core.UnconstrainedDataManager;
import io.jmix.security.role.assignment.RoleAssignmentRoleType;
import io.jmix.securitydata.entity.RoleAssignmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itmo.mpi.hospital.entity.God;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class RolesTestData {

    private static final List<String> godResourceRole = List.of("ui-minimal", "god-role");
    private static final List<String> godRowLevelRoles = List.of("god-row-level-role");

    Map<String, String> godRoles = Map.of("ui-minimal", RoleAssignmentRoleType.RESOURCE,
            "god-role", RoleAssignmentRoleType.RESOURCE,
            "god-row-level-role", RoleAssignmentRoleType.ROW_LEVEL);

    @Autowired
    private UnconstrainedDataManager unconstrainedDataManager;

    public List<RoleAssignmentEntity> getRoles(God god) {
        List<RoleAssignmentEntity> result = new ArrayList<>();

        for (Map.Entry<String, String> roleAssignment : godRoles.entrySet()) {
            RoleAssignmentEntity roleAssignmentEntity = unconstrainedDataManager.create(RoleAssignmentEntity.class);

            roleAssignmentEntity.setRoleCode(roleAssignment.getKey());
            roleAssignmentEntity.setUsername(god.getUsername());
            roleAssignmentEntity.setRoleType(roleAssignment.getValue());

            result.add(roleAssignmentEntity);
        }

        return result;
    }

}
