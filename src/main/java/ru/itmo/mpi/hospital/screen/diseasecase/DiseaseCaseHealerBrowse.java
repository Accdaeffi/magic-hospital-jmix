package ru.itmo.mpi.hospital.screen.diseasecase;

import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.action.Action;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.mpi.hospital.entity.DiseaseCase;

@UiController("DiseaseCase.healer-browse")
@UiDescriptor("disease-case-healer-browse.xml")
@LookupComponent("diseaseCasesTable")
public class DiseaseCaseHealerBrowse extends StandardLookup<DiseaseCase> {

    @Autowired
    private CollectionContainer<DiseaseCase> diseaseCasesDc;

    @Autowired
    private CollectionLoader<DiseaseCase> diseaseCasesDl;

    @Autowired
    ScreenBuilders screenBuilders;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        diseaseCasesDl.load();
    }

    @Subscribe("diseaseCasesTable.examine")
    public void onViewPrayer(Action.ActionPerformedEvent event) {

        screenBuilders.editor(DiseaseCase.class, this)
                .withScreenClass(DiseaseCaseHealerExamine.class)
                .editEntity(diseaseCasesDc.getItem())
                .build().show();
    }

}