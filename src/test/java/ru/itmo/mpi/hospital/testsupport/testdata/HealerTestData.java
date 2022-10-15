package ru.itmo.mpi.hospital.testsupport.testdata;

import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import io.jmix.security.role.assignment.RoleAssignmentRoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itmo.mpi.hospital.entity.Healer;
import ru.itmo.mpi.hospital.testsupport.testdata.util.RolesTestData;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class HealerTestData {

    @Autowired
    DataManager dataManager;

    @Autowired
    RolesTestData rolesTestData;

    @Autowired
    SystemAuthenticator authenticator;

    public List<Healer> healers = new ArrayList<>();

    private static final String[] listUsername = {"healerUserName1", "healerUserName2", "healerUserName3"};
    private static final String[] listName = {"healerName1", "healerName2", "healerName3"};
    private static final String[] listSurname = {"healerSurname1", "healerSurname2", "healerSurname3"};

    Map<String, String> healerRoles = Map.of("ui-minimal", RoleAssignmentRoleType.RESOURCE,
            "healer-role", RoleAssignmentRoleType.RESOURCE,
            "healer-row-level-role", RoleAssignmentRoleType.ROW_LEVEL);

    @PostConstruct
    void init() {

        for (int i = 0; i < listUsername.length; i++) {

            Healer healer = dataManager.create(Healer.class);
            healer.setUsername(listUsername[i]);
            healer.setFirstName(listName[i]);
            healer.setLastName(listSurname[i]);

            healer = dataManager.save(healer);

            healers.add(healer);

            dataManager.save(rolesTestData.getRoles(healer, healerRoles).toArray());
        }

    }

}
