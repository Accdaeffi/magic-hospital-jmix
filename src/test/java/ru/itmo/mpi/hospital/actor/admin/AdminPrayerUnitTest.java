package ru.itmo.mpi.hospital.actor.admin;

import io.jmix.core.DataManager;
import io.jmix.ui.Screens;
import io.jmix.ui.testassist.junit.UiTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.mpi.hospital.entity.Prayer;
import ru.itmo.mpi.hospital.screen.prayer.PrayerAdministratorBrowse;
import ru.itmo.mpi.hospital.screen.prayer.PrayerAdministratorView;
import ru.itmo.mpi.hospital.testsupport.ScreenInteractions;
import ru.itmo.mpi.hospital.testsupport.TableInteractions;
import ru.itmo.mpi.hospital.testsupport.WebIntegrationTest;
import ru.itmo.mpi.hospital.testsupport.testdata.RequestTestData;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@UiTest(authenticatedUser = "regUserName1", mainScreenId = "MainScreen", screenBasePackages = "ru.itmo.mpi.hospital.screen")
public class AdminPrayerUnitTest extends WebIntegrationTest {

    @Autowired
    RequestTestData prayerTestData;

    @Autowired
    private DataManager dataManager;

    @BeforeEach
    void setUp() {
        prayerTestData.loadDefault();
    }

    @AfterEach
    void tearDown() {
        prayerTestData.cleanup();
    }

    @Test
    void test_admin_prayers_list(Screens screens) {
        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        PrayerAdministratorBrowse prayerBrowse = screenInteractions.open(PrayerAdministratorBrowse.class);
        TableInteractions<Prayer> prayerTable = entityTable(prayerBrowse, Prayer.class, "prayersTable");

        // expect:
        List<Prayer> allPrayersFromPage = prayerTable.allItems();
        List<Prayer> allPrayersFromDb = dataManager.load(Prayer.class).all().list();
        Assertions.assertThat(allPrayersFromDb).containsExactlyInAnyOrderElementsOf(allPrayersFromPage);
    }

    // todo fix
    @Test
    void test_admin_open_prayer(Screens screens) {
        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        PrayerAdministratorBrowse prayerBrowse = screenInteractions.open(PrayerAdministratorBrowse.class);
        TableInteractions<Prayer> prayerTable = entityTable(prayerBrowse, Prayer.class, "prayersTable");

        // and:
        Prayer firstPrayer = prayerTable.firstItem();

        // and:
        prayerTable.view(firstPrayer);

        // then:
        PrayerAdministratorView prayerView = screenInteractions.findOpenScreen(PrayerAdministratorView.class);

        assertThat(prayerView.getEditedEntity())
                .isEqualTo(firstPrayer);
    }

}
