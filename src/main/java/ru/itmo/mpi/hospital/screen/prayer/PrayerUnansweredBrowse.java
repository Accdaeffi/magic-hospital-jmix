package ru.itmo.mpi.hospital.screen.prayer;

import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.action.Action;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itmo.mpi.hospital.entity.Prayer;

@UiController("PrayerUnanswered.browse")
@UiDescriptor("prayer-unanswered-browse.xml")
@LookupComponent("prayersTable")
public class PrayerUnansweredBrowse extends StandardLookup<Prayer> {

    @Autowired
    private CollectionContainer<Prayer> prayersDc;

    @Autowired
    private CollectionLoader<Prayer> prayersDl;

    @Autowired
    ScreenBuilders screenBuilders;

    @Subscribe("prayersTable.viewPrayer")
    public void onViewPrayer(Action.ActionPerformedEvent event) {

        Prayer selectedPrayer = prayersDc.getItem();

        screenBuilders.editor(Prayer.class, this)
                .withScreenClass(PrayerResolver.class)
                .withAfterCloseListener(listener -> prayersDc.replaceItem(selectedPrayer))
                .editEntity(selectedPrayer)
                .build().show();
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        prayersDl.load();
    }

}