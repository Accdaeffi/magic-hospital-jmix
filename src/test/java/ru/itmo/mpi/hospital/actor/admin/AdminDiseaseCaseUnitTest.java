package ru.itmo.mpi.hospital.actor.admin;

import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import io.jmix.ui.Screens;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.EntityPicker;
import io.jmix.ui.component.TextArea;
import io.jmix.ui.testassist.junit.UiTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.mpi.hospital.entity.DiseaseCase;
import ru.itmo.mpi.hospital.entity.Healer;
import ru.itmo.mpi.hospital.entity.Patient;
import ru.itmo.mpi.hospital.screen.diseasecase.DiseaseCaseAdministratorBrowse;
import ru.itmo.mpi.hospital.screen.diseasecase.DiseaseCaseAdministratorEdit;
import ru.itmo.mpi.hospital.screen.healer.HealerAdministratorBrowse;
import ru.itmo.mpi.hospital.screen.patient.PatientAdministratorHealthy;
import ru.itmo.mpi.hospital.testsupport.ScreenInteractions;
import ru.itmo.mpi.hospital.testsupport.TableInteractions;
import ru.itmo.mpi.hospital.testsupport.WebIntegrationTest;
import ru.itmo.mpi.hospital.testsupport.testdata.PatientTestData;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@UiTest(authenticatedUser = "regUserName1", mainScreenId = "MainScreen", screenBasePackages = "ru.itmo.mpi.hospital.screen")
public class AdminDiseaseCaseUnitTest extends WebIntegrationTest {

    @Autowired
    PatientTestData diseaseCaseTestData;

    @Autowired
    private DataManager dataManager;

    @Autowired
    SystemAuthenticator authenticator;

    @BeforeEach
    void setUp() {
        diseaseCaseTestData.loadDefault();
    }

    @AfterEach
    void tearDown() {
        diseaseCaseTestData.cleanup();
    }

    @Test
    void test_admin_disease_case_list(Screens screens) {
        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        DiseaseCaseAdministratorBrowse diseaseCaseBrowse = screenInteractions.open(DiseaseCaseAdministratorBrowse.class);
        TableInteractions<DiseaseCase> diseaseCasesTable = entityTable(diseaseCaseBrowse, DiseaseCase.class, "diseaseCasesTable");

        // expect:
        List<DiseaseCase> allDiseaseCasesFromTable = diseaseCasesTable.allItems();
        List<DiseaseCase> allDiseaseCasesFromDb = dataManager.load(DiseaseCase.class).all().list();

        Assertions.assertThat(allDiseaseCasesFromDb).containsExactlyInAnyOrderElementsOf(allDiseaseCasesFromTable);
    }

    @Test
    void test_admin_create_disease_case(Screens screens) {

        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        DiseaseCaseAdministratorBrowse diseaseCaseBrowse = screenInteractions.open(DiseaseCaseAdministratorBrowse.class);
        TableInteractions<DiseaseCase> diseaseCasesTable = entityTable(diseaseCaseBrowse, DiseaseCase.class, "diseaseCasesTable");

        String complaints = "complaints text";

        diseaseCasesTable.create();

        DiseaseCaseAdministratorEdit diseaseCaseCreate = screenInteractions.findOpenScreen(DiseaseCaseAdministratorEdit.class);

        // when:
        ((TextArea<String>) (diseaseCaseCreate.getWindow().getComponent("patientComplaintsField"))).setValue(complaints);
        ((EntityPicker<Patient>) (diseaseCaseCreate.getWindow().getComponent("patientField"))).getActionNN("entityLookup").actionPerform(null);

        // patient select part
        PatientAdministratorHealthy patientBrowse = screenInteractions.findOpenScreen(PatientAdministratorHealthy.class);
        TableInteractions<Patient> patientTable = entityTable(patientBrowse, Patient.class, "patientsTable");

        Patient patient = patientTable.firstItem();

        patientTable.selectItem(patient.getId());
        ((Button) (patientBrowse.getWindow().getComponent("lookupSelectActionBtn"))).click();

        ((EntityPicker<Healer>) (diseaseCaseCreate.getWindow().getComponent("healerField"))).getActionNN("entityLookup").actionPerform(null);

        // healer select part
        HealerAdministratorBrowse healerBrowse = screenInteractions.findOpenScreen(HealerAdministratorBrowse.class);
        TableInteractions<Healer> healerTable = entityTable(healerBrowse, Healer.class, "healersTable");

        Healer healer = healerTable.firstItem();

        healerTable.selectItem(healer.getId());
        ((Button) (healerBrowse.getWindow().getComponent("lookupSelectActionBtn"))).click();

        // finished creating disease case
        ((Button) (diseaseCaseCreate.getWindow().getComponent("commitAndCloseBtn"))).click();

        // then:
        DiseaseCase createdDiseaseCase = dataManager.load(DiseaseCase.class)
                .all().list().stream()
                .filter(diseaseCase -> diseaseCase.getPatientComplaints().equals(complaints))
                .findFirst().orElse(null);

        assertThat(createdDiseaseCase).isNotNull();
        assertThat(createdDiseaseCase.getPatient()).isEqualTo(patient);
        assertThat(createdDiseaseCase.getHealer()).isEqualTo(healer);

        // cleanup
        authenticator.withSystem(() -> {
            dataManager.remove(createdDiseaseCase);

            return "done";
        });
    }


}
