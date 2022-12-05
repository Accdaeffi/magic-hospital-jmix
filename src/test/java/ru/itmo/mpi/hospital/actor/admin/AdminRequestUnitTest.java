package ru.itmo.mpi.hospital.actor.admin;

import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import io.jmix.ui.Screens;
import io.jmix.ui.testassist.junit.UiTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.mpi.hospital.entity.DiseaseCase;
import ru.itmo.mpi.hospital.entity.Request;
import ru.itmo.mpi.hospital.screen.request.RequestAdministratorBrowse;
import ru.itmo.mpi.hospital.screen.request.RequestAdministratorView;
import ru.itmo.mpi.hospital.testsupport.ScreenInteractions;
import ru.itmo.mpi.hospital.testsupport.TableInteractions;
import ru.itmo.mpi.hospital.testsupport.WebIntegrationTest;
import ru.itmo.mpi.hospital.testsupport.testdata.RequestTestData;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@UiTest(authenticatedUser = "regUserName1", mainScreenId = "MainScreen", screenBasePackages = "ru.itmo.mpi.hospital.screen")
public class AdminRequestUnitTest extends WebIntegrationTest {

    @Autowired
    RequestTestData requestTestData;

    @Autowired
    private DataManager dataManager;

    @Autowired
    SystemAuthenticator authenticator;

    @BeforeEach
    void setUp() {
        requestTestData.loadDefault();
    }

    @AfterEach
    void tearDown() {
        requestTestData.cleanup();
    }

    @Test
    void test_admin_request_list(Screens screens) {
        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        RequestAdministratorBrowse requestBrowse = screenInteractions.open(RequestAdministratorBrowse.class);
        TableInteractions<Request> requestTable = entityTable(requestBrowse, Request.class, "requestsTable");

        // expect:
        List<Request> allRequestsFromPage = requestTable.allItems();
        List<DiseaseCase> allDiseaseCasesFromDb = dataManager.load(DiseaseCase.class).all().list();
        List<Request> allRequestsFromDb = dataManager.load(Request.class).all().list().stream().filter(prayer -> allDiseaseCasesFromDb.contains(prayer.getDiseaseCase())).collect(Collectors.toList());
        Assertions.assertThat(allRequestsFromDb).containsExactlyInAnyOrderElementsOf(allRequestsFromPage);
    }

    @Test
    void test_admin_open_request(Screens screens) {
        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        RequestAdministratorBrowse requestBrowse = screenInteractions.open(RequestAdministratorBrowse.class);
        TableInteractions<Request> requestTable = entityTable(requestBrowse, Request.class, "requestsTable");

        // and:
        Request firstRequest = requestTable.firstItem();

        // and:
        requestTable.view(firstRequest);

        // then:
        RequestAdministratorView requestView = screenInteractions.findOpenScreen(RequestAdministratorView.class);

        assertThat(requestView.getEditedEntity())
                .isEqualTo(firstRequest);
    }


}
