package ru.itmo.mpi.hospital.testsupport.testdata;

import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import io.jmix.security.role.assignment.RoleAssignmentRoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itmo.mpi.hospital.entity.God;
import ru.itmo.mpi.hospital.testsupport.testdata.util.RolesTestData;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class GodTestData {

    private static boolean loaded = false;

    @Autowired
    DataManager dataManager;

    @Autowired
    RolesTestData rolesTestData;

    @Autowired
    SystemAuthenticator authenticator;

    public List<God> gods = new ArrayList<>();

    private static final String[] listUsername = {"godUserName1", "godUserName2"};
    private static final String[] listName = {"godName1", "godName2"};
    private static final String[] listSurname = {"godSurname1", "godSurname2"};

    Map<String, String> godRoles = Map.of("ui-minimal", RoleAssignmentRoleType.RESOURCE,
            "god-role", RoleAssignmentRoleType.RESOURCE,
            "god-row-level-role", RoleAssignmentRoleType.ROW_LEVEL);

    @PostConstruct
    void init() {

        if (!loaded) {

            gods = new ArrayList<>();

            for (int i = 0; i < listUsername.length; i++) {

                God god = dataManager.create(God.class);

                god.setUsername(listUsername[i]);
                god.setFirstName(listName[i]);
                god.setLastName(listSurname[i]);

                god = dataManager.save(god);

                gods.add(god);

                dataManager.save(rolesTestData.getRoles(god, godRoles).toArray());
            }

            loaded = true;
        }

    }


}
