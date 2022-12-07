package ru.itmo.mpi.hospital.screen.healer;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.Healer;

@UiController("Healer.administrator-view")
@UiDescriptor("healer-administrator-view.xml")
@EditedEntityContainer("healerDc")
public class HealerAdministratorView extends StandardEditor<Healer> {
}