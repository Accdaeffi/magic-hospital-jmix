package ru.itmo.mpi.hospital.screen.diseasecase;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.DiseaseCase;

@UiController("DiseaseCase.administrator-browse")
@UiDescriptor("disease-case-administrator-browse.xml")
@LookupComponent("diseaseCasesTable")
public class DiseaseCaseAdministratorBrowse extends StandardLookup<DiseaseCase> {
}