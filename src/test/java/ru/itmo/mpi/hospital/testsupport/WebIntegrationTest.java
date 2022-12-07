package ru.itmo.mpi.hospital.testsupport;

import io.jmix.ui.Screens;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.testassist.UiTestAssistConfiguration;
import io.jmix.ui.testassist.junit.UiTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import ru.itmo.mpi.hospital.MagicHospitalApplication;

import javax.validation.constraints.NotNull;

@SpringBootTest
@UiTest(authenticatedUser = "admin", mainScreenId = "MainScreen", screenBasePackages = "ru.itmo.mpi.hospital.screen")
@ContextConfiguration(classes = {MagicHospitalApplication.class, UiTestAssistConfiguration.class})
@AutoConfigureTestDatabase
public abstract class WebIntegrationTest {


    @BeforeEach
    void removeAllScreens(Screens screens) {
        screens.removeAll();
    }

    @NotNull
    protected <T> TableInteractions<T> entityTable(StandardLookup<T> browseScreen, Class<T> clazz, String componentId) {
        return TableInteractions.of(browseScreen, clazz, componentId);
    }
}