package ru.itmo.mpi.hospital.screen;

import io.jmix.core.DataManager;
import io.jmix.ui.Screens;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.mpi.hospital.entity.Prayer;
import ru.itmo.mpi.hospital.screen.prayer.PrayerBrowse;
import ru.itmo.mpi.hospital.screen.prayer.PrayerEdit;
import ru.itmo.mpi.hospital.testsupport.ScreenInteractions;
import ru.itmo.mpi.hospital.testsupport.TableInteractions;
import ru.itmo.mpi.hospital.testsupport.WebIntegrationTest;
import ru.itmo.mpi.hospital.testsupport.testdata.PrayerTestData;

import javax.validation.constraints.NotNull;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;


class PrayerBrowseTest extends WebIntegrationTest {

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
    void given_oneCustomerExists_when_openCustomerBrowse_then_tableContainsTheCustomer(Screens screens) {

        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        PrayerBrowse customerBrowse = screenInteractions.open(PrayerBrowse.class);
        TableInteractions<Prayer> customerTable = customerTable(customerBrowse);

        System.out.println(customerTable.firstItem().getPrayText());
        System.out.println(prayer.getPrayText());

        // expect:
        assertTrue(customerTable.firstItem().getPrayText().equals(prayer.getPrayText()));

        assertThat(customerTable.firstItem())
                .isEqualTo(prayer);


    }


    @Test
    void given_oneCustomerExists_when_editCustomer_then_editCustomerEditorIsShown(Screens screens) {

        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        PrayerBrowse customerBrowse = screenInteractions.open(PrayerBrowse.class);
        TableInteractions<Prayer> customerTable = customerTable(customerBrowse);

        // and:
        Prayer firstCustomer = customerTable.firstItem();

        // and:
        customerTable.edit(firstCustomer);

        // then:
        PrayerEdit customerEdit = screenInteractions.findOpenScreen(PrayerEdit.class);

        assertThat(customerEdit.getEditedEntity())
                .isEqualTo(firstCustomer);
    }

    /*@AfterEach
    void tearDown() {
        dataManager.remove(prayer);
    }*/

    @NotNull
    private TableInteractions<Prayer> customerTable(PrayerBrowse customerBrowse) {
        return TableInteractions.of(customerBrowse, Prayer.class, "prayersTable");
    }
}
