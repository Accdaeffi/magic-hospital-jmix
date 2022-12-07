package ru.itmo.mpi.hospital.screen.request;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.Request;

@UiController("Request.administrator-view")
@UiDescriptor("request-administrator-view.xml")
@EditedEntityContainer("requestDc")
public class RequestAdministratorView extends StandardEditor<Request> {
}