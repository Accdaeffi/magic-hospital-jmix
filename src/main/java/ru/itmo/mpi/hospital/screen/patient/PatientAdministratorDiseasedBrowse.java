package ru.itmo.mpi.hospital.screen.patient;

import io.jmix.core.DataManager;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.Button;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.Target;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.mpi.hospital.entity.Patient;
import ru.itmo.mpi.hospital.entity.PatientState;
import ru.itmo.mpi.hospital.entity.SocialStatus;

import java.util.List;
import java.util.stream.Collectors;

@UiController("Patient.administrator-diseased-browse")
@UiDescriptor("patient-administrator-diseased-browse.xml")
@LookupComponent("patientsTable")
public class PatientAdministratorDiseasedBrowse extends StandardLookup<Patient> {

    @Autowired
    DataManager dataManager;

    @Autowired
    private Button buryBtn;

    @Autowired
    private CollectionContainer<Patient> patientsDc;

    @Subscribe(id = "patientsDc", target = Target.DATA_CONTAINER)
    public void onPatientsDcItemChange(InstanceContainer.ItemChangeEvent<Patient> event) {
        Patient corpse = patientsDc.getItemOrNull();

        buryBtn.setEnabled(corpse != null && (corpse.getPatientState() == PatientState.DISEASED || corpse.getSocialStatus() != SocialStatus.ARISTOCRAT));
    }

    @Subscribe("patientsTable.bury")
    public void onPatientsTableBury(Action.ActionPerformedEvent event) {
        Patient corpse = patientsDc.getItem();

        System.out.println(corpse.toString());

        corpse.setPatientState(PatientState.BURIED);

        dataManager.save(corpse);
    }

    @Subscribe("patientsTable.buryAll")
    public void onPatientsTableBuryAll(Action.ActionPerformedEvent event) {
        List<Patient> corpses = patientsDc.getItems();

        String result = corpses
                .stream()
                .filter(c -> c.getPatientState() == PatientState.DISEASED)
                .map(Patient::toString)
                .collect(Collectors.joining("\n"));
        System.out.println(result);

        corpses.stream()
                .filter(c -> c.getPatientState() == PatientState.DISEASED)
                .forEach(c -> {
                    c.setPatientState(PatientState.BURIED);
                    dataManager.save(c);
                });

    }
}