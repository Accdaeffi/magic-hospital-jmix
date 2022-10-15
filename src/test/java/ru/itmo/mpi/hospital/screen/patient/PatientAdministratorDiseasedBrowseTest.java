package ru.itmo.mpi.hospital.screen.patient;

import io.jmix.core.DataManager;
import io.jmix.ui.Screens;
import io.jmix.ui.testassist.junit.UiTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.mpi.hospital.entity.Patient;
import ru.itmo.mpi.hospital.testsupport.ScreenInteractions;
import ru.itmo.mpi.hospital.testsupport.TableInteractions;
import ru.itmo.mpi.hospital.testsupport.WebIntegrationTest;
import ru.itmo.mpi.hospital.testsupport.testdata.PatientTestData;

import javax.validation.constraints.NotNull;

import static org.assertj.core.api.Assertions.assertThat;

@UiTest(authenticatedUser = "regUserName1", mainScreenId = "MainScreen", screenBasePackages = "ru.itmo.mpi.hospital.screen")
public class PatientAdministratorDiseasedBrowseTest extends WebIntegrationTest {

    @Autowired
    private PatientTestData patients;

    @Autowired
    private DataManager dataManager;

    @BeforeEach
    void setUp() {
        patients.loadDefault();
    }

    @AfterEach
    void tearDown() {
        patients.cleanup();
    }

    @Test
    void given_patientsExists_when_openDiseasedPatients_then_tableContainsThePatients(Screens screens) {

        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        PatientAdministratorDiseasedBrowse patientBrowse = screenInteractions.open(PatientAdministratorDiseasedBrowse.class);
        TableInteractions<Patient> patientTable = entityTable(patientBrowse);

        // expect:
        assertThat(patientTable.allItems().size())
                .isEqualTo(patients.patients.size());

    }

    @NotNull
    private TableInteractions<Patient> entityTable(PatientAdministratorDiseasedBrowse browseScreen) {
        return TableInteractions.of(browseScreen, Patient.class, "patientsTable");
    }
}
