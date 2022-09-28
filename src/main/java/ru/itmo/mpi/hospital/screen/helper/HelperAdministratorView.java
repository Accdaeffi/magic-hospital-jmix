package ru.itmo.mpi.hospital.screen.helper;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.Helper;

@UiController("Helper.administrator-view")
@UiDescriptor("helper-administrator-view.xml")
@EditedEntityContainer("helperDc")
public class HelperAdministratorView extends StandardEditor<Helper> {
}