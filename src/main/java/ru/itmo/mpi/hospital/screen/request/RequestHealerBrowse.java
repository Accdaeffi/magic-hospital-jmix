package ru.itmo.mpi.hospital.screen.request;

import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.mpi.hospital.entity.Request;

@UiController("Request.healer-browse")
@UiDescriptor("request-healer-browse.xml")
@LookupComponent("requestsTable")
public class RequestHealerBrowse extends StandardLookup<Request> {

    @Autowired
    private CollectionLoader<Request> requestsDl;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        requestsDl.load();
    }

}