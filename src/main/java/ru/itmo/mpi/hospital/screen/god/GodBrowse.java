package ru.itmo.mpi.hospital.screen.god;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.God;

@UiController("God.browse")
@UiDescriptor("god-browse.xml")
@LookupComponent("godsTable")
public class GodBrowse extends StandardLookup<God> {
}