package ru.itmo.mpi.hospital.screen.prayer;

import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.mpi.hospital.entity.DiseaseCase;
import ru.itmo.mpi.hospital.entity.Prayer;
import ru.itmo.mpi.hospital.entity.PrayerStatus;

@UiController("Prayer.healer-create")
@UiDescriptor("prayer-healer-create.xml")
@EditedEntityContainer("prayerDc")
public class PrayerHealerCreate extends StandardEditor<Prayer> {

    @Autowired
    InstanceContainer<Prayer> prayerDc;

    private DiseaseCase diseaseCase;

    public void setDiseaseCase(DiseaseCase diseaseCase) {
        this.diseaseCase = diseaseCase;
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        prayerDc.getItem().setDiseaseCase(diseaseCase);
        prayerDc.getItem().setPrayerStatus(PrayerStatus.UNANSWERED);
    }


}