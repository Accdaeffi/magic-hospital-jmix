package ru.itmo.mpi.hospital.testsupport.testdata;

import ru.itmo.mpi.hospital.entity.God;
import ru.itmo.mpi.hospital.entity.Healer;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Component
public class HealerTestData {

    @Autowired
    DataManager dataManager;

    public List<Healer> healers;

    private static final String[] listUsername = {"healerUserName1", "healerUserName2", "healerUserName3"};
    private static final String[] listName = {"healerName1", "healerName2", "healerName3"};
    private static final String[] listSurname = {"healerSurname1", "healerSurname2", "healerSurname3"};

    @PostConstruct
    void init() {
        healers = new ArrayList<>();

        for (int i = 0; i < listUsername.length; i++) {

            Healer healer = dataManager.create(Healer.class);
            healer.setUsername(listUsername[i]);
            healer.setFirstName(listName[i]);
            healer.setLastName(listSurname[i]);

            healers.add(healer);
        }

    }

    @PreDestroy
    void preDestroy() {
        healers.forEach(object -> dataManager.remove(object));
    }
}
