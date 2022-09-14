package ru.itmo.mpi.hospital.testsupport.testdata;

import ru.itmo.mpi.hospital.entity.Disease;
import ru.itmo.mpi.hospital.entity.Patient;
import ru.itmo.mpi.hospital.entity.SocialStatus;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Component
public class PatientTestData {

    @Autowired
    DataManager dataManager;

    public List<Patient> patients;

    private static final String[] listName = {"patientName1", "patientName2", "patientName3"};
    private static final String[] listSurname = {"patientSurname1", "patientSurname2", "patientSurname3"};
    private static final Boolean[] listMale = {true, false, true};
    private static final Boolean[] listMage = {false, false, true};
    private static final SocialStatus[] listSocialStatus = {SocialStatus.ARISTOCRAT, SocialStatus.COMMONER, SocialStatus.SLAVE};

    @PostConstruct
    void init() {
        patients = new ArrayList<>();

        for (int i = 0; i<listName.length; i++) {

            Patient patient = dataManager.create(Patient.class);
            patient.setName(listName[i]);
            patient.setSurname(listSurname[i]);
            patient.setIsMale(listMale[i]);
            patient.setIsMage(listMage[i]);
            patient.setSocialStatus(listSocialStatus[i]);

            patients.add(patient);
        }

    }

    @PreDestroy
    void preDestroy() {
        patients.forEach(object -> dataManager.remove(object));
    }
}
