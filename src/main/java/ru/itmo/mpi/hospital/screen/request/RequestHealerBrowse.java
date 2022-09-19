package ru.itmo.mpi.hospital.screen.request;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.Request;

@UiController("Request.healer-browse")
@UiDescriptor("request-healer-browse.xml")
@LookupComponent("requestsTable")
public class RequestHealerBrowse extends StandardLookup<Request> {
}