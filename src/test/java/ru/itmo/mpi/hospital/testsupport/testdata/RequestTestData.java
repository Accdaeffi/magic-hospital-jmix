package ru.itmo.mpi.hospital.testsupport.testdata;

import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itmo.mpi.hospital.entity.Request;
import ru.itmo.mpi.hospital.entity.RequestStatus;

import java.util.ArrayList;
import java.util.List;

@Component
public class RequestTestData {

    private static boolean loaded = false;

    public List<Request> requests = new ArrayList<>();

    @Autowired
    DataManager dataManager;

    @Autowired
    SystemAuthenticator authenticator;

    @Autowired
    HelperTestData helperTestData;

    @Autowired
    DiseaseCaseTestData diseaseCaseTestData;

    private static final String[] listRequestText = {"request1", "request2"};
    private static final int[] listWaterAmount = {1, 0};
    private static final int[] listDustAmount = {50, 70};
    private static final boolean[] listPentaHelp = {true, false};
    private static final RequestStatus[] listRequestStatus = {RequestStatus.INITIALISED, RequestStatus.COMPLETED};

    public void loadDefault() {
        if (!loaded) {

            diseaseCaseTestData.loadDefaults();

            authenticator.withSystem(() -> {

                Request request = dataManager.create(Request.class);
                request.setHelper(helperTestData.helpers.get(0));
                request.setDiseaseCase(diseaseCaseTestData.diseaseCases.get(0));
                request.setRequestStatus(listRequestStatus[0]);
                request.setRequiredPentaHelp(listPentaHelp[0]);
                request.setWaterRequired(listWaterAmount[0]);
                request.setDustAmountRequired(listDustAmount[0]);
                requests.add(dataManager.save(request));

                request = dataManager.create(Request.class);
                request.setHelper(helperTestData.helpers.get(1));
                request.setDiseaseCase(diseaseCaseTestData.diseaseCases.get(1));
                request.setRequestStatus(listRequestStatus[1]);
                request.setRequiredPentaHelp(listPentaHelp[1]);
                request.setWaterRequired(listWaterAmount[1]);
                request.setDustAmountRequired(listDustAmount[1]);
                requests.add(dataManager.save(request));

                return "done";
            });

            loaded = true;
        }
    }

    public void cleanup() {
        if (loaded) {

            authenticator.withSystem(() -> {

                requests.forEach(object -> dataManager.remove(object));
                requests.clear();

                return "done";
            });

            diseaseCaseTestData.cleanup();

            loaded = false;
        }
    }




}
