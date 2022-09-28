package ru.itmo.mpi.hospital.screen.prayer;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.Prayer;

@UiController("Prayer.healer-browse")
@UiDescriptor("prayer-healer-browse.xml")
@LookupComponent("prayersTable")
public class PrayerHealerBrowse extends StandardLookup<Prayer> {
}