package ru.itmo.mpi.hospital.screen.prayer;

import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.mpi.hospital.entity.DiseaseCase;
import ru.itmo.mpi.hospital.entity.Prayer;

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
    }
}