package ru.itmo.mpi.hospital.screen.request;

import io.jmix.core.DataManager;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.Button;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.mpi.hospital.entity.Request;
import ru.itmo.mpi.hospital.entity.RequestStatus;

@UiController("Request.helper-process")
@UiDescriptor("request-helper-process.xml")
@EditedEntityContainer("requestDc")
public class RequestHelperProcess extends StandardEditor<Request> {

    @Autowired
    InstanceContainer<Request> requestDc;

    @Autowired
    DataManager dataManager;

    @Autowired
    Button startProcessingBtn;

    @Subscribe("startProcessing")
    public void onStartProcessing(Action.ActionPerformedEvent event) {
        Request request = requestDc.getItem();

        Request requestFromDb = dataManager.load(Request.class).id(request.getId()).one();

        if (requestFromDb.getRequestStatus() != RequestStatus.INITIALISED) {
            closeWithDiscard();
        }

        if (request.getRequestStatus() == RequestStatus.INITIALISED) {
            request.setRequestStatus(RequestStatus.PROCESSING);
            commitChanges();
            startProcessingBtn.setEnabled(false);
        }
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        Request request = requestDc.getItem();

        if (request.getRequestStatus() == RequestStatus.PROCESSING) {
            startProcessingBtn.setEnabled(false);
        }
    }
}