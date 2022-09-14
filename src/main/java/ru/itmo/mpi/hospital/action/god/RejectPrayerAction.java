package ru.itmo.mpi.hospital.action.god;

import ru.itmo.mpi.hospital.entity.Prayer;
import ru.itmo.mpi.hospital.entity.PrayerStatus;
import ru.itmo.mpi.hospital.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.action.ActionType;
import io.jmix.ui.action.ItemTrackingAction;
import io.jmix.ui.component.Component;
import io.jmix.ui.meta.StudioAction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Function;

@StudioAction(target = "io.jmix.ui.component.ListComponent", description = "Reject prayer")
@ActionType("rejectPrayer")
public class RejectPrayerAction<E extends Prayer> extends ItemTrackingAction {

    @Autowired
    CurrentAuthentication currentAuthentication;

    @Autowired
    private DataManager dataManager;

    public RejectPrayerAction(String id) {
        super(id);
        setCaption("Reject prayer");
    }

    private Function<String, Prayer> prayerGenerator;

    public void setPrayerGenerator(Function<String, Prayer> prayerGenerator) {
        this.prayerGenerator = prayerGenerator;
    }

    @Override
    public void actionPerform(Component component) {

        Prayer selectedPrayer = prayerGenerator.apply(new String());

        if (selectedPrayer == null || selectedPrayer.getPrayerStatus() != PrayerStatus.UNANSWERED) {
            return;
        }

        selectedPrayer.setPrayerStatus(PrayerStatus.REJECTED);
        dataManager.save(selectedPrayer);

        PrayerAnsweredEvent event = new PrayerAnsweredEvent(this, selectedPrayer, (User) currentAuthentication.getUser());
        eventHub.publish(PrayerAnsweredEvent.class, event);
    }

}
