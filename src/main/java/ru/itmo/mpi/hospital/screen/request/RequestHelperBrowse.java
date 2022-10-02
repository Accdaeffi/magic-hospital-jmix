package ru.itmo.mpi.hospital.screen.request;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.Request;

@UiController("Request.helper-browse")
@UiDescriptor("request-helper-browse.xml")
@LookupComponent("requestsTable")
public class RequestHelperBrowse extends StandardLookup<Request> {
}