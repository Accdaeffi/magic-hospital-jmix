package ru.itmo.mpi.hospital.screen.security;

import io.jmix.core.DataManager;
import io.jmix.core.security.AccessDeniedException;
import io.jmix.securityui.screen.resourcerole.ResourceRoleModelLookup;
import io.jmix.securityui.screen.roleassignment.RoleAssignmentScreen;
import io.jmix.securityui.screen.rowlevelrole.RowLevelRoleModelLookup;
import io.jmix.ui.Screens;
import io.jmix.ui.testassist.junit.UiTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.mpi.hospital.entity.Administrator;
import ru.itmo.mpi.hospital.entity.DiseaseCase;
import ru.itmo.mpi.hospital.entity.God;
import ru.itmo.mpi.hospital.entity.Healer;
import ru.itmo.mpi.hospital.entity.Helper;
import ru.itmo.mpi.hospital.entity.Patient;
import ru.itmo.mpi.hospital.entity.Prayer;
import ru.itmo.mpi.hospital.entity.Request;
import ru.itmo.mpi.hospital.screen.administrator.AdministratorBrowse;
import ru.itmo.mpi.hospital.screen.administrator.AdministratorEdit;
import ru.itmo.mpi.hospital.screen.disease.DiseaseHealerBrowse;
import ru.itmo.mpi.hospital.screen.diseasecase.DiseaseCaseAdministratorBrowse;
import ru.itmo.mpi.hospital.screen.diseasecase.DiseaseCaseAdministratorEdit;
import ru.itmo.mpi.hospital.screen.diseasecase.DiseaseCaseHealerBrowse;
import ru.itmo.mpi.hospital.screen.diseasecase.DiseaseCaseHealerExamine;
import ru.itmo.mpi.hospital.screen.god.GodAdministratorBrowse;
import ru.itmo.mpi.hospital.screen.god.GodAdministratorView;
import ru.itmo.mpi.hospital.screen.god.GodBrowse;
import ru.itmo.mpi.hospital.screen.god.GodEdit;
import ru.itmo.mpi.hospital.screen.god.GodHealerBrowse;
import ru.itmo.mpi.hospital.screen.healer.HealerAdministratorBrowse;
import ru.itmo.mpi.hospital.screen.healer.HealerAdministratorView;
import ru.itmo.mpi.hospital.screen.healer.HealerBrowse;
import ru.itmo.mpi.hospital.screen.healer.HealerEdit;
import ru.itmo.mpi.hospital.screen.helper.HelperAdministratorView;
import ru.itmo.mpi.hospital.screen.helper.HelperAdminsitratorBrowse;
import ru.itmo.mpi.hospital.screen.helper.HelperBrowse;
import ru.itmo.mpi.hospital.screen.helper.HelperEdit;
import ru.itmo.mpi.hospital.screen.helper.HelperHealerBrowse;
import ru.itmo.mpi.hospital.screen.patient.PatientAdministratorBrowse;
import ru.itmo.mpi.hospital.screen.patient.PatientAdministratorDiseasedBrowse;
import ru.itmo.mpi.hospital.screen.patient.PatientAdministratorEdit;
import ru.itmo.mpi.hospital.screen.patient.PatientAdministratorHealthy;
import ru.itmo.mpi.hospital.screen.prayer.PrayerAdministratorBrowse;
import ru.itmo.mpi.hospital.screen.prayer.PrayerAdministratorView;
import ru.itmo.mpi.hospital.screen.prayer.PrayerGodBrowse;
import ru.itmo.mpi.hospital.screen.prayer.PrayerGodView;
import ru.itmo.mpi.hospital.screen.prayer.PrayerHealerBrowse;
import ru.itmo.mpi.hospital.screen.prayer.PrayerHealerCreate;
import ru.itmo.mpi.hospital.screen.prayer.PrayerHealerView;
import ru.itmo.mpi.hospital.screen.prayer.PrayerResolver;
import ru.itmo.mpi.hospital.screen.prayer.PrayerUnansweredBrowse;
import ru.itmo.mpi.hospital.screen.request.RequestAdministratorBrowse;
import ru.itmo.mpi.hospital.screen.request.RequestAdministratorView;
import ru.itmo.mpi.hospital.screen.request.RequestHealerBrowse;
import ru.itmo.mpi.hospital.screen.request.RequestHealerCreate;
import ru.itmo.mpi.hospital.screen.request.RequestHealerEdit;
import ru.itmo.mpi.hospital.screen.request.RequestHelperBrowse;
import ru.itmo.mpi.hospital.screen.request.RequestHelperProcess;
import ru.itmo.mpi.hospital.testsupport.ScreenInteractions;
import ru.itmo.mpi.hospital.testsupport.WebIntegrationTest;

@UiTest(authenticatedUser = "regUserName1", mainScreenId = "MainScreen", screenBasePackages = "ru.itmo.mpi.hospital.screen")
public class AdministratorSecurityTest extends WebIntegrationTest {

    @Autowired
    private DataManager dataManager;

    @Test
    void when_openAdminBrowseDiseasedPatients_then_opened(Screens screens) {
        // when:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        screenInteractions.open(PatientAdministratorDiseasedBrowse.class);
    }

    @Test
    void when_openAdminBrowseHealthyPatients_then_opened(Screens screens) {
        // when:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        screenInteractions.open(PatientAdministratorHealthy.class);
    }

    @Test
    void when_openAdminBrowsePatients_then_opened(Screens screens) {
        // when:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        screenInteractions.open(PatientAdministratorBrowse.class);
    }

    @Test
    void when_openAdminEditPatient_then_opened(Screens screens) {
        // when:
        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        screenInteractions.openEditorForCreation(PatientAdministratorEdit.class, Patient.class);
    }

    //"DiseaseCase.administrator-browse", "DiseaseCase.administrator-edit"

    @Test
    void when_openAdminDiseaseCaseBrowse_then_opened(Screens screens) {
        // when:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        screenInteractions.open(DiseaseCaseAdministratorBrowse.class);
    }

    @Test
    void when_openAdminDiseaseCaseEdit_then_opened(Screens screens) {
        // when:
        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        screenInteractions.openEditorForCreation(DiseaseCaseAdministratorEdit.class, DiseaseCase.class);
    }

    //"Prayer.administrator-browse", "Prayer.administrator-view"

    @Test
    void when_openAdminPrayerBrowse_then_opened(Screens screens) {
        // when:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        screenInteractions.open(PrayerAdministratorBrowse.class);
    }

    @Test
    void when_openAdminPrayerView_then_opened(Screens screens) {
        // when:
        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        screenInteractions.openEditorForCreation(PrayerAdministratorView.class, Prayer.class);
    }

    //"Request.administrator-browse", "Request.administrator-view"

    @Test
    void when_openAdminRequestBrowse_then_opened(Screens screens) {
        // when:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        screenInteractions.open(RequestAdministratorBrowse.class);
    }

    @Test
    void when_openAdminRequestView_then_opened(Screens screens) {
        // when:
        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        screenInteractions.openEditorForCreation(RequestAdministratorView.class, Request.class);
    }

    //"Helper.administrator-browse", "Helper.administrator-view"

    @Test
    void when_openAdminHelperBrowse_then_opened(Screens screens) {
        // when:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        screenInteractions.open(HelperAdminsitratorBrowse.class);
    }

    @Test
    void when_openAdminHelperView_then_opened(Screens screens) {
        // when:
        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        screenInteractions.openEditorForCreation(HelperAdministratorView.class, Helper.class);
    }

    //"God.administrator-browse", "God.administrator-view"

    @Test
    void when_openAdminGodBrowse_then_opened(Screens screens) {
        // when:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        screenInteractions.open(GodAdministratorBrowse.class);
    }

    @Test
    void when_openAdminGodView_then_opened(Screens screens) {
        // when:
        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        screenInteractions.openEditorForCreation(GodAdministratorView.class, God.class);
    }

    //"Healer.administrator-browse", "Healer.administrator-view"

    @Test
    void when_openAdminHealerBrowse_then_opened(Screens screens) {
        // when:
        ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
        screenInteractions.open(HealerAdministratorBrowse.class);
    }

    @Test
    void when_openAdminHealerView_then_opened(Screens screens) {
        // when:
        ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
        screenInteractions.openEditorForCreation(HealerAdministratorView.class, Healer.class);
    }

    //----------------------------------------
    //--             negative               --
    //----------------------------------------

    //"Prayer.god-browse", "PrayerUnanswered.browse", "Prayer.god-view", "Prayer.resolve"

    @Test
    void when_openAdminPrayerGodBrowse_then_failed(Screens screens) {
        // when:
        try {
            ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
            screenInteractions.open(PrayerGodBrowse.class);
            Assertions.fail("No exception thrown!");
        } catch (AccessDeniedException ignored) {
        }
    }

    @Test
    void when_openAdminPrayerUnansweredBrowse_then_failed(Screens screens) {
        // when:
        try {
            ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
            screenInteractions.open(PrayerUnansweredBrowse.class);
            Assertions.fail("No exception thrown!");
        } catch (AccessDeniedException ignored) {
        }
    }

    @Test
    void when_openAdminPrayerGodView_then_failed(Screens screens) {
        // when:
        try {
            ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
            screenInteractions.openEditorForCreation(PrayerGodView.class, Prayer.class);
            Assertions.fail("No exception thrown!");
        } catch (AccessDeniedException ignored) {
        }
    }

    @Test
    void when_openAdminPrayerGodResolve_then_failed(Screens screens) {
        // when:
        try {
            ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
            screenInteractions.openEditorForCreation(PrayerResolver.class, Prayer.class);
            Assertions.fail("No exception thrown!");
        } catch (AccessDeniedException ignored) {
        }
    }

    //"Prayer.healer-create", "Prayer.healer-browse", "Prayer.healer-view"

    @Test
    void when_openAdminPrayerHealerCreate_then_failed(Screens screens) {
        // when:
        try {
            ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
            screenInteractions.openEditorForCreation(PrayerHealerCreate.class, Prayer.class);
            Assertions.fail("No exception thrown!");
        } catch (AccessDeniedException ignored) {
        }
    }

    @Test
    void when_openAdminPrayerHealerBrowse_then_failed(Screens screens) {
        // when:
        try {
            ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
            screenInteractions.open(PrayerHealerBrowse.class);
            Assertions.fail("No exception thrown!");
        } catch (AccessDeniedException ignored) {
        }
    }

    @Test
    void when_openAdminPrayerHealerView_then_failed(Screens screens) {
        // when:
        try {
            ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
            screenInteractions.openEditorForCreation(PrayerHealerView.class, Prayer.class);
            Assertions.fail("No exception thrown!");
        } catch (AccessDeniedException ignored) {
        }
    }

    //"DiseaseCase.healer-examine", "DiseaseCase.healer-browse"

    @Test
    void when_openAdminDiseaseCaseHealerBrowse_then_failed(Screens screens) {
        // when:
        try {
            ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
            screenInteractions.open(DiseaseCaseHealerBrowse.class);
            Assertions.fail("No exception thrown!");
        } catch (AccessDeniedException ignored) {
        }
    }

    @Test
    void when_openAdminDiseaseCaseHealerExamine_then_failed(Screens screens) {
        // when:
        try {
            ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
            screenInteractions.openEditorForCreation(DiseaseCaseHealerExamine.class, DiseaseCase.class);
            Assertions.fail("No exception thrown!");
        } catch (AccessDeniedException ignored) {
        }
    }

    // "Request.healer-create", "Request.healer-browse", "Request.healer-edit"

    @Test
    void when_openAdminRequestHealerCreate_then_failed(Screens screens) {
        // when:
        try {
            ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
            screenInteractions.openEditorForCreation(RequestHealerCreate.class, Request.class);
            Assertions.fail("No exception thrown!");
        } catch (AccessDeniedException ignored) {
        }
    }

    @Test
    void when_openAdminRequestHealerBrowse_then_failed(Screens screens) {
        // when:
        try {
            ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
            screenInteractions.open(RequestHealerBrowse.class);
            Assertions.fail("No exception thrown!");
        } catch (AccessDeniedException ignored) {
        }
    }

    @Test
    void when_openAdminRequestHealerEdit_then_failed(Screens screens) {
        // when:
        try {
            ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
            screenInteractions.openEditorForCreation(RequestHealerEdit.class, Request.class);
            Assertions.fail("No exception thrown!");
        } catch (AccessDeniedException ignored) {
        }
    }

    // "Helper.healer-browse"

    @Test
    void when_openAdminHelperHealerBrowse_then_failed(Screens screens) {
        // when:
        try {
            ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
            screenInteractions.open(HelperHealerBrowse.class);
            Assertions.fail("No exception thrown!");
        } catch (AccessDeniedException ignored) {
        }
    }

    // "God.healer-browse"

    @Test
    void when_openAdminGodHealerBrowse_then_failed(Screens screens) {
        // when:
        try {
            ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
            screenInteractions.open(GodHealerBrowse.class);
            Assertions.fail("No exception thrown!");
        } catch (AccessDeniedException ignored) {
        }
    }

    // "Disease.healer-browse"

    @Test
    void when_openAdminDiseaseHealerBrowse_then_failed(Screens screens) {
        // when:
        try {
            ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
            screenInteractions.open(DiseaseHealerBrowse.class);
            Assertions.fail("No exception thrown!");
        } catch (AccessDeniedException ignored) {
        }
    }

    // "Request.helper-browse", "Request.helper-process"

    @Test
    void when_openAdminRequestHelperBrowse_then_failed(Screens screens) {
        // when:
        try {
            ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
            screenInteractions.open(RequestHelperBrowse.class);
            Assertions.fail("No exception thrown!");
        } catch (AccessDeniedException ignored) {
        }
    }

    @Test
    void when_openAdminRequestHelperProcess_then_failed(Screens screens) {
        // when:
        try {
            ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
            screenInteractions.openEditorForCreation(RequestHelperProcess.class, Request.class);
            Assertions.fail("No exception thrown!");
        } catch (AccessDeniedException ignored) {
        }
    }

    // security

    //"Healer.browse", "Healer.edit"

    @Test
    void when_openAdminHealerBrowse_then_failed(Screens screens) {
        // when:
        try {
            ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
            screenInteractions.open(HealerBrowse.class);
            Assertions.fail("No exception thrown!");
        } catch (AccessDeniedException ignored) {
        }
    }

    @Test
    void when_openAdminHealerEdit_then_failed(Screens screens) {
        // when:
        try {
            ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
            screenInteractions.openEditorForCreation(HealerEdit.class, Healer.class);
            Assertions.fail("No exception thrown!");
        } catch (AccessDeniedException ignored) {
        }
    }

    //"Helper.browse", "Helper.edit"

    @Test
    void when_openAdminHelperBrowse_then_failed(Screens screens) {
        // when:
        try {
            ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
            screenInteractions.open(HelperBrowse.class);
            Assertions.fail("No exception thrown!");
        } catch (AccessDeniedException ignored) {
        }
    }

    @Test
    void when_openAdminHelperEdit_then_failed(Screens screens) {
        // when:
        try {
            ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
            screenInteractions.openEditorForCreation(HelperEdit.class, Helper.class);
            Assertions.fail("No exception thrown!");
        } catch (AccessDeniedException ignored) {
        }
    }

    //"Administrator.browse", "Administrator.edit"

    @Test
    void when_openAdminAdministratorBrowse_then_failed(Screens screens) {
        // when:
        try {
            ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
            screenInteractions.open(AdministratorBrowse.class);
            Assertions.fail("No exception thrown!");
        } catch (AccessDeniedException ignored) {
        }
    }

    @Test
    void when_openAdminAdministratorEdit_then_failed(Screens screens) {
        // when:
        try {
            ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
            screenInteractions.openEditorForCreation(AdministratorEdit.class, Administrator.class);
            Assertions.fail("No exception thrown!");
        } catch (AccessDeniedException ignored) {
        }
    }

    //"God.browse", "God.edit"

    @Test
    void when_openAdminGodBrowse_then_failed(Screens screens) {
        // when:
        try {
            ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
            screenInteractions.open(GodBrowse.class);
            Assertions.fail("No exception thrown!");
        } catch (AccessDeniedException ignored) {
        }
    }

    @Test
    void when_openAdminGodEdit_then_failed(Screens screens) {
        // when:
        try {
            ScreenInteractions screenInteractions = ScreenInteractions.forEditor(screens, dataManager);
            screenInteractions.openEditorForCreation(GodEdit.class, God.class);
            Assertions.fail("No exception thrown!");
        } catch (AccessDeniedException ignored) {
        }
    }

    //"sec_RoleAssignmentScreen", "sec_ResourceRoleModel.lookup", "sec_RowLevelRoleModel.lookup"

    @Test
    void when_openAdminRoleAssignmentScreen_then_failed(Screens screens) {
        // when:
        try {
            ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
            screenInteractions.open(RoleAssignmentScreen.class);
            Assertions.fail("No exception thrown!");
        } catch (AccessDeniedException ignored) {
        }
    }

    @Test
    void when_openAdminResourceRoleModelLookup_then_failed(Screens screens) {
        // when:
        try {
            ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
            screenInteractions.open(ResourceRoleModelLookup.class);
            Assertions.fail("No exception thrown!");
        } catch (AccessDeniedException ignored) {
        }
    }

    @Test
    void when_openAdminRowLevelRoleModelLookup_then_failed(Screens screens) {
        // when:
        try {
            ScreenInteractions screenInteractions = ScreenInteractions.forBrowse(screens);
            screenInteractions.open(RowLevelRoleModelLookup.class);
            Assertions.fail("No exception thrown!");
        } catch (AccessDeniedException ignored) {
        }
    }


}
