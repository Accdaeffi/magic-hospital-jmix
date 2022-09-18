package ru.itmo.mpi.hospital.testsupport.testdata;

import ru.itmo.mpi.hospital.entity.Disease;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class DiseaseTestData implements TestDataProvisioning<Disease, Disease.DiseaseBuilder> {

    @Autowired
    DataManager dataManager;

    public List<Disease> diseases;

    @Override
    public Disease defaultData() {
        dataManager.create(Disease.class);
        return new Disease("title1", "recipe1", "symptom1");
        return Disease.builder()
                .title("title1")
                .recipe("recipe1")
                .symptoms("symptom1");
    }

    @Override
    public Disease save(Disease dto) {
        return dataManager.save(dto);
    }

    @Override
    public Disease saveDefault() {
        return dataManager.save(defaultData().build());
    }

    @Override
    public Disease create(Disease dto) {
        Disease disease = dataManager.create(Disease.class);
        disease.setRecipe(dto.getRecipe());
        disease.setTitle(dto.getTitle());
        disease.setSymptoms(dto.getSymptoms());
        return disease;
    }

    @Override
    public Disease createDefault() {
        Disease disease = dataManager.create(Disease.class);
        Disease buffer = defaultData();
        disease.setRecipe(buffer.getRecipe());
        disease.setTitle(buffer.getTitle());
        disease.setSymptoms(buffer.getSymptoms());
        return disease;
    }

}
