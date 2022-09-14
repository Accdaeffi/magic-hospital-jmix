package ru.itmo.mpi.hospital.screen.disease;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.Disease;

@UiController("Disease.edit")
@UiDescriptor("disease-edit.xml")
@EditedEntityContainer("diseaseDc")
public class DiseaseEdit extends StandardEditor<Disease> {
}