package ru.itmo.mpi.hospital.screen.prayer;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.Prayer;

@UiController("Prayer.god-browse")
@UiDescriptor("prayer-god-browse.xml")
@LookupComponent("prayersTable")
public class PrayerGodBrowse extends StandardLookup<Prayer> {
}