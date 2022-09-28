package ru.itmo.mpi.hospital.screen.request;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.Request;

@UiController("Request.browse")
@UiDescriptor("request-browse.xml")
@LookupComponent("requestsTable")
public class RequestBrowse extends StandardLookup<Request> {
}