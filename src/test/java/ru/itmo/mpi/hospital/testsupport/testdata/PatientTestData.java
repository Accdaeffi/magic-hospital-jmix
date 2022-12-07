package ru.itmo.mpi.hospital.testsupport.testdata;

import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itmo.mpi.hospital.entity.Patient;
import ru.itmo.mpi.hospital.entity.PatientState;
import ru.itmo.mpi.hospital.entity.SocialStatus;

import java.util.ArrayList;
import java.util.List;

@Component
public class PatientTestData {

    private static boolean loaded = false;
    @Autowired
    DataManager dataManager;

    @Autowired
    SystemAuthenticator authenticator;

    public List<Patient> patients = new ArrayList<>();

    private static final String[] listName = {"patientName1", "patientName2", "patientName3", "deadMen1", "deadMen2"};
    private static final String[] listSurname = {"patientSurname1", "patientSurname2", "patientSurname3", "", ""};
    private static final Boolean[] listMale = {true, false, true, true, true};
    private static final Boolean[] listMage = {false, false, true, false, false};
    private static final SocialStatus[] listSocialStatus = {SocialStatus.ARISTOCRAT, SocialStatus.COMMONER, SocialStatus.SLAVE, SocialStatus.COMMONER, SocialStatus.COMMONER};
    private static final PatientState[] listPatientStates = {PatientState.SICK, PatientState.HEALTHY, PatientState.BURIED, PatientState.DISEASED, PatientState.DISEASED};

    public void loadDefault() {
        if (!loaded) {

            authenticator.withSystem(() -> {
                for (int i = 0; i < listName.length; i++) {

                    Patient patient = dataManager.create(Patient.class);
                    patient.setName(listName[i]);
                    patient.setSurname(listSurname[i]);
                    patient.setIsMale(listMale[i]);
                    patient.setIsMage(listMage[i]);
                    patient.setSocialStatus(listSocialStatus[i]);
                    patient.setPatientState(listPatientStates[i]);

                    patients.add(dataManager.save(patient));
                }

                return "done!";
            });

            loaded = true;
        }

    }

    public void cleanup() {
        if (loaded) {

            authenticator.withSystem(() -> {
                patients.forEach(object -> dataManager.remove(object));
                return "done!";
            });

            patients.clear();

            loaded = false;
        }
    }
}
