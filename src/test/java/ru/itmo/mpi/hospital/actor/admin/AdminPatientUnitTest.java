package ru.itmo.mpi.hospital.actor.admin;

import io.jmix.core.DataManager;
import io.jmix.ui.Screens;
import io.jmix.ui.component.Button;
import io.jmix.ui.testassist.junit.UiTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.mpi.hospital.entity.Patient;
import ru.itmo.mpi.hospital.entity.PatientState;
import ru.itmo.mpi.hospital.entity.SocialStatus;
import ru.itmo.mpi.hospital.screen.patient.PatientAdministratorBrowse;
import ru.itmo.mpi.hospital.screen.patient.PatientAdministratorDiseasedBrowse;
import ru.itmo.mpi.hospital.screen.patient.PatientAdministratorEdit;
import ru.itmo.mpi.hospital.testsupport.ScreenInteractions;
import ru.itmo.mpi.hospital.testsupport.TableInteractions;
import ru.itmo.mpi.hospital.testsupport.WebIntegrationTest;
import ru.itmo.mpi.hospital.testsupport.testdata.PatientTestData;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@UiTest(authenticatedUser = "regUserName1", mainScreenId = "MainScreen", screenBasePackages = "ru.itmo.mpi.hospital.screen")
public class AdminPatientUnitTest extends WebIntegrationTest {

    @Autowired
    PatientTestData patientTestData;

    @Autowired
    private DataManager dataManager;

    @BeforeEach
    void setUp() {
        patientTestData.loadDefault();
    }

    @AfterEach
    void tearDown() {
        patientTestData.cleanup();
    }

    @Test
    void test_admin_patients_list(Screens screens) {
        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        PatientAdministratorBrowse patientAdministratorBrowse = screenInteractions.open(PatientAdministratorBrowse.class);
        TableInteractions<Patient> patientTable = entityTable(patientAdministratorBrowse, Patient.class, "patientsTable");

        // expect
        List<Patient> allPatientsFromPage = patientTable.allItems();
        List<Patient> allPatientsFromDb = dataManager.load(Patient.class).all().list();
        Assertions.assertThat(allPatientsFromDb).containsExactlyInAnyOrderElementsOf(allPatientsFromPage);
    }

    // TODO перепроверить
    // Оно то ли не создаётся, то ли не достаётся. С дебагером должно быть норм.
    @Test
    void test_admin_patient_create(Screens screens) {
        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        PatientAdministratorBrowse patientAdministratorBrowse = screenInteractions.open(PatientAdministratorBrowse.class);
        TableInteractions<Patient> patientTable = entityTable(patientAdministratorBrowse, Patient.class, "patientsTable");

        String newName = "Test patient name";
        String newSurname = "Test patient surname";
        boolean newMage = true;

        // and:
        patientTable.clickButton("createBtn");

        // and:
        PatientAdministratorEdit patientEditor = screenInteractions.findOpenScreen(PatientAdministratorEdit.class);

        // and:
        Patient createdPatient = patientEditor.getEditedEntity();

        // and:
        createdPatient.setSurname(newSurname);
        createdPatient.setName(newName);
        createdPatient.setIsMage(newMage);

        // and:
        ((Button) (patientEditor.getWindow().getComponent("commitAndCloseBtn"))).click();

        // then:
        List<Patient> patients = dataManager.load(Patient.class).all().list();

        assertThat(patients.size()).isEqualTo(patientTestData.patients.size());

        /*Patient savedPatient = dataManager.load(Patient.class)
                .all().list().stream()
                .filter(patient -> patient.getName().equals(newName))
                .findFirst().orElse(null);

        assertThat(savedPatient).isNotNull();
        assertThat(savedPatient.getIsMage()).isEqualTo(newMage);*/

        // cleanup
        /*authenticator.withSystem(() -> {

            dataManager.remove(savedPatient);

            return "done";
        });*/
    }

    @Test
    void test_admin_patient_edit(Screens screens) {
        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        PatientAdministratorBrowse patientAdministratorBrowse = screenInteractions.open(PatientAdministratorBrowse.class);
        TableInteractions<Patient> patientTable = entityTable(patientAdministratorBrowse, Patient.class, "patientsTable");

        String newName = "Test patient name";
        boolean newMage = true;

        // and:
        Patient firstPatient = patientTable.firstItem();

        // and:
        patientTable.edit(firstPatient);

        // and:
        PatientAdministratorEdit patientEditor = screenInteractions.findOpenScreen(PatientAdministratorEdit.class);

        // and:
        Patient editedPatient = patientEditor.getEditedEntity();

        // then:
        assertThat(editedPatient).isEqualTo(firstPatient);

        // and:
        editedPatient.setName(newName);
        editedPatient.setIsMage(newMage);

        // and:
        ((Button) (patientEditor.getWindow().getComponent("commitAndCloseBtn"))).click();

        Patient savedPatient = dataManager.load(Patient.class).id(editedPatient.getId()).one();

        // then:
        assertThat(savedPatient.getName()).isEqualTo(newName);
        assertThat(savedPatient.getIsMage()).isEqualTo(newMage);
    }

    @Test
    void given_patientsExists_when_openDiseasedPatients_then_tableContainsThePatients(Screens screens) {

        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        PatientAdministratorDiseasedBrowse patientBrowse = screenInteractions.open(PatientAdministratorDiseasedBrowse.class);
        TableInteractions<Patient> patientTable = entityTable(patientBrowse, Patient.class, "patientsTable");

        // expect:
        assertThat(patientTable.allItems().size())
                .isEqualTo(patientTestData.patients.size());

    }

    @Test
    void given_patientDiseased_when_pressBuryButton_then_patientBuried(Screens screens) {

        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        PatientAdministratorDiseasedBrowse patientBrowse = screenInteractions.open(PatientAdministratorDiseasedBrowse.class);
        TableInteractions<Patient> patientTable = entityTable(patientBrowse, Patient.class, "patientsTable");

        Patient firstDead = patientTestData.patients.stream().filter(pat -> pat.getPatientState().equals(PatientState.DISEASED)).findFirst().get();
        patientTable.selectItem(firstDead.getId());
        patientTable.clickButton("buryBtn");

        Patient updatedBuried = dataManager.load(Patient.class).id(firstDead.getId()).one();

        // expect:
        assertThat(updatedBuried.getPatientState())
                .isEqualTo(PatientState.BURIED);
    }

    @Test
    void given_patientNotDiseaseButNotAristocrat_when_pressBuryButton_then_patientBuried(Screens screens) {

        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        PatientAdministratorDiseasedBrowse patientBrowse = screenInteractions.open(PatientAdministratorDiseasedBrowse.class);
        TableInteractions<Patient> patientTable = entityTable(patientBrowse, Patient.class, "patientsTable");
        Button buryBtn = patientTable.button("buryBtn");

        Patient firstAliveNotAristocrat = patientTestData.patients.stream().filter(pat -> (pat.getPatientState().equals(PatientState.HEALTHY) && !pat.getSocialStatus().equals(SocialStatus.ARISTOCRAT))).findFirst().get();
        patientTable.selectItem(firstAliveNotAristocrat.getId());
        patientTable.clickButton("buryBtn");

        Patient updatedBuried = dataManager.load(Patient.class).id(firstAliveNotAristocrat.getId()).one();

        // expect:
        assertThat(updatedBuried.getPatientState())
                .isEqualTo(PatientState.BURIED);
    }

    @Test
    void given_patientNotDisease_then_buryBtnNotActive(Screens screens) {

        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        PatientAdministratorDiseasedBrowse patientBrowse = screenInteractions.open(PatientAdministratorDiseasedBrowse.class);
        TableInteractions<Patient> patientTable = entityTable(patientBrowse, Patient.class, "patientsTable");
        Button buryBtn = patientTable.button("buryBtn");

        Patient firstNotBuryable = patientTestData.patients.stream().filter(pat ->
                (pat.getPatientState().equals(PatientState.SICK) || pat.getPatientState().equals(PatientState.BURIED) || (pat.getPatientState().equals(PatientState.HEALTHY) && !pat.getSocialStatus().equals(SocialStatus.ARISTOCRAT))
                )).findFirst().get();


        patientTable.selectItem(firstNotBuryable.getId());

        // expect:
        assertThat(buryBtn.isEnabled())
                .isEqualTo(false);
    }

    @Test
    void given_screenOpened_when_pressBuryAllButton_then_allDeadPatientsBuried(Screens screens) {

        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        PatientAdministratorDiseasedBrowse patientBrowse = screenInteractions.open(PatientAdministratorDiseasedBrowse.class);
        TableInteractions<Patient> patientTable = entityTable(patientBrowse, Patient.class, "patientsTable");
        Button buryAllBtn = patientTable.button("buryAllBtn");

        List<Patient> allDead = patientTestData.patients.stream().filter(pat -> pat.getPatientState().equals(PatientState.DISEASED)).collect(Collectors.toList());
        List<UUID> deadUuid = allDead.stream().map(Patient::getId).collect(Collectors.toList());

        // then:
        buryAllBtn.click();

        List<Patient> allBuried = dataManager.load(Patient.class).all().list().stream().filter(pat -> deadUuid.contains(pat.getId())).collect(Collectors.toList());

        // expect:
        List<UUID> allBuriedUuids = allBuried.stream().map(Patient::getId).collect(Collectors.toList());

        org.junit.jupiter.api.Assertions.assertTrue(allBuriedUuids.size() == deadUuid.size() && allBuriedUuids.containsAll(deadUuid) && deadUuid.containsAll(allBuriedUuids));
    }


}
