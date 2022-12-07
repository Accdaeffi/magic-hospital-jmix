package ru.itmo.mpi.hospital.testsupport.testdata;

import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import io.jmix.security.role.assignment.RoleAssignmentRoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itmo.mpi.hospital.entity.Administrator;
import ru.itmo.mpi.hospital.testsupport.testdata.util.RolesTestData;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class AdministratorTestData {

    private static boolean loaded = false;

    @Autowired
    DataManager dataManager;

    @Autowired
    RolesTestData rolesTestData;

    @Autowired
    SystemAuthenticator authenticator;

    public List<Administrator> regs = new ArrayList<>();

    private static final String[] listUsername = {"regUserName1", "regUserName2", "regUserName3"};
    private static final String[] listName = {"regName1", "regName2", "regName3"};
    private static final String[] listSurname = {"regSurname1", "regSurname2", "regSurname3"};

    Map<String, String> regRoles = Map.of("ui-minimal", RoleAssignmentRoleType.RESOURCE,
            "administrator-role", RoleAssignmentRoleType.RESOURCE);

    @PostConstruct
    void init() {

        if (!loaded) {

            for (int i = 0; i < listUsername.length; i++) {

                Administrator reg = dataManager.create(Administrator.class);
                reg.setUsername(listUsername[i]);
                reg.setFirstName(listName[i]);
                reg.setLastName(listSurname[i]);

                reg = dataManager.save(reg);

                regs.add(reg);

                dataManager.save(rolesTestData.getRoles(reg, regRoles).toArray());
            }

            loaded = true;
        }


    }

}
