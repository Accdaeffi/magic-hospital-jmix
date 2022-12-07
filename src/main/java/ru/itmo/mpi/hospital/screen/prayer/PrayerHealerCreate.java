package ru.itmo.mpi.hospital.screen.prayer;

import io.jmix.core.DataManager;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.Target;
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

    @Autowired
    DataManager dataManager;

    private DiseaseCase diseaseCase;

    public void setDiseaseCase(DiseaseCase diseaseCase) {
        this.diseaseCase = diseaseCase;
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        prayerDc.getItem().setDiseaseCase(diseaseCase);
        prayerDc.getItem().setPrayerStatus(PrayerStatus.UNANSWERED);
    }

    @Subscribe(target = Target.DATA_CONTEXT)
    public void onPreCommit(DataContext.PreCommitEvent event) {
        DiseaseCase diseaseCase = prayerDc.getItem().getDiseaseCase();
        DiseaseCase diseaseCaseFromDb = dataManager.load(DiseaseCase.class).id(diseaseCase.getId()).one();

        if (diseaseCaseFromDb.getPrayer() != null) {
            disableCommitActions();
            event.preventCommit();
        }
    }


}