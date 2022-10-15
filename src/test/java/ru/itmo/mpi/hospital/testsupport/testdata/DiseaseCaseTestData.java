package ru.itmo.mpi.hospital.testsupport.testdata;

import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itmo.mpi.hospital.entity.DiseaseCase;
import ru.itmo.mpi.hospital.entity.DiseaseCaseState;

import java.util.ArrayList;
import java.util.List;

@Component
public class DiseaseCaseTestData {

    public List<DiseaseCase> diseaseCases = new ArrayList<>();

    @Autowired
    DataManager dataManager;

    @Autowired
    DiseaseTestData diseaseTestData;

    @Autowired
    HealerTestData healerTestData;

    @Autowired
    PatientTestData patientTestData;

    @Autowired
    SystemAuthenticator systemAuthenticator;

    private static final String[] listComplaints = {"complaints1", "complaints2", "complaints3"};
    private static final String[] listActions = {"actions1", "actions2", "actions3"};
    private static final DiseaseCaseState[] listDiseaseCaseStates = {DiseaseCaseState.AT_WORK, DiseaseCaseState.RECOVERY, DiseaseCaseState.DEATH};

    public void loadDefaults() {
        patientTestData.loadDefault();

        systemAuthenticator.withSystem(() -> {

            DiseaseCase diseaseCase = dataManager.create(DiseaseCase.class);
            diseaseCase.setDisease(diseaseTestData.diseases.get(0));
            diseaseCase.setActions(listActions[0]);
            diseaseCase.setPatientComplaints(listComplaints[0]);
            diseaseCase.setPatient(patientTestData.patients.get(0));
            diseaseCase.setHealer(healerTestData.healers.get(0));
            diseaseCase.setDiseaseCaseState(listDiseaseCaseStates[0]);
            diseaseCases.add(dataManager.save(diseaseCase));

            diseaseCase = dataManager.create(DiseaseCase.class);
            diseaseCase.setDisease(diseaseTestData.diseases.get(1));
            diseaseCase.setActions(listActions[1]);
            diseaseCase.setPatientComplaints(listComplaints[1]);
            diseaseCase.setPatient(patientTestData.patients.get(1));
            diseaseCase.setHealer(healerTestData.healers.get(0));
            diseaseCase.setDiseaseCaseState(listDiseaseCaseStates[1]);
            diseaseCases.add(dataManager.save(diseaseCase));

            diseaseCase = dataManager.create(DiseaseCase.class);
            diseaseCase.setDisease(diseaseTestData.diseases.get(1));
            diseaseCase.setActions(listActions[2]);
            diseaseCase.setPatientComplaints(listComplaints[2]);
            diseaseCase.setPatient(patientTestData.patients.get(2));
            diseaseCase.setHealer(healerTestData.healers.get(1));
            diseaseCase.setDiseaseCaseState(listDiseaseCaseStates[2]);
            diseaseCases.add(dataManager.save(diseaseCase));

            return "done";
        });

    }

    public void cleanup() {
        systemAuthenticator.withSystem(() -> {
            diseaseCases.forEach(object -> dataManager.remove(object));
            return "Done";
        });
        diseaseCases.clear();

        patientTestData.cleanup();
    }

}
