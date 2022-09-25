package ru.itmo.mpi.hospital.screen.god;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.God;

@UiController("God.administrator-browse")
@UiDescriptor("god-administrator-browse.xml")
@LookupComponent("godsTable")
public class GodAdministratorBrowse extends StandardLookup<God> {
}