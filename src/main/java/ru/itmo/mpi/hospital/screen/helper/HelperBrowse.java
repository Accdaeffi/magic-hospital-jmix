package ru.itmo.mpi.hospital.screen.helper;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.Helper;

@UiController("Helper.browse")
@UiDescriptor("helper-browse.xml")
@LookupComponent("helpersTable")
public class HelperBrowse extends StandardLookup<Helper> {
}