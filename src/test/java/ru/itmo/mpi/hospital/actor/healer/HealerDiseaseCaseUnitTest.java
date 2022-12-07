package ru.itmo.mpi.hospital.actor.healer;

import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.core.security.SystemAuthenticator;
import io.jmix.ui.Screens;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.CheckBox;
import io.jmix.ui.component.EntityPicker;
import io.jmix.ui.component.Slider;
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
import ru.itmo.mpi.hospital.entity.God;
import ru.itmo.mpi.hospital.entity.Healer;
import ru.itmo.mpi.hospital.entity.Helper;
import ru.itmo.mpi.hospital.entity.Prayer;
import ru.itmo.mpi.hospital.entity.Request;
import ru.itmo.mpi.hospital.screen.disease.DiseaseHealerBrowse;
import ru.itmo.mpi.hospital.screen.diseasecase.DiseaseCaseHealerBrowse;
import ru.itmo.mpi.hospital.screen.diseasecase.DiseaseCaseHealerExamine;
import ru.itmo.mpi.hospital.screen.god.GodHealerBrowse;
import ru.itmo.mpi.hospital.screen.helper.HelperHealerBrowse;
import ru.itmo.mpi.hospital.screen.prayer.PrayerHealerCreate;
import ru.itmo.mpi.hospital.screen.request.RequestHealerCreate;
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

    @Test
    void test_record_death(Screens screens) {
        // given:
        Healer currentHealer = ((Healer) auth.getUser());
        DiseaseCase firstUnprocessed = diseaseCaseTestData.diseaseCases.stream()
                .filter(diseaseCase -> diseaseCase.getHealer().equals(currentHealer))
                .filter(diseaseCase -> diseaseCase.getDiseaseCaseState().equals(DiseaseCaseState.AT_WORK))
                .findFirst()
                .orElse(null);

        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        DiseaseCaseHealerExamine diseaseCaseExaminer = screenInteractions.openEditorForEditing(DiseaseCaseHealerExamine.class, DiseaseCase.class, firstUnprocessed);

        // when:
        ((Button) (diseaseCaseExaminer.getWindow().getComponent("recordDeathBtn"))).click();

        // then:
        DiseaseCase updatedDiseaseCase = dataManager.load(DiseaseCase.class).id(firstUnprocessed.getId()).one();

        assertThat(updatedDiseaseCase.getDiseaseCaseState()).isEqualTo(DiseaseCaseState.DEATH);
    }

    @Test
    void test_record_recovery(Screens screens) {
        // given:
        Healer currentHealer = ((Healer) auth.getUser());
        DiseaseCase firstUnprocessed = diseaseCaseTestData.diseaseCases.stream()
                .filter(diseaseCase -> diseaseCase.getHealer().equals(currentHealer))
                .filter(diseaseCase -> diseaseCase.getDiseaseCaseState().equals(DiseaseCaseState.AT_WORK))
                .findFirst()
                .orElse(null);

        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        DiseaseCaseHealerExamine diseaseCaseExaminer = screenInteractions.openEditorForEditing(DiseaseCaseHealerExamine.class, DiseaseCase.class, firstUnprocessed);

        // when:
        ((Button) (diseaseCaseExaminer.getWindow().getComponent("recordRecoveryBtn"))).click();

        // then:
        DiseaseCase updatedDiseaseCase = dataManager.load(DiseaseCase.class).id(firstUnprocessed.getId()).one();

        assertThat(updatedDiseaseCase.getDiseaseCaseState()).isEqualTo(DiseaseCaseState.RECOVERY);
    }

    @Test
    void test_create_new_prayer(Screens screens) {

        // given:
        Healer currentHealer = ((Healer) auth.getUser());
        DiseaseCase firstUnprocessed = diseaseCaseTestData.diseaseCases.stream()
                .filter(diseaseCase -> diseaseCase.getHealer().equals(currentHealer))
                .filter(diseaseCase -> diseaseCase.getDiseaseCaseState().equals(DiseaseCaseState.AT_WORK))
                .findFirst()
                .orElse(null);

        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        DiseaseCaseHealerExamine diseaseCaseExaminer = screenInteractions.openEditorForEditing(DiseaseCaseHealerExamine.class, DiseaseCase.class, firstUnprocessed);

        String prayText = "test";

        ((Button) (diseaseCaseExaminer.getWindow().getComponent("createPrayerBtn"))).click();

        PrayerHealerCreate prayerHealerCreate = screenInteractions.findOpenScreen(PrayerHealerCreate.class);

        // when:
        ((TextArea<String>) (prayerHealerCreate.getWindow().getComponent("prayTextField"))).setValue(prayText);
        ((EntityPicker<Disease>) (prayerHealerCreate.getWindow().getComponent("godField"))).getActionNN("entityLookup").actionPerform(null);

        // god select part
        GodHealerBrowse godBrowse = screenInteractions.findOpenScreen(GodHealerBrowse.class);
        TableInteractions<God> godTable = entityTable(godBrowse, God.class, "godsTable");

        God god = godTable.firstItem();

        godTable.selectItem(god.getId());
        ((Button) (godBrowse.getWindow().getComponent("lookupSelectActionBtn"))).click();

        // finished selecting god
        ((Button) (prayerHealerCreate.getWindow().getComponent("commitAndCloseBtn"))).click();

        // then:
        DiseaseCase updatedDiseaseCase = dataManager.load(DiseaseCase.class).id(firstUnprocessed.getId()).one();

        assertThat(updatedDiseaseCase.getPrayer()).isNotNull();

        Prayer createdPrayer = updatedDiseaseCase.getPrayer();

        assertThat(createdPrayer.getPrayText()).isEqualTo(prayText);
        assertThat(createdPrayer.getGod()).isEqualTo(god);

        // cleanup
        authenticator.withSystem(() -> {

            dataManager.remove(createdPrayer);

            return "done";
        });
    }

    @Test
    void test_create_new_request(Screens screens) {

        // given:
        Healer currentHealer = ((Healer) auth.getUser());
        DiseaseCase firstUnprocessed = diseaseCaseTestData.diseaseCases.stream()
                .filter(diseaseCase -> diseaseCase.getHealer().equals(currentHealer))
                .filter(diseaseCase -> diseaseCase.getDiseaseCaseState().equals(DiseaseCaseState.AT_WORK))
                .findFirst()
                .orElse(null);

        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        DiseaseCaseHealerExamine diseaseCaseExaminer = screenInteractions.openEditorForEditing(DiseaseCaseHealerExamine.class, DiseaseCase.class, firstUnprocessed);

        String requestText = "test";
        boolean requiredPenta = true;
        int dustAmount = 70;
        int waterAmount = 3;

        ((Button) (diseaseCaseExaminer.getWindow().getComponent("createRequestBtn"))).click();

        RequestHealerCreate requestHealerCreate = screenInteractions.findOpenScreen(RequestHealerCreate.class);

        // when:
        ((CheckBox) (requestHealerCreate.getWindow().getComponent("requiredPentaHelpField"))).setValue(requiredPenta);
        ((Slider) (requestHealerCreate.getWindow().getComponent("dustAmountRequiredField"))).setValue(dustAmount);
        ((Slider) (requestHealerCreate.getWindow().getComponent("waterRequiredField"))).setValue(waterAmount);
        ((TextArea<String>) (requestHealerCreate.getWindow().getComponent("additionalInfoField"))).setValue(requestText);

        ((EntityPicker<Disease>) (requestHealerCreate.getWindow().getComponent("helperField"))).getActionNN("entityLookup").actionPerform(null);

        // helper select part
        HelperHealerBrowse helperBrowse = screenInteractions.findOpenScreen(HelperHealerBrowse.class);
        TableInteractions<Helper> helperTable = entityTable(helperBrowse, Helper.class, "helpersTable");

        Helper helper = helperTable.firstItem();

        helperTable.selectItem(helper.getId());
        ((Button) (helperBrowse.getWindow().getComponent("lookupSelectActionBtn"))).click();

        // finished selecting helper
        ((Button) (requestHealerCreate.getWindow().getComponent("commitAndCloseBtn"))).click();

        // then:
        DiseaseCase updatedDiseaseCase = dataManager.load(DiseaseCase.class).id(firstUnprocessed.getId()).one();

        assertThat(updatedDiseaseCase.getRequest()).isNotNull();

        Request createdRequest = updatedDiseaseCase.getRequest();

        assertThat(createdRequest.getRequiredPentaHelp()).isEqualTo(requiredPenta);
        assertThat(createdRequest.getDustAmountRequired()).isEqualTo(dustAmount);
        assertThat(createdRequest.getWaterRequired()).isEqualTo(waterAmount);
        assertThat(createdRequest.getAdditionalInfo()).isEqualTo(requestText);
        assertThat(createdRequest.getHelper()).isEqualTo(helper);

        // cleanup
        authenticator.withSystem(() -> {

            dataManager.remove(createdRequest);

            return "done";
        });
    }


}
