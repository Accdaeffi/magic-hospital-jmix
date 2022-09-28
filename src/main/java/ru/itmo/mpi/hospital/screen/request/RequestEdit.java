package ru.itmo.mpi.hospital.screen.request;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.Request;

@UiController("Request.edit")
@UiDescriptor("request-edit.xml")
@EditedEntityContainer("requestDc")
public class RequestEdit extends StandardEditor<Request> {
}