package ru.itmo.mpi.hospital.screen.administrator;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.Administrator;

@UiController("Administrator.edit")
@UiDescriptor("administrator-edit.xml")
@EditedEntityContainer("administratorDc")
public class AdministratorEdit extends StandardEditor<Administrator> {
}