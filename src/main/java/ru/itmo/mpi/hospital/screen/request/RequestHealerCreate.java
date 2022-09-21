package ru.itmo.mpi.hospital.screen.request;

import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.mpi.hospital.entity.DiseaseCase;
import ru.itmo.mpi.hospital.entity.Request;
import ru.itmo.mpi.hospital.entity.RequestStatus;

@UiController("Request.healer-create")
@UiDescriptor("request-healer-create.xml")
@EditedEntityContainer("requestDc")
public class RequestHealerCreate extends StandardEditor<Request> {

    @Autowired
    InstanceContainer<Request> requestDc;

    private DiseaseCase diseaseCase;

    public void setDiseaseCase(DiseaseCase diseaseCase) {
        this.diseaseCase = diseaseCase;
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        requestDc.getItem().setDiseaseCase(diseaseCase);
        requestDc.getItem().setRequestStatus(RequestStatus.INITIALISED);
    }
}