package ru.itmo.mpi.hospital.screen.patient;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.Patient;

@UiController("Patient.administrator-healthy")
@UiDescriptor("patient-administrator-healthy.xml")
@LookupComponent("patientsTable")
public class PatientAdministratorHealthy extends StandardLookup<Patient> {
}