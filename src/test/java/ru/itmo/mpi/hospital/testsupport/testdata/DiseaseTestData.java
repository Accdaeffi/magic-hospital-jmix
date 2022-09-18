package ru.itmo.mpi.hospital.testsupport.testdata;

import ru.itmo.mpi.hospital.entity.Disease;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Component
public class DiseaseTestData {

    @Autowired
    DataManager dataManager;

    public List<Disease> diseases;

    private static final String[] listTitles = {"title1", "title2", "title3"};
    private static final String[] listRecipe = {"recipe1", "recipe2", "recipe3"};
    private static final String[] listSymptoms = {"symptom1", "symptom2", "symptom3"};

    @PostConstruct
    void init() {
        diseases = new ArrayList<>();

        for (int i = 0; i<listTitles.length; i++) {

            Disease disease = dataManager.create(Disease.class);
            disease.setTitle(listTitles[i]);
            disease.setRecipe(listRecipe[i]);
            disease.setSymptoms(listSymptoms[i]);

            diseases.add(dataManager.save(disease));
        }

    }

    @PreDestroy
    void preDestroy() {
        diseases.forEach(object -> dataManager.remove(object));
    }

}
