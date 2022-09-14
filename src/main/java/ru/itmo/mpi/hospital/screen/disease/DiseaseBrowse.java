package ru.itmo.mpi.hospital.screen.disease;

import io.jmix.ui.screen.*;
import ru.itmo.mpi.hospital.entity.Disease;

@UiController("Disease.browse")
@UiDescriptor("disease-browse.xml")
@LookupComponent("diseasesTable")
public class DiseaseBrowse extends StandardLookup<Disease> {
}