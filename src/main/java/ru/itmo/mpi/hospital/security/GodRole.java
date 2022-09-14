package ru.itmo.mpi.hospital.security;

import ru.itmo.mpi.hospital.entity.DiseaseCase;
import ru.itmo.mpi.hospital.entity.Healer;
import ru.itmo.mpi.hospital.entity.Patient;
import ru.itmo.mpi.hospital.entity.Prayer;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

@ResourceRole(name = "GodRole", code = "god-role")
public interface GodRole {

    @EntityPolicy(entityClass = Prayer.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
    @EntityAttributePolicy(entityClass = Prayer.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityAttributePolicy(entityClass = Prayer.class, attributes = "prayerStatus", action = EntityAttributePolicyAction.MODIFY)
    @ScreenPolicy(screenIds = {"Prayer.browse", "Prayer.view", "PrayerUnanswered.browse"})
    @MenuPolicy(menuIds = {"Prayer.browse", "PrayerUnanswered.browse"})
    void prayer();

    @EntityPolicy(entityClass = DiseaseCase.class, actions = {EntityPolicyAction.READ})
    @EntityAttributePolicy(entityClass = DiseaseCase.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void diseaseCase();

    @EntityPolicy(entityClass = Patient.class, actions = {EntityPolicyAction.READ})
    @EntityAttributePolicy(entityClass = Patient.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void patient();

    @EntityPolicy(entityClass = Healer.class, actions = {EntityPolicyAction.READ})
    @EntityAttributePolicy(entityClass = Healer.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void healer();

}