package ru.itmo.mpi.hospital.screen.patient;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.Patient;

@UiController("Patient.browse")
@UiDescriptor("patient-browse.xml")
@LookupComponent("patientsTable")
public class PatientBrowse extends StandardLookup<Patient> {
}