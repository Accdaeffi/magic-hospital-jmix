package ru.itmo.mpi.hospital.actor.helper;

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
import ru.itmo.mpi.hospital.entity.Helper;
import ru.itmo.mpi.hospital.entity.Request;
import ru.itmo.mpi.hospital.entity.RequestStatus;
import ru.itmo.mpi.hospital.screen.request.RequestHelperBrowse;
import ru.itmo.mpi.hospital.screen.request.RequestHelperProcess;
import ru.itmo.mpi.hospital.testsupport.ScreenInteractions;
import ru.itmo.mpi.hospital.testsupport.TableInteractions;
import ru.itmo.mpi.hospital.testsupport.WebIntegrationTest;
import ru.itmo.mpi.hospital.testsupport.testdata.RequestTestData;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@UiTest(authenticatedUser = "helperUserName1", mainScreenId = "MainScreen", screenBasePackages = "ru.itmo.mpi.hospital.screen")
public class HelperUnitTest extends WebIntegrationTest {

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
    void test_helper_requests_list(Screens screens) {
        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        RequestHelperBrowse requestHelperBrowse = screenInteractions.open(RequestHelperBrowse.class);
        TableInteractions<Request> requestTable = entityTable(requestHelperBrowse, Request.class, "requestsTable");
        Helper currentHelper = ((Helper) auth.getUser());

        // expect:
        assertThat(requestTable.allItems().stream().allMatch(pr -> pr.getHelper().equals(currentHelper))).isTrue();

        List<Request> allRequestsFromPage = requestTable.allItems();
        List<Request> allRequestsFromDb = dataManager.load(Request.class).all().list().stream().filter(request -> request.getHelper().equals(currentHelper)).collect(Collectors.toList());
        Assertions.assertThat(allRequestsFromDb).containsExactlyInAnyOrderElementsOf(allRequestsFromPage);
    }

    @Test
    void test_open_prayer(Screens screens) {
        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        RequestHelperBrowse requestHelperBrowse = screenInteractions.open(RequestHelperBrowse.class);
        TableInteractions<Request> requestTable = entityTable(requestHelperBrowse, Request.class, "requestsTable");

        // and:
        Request firstRequest = requestTable.firstItem();

        // and:
        requestTable.edit(firstRequest);

        // then:
        RequestHelperProcess requestProcess = screenInteractions.findOpenScreen(RequestHelperProcess.class);

        assertThat(requestProcess.getEditedEntity()).isEqualTo(firstRequest);
    }

    @Test
    void test_start_process_request(Screens screens) {

        // given:
        Helper currentHelper = ((Helper) auth.getUser());
        Request firstUnprocessedRequest = requestTestData.requests.stream()
                .filter(prayer -> prayer.getHelper().equals(currentHelper))
                .filter(prayer -> prayer.getRequestStatus().equals(RequestStatus.INITIALISED))
                .findFirst().orElse(null);

        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        RequestHelperProcess prayerResolver = screenInteractions.openEditorForEditing(RequestHelperProcess.class, Request.class, firstUnprocessedRequest);

        // when:
        ((Button) (prayerResolver.getWindow().getComponent("startProcessingBtn"))).click();

        // then:
        Request startedProcessingRequest = dataManager.load(Request.class).id(firstUnprocessedRequest.getId()).one();

        assertThat(startedProcessingRequest.getRequestStatus()).isEqualTo(RequestStatus.PROCESSING);
    }


}
