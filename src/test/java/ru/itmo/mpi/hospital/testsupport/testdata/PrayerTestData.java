package ru.itmo.mpi.hospital.testsupport.testdata;

import ru.itmo.mpi.hospital.entity.Prayer;
import ru.itmo.mpi.hospital.entity.PrayerStatus;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Component
public class PrayerTestData {

    public List<Prayer> prayers;

    @Autowired
    DataManager dataManager;

    @Autowired
    GodTestData godTestData;

    @Autowired
    DiseaseCaseTestData diseaseCaseTestData;

    private static final String[] listPrayText = {"pray1", "pray2", "pray3"};
    private static final PrayerStatus[] listPrayerStatus = {PrayerStatus.UNANSWERED, PrayerStatus.UNANSWERED, PrayerStatus.REJECTED};

    @PostConstruct
    void init() {
        prayers = new ArrayList<>();

        Prayer prayer = dataManager.create(Prayer.class);
        prayer.setGod(godTestData.gods.get(0));
        prayer.setDiseaseCase(diseaseCaseTestData.diseaseCases.get(0));
        prayer.setPrayerStatus(listPrayerStatus[0]);
        prayer.setPrayText(listPrayText[0]);
        prayers.add(prayer);

        prayer = dataManager.create(Prayer.class);
        prayer.setGod(godTestData.gods.get(1));
        prayer.setDiseaseCase(diseaseCaseTestData.diseaseCases.get(1));
        prayer.setPrayerStatus(listPrayerStatus[1]);
        prayer.setPrayText(listPrayText[1]);
        prayers.add(prayer);

        prayer = dataManager.create(Prayer.class);
        prayer.setGod(godTestData.gods.get(0));
        prayer.setDiseaseCase(diseaseCaseTestData.diseaseCases.get(2));
        prayer.setPrayerStatus(listPrayerStatus[2]);
        prayer.setPrayText(listPrayText[2]);
        prayers.add(prayer);
    }

    @PreDestroy
    void preDestroy() {
        prayers.forEach(object -> dataManager.remove(object));
    }

}
