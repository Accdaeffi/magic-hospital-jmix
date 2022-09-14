package ru.itmo.mpi.hospital.screen.helper;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.Helper;

@UiController("Helper.edit")
@UiDescriptor("helper-edit.xml")
@EditedEntityContainer("helperDc")
public class HelperEdit extends StandardEditor<Helper> {
}