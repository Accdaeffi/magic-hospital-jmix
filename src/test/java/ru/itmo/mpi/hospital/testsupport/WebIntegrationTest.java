package ru.itmo.mpi.hospital.testsupport;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import ru.itmo.mpi.hospital.MagicHospitalApplication;
import ru.itmo.mpi.hospital.testsupport.testdata.TestData;
import io.jmix.ui.Screens;
import io.jmix.ui.testassist.UiTestAssistConfiguration;
import io.jmix.ui.testassist.junit.UiTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@UiTest(authenticatedUser = "admin", mainScreenId = "MainScreen", screenBasePackages = "ru.itmo.mpi.hospital.screen")
@ContextConfiguration(classes = {MagicHospitalApplication.class, TestData.class, UiTestAssistConfiguration.class})
@AutoConfigureTestDatabase
public abstract class WebIntegrationTest {


    @BeforeEach
    void removeAllScreens(Screens screens) {
        screens.removeAll();
    }
}