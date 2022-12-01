package ru.itmo.mpi.hospital.actor.healer;

import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.Screens;
import io.jmix.ui.testassist.junit.UiTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.mpi.hospital.entity.Healer;
import ru.itmo.mpi.hospital.entity.Prayer;
import ru.itmo.mpi.hospital.screen.prayer.PrayerHealerBrowse;
import ru.itmo.mpi.hospital.screen.prayer.PrayerHealerView;
import ru.itmo.mpi.hospital.testsupport.ScreenInteractions;
import ru.itmo.mpi.hospital.testsupport.TableInteractions;
import ru.itmo.mpi.hospital.testsupport.WebIntegrationTest;
import ru.itmo.mpi.hospital.testsupport.testdata.PrayerTestData;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@UiTest(authenticatedUser = "healerUserName1", mainScreenId = "MainScreen", screenBasePackages = "ru.itmo.mpi.hospital.screen")
public class HealerPrayerUnitTest extends WebIntegrationTest {

    @Autowired
    private PrayerTestData prayerTestData;

    @Autowired
    private DataManager dataManager;

    @Autowired
    private CurrentAuthentication auth;

    @BeforeEach
    void setUp() {
        prayerTestData.loadDefault();
    }

    @AfterEach
    void tearDown() {
        prayerTestData.cleanup();
    }

    @Test
    void test_healer_prayers_list(Screens screens) {

        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        PrayerHealerBrowse prayerHealerBrowse = screenInteractions.open(PrayerHealerBrowse.class);
        TableInteractions<Prayer> prayerTable = entityTable(prayerHealerBrowse, Prayer.class, "prayersTable");
        Healer currentHealer = ((Healer) auth.getUser());

        // expect:
        assertThat(prayerTable.allItems().stream().allMatch(pr -> pr.getDiseaseCase().getHealer().equals(currentHealer))).isTrue();

        List<Prayer> allPrayersFromPage = prayerTable.allItems();
        List<Prayer> allPrayersFromDb = dataManager.load(Prayer.class).all().list().stream().filter(prayer -> prayer.getDiseaseCase().getHealer().equals(currentHealer)).collect(Collectors.toList());
        Assertions.assertThat(allPrayersFromDb).containsExactlyInAnyOrderElementsOf(allPrayersFromPage);
    }

    @Test
    void test_healer_open_prayer(Screens screens) {
        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        PrayerHealerBrowse prayerHealerBrowse = screenInteractions.open(PrayerHealerBrowse.class);
        TableInteractions<Prayer> prayerTable = entityTable(prayerHealerBrowse, Prayer.class, "prayersTable");

        // and:
        Prayer firstPrayer = prayerTable.firstItem();

        // and:
        prayerTable.view(firstPrayer);

        // then:
        PrayerHealerView prayerView = screenInteractions.findOpenScreen(PrayerHealerView.class);

        assertThat(prayerView.getEditedEntity())
                .isEqualTo(firstPrayer);
    }

}
