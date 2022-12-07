package ru.itmo.mpi.hospital.screen.prayer;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.Prayer;

@UiController("Prayer.god-view")
@UiDescriptor("prayer-god-view.xml")
@EditedEntityContainer("prayerDc")
public class PrayerGodView extends StandardEditor<Prayer> {
}