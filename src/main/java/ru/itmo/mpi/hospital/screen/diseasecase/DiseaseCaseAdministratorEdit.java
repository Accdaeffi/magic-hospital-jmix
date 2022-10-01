package ru.itmo.mpi.hospital.screen.diseasecase;

import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.mpi.hospital.entity.Administrator;
import ru.itmo.mpi.hospital.entity.DiseaseCase;
import ru.itmo.mpi.hospital.entity.DiseaseCaseState;

@UiController("DiseaseCase.administrator-edit")
@UiDescriptor("disease-case-administrator-edit.xml")
@EditedEntityContainer("diseaseCaseDc")
public class DiseaseCaseAdministratorEdit extends StandardEditor<DiseaseCase> {

    @Autowired
    CurrentAuthentication authentication;

    @Autowired
    DataManager dataManager;

    @Autowired
    InstanceContainer<DiseaseCase> diseaseCaseDc;

    @Subscribe(target = Target.DATA_CONTEXT)
    public void onPreCommit(DataContext.PreCommitEvent event) {
        Administrator currentAdministrator = ((Administrator) authentication.getUser());

        DiseaseCase diseaseCase = diseaseCaseDc.getItem();
        diseaseCase.setRegistrator(currentAdministrator);
        diseaseCase.setDiseaseCaseState(DiseaseCaseState.AT_WORK);
    }
}