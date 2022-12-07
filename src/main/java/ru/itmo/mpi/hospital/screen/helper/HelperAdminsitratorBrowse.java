package ru.itmo.mpi.hospital.screen.helper;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.Helper;

@UiController("Helper.administrator-browse")
@UiDescriptor("helper-administrator-browse.xml")
@LookupComponent("helpersTable")
public class HelperAdminsitratorBrowse extends StandardLookup<Helper> {
}