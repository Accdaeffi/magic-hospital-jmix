package ru.itmo.mpi.hospital.screen.prayer;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.Prayer;

@UiController("Prayer.administrator-browse")
@UiDescriptor("prayer-administrator-browse.xml")
@LookupComponent("prayersTable")
public class PrayerAdministratorBrowse extends StandardLookup<Prayer> {
}