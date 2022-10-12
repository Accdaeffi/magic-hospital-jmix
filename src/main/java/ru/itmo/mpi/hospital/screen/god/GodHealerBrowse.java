package ru.itmo.mpi.hospital.screen.god;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.God;

@UiController("God.healer-browse")
@UiDescriptor("god-healer-browse.xml")
@LookupComponent("godsTable")
public class GodHealerBrowse extends StandardLookup<God> {
}