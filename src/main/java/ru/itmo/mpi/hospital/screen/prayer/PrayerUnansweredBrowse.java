package ru.itmo.mpi.hospital.screen.prayer;

import ru.itmo.mpi.hospital.entity.Prayer;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.action.Action;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("PrayerUnanswered.browse")
@UiDescriptor("prayer-unanswered-browse.xml")
@LookupComponent("prayersTable")
public class PrayerUnansweredBrowse extends StandardLookup<Prayer> {

    @Autowired
    CurrentAuthentication currentAuthentication;

    @Autowired
    private CollectionContainer<Prayer> prayersDc;

    @Autowired
    ScreenBuilders screenBuilders;

    @Subscribe("prayersTable.viewPrayer")
    public void onViewPrayer(Action.ActionPerformedEvent event) {

        Prayer selectedPrayer = prayersDc.getItem();

        screenBuilders.editor(Prayer.class, this)
                .withScreenClass(PrayerResolver.class)
                .withAfterCloseListener(listener -> {
                    prayersDc.replaceItem(selectedPrayer);
                })
                .editEntity(selectedPrayer)
                .build().show();
    }

    /*@EventListener
    public void onPrayerAnswered(PrayerAnsweredEvent prayerAnsweredEvent) {
        System.out.println("hello");

        UUID currentUserId = ((User) currentAuthentication.getUser()).getId();
        if (currentUserId.equals(prayerAnsweredEvent.getInitiator())) {
            prayersDc.replaceItem(prayerAnsweredEvent.getPrayer());
        }
    }*/


    /*@Override
    public void onApplicationEvent(PrayerAnsweredEvent prayerAnsweredEvent) {
        System.out.println("hello");

        UUID currentUserId = ((User) currentAuthentication.getUser()).getId();
        if (currentUserId.equals(prayerAnsweredEvent.getInitiator())) {
            prayersDc.replaceItem(prayerAnsweredEvent.getPrayer());
        }
    }*/
}