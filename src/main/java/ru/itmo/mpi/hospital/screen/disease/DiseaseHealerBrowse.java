package ru.itmo.mpi.hospital.screen.disease;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.Disease;

@UiController("Disease.healer-browse")
@UiDescriptor("disease-healer-browse.xml")
@LookupComponent("diseasesTable")
public class DiseaseHealerBrowse extends StandardLookup<Disease> {
}