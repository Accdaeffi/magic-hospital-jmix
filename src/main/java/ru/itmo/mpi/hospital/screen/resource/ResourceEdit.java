package ru.itmo.mpi.hospital.screen.resource;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.Resource;

@UiController("Resource_.edit")
@UiDescriptor("resource-edit.xml")
@EditedEntityContainer("resourceDc")
public class ResourceEdit extends StandardEditor<Resource> {
}