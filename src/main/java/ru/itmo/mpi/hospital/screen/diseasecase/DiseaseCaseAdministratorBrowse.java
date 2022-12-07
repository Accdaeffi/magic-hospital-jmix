package ru.itmo.mpi.hospital.screen.diseasecase;

import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.mpi.hospital.entity.DiseaseCase;

@UiController("DiseaseCase.administrator-browse")
@UiDescriptor("disease-case-administrator-browse.xml")
@LookupComponent("diseaseCasesTable")
public class DiseaseCaseAdministratorBrowse extends StandardLookup<DiseaseCase> {

    @Autowired
    private CollectionLoader<DiseaseCase> diseaseCasesDl;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        diseaseCasesDl.load();
    }

}