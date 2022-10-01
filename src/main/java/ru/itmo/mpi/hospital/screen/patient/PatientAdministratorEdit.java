package ru.itmo.mpi.hospital.screen.patient;

import io.jmix.ui.model.DataContext;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.mpi.hospital.entity.Patient;
import ru.itmo.mpi.hospital.entity.PatientState;

@UiController("Patient.administrator-edit")
@UiDescriptor("patient-administrator-edit.xml")
@EditedEntityContainer("patientDc")
public class PatientAdministratorEdit extends StandardEditor<Patient> {

    @Autowired
    InstanceContainer<Patient> patientDc;

    @Subscribe(target = Target.DATA_CONTEXT)
    public void onPreCommit(DataContext.PreCommitEvent event) {
        Patient patient = patientDc.getItem();
        if (patient.getPatientState() == null) {
            patient.setPatientState(PatientState.HEALTHY);
        }
    }
}