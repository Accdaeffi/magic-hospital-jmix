package ru.itmo.mpi.hospital.screen.resource;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.Resource;

@UiController("Resource_.browse")
@UiDescriptor("resource-browse.xml")
@LookupComponent("resourcesTable")
public class ResourceBrowse extends StandardLookup<Resource> {
}