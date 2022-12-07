package ru.itmo.mpi.hospital.screen.prayer;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.Prayer;

@UiController("Prayer.administrator-view")
@UiDescriptor("prayer-administrator-view.xml")
@EditedEntityContainer("prayerDc")
public class PrayerAdministratorView extends StandardEditor<Prayer> {
}