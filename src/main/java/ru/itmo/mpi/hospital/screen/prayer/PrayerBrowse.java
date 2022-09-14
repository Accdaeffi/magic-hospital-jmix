package ru.itmo.mpi.hospital.screen.prayer;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.Prayer;

@UiController("Prayer.browse")
@UiDescriptor("prayer-browse.xml")
@LookupComponent("prayersTable")
public class PrayerBrowse extends StandardLookup<Prayer> {
}