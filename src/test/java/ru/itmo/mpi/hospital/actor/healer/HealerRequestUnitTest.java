package ru.itmo.mpi.hospital.actor.healer;

import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.Screens;
import io.jmix.ui.component.Button;
import io.jmix.ui.testassist.junit.UiTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.mpi.hospital.entity.DiseaseCase;
import ru.itmo.mpi.hospital.entity.Healer;
import ru.itmo.mpi.hospital.entity.Request;
import ru.itmo.mpi.hospital.entity.RequestStatus;
import ru.itmo.mpi.hospital.screen.request.RequestHealerBrowse;
import ru.itmo.mpi.hospital.screen.request.RequestHealerEdit;
import ru.itmo.mpi.hospital.testsupport.ScreenInteractions;
import ru.itmo.mpi.hospital.testsupport.TableInteractions;
import ru.itmo.mpi.hospital.testsupport.WebIntegrationTest;
import ru.itmo.mpi.hospital.testsupport.testdata.RequestTestData;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@UiTest(authenticatedUser = "healerUserName1", mainScreenId = "MainScreen", screenBasePackages = "ru.itmo.mpi.hospital.screen")
public class HealerRequestUnitTest extends WebIntegrationTest {


    @Autowired
    private RequestTestData requestTestData;

    @Autowired
    private DataManager dataManager;

    @Autowired
    private CurrentAuthentication auth;

    @BeforeEach
    void setUp() {
        requestTestData.loadDefault();
    }

    @AfterEach
    void tearDown() {
        requestTestData.cleanup();
    }

    @Test
    void test_healer_request_list(Screens screens) {

        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        RequestHealerBrowse requestHealerBrowse = screenInteractions.open(RequestHealerBrowse.class);
        TableInteractions<Request> requestTable = entityTable(requestHealerBrowse, Request.class, "requestsTable");
        Healer currentHealer = ((Healer) auth.getUser());

        // expect:
        assertThat(requestTable.allItems().stream().allMatch(pr -> pr.getDiseaseCase().getHealer().equals(currentHealer))).isTrue();

        List<Request> allRequestsFromPage = requestTable.allItems();
        List<DiseaseCase> allDiseaseCasesFromDb = dataManager.load(DiseaseCase.class).all().list().stream().filter(diseaseCase -> diseaseCase.getHealer().equals(currentHealer)).collect(Collectors.toList());
        List<Request> allRequestsFromDb = dataManager.load(Request.class).all().list().stream().filter(prayer -> allDiseaseCasesFromDb.contains(prayer.getDiseaseCase())).collect(Collectors.toList());
        Assertions.assertThat(allRequestsFromDb).containsExactlyInAnyOrderElementsOf(allRequestsFromPage);
    }

    @Test
    void test_healer_open_request(Screens screens) {
        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        RequestHealerBrowse requestHealerBrowse = screenInteractions.open(RequestHealerBrowse.class);
        TableInteractions<Request> requestTable = entityTable(requestHealerBrowse, Request.class, "requestsTable");

        // and:
        Request firstRequest = requestTable.firstItem();

        // and:
        requestTable.edit(firstRequest);

        // then:
        RequestHealerEdit requestView = screenInteractions.findOpenScreen(RequestHealerEdit.class);

        assertThat(requestView.getEditedEntity())
                .isEqualTo(firstRequest);
    }

    @Test
    void test_record_request_completion(Screens screens) {
        // given:
        Healer currentHealer = ((Healer) auth.getUser());
        Request firstUnprocessed = requestTestData.requests.stream()
                .filter(request -> request.getDiseaseCase().getHealer().equals(currentHealer))
                .filter(request -> request.getRequestStatus().equals(RequestStatus.PROCESSING))
                .findFirst()
                .orElse(null);

        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        RequestHealerEdit requestExaminer = screenInteractions.openEditorForEditing(RequestHealerEdit.class, Request.class, firstUnprocessed);

        // when:
        ((Button) (requestExaminer.getWindow().getComponent("receiveBtn"))).click();

        // then:
        Request updatedRequest = dataManager.load(Request.class).id(firstUnprocessed.getId()).one();

        assertThat(updatedRequest.getRequestStatus()).isEqualTo(RequestStatus.COMPLETED);
    }

}
