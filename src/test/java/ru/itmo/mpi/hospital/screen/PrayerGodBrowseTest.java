package ru.itmo.mpi.hospital.screen;

import io.jmix.core.DataManager;
import io.jmix.ui.Screens;
import io.jmix.ui.testassist.junit.UiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.mpi.hospital.entity.Prayer;
import ru.itmo.mpi.hospital.screen.prayer.PrayerEdit;
import ru.itmo.mpi.hospital.screen.prayer.PrayerGodBrowse;
import ru.itmo.mpi.hospital.testsupport.ScreenInteractions;
import ru.itmo.mpi.hospital.testsupport.TableInteractions;
import ru.itmo.mpi.hospital.testsupport.WebIntegrationTest;
import ru.itmo.mpi.hospital.testsupport.testdata.PrayerTestData;

import javax.validation.constraints.NotNull;

import static org.assertj.core.api.Assertions.assertThat;

@UiTest(authenticatedUser = "godUserName1", mainScreenId = "MainScreen", screenBasePackages = "ru.itmo.mpi.hospital.screen")
public class PrayerGodBrowseTest extends WebIntegrationTest {

    private Prayer prayer;
    @Autowired
    private PrayerTestData prayers;

    @Autowired
    private DataManager dataManager;


    @BeforeEach
    void setUp() {

        /*prayer = dataManager.create(Prayer.class);

        prayer.setPrayText("test");

        prayer = dataManager.save(prayer);*/
        prayer = prayers.prayers.get(0);

    }

    @Test
    void given_onePrayerExists_when_openPrayerBrowse_then_tableContainsThePrayer(Screens screens) {

        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        PrayerGodBrowse prayerBrowse = screenInteractions.open(PrayerGodBrowse.class);
        TableInteractions<Prayer> prayerTable = entityTable(prayerBrowse);

        System.out.println(prayerTable.firstItem().getPrayText());
        System.out.println(prayer.getPrayText());

        // expect:
        assertThat(prayerTable.firstItem())
                .isEqualTo(prayer);


    }


    @Test
    void given_onePrayerExists_when_editPrayer_then_editPrayerEditorIsShown(Screens screens) {

        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        PrayerGodBrowse prayerBrowse = screenInteractions.open(PrayerGodBrowse.class);
        TableInteractions<Prayer> prayerTable = entityTable(prayerBrowse);

        // and:
        Prayer firstPrayer = prayerTable.firstItem();

        // and:
        prayerTable.view(firstPrayer);

        // then:
        PrayerEdit prayerEdit = screenInteractions.findOpenScreen(PrayerEdit.class);

        assertThat(prayerEdit.getEditedEntity())
                .isEqualTo(firstPrayer);
    }

    @NotNull
    private TableInteractions<Prayer> entityTable(PrayerGodBrowse browseScreen) {
        return TableInteractions.of(browseScreen, Prayer.class, "prayersTable");
    }

}
