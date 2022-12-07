package ru.itmo.mpi.hospital.screen.patient;

import io.jmix.core.DataManager;
import io.jmix.ui.Screens;
import io.jmix.ui.component.Button;
import io.jmix.ui.testassist.junit.UiTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.mpi.hospital.entity.Patient;
import ru.itmo.mpi.hospital.entity.PatientState;
import ru.itmo.mpi.hospital.entity.SocialStatus;
import ru.itmo.mpi.hospital.testsupport.ScreenInteractions;
import ru.itmo.mpi.hospital.testsupport.TableInteractions;
import ru.itmo.mpi.hospital.testsupport.WebIntegrationTest;
import ru.itmo.mpi.hospital.testsupport.testdata.PatientTestData;

import javax.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Test
    void given_patientDiseased_when_pressBuryButton_then_patientBuried(Screens screens) {

        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        PatientAdministratorDiseasedBrowse patientBrowse = screenInteractions.open(PatientAdministratorDiseasedBrowse.class);
        TableInteractions<Patient> patientTable = entityTable(patientBrowse);

        Patient firstDead = patients.patients.stream().filter(pat -> pat.getPatientState().equals(PatientState.DISEASED)).findFirst().get();
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
        TableInteractions<Patient> patientTable = entityTable(patientBrowse);
        Button buryBtn = patientTable.button("buryBtn");

        Patient firstAliveNotAristocrat = patients.patients.stream().filter(pat -> (pat.getPatientState().equals(PatientState.HEALTHY) && !pat.getSocialStatus().equals(SocialStatus.ARISTOCRAT))).findFirst().get();
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
        TableInteractions<Patient> patientTable = entityTable(patientBrowse);
        Button buryBtn = patientTable.button("buryBtn");

        Patient firstNotBuryable = patients.patients.stream().filter(pat ->
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
        TableInteractions<Patient> patientTable = entityTable(patientBrowse);
        Button buryAllBtn = patientTable.button("buryAllBtn");

        List<Patient> allDead = patients.patients.stream().filter(pat -> pat.getPatientState().equals(PatientState.DISEASED)).collect(Collectors.toList());
        List<UUID> deadUuid = allDead.stream().map(Patient::getId).collect(Collectors.toList());

        // then:
        buryAllBtn.click();

        List<Patient> allBuried = dataManager.load(Patient.class).all().list().stream().filter(pat -> deadUuid.contains(pat.getId())).collect(Collectors.toList());

        // expect:
        List<UUID> allBuriedUuids = allBuried.stream().map(Patient::getId).collect(Collectors.toList());

        Assertions.assertTrue(allBuriedUuids.size() == deadUuid.size() && allBuriedUuids.containsAll(deadUuid) && deadUuid.containsAll(allBuriedUuids));
    }

    @NotNull
    private TableInteractions<Patient> entityTable(PatientAdministratorDiseasedBrowse browseScreen) {
        return TableInteractions.of(browseScreen, Patient.class, "patientsTable");
    }
}
