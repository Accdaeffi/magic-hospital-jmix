package ru.itmo.mpi.hospital.screen.request;

import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.mpi.hospital.entity.Request;

@UiController("Request.helper-browse")
@UiDescriptor("request-helper-browse.xml")
@LookupComponent("requestsTable")
public class RequestHelperBrowse extends StandardLookup<Request> {

    @Autowired
    private CollectionLoader<Request> requestsDl;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        requestsDl.load();
    }

}