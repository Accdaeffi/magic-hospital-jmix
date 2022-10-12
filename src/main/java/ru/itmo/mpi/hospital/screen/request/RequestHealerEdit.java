package ru.itmo.mpi.hospital.screen.request;

import io.jmix.core.DataManager;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.Button;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.mpi.hospital.entity.Request;
import ru.itmo.mpi.hospital.entity.RequestStatus;

@UiController("Request.healer-edit")
@UiDescriptor("request-healer-edit.xml")
@EditedEntityContainer("requestDc")
public class RequestHealerEdit extends StandardEditor<Request> {

    @Autowired
    InstanceContainer<Request> requestDc;

    @Autowired
    DataManager dataManager;

    @Autowired
    Button receiveBtn;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        Request request = requestDc.getItem();

        if (request.getRequestStatus() != RequestStatus.PROCESSING) {
            receiveBtn.setEnabled(false);
        }
    }

    @Subscribe("receive")
    public void onReceive(Action.ActionPerformedEvent event) {
        Request request = requestDc.getItem();

        Request requestFromDb = dataManager.load(Request.class).id(request.getId()).one();

        if (requestFromDb.getRequestStatus() != RequestStatus.PROCESSING) {
            closeWithDiscard();
        }

        if (request.getRequestStatus() == RequestStatus.PROCESSING) {
            request.setRequestStatus(RequestStatus.COMPLETED);
            closeWithCommit();
        }
    }
}