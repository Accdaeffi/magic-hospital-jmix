package ru.itmo.mpi.hospital.screen.diseasecase;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.DiseaseCase;

@UiController("DiseaseCase.edit")
@UiDescriptor("disease-case-edit.xml")
@EditedEntityContainer("diseaseCaseDc")
public class DiseaseCaseEdit extends StandardEditor<DiseaseCase> {
}