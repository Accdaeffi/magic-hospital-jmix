package ru.itmo.mpi.hospital.screen.request;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.Request;

@UiController("Request.administrator-browse")
@UiDescriptor("request-administrator-browse.xml")
@LookupComponent("requestsTable")
public class RequestAdministratorBrowse extends StandardLookup<Request> {
}