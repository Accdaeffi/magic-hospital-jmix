package ru.itmo.mpi.hospital.action.god;

import ru.itmo.mpi.hospital.entity.Prayer;
import ru.itmo.mpi.hospital.entity.User;
import io.jmix.ui.action.Action;
import org.springframework.context.ApplicationEvent;

import java.util.EventObject;
import java.util.UUID;

public class PrayerAnsweredEvent extends EventObject {

    Prayer prayer;

    UUID userId;

    public PrayerAnsweredEvent(Action origin, Prayer prayer, User user) {
        super(origin);
        this.prayer = prayer;
        this.userId = user.getId();
    }

    public Prayer getPrayer() {
        return prayer;
    }

    public UUID getInitiator() {
        return userId;
    }
}
