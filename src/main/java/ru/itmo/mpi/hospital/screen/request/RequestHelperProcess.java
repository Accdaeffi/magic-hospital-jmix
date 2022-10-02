package ru.itmo.mpi.hospital.screen.request;

import io.jmix.ui.action.Action;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.mpi.hospital.entity.Patient;
import ru.itmo.mpi.hospital.entity.Request;
import ru.itmo.mpi.hospital.entity.RequestStatus;

@UiController("Request.helper-process")
@UiDescriptor("request-helper-process.xml")
@EditedEntityContainer("requestDc")
public class RequestHelperProcess extends StandardEditor<Request> {

    @Autowired
    InstanceContainer<Request> requestDc;

    @Subscribe("startProcessing")
    public void onStartProcessing(Action.ActionPerformedEvent event) {
        Request request = requestDc.getItem();

        if (request.getRequestStatus() == RequestStatus.INITIALISED) {
            request.setRequestStatus(RequestStatus.PROCESSING);
            commitChanges();
        }
    }
}