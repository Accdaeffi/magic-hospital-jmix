package ru.itmo.mpi.hospital.testsupport.testdata;

import org.springframework.transaction.annotation.Transactional;
import ru.itmo.mpi.hospital.entity.Disease;
import ru.itmo.mpi.hospital.entity.God;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class GodTestData implements TestDataProvisioning<God, God.GodBuilder> {

    @Autowired
    DataManager dataManager;

    public List<God> gods;

    private static final String[] listUsername = {"godUserName1", "godUserName2", "godUserName3"};
    private static final String[] listName = {"godName1", "godName2", "godName3"};
    private static final String[] listSurname = {"godSurname1", "godSurname2", "godSurname3"};

    public void loadData() {
        gods = new ArrayList<>();

        for (int i = 0; i < listUsername.length; i++) {

            //God god = dataManager.create(God.class);
            God god = new God();
            god.setUsername(listUsername[i]);
            god.setFirstName(listName[i]);
            god.setLastName(listSurname[i]);

            gods.add(dataManager.save(god));
        }

    }

    public void unloadData() {
        gods.forEach(object -> dataManager.remove(object));
        gods.clear();
    }

    @Override
    public God.GodBuilder defaultData() {
        return God.builder().active(true);
    }

    @Override
    public God save(God dto) {
        return null;
    }

    @Override
    public God saveDefault() {
        return null;
    }

    @Override
    public God create(God dto) {
        return null;
    }

    @Override
    public God createDefault() {
        return null;
    }
}
