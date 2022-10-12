package ru.itmo.mpi.hospital.screen.prayer;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.Prayer;

@UiController("Prayer.healer-view")
@UiDescriptor("prayer-healer-view.xml")
@EditedEntityContainer("prayerDc")
public class PrayerHealerView extends StandardEditor<Prayer> {
}