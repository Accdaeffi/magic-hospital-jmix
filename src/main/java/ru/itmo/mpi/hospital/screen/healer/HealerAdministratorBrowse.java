package ru.itmo.mpi.hospital.screen.healer;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.Healer;

@UiController("Healer.administrator-browse")
@UiDescriptor("healer-administrator-browse.xml")
@LookupComponent("healersTable")
public class HealerAdministratorBrowse extends StandardLookup<Healer> {
}