package ru.itmo.mpi.hospital.screen.god;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.God;

@UiController("God.administrator-view")
@UiDescriptor("god-administrator-view.xml")
@EditedEntityContainer("godDc")
public class GodAdministratorView extends StandardEditor<God> {
}