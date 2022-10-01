package ru.itmo.mpi.hospital.screen.diseasecase;

import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.Button;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.StandardOutcome;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.mpi.hospital.entity.DiseaseCase;
import ru.itmo.mpi.hospital.entity.DiseaseCaseState;
import ru.itmo.mpi.hospital.entity.Patient;
import ru.itmo.mpi.hospital.entity.PatientState;
import ru.itmo.mpi.hospital.entity.Prayer;
import ru.itmo.mpi.hospital.entity.Request;
import ru.itmo.mpi.hospital.screen.prayer.PrayerHealerCreate;
import ru.itmo.mpi.hospital.screen.request.RequestHealerCreate;

@UiController("DiseaseCase.healer-examine")
@UiDescriptor("disease-case-healer-examine.xml")
@EditedEntityContainer("diseaseCaseDc")
public class DiseaseCaseHealerExamine extends StandardEditor<DiseaseCase> {

    @Autowired
    InstanceContainer<DiseaseCase> diseaseCaseDc;

    @Autowired
    Button createPrayerBtn;

    @Autowired
    Button createRequestBtn;

    @Autowired
    ScreenBuilders screenBuilders;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        DiseaseCase diseaseCase = diseaseCaseDc.getItem();

        if (diseaseCase.getPrayer() != null) {
            createPrayerBtn.setEnabled(false);
        }

        if (diseaseCase.getRequest() != null) {
            createRequestBtn.setEnabled(false);
        }
    }


    @Subscribe("createPrayer")
    public void onCreatePrayer(Action.ActionPerformedEvent event) {
        PrayerHealerCreate screen = screenBuilders.editor(Prayer.class, this)
                .withScreenClass(PrayerHealerCreate.class)
                .withAfterCloseListener(afterCloseEvent -> {
                    if (afterCloseEvent.closedWith(StandardOutcome.COMMIT)) {
                        createPrayerBtn.setEnabled(false);
                    }
                })
                .newEntity()
                .build();

        screen.setDiseaseCase(diseaseCaseDc.getItem());
        screen.show();
    }

    @Subscribe("createRequest")
    public void onCreateRequest(Action.ActionPerformedEvent event) {
        RequestHealerCreate screen = screenBuilders.editor(Request.class, this)
                .withScreenClass(RequestHealerCreate.class)
                .withAfterCloseListener(afterCloseEvent -> {
                    if (afterCloseEvent.closedWith(StandardOutcome.COMMIT)) {
                        createRequestBtn.setEnabled(false);
                    }
                })
                .newEntity()
                .build();

        screen.setDiseaseCase(diseaseCaseDc.getItem());
        screen.show();
    }

    @Subscribe("recordRecovery")
    public void onRecordRecovery(Action.ActionPerformedEvent event) {
        DiseaseCase diseaseCase = diseaseCaseDc.getItem();
        Patient patient = diseaseCase.getPatient();

        patient.setPatientState(PatientState.HEALTHY);
        diseaseCase.setDiseaseCaseState(DiseaseCaseState.RECOVERY);

        closeWithCommit();
    }

    @Subscribe("recordDeath")
    public void onRecordDeath(Action.ActionPerformedEvent event) {
        DiseaseCase diseaseCase = diseaseCaseDc.getItem();
        Patient patient = diseaseCase.getPatient();

        patient.setPatientState(PatientState.DISEASED);
        diseaseCase.setDiseaseCaseState(DiseaseCaseState.DEATH);

        closeWithCommit();
    }

}