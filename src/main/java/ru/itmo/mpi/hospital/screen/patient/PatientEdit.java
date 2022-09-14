package ru.itmo.mpi.hospital.screen.patient;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.Patient;

@UiController("Patient.edit")
@UiDescriptor("patient-edit.xml")
@EditedEntityContainer("patientDc")
public class PatientEdit extends StandardEditor<Patient> {
}