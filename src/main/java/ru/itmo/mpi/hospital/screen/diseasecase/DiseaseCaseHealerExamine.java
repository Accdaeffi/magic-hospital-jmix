package ru.itmo.mpi.hospital.screen.diseasecase;

import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.action.Action;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.mpi.hospital.entity.DiseaseCase;
import ru.itmo.mpi.hospital.entity.Prayer;
import ru.itmo.mpi.hospital.screen.prayer.PrayerHealerCreate;

@UiController("DiseaseCase.healer-examine")
@UiDescriptor("disease-case-healer-examine.xml")
@EditedEntityContainer("diseaseCaseDc")
public class DiseaseCaseHealerExamine extends StandardEditor<DiseaseCase> {

    @Autowired
    InstanceContainer<DiseaseCase> diseaseCaseDc;

    @Autowired
    ScreenBuilders screenBuilders;

    @Subscribe("createPrayer")
    public void onCreatePrayer(Action.ActionPerformedEvent event) {
        PrayerHealerCreate screen = screenBuilders.editor(Prayer.class, this)
                .withScreenClass(PrayerHealerCreate.class)
                .newEntity()
                .build();

        screen.setDiseaseCase(diseaseCaseDc.getItem());
        screen.show();
    }

}