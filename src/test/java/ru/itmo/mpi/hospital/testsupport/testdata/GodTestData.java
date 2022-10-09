package ru.itmo.mpi.hospital.testsupport.testdata;

import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itmo.mpi.hospital.entity.God;
import ru.itmo.mpi.hospital.testsupport.testdata.util.RolesTestData;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class GodTestData {

    @Autowired
    DataManager dataManager;

    @Autowired
    RolesTestData rolesTestData;

    public List<God> gods;

    private static final String[] listUsername = {"godUserName1"};
    private static final String[] listName = {"godName1"};
    private static final String[] listSurname = {"godSurname1"};

    @PostConstruct
    void init() {
        gods = new ArrayList<>();

        for (int i = 0; i < listUsername.length; i++) {

            God god = dataManager.create(God.class);

            god.setUsername(listUsername[i]);
            god.setFirstName(listName[i]);
            god.setLastName(listSurname[i]);


            god = dataManager.save(god);

            gods.add(god);
            
            dataManager.save(rolesTestData.getRoles(god).toArray());
        }

    }

}
