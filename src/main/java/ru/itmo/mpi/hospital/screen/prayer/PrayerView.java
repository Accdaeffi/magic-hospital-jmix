package ru.itmo.mpi.hospital.screen.prayer;

import ru.itmo.mpi.hospital.action.god.AcceptPrayerAction;
import ru.itmo.mpi.hospital.action.god.RejectPrayerAction;
import ru.itmo.mpi.hospital.entity.Prayer;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.Install;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.Target;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

import javax.inject.Named;

@UiController("Prayer.view")
@UiDescriptor("prayer-view.xml")
@EditedEntityContainer("prayerDc")
public class PrayerView extends StandardEditor<Prayer> {

    @Named("acceptPrayerId")
    private AcceptPrayerAction<Prayer> acceptPrayerAction;

    @Install(to = "acceptPrayerId", subject = "prayerGenerator")
    private Prayer acceptPrayerPrayerGenerator(String string) {
        return this.getEditedEntity();
    }

    @Named("rejectPrayerId")
    private RejectPrayerAction<Prayer> rejectPrayerAction;

    @Install(to = "rejectPrayerId", subject = "prayerGenerator")
    private Prayer rejectPrayerPrayerGenerator(String string) {
        return this.getEditedEntity();
    }

    @Subscribe(target = Target.DATA_CONTEXT)
    public void onChange(DataContext.ChangeEvent event) {
        closeWithCommit();
    }


}