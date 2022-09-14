package ru.itmo.mpi.hospital.screen.diseasecase;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.DiseaseCase;

@UiController("DiseaseCase.browse")
@UiDescriptor("disease-case-browse.xml")
@LookupComponent("diseaseCasesTable")
public class DiseaseCaseBrowse extends StandardLookup<DiseaseCase> {
}