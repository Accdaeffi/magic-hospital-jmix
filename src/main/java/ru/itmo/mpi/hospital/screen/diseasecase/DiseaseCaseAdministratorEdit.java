package ru.itmo.mpi.hospital.screen.diseasecase;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.DiseaseCase;

@UiController("DiseaseCase.administrator-edit")
@UiDescriptor("disease-case-administrator-edit.xml")
@EditedEntityContainer("diseaseCaseDc")
public class DiseaseCaseAdministratorEdit extends StandardEditor<DiseaseCase> {
}