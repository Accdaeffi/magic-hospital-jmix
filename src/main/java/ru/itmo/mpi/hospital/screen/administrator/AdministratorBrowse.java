package ru.itmo.mpi.hospital.screen.administrator;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.Administrator;

@UiController("Administrator.browse")
@UiDescriptor("administrator-browse.xml")
@LookupComponent("administratorsTable")
public class AdministratorBrowse extends StandardLookup<Administrator> {
}