package ru.itmo.mpi.hospital.screen.healer;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.Healer;

@UiController("Healer.edit")
@UiDescriptor("healer-edit.xml")
@EditedEntityContainer("healerDc")
public class HealerEdit extends StandardEditor<Healer> {
}