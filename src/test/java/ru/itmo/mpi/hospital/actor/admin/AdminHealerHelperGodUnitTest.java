package ru.itmo.mpi.hospital.actor.admin;

import io.jmix.core.DataManager;
import io.jmix.ui.Screens;
import io.jmix.ui.testassist.junit.UiTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.mpi.hospital.entity.God;
import ru.itmo.mpi.hospital.entity.Healer;
import ru.itmo.mpi.hospital.entity.Helper;
import ru.itmo.mpi.hospital.screen.god.GodAdministratorBrowse;
import ru.itmo.mpi.hospital.screen.god.GodAdministratorView;
import ru.itmo.mpi.hospital.screen.healer.HealerAdministratorBrowse;
import ru.itmo.mpi.hospital.screen.healer.HealerAdministratorView;
import ru.itmo.mpi.hospital.screen.helper.HelperAdministratorView;
import ru.itmo.mpi.hospital.screen.helper.HelperAdminsitratorBrowse;
import ru.itmo.mpi.hospital.testsupport.ScreenInteractions;
import ru.itmo.mpi.hospital.testsupport.TableInteractions;
import ru.itmo.mpi.hospital.testsupport.WebIntegrationTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@UiTest(authenticatedUser = "regUserName1", mainScreenId = "MainScreen", screenBasePackages = "ru.itmo.mpi.hospital.screen")
public class AdminHealerHelperGodUnitTest extends WebIntegrationTest {

    @Autowired
    private DataManager dataManager;

    @Test
    void test_admin_healers_list(Screens screens) {
        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        HealerAdministratorBrowse healerBrowse = screenInteractions.open(HealerAdministratorBrowse.class);
        TableInteractions<Healer> healerTable = entityTable(healerBrowse, Healer.class, "healersTable");

        // expect:
        List<Healer> allFromPage = healerTable.allItems();
        List<Healer> allFromDb = dataManager.load(Healer.class).all().list();
        Assertions.assertThat(allFromDb).containsExactlyInAnyOrderElementsOf(allFromPage);
    }

    @Test
    void test_admin_open_healer(Screens screens) {
        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        HealerAdministratorBrowse healerBrowse = screenInteractions.open(HealerAdministratorBrowse.class);
        TableInteractions<Healer> healerTable = entityTable(healerBrowse, Healer.class, "healersTable");

        // and:
        Healer first = healerTable.firstItem();

        // and:
        healerTable.view(first);

        // then:
        HealerAdministratorView healerView = screenInteractions.findOpenScreen(HealerAdministratorView.class);

        assertThat(healerView.getEditedEntity())
                .isEqualTo(first);
    }

    @Test
    void test_admin_helper_list(Screens screens) {
        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        HelperAdminsitratorBrowse helperBrowse = screenInteractions.open(HelperAdminsitratorBrowse.class);
        TableInteractions<Helper> helpersTable = entityTable(helperBrowse, Helper.class, "helpersTable");

        // expect:
        List<Helper> allFromPage = helpersTable.allItems();
        List<Helper> allFromDb = dataManager.load(Helper.class).all().list();
        Assertions.assertThat(allFromDb).containsExactlyInAnyOrderElementsOf(allFromPage);
    }

    @Test
    void test_admin_open_helper(Screens screens) {
        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        HelperAdminsitratorBrowse helperBrowse = screenInteractions.open(HelperAdminsitratorBrowse.class);
        TableInteractions<Helper> helpersTable = entityTable(helperBrowse, Helper.class, "helpersTable");

        // and:
        Helper first = helpersTable.firstItem();

        // and:
        helpersTable.view(first);

        // then:
        HelperAdministratorView helperView = screenInteractions.findOpenScreen(HelperAdministratorView.class);

        assertThat(helperView.getEditedEntity())
                .isEqualTo(first);
    }

    @Test
    void test_admin_gods_list(Screens screens) {
        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        GodAdministratorBrowse godBrowse = screenInteractions.open(GodAdministratorBrowse.class);
        TableInteractions<God> godTable = entityTable(godBrowse, God.class, "godsTable");

        // expect:
        List<God> allFromPage = godTable.allItems();
        List<God> allFromDb = dataManager.load(God.class).all().list();
        Assertions.assertThat(allFromDb).containsExactlyInAnyOrderElementsOf(allFromPage);
    }

    @Test
    void test_admin_open_god(Screens screens) {
        // given:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        GodAdministratorBrowse godBrowse = screenInteractions.open(GodAdministratorBrowse.class);
        TableInteractions<God> godTable = entityTable(godBrowse, God.class, "godsTable");

        // and:
        God first = godTable.firstItem();

        // and:
        godTable.view(first);

        // then:
        GodAdministratorView godView = screenInteractions.findOpenScreen(GodAdministratorView.class);

        assertThat(godView.getEditedEntity())
                .isEqualTo(first);
    }


}
