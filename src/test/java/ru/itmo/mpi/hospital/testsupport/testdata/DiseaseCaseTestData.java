package ru.itmo.mpi.hospital.testsupport.testdata;

import ru.itmo.mpi.hospital.entity.Disease;
import ru.itmo.mpi.hospital.entity.DiseaseCase;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

@Component
public class DiseaseCaseTestData {

    public List<DiseaseCase> diseaseCases;

    @Autowired
    DataManager dataManager;

    @Autowired
    DiseaseTestData diseaseTestData;

    @Autowired
    HealerTestData healerTestData;

    @Autowired
    PatientTestData patientTestData;

    private static final String[] listComplaints = {"complaints1", "complaints2", "complaints3"};
    private static final String[] listActions = {"actions1", "actions2", "actions3"};

    @PostConstruct
    void init() {
        diseaseCases = new ArrayList<>();

        DiseaseCase diseaseCase = dataManager.create(DiseaseCase.class);
        diseaseCase.setDisease(diseaseTestData.diseases.get(0));
        diseaseCase.setActions(listActions[0]);
        diseaseCase.setPatientComplaints(listComplaints[0]);
        diseaseCase.setPatient(patientTestData.patients.get(0));
        diseaseCase.setHealer(healerTestData.healers.get(0));
        diseaseCases.add(diseaseCase);

        diseaseCase = dataManager.create(DiseaseCase.class);
        diseaseCase.setDisease(diseaseTestData.diseases.get(1));
        diseaseCase.setActions(listActions[1]);
        diseaseCase.setPatientComplaints(listComplaints[1]);
        diseaseCase.setPatient(patientTestData.patients.get(1));
        diseaseCase.setHealer(healerTestData.healers.get(0));
        diseaseCases.add(diseaseCase);

        diseaseCase = dataManager.create(DiseaseCase.class);
        diseaseCase.setDisease(diseaseTestData.diseases.get(1));
        diseaseCase.setActions(listActions[2]);
        diseaseCase.setPatientComplaints(listComplaints[2]);
        diseaseCase.setPatient(patientTestData.patients.get(2));
        diseaseCase.setHealer(healerTestData.healers.get(1));
        diseaseCases.add(diseaseCase);
    }

    @PreDestroy
    void preDestroy() {
        diseaseCases.forEach(object -> dataManager.remove(object));
    }

}