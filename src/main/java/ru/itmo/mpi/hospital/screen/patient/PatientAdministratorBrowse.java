package ru.itmo.mpi.hospital.screen.patient;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.Patient;

@UiController("Patient.administrator-browse")
@UiDescriptor("patient-administrator-browse.xml")
@LookupComponent("patientsTable")
public class PatientAdministratorBrowse extends StandardLookup<Patient> {
}