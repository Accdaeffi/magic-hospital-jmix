package ru.itmo.mpi.hospital.testsupport.testdata;

import ru.itmo.mpi.hospital.entity.God;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Component
public class GodTestData {

    @Autowired
    DataManager dataManager;

    public List<God> gods;

    private static final String[] listUsername = {"godUserName1", "godUserName2", "godUserName3"};
    private static final String[] listName = {"godName1", "godName2", "godName3"};
    private static final String[] listSurname = {"godSurname1", "godSurname2", "godSurname3"};

    @PostConstruct
    void init() {
        gods = new ArrayList<>();

        for (int i = 0; i < listUsername.length; i++) {

            God god = dataManager.create(God.class);
            god.setUsername(listUsername[i]);
            god.setFirstName(listName[i]);
            god.setLastName(listSurname[i]);

            gods.add(god);
        }

    }

    @PreDestroy
    void preDestroy() {
        gods.forEach(object -> dataManager.remove(object));
    }
}
