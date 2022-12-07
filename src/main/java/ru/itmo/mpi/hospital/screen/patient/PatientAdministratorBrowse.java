package ru.itmo.mpi.hospital.screen.patient;

import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.mpi.hospital.entity.Patient;

@UiController("Patient.administrator-browse")
@UiDescriptor("patient-administrator-browse.xml")
@LookupComponent("patientsTable")
public class PatientAdministratorBrowse extends StandardLookup<Patient> {

    @Autowired
    private CollectionLoader<Patient> patientsDl;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        patientsDl.load();
    }

}