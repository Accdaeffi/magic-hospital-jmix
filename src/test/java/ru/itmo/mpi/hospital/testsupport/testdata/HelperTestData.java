package ru.itmo.mpi.hospital.testsupport.testdata;

import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import io.jmix.security.role.assignment.RoleAssignmentRoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itmo.mpi.hospital.entity.Helper;
import ru.itmo.mpi.hospital.testsupport.testdata.util.RolesTestData;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class HelperTestData {

    private static boolean loaded = false;

    @Autowired
    DataManager dataManager;

    @Autowired
    RolesTestData rolesTestData;

    @Autowired
    SystemAuthenticator authenticator;

    public List<Helper> helpers = new ArrayList<>();

    private static final String[] listUsername = {"helperUserName", "helperUserName2", "helperUserName3"};
    private static final String[] listName = {"helperName1", "helperName2", "helperName3"};
    private static final String[] listSurname = {"helperSurname1", "helperSurname2", "helperSurname3"};

    Map<String, String> helperRoles = Map.of("ui-minimal", RoleAssignmentRoleType.RESOURCE,
            "helper-role", RoleAssignmentRoleType.RESOURCE,
            "helper-row-level-role", RoleAssignmentRoleType.ROW_LEVEL);

    @PostConstruct
    void init() {

        if (!loaded) {

            for (int i = 0; i < listUsername.length; i++) {

                Helper helper = dataManager.create(Helper.class);
                helper.setUsername(listUsername[i]);
                helper.setFirstName(listName[i]);
                helper.setLastName(listSurname[i]);

                helper = dataManager.save(helper);

                helpers.add(helper);

                dataManager.save(rolesTestData.getRoles(helper, helperRoles).toArray());
            }

            loaded = true;
        }
    }

}
