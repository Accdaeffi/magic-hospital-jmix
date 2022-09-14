package ru.itmo.mpi.hospital.screen.god;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.God;

@UiController("God.edit")
@UiDescriptor("god-edit.xml")
@EditedEntityContainer("godDc")
public class GodEdit extends StandardEditor<God> {
}