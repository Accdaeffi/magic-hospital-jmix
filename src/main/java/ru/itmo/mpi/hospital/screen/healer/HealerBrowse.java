package ru.itmo.mpi.hospital.screen.healer;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.Healer;

@UiController("Healer.browse")
@UiDescriptor("healer-browse.xml")
@LookupComponent("healersTable")
public class HealerBrowse extends StandardLookup<Healer> {
}