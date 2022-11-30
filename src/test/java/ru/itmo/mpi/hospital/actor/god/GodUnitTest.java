package ru.itmo.mpi.hospital.actor.god;

import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.Screens;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.testassist.junit.UiTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.mpi.hospital.entity.God;
import ru.itmo.mpi.hospital.entity.Prayer;
import ru.itmo.mpi.hospital.entity.PrayerStatus;
import ru.itmo.mpi.hospital.screen.prayer.PrayerGodBrowse;
import ru.itmo.mpi.hospital.screen.prayer.PrayerGodView;
import ru.itmo.mpi.hospital.screen.prayer.PrayerResolver;
import ru.itmo.mpi.hospital.screen.prayer.PrayerUnansweredBrowse;
import ru.itmo.mpi.hospital.testsupport.ScreenInteractions;
import ru.itmo.mpi.hospital.testsupport.TableInteractions;
import ru.itmo.mpi.hospital.testsupport.WebIntegrationTest;
import ru.itmo.mpi.hospital.testsupport.testdata.PrayerTestData;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@UiTest(authenticatedUser = "godUserName1", mainScreenId = "MainScreen", screenBasePackages = "ru.itmo.mpi.hospital.screen")
public class GodUnitTest extends WebIntegrationTest {

    private Prayer prayer;
    @Autowired
    private PrayerTestData prayers;

    @Autowired
    private DataManager dataManager;

    @Autowired
    private CurrentAuthentication auth;

    @BeforeEach
    void setUp() {

        prayers.loadDefault();
        prayer = prayers.prayers.get(0);

    }

    @Test
    void test_gods_prayers_list(Screens screens) {

        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        PrayerGodBrowse prayerGodBrowse = screenInteractions.open(PrayerGodBrowse.class);
        TableInteractions<Prayer> prayerGodTable = entityTable(prayerGodBrowse, Prayer.class, "prayersTable");
        God currentGod = ((God) auth.getUser());

        // expect:
        assertThat(prayerGodTable.allItems().stream().allMatch(pr -> pr.getGod().equals(currentGod))).isTrue();

        List<Prayer> allPrayersFromPage = prayerGodTable.allItems();
        List<Prayer> allPrayersFromDb = dataManager.load(Prayer.class).all().list().stream().filter(prayer -> prayer.getGod().equals(currentGod)).collect(Collectors.toList());
        Assertions.assertThat(allPrayersFromDb).containsExactlyInAnyOrderElementsOf(allPrayersFromPage);


    }


    @Test
    void test_unanswered_gods_prayers_list(Screens screens) {

        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        PrayerUnansweredBrowse prayerUnansweredBrowse = screenInteractions.open(PrayerUnansweredBrowse.class);
        TableInteractions<Prayer> prayerUnansweredTable = entityTable(prayerUnansweredBrowse, Prayer.class, "prayersTable");
        God currentGod = ((God) auth.getUser());

        // expect:
        assertThat(prayerUnansweredTable.allItems().stream().allMatch(pr -> pr.getGod().equals(currentGod))).isTrue();
        assertThat(prayerUnansweredTable.allItems()
                .stream()
                .allMatch(pr ->
                        pr.getPrayerStatus().equals(PrayerStatus.UNANSWERED)
                )).isTrue();


        List<Prayer> allPrayersFromPage = prayerUnansweredTable.allItems();
        List<Prayer> allPrayersFromDb = dataManager.load(Prayer.class)
                .all().list().stream()
                .filter(prayer -> prayer.getGod().equals(currentGod))
                .filter(prayer -> prayer.getPrayerStatus().equals(PrayerStatus.UNANSWERED))
                .collect(Collectors.toList());
        Assertions.assertThat(allPrayersFromDb).containsExactlyInAnyOrderElementsOf(allPrayersFromPage);


    }


    @Test
    void test_current_prayer(Screens screens) {
        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        PrayerGodBrowse prayerBrowse = screenInteractions.open(PrayerGodBrowse.class);
        TableInteractions<Prayer> prayerTable = entityTable(prayerBrowse, Prayer.class, "prayersTable");

        // and:
        Prayer firstPrayer = prayerTable.firstItem();

        // and:
        prayerTable.view(firstPrayer);

        // then:
        PrayerGodView prayerEdit = screenInteractions.findOpenScreen(PrayerGodView.class);

        assertThat(prayerEdit.getEditedEntity())
                .isEqualTo(firstPrayer);
    }

    @Test
    void test_unanswered_current_prayer(Screens screens) {
        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        PrayerUnansweredBrowse prayerUnansweredBrowse = screenInteractions.open(PrayerUnansweredBrowse.class);
        TableInteractions<Prayer> prayerUnansweredTable = entityTable(prayerUnansweredBrowse, Prayer.class, "prayersTable");
        God currentGod = ((God) auth.getUser());
        // and:
        Prayer firstPrayer = prayerUnansweredTable.firstItem();

        // and:
        prayerUnansweredTable.selectItem(firstPrayer.getId());
        //prayerUnansweredTable.button("viewBtn").click();
        prayerUnansweredTable.view(firstPrayer);

        // then:
        PrayerResolver prayerEdit = screenInteractions.findOpenScreen(PrayerResolver.class);

        assertThat(prayerEdit.getEditedEntity())
                .isEqualTo(firstPrayer);
    }


    @Test
    void test_accept_unanswered_prayer(Screens screens) {

        // given:
        God currentGod = (God) auth.getUser();
        Prayer unansweredPrayer = prayers.prayers.stream()
                .filter(prayer -> prayer.getGod().equals(currentGod))
                .filter(prayer -> prayer.getPrayerStatus().equals(PrayerStatus.UNANSWERED))
                .findFirst().orElse(null);

        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        PrayerResolver prayerResolver = screenInteractions.openEditorForEditing(PrayerResolver.class, Prayer.class, unansweredPrayer);

        // when:
        ((Button) (prayerResolver.getWindow().getComponent("acceptBtn"))).click();

        // then:
        Prayer answeredPrayer = dataManager.load(Prayer.class).id(unansweredPrayer.getId()).one();

        assertThat(answeredPrayer.getPrayerStatus()).isEqualTo(PrayerStatus.ACCEPTED);
    }

    @Test
    void test_decline_unanswered_prayer(Screens screens) {
        // given:
        God currentGod = (God) auth.getUser();
        Prayer unansweredPrayer = prayers.prayers.stream()
                .filter(prayer -> prayer.getGod().equals(currentGod))
                .filter(prayer -> prayer.getPrayerStatus().equals(PrayerStatus.UNANSWERED))
                .findFirst().orElse(null);

        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        PrayerResolver prayerResolver = screenInteractions.openEditorForEditing(PrayerResolver.class, Prayer.class, unansweredPrayer);

        // when:
        ((Button) (prayerResolver.getWindow().getComponent("rejectBtn"))).click();

        // then:
        Prayer answeredPrayer = dataManager.load(Prayer.class).id(unansweredPrayer.getId()).one();

        assertThat(answeredPrayer.getPrayerStatus()).isEqualTo(PrayerStatus.REJECTED);
    }



    @NotNull
    private <T> TableInteractions<T> entityTable(StandardLookup<T> browseScreen, Class<T> clazz, String componentId) {
        return TableInteractions.of(browseScreen, clazz, componentId);
    }

    @AfterEach
    void tearDown() {
        prayers.cleanup();
    }

}
