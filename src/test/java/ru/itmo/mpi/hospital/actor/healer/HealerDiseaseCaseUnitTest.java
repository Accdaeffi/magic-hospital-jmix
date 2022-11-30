package ru.itmo.mpi.hospital.actor.healer;

import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
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
import ru.itmo.mpi.hospital.entity.Disease;
import ru.itmo.mpi.hospital.entity.DiseaseCase;
import ru.itmo.mpi.hospital.entity.DiseaseCaseState;
import ru.itmo.mpi.hospital.entity.Healer;
import ru.itmo.mpi.hospital.screen.disease.DiseaseHealerBrowse;
import ru.itmo.mpi.hospital.screen.diseasecase.DiseaseCaseHealerBrowse;
import ru.itmo.mpi.hospital.screen.diseasecase.DiseaseCaseHealerExamine;
import ru.itmo.mpi.hospital.testsupport.ScreenInteractions;
import ru.itmo.mpi.hospital.testsupport.TableInteractions;
import ru.itmo.mpi.hospital.testsupport.WebIntegrationTest;
import ru.itmo.mpi.hospital.testsupport.testdata.DiseaseCaseTestData;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@UiTest(authenticatedUser = "healerUserName1", mainScreenId = "MainScreen", screenBasePackages = "ru.itmo.mpi.hospital.screen")
public class HealerDiseaseCaseUnitTest extends WebIntegrationTest {

    @Autowired
    private DiseaseCaseTestData diseaseCaseTestData;

    @Autowired
    private DataManager dataManager;

    @Autowired
    private CurrentAuthentication auth;

    @BeforeEach
    void setUp() {
        diseaseCaseTestData.loadDefault();
    }

    @AfterEach
    void tearDown() {
        diseaseCaseTestData.cleanup();
    }

    @Test
    void test_healer_disease_case_list(Screens screens) {
        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        DiseaseCaseHealerBrowse diseaseCaseHealerBrowse = screenInteractions.open(DiseaseCaseHealerBrowse.class);
        TableInteractions<DiseaseCase> diseaseCasesTable = entityTable(diseaseCaseHealerBrowse, DiseaseCase.class, "diseaseCasesTable");
        Healer currentHealer = ((Healer) auth.getUser());

        // expect:
        assertThat(diseaseCasesTable.allItems().stream().allMatch(pr -> pr.getHealer().equals(currentHealer))).isTrue();

        List<DiseaseCase> allDiseaseCasesFromTable = diseaseCasesTable.allItems();
        List<DiseaseCase> allDiseaseCasesFromDb = dataManager.load(DiseaseCase.class).all().list().stream()
                .filter(diseaseCase -> diseaseCase.getHealer().equals(currentHealer))
                .filter(diseaseCase -> diseaseCase.getDiseaseCaseState().equals(DiseaseCaseState.AT_WORK))
                .collect(Collectors.toList());

        Assertions.assertThat(allDiseaseCasesFromDb).containsExactlyInAnyOrderElementsOf(allDiseaseCasesFromTable);
    }

    @Test
    void test_open_disease_case(Screens screens) {
        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        DiseaseCaseHealerBrowse diseaseCaseHealerBrowse = screenInteractions.open(DiseaseCaseHealerBrowse.class);
        TableInteractions<DiseaseCase> diseaseCasesTable = entityTable(diseaseCaseHealerBrowse, DiseaseCase.class, "diseaseCasesTable");

        // and:
        DiseaseCase firstDiseaseCase = diseaseCasesTable.firstItem();

        // and:
        diseaseCasesTable.selectItem(firstDiseaseCase.getId());
        diseaseCasesTable.clickButton("examineBtn");

        // then:
        DiseaseCaseHealerExamine diseaseCaseExamine = screenInteractions.findOpenScreen(DiseaseCaseHealerExamine.class);

        assertThat(diseaseCaseExamine.getEditedEntity()).isEqualTo(firstDiseaseCase);
    }

    @Test
    void test_edit_disease_case(Screens screens) {

        // given:
        Healer currentHealer = ((Healer) auth.getUser());
        DiseaseCase firstUnprocessed = diseaseCaseTestData.diseaseCases.stream()
                .filter(diseaseCase -> diseaseCase.getHealer().equals(currentHealer))
                .filter(diseaseCase -> diseaseCase.getDiseaseCaseState().equals(DiseaseCaseState.AT_WORK))
                .findFirst()
                .orElse(null);

        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        DiseaseCaseHealerExamine diseaseCaseExaminer = screenInteractions.openEditorForEditing(DiseaseCaseHealerExamine.class, DiseaseCase.class, firstUnprocessed);

        String actionsText = "test";

        // when:
        ((TextArea<String>) (diseaseCaseExaminer.getWindow().getComponent("actionsField"))).setValue(actionsText);
        ((EntityPicker<Disease>) (diseaseCaseExaminer.getWindow().getComponent("diseaseField"))).getActionNN("entityLookup").actionPerform(null);

        // disease select part
        DiseaseHealerBrowse diseaseBrowse = screenInteractions.findOpenScreen(DiseaseHealerBrowse.class);
        TableInteractions<Disease> diseaseTable = entityTable(diseaseBrowse, Disease.class, "diseasesTable");

        Disease disease = diseaseTable.firstItem();

        diseaseTable.selectItem(disease.getId());

        ((Button) (diseaseBrowse.getWindow().getComponent("lookupSelectActionBtn"))).click();

        // finished editing
        ((Button) (diseaseCaseExaminer.getWindow().getComponent("commitAndCloseBtn"))).click();

        // then:
        DiseaseCase updatedDiseaseCase = dataManager.load(DiseaseCase.class).id(firstUnprocessed.getId()).one();

        assertThat(updatedDiseaseCase.getDisease()).isEqualTo(disease);
        assertThat(updatedDiseaseCase.getActions()).isEqualTo(actionsText);
    }


}
