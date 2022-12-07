package ru.itmo.mpi.hospital.testsupport.testdata.util;

import io.jmix.core.UnconstrainedDataManager;
import io.jmix.securitydata.entity.RoleAssignmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itmo.mpi.hospital.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class RolesTestData {

    @Autowired
    private UnconstrainedDataManager unconstrainedDataManager;

    public List<RoleAssignmentEntity> getRoles(User user, Map<String, String> roles) {
        List<RoleAssignmentEntity> result = new ArrayList<>();

        for (Map.Entry<String, String> roleAssignment : roles.entrySet()) {
            RoleAssignmentEntity roleAssignmentEntity = unconstrainedDataManager.create(RoleAssignmentEntity.class);

            roleAssignmentEntity.setRoleCode(roleAssignment.getKey());
            roleAssignmentEntity.setUsername(user.getUsername());
            roleAssignmentEntity.setRoleType(roleAssignment.getValue());

            result.add(roleAssignmentEntity);
        }

        return result;
    }

}
