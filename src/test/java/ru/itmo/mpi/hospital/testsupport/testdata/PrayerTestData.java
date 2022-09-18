package ru.itmo.mpi.hospital.testsupport.testdata;

import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itmo.mpi.hospital.entity.Prayer;
import ru.itmo.mpi.hospital.entity.PrayerStatus;

import javax.annotation.PostConstruct;
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

    private static final String[] listPrayText = {"pray1"};
    private static final PrayerStatus[] listPrayerStatus = {PrayerStatus.UNANSWERED, PrayerStatus.UNANSWERED, PrayerStatus.REJECTED};

    @PostConstruct
    void init() {
        prayers = new ArrayList<>();

        Prayer prayer = dataManager.create(Prayer.class);
        prayer.setGod(godTestData.gods.get(0));
        prayer.setDiseaseCase(diseaseCaseTestData.diseaseCases.get(0));
        prayer.setPrayerStatus(listPrayerStatus[0]);
        prayer.setPrayText(listPrayText[0]);
        prayers.add(dataManager.save(prayer));
    }

}
