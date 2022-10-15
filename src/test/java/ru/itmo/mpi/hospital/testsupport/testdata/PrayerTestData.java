package ru.itmo.mpi.hospital.testsupport.testdata;

import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itmo.mpi.hospital.entity.Prayer;
import ru.itmo.mpi.hospital.entity.PrayerStatus;

import java.util.ArrayList;
import java.util.List;

@Component
public class PrayerTestData {

    public List<Prayer> prayers = new ArrayList<>();

    @Autowired
    DataManager dataManager;

    @Autowired
    SystemAuthenticator authenticator;

    @Autowired
    GodTestData godTestData;

    @Autowired
    DiseaseCaseTestData diseaseCaseTestData;

    private static final String[] listPrayText = {"pray1", "pray2", "pray3"};
    private static final PrayerStatus[] listPrayerStatus = {PrayerStatus.UNANSWERED, PrayerStatus.ACCEPTED, PrayerStatus.REJECTED};

    public void loadDefault() {
        diseaseCaseTestData.loadDefaults();

        authenticator.withSystem(() -> {

            Prayer prayer = dataManager.create(Prayer.class);
            prayer.setGod(godTestData.gods.get(0));
            prayer.setDiseaseCase(diseaseCaseTestData.diseaseCases.get(0));
            prayer.setPrayerStatus(listPrayerStatus[0]);
            prayer.setPrayText(listPrayText[0]);
            prayers.add(dataManager.save(prayer));

            prayer = dataManager.create(Prayer.class);
            prayer.setGod(godTestData.gods.get(0));
            prayer.setDiseaseCase(diseaseCaseTestData.diseaseCases.get(1));
            prayer.setPrayerStatus(listPrayerStatus[1]);
            prayer.setPrayText(listPrayText[1]);
            prayers.add(dataManager.save(prayer));

            prayer = dataManager.create(Prayer.class);
            prayer.setGod(godTestData.gods.get(1));
            prayer.setDiseaseCase(diseaseCaseTestData.diseaseCases.get(2));
            prayer.setPrayerStatus(listPrayerStatus[2]);
            prayer.setPrayText(listPrayText[2]);
            prayers.add(dataManager.save(prayer));

            return "done";
        });
    }

    public void cleanup() {
        authenticator.withSystem(() -> {

            prayers.forEach(object -> dataManager.remove(object));
            prayers.clear();

            return "done";
        });

        diseaseCaseTestData.cleanup();

    }

}
