package ru.itmo.mpi.hospital.screen.helper;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.Helper;

@UiController("Helper.healer-browse")
@UiDescriptor("helper-healer-browse.xml")
@LookupComponent("helpersTable")
public class HelperHealerBrowse extends StandardLookup<Helper> {
}