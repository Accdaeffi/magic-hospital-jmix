package ru.itmo.mpi.hospital.screen;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import ru.itmo.mpi.hospital.entity.Prayer;
import ru.itmo.mpi.hospital.screen.prayer.PrayerBrowse;
import ru.itmo.mpi.hospital.screen.prayer.PrayerEdit;
import ru.itmo.mpi.hospital.testsupport.ScreenInteractions;
import ru.itmo.mpi.hospital.testsupport.TableInteractions;
import ru.itmo.mpi.hospital.testsupport.WebIntegrationTest;
import ru.itmo.mpi.hospital.testsupport.testdata.PrayerTestData;
import io.jmix.ui.Screens;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;


class PrayerBrowseTest extends WebIntegrationTest {

    private Prayer prayer;
    @Autowired
    private PrayerTestData prayers;


    @BeforeEach
    void setUp() {
        prayers.loadData();
        prayer = prayers.prayers.get(0);
    }

    @AfterEach
    void tearDown() {
        prayers.unloadData();
    }

    @Test
    void given_oneCustomerExists_when_openCustomerBrowse_then_tableContainsTheCustomer(Screens screens) {

        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        PrayerBrowse customerBrowse = screenInteractions.open(PrayerBrowse.class);
        TableInteractions<Prayer> customerTable = customerTable(customerBrowse);

        // expect:
        Assertions.assertEquals(customerTable.firstItem().getPrayText(), prayer.getPrayText());

        /*assertThat(customerTable.firstItem())
                .isEqualTo(prayer);*/


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

    @NotNull
    private TableInteractions<Prayer> customerTable(PrayerBrowse customerBrowse) {
        return TableInteractions.of(customerBrowse, Prayer.class, "prayersTable");
    }
}
