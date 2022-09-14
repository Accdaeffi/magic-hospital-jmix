package ru.itmo.mpi.hospital.screen.prayer;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.Prayer;

@UiController("Prayer.edit")
@UiDescriptor("prayer-edit.xml")
@EditedEntityContainer("prayerDc")
public class PrayerEdit extends StandardEditor<Prayer> {
}