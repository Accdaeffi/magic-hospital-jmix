package ru.itmo.mpi.hospital.security;

import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;
import ru.itmo.mpi.hospital.entity.Administrator;
import ru.itmo.mpi.hospital.entity.Disease;
import ru.itmo.mpi.hospital.entity.DiseaseCase;
import ru.itmo.mpi.hospital.entity.God;
import ru.itmo.mpi.hospital.entity.Healer;
import ru.itmo.mpi.hospital.entity.Helper;
import ru.itmo.mpi.hospital.entity.Patient;
import ru.itmo.mpi.hospital.entity.Prayer;
import ru.itmo.mpi.hospital.entity.Request;

@ResourceRole(name = "HealerRole", code = "healer-role", scope = "UI")
public interface HealerRole {

    @EntityPolicy(entityClass = Prayer.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.CREATE})
    @EntityAttributePolicy(entityClass = Prayer.class, attributes = {"god", "prayText", "diseaseCase", "prayerStatus"}, action = EntityAttributePolicyAction.MODIFY)
    @EntityAttributePolicy(entityClass = Prayer.class, attributes = "prayerStatus", action = EntityAttributePolicyAction.VIEW)
    @ScreenPolicy(screenIds = {"Prayer.healer-create", "Prayer.browse"})
    @MenuPolicy(menuIds = "Prayer.browse")
    void prayer();

    @EntityPolicy(entityClass = DiseaseCase.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
    @EntityAttributePolicy(entityClass = DiseaseCase.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityAttributePolicy(entityClass = DiseaseCase.class, attributes = {"patientComplaints", "actions", "disease", "prayer"}, action = EntityAttributePolicyAction.MODIFY)
    @ScreenPolicy(screenIds = {"DiseaseCase.healer-examine", "DiseaseCase.healer-browse"})
    @MenuPolicy(menuIds = "DiseaseCase.healer-browse")
    void diseaseCase();

    @EntityPolicy(entityClass = Request.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.CREATE, EntityPolicyAction.UPDATE})
    @EntityAttributePolicy(entityClass = Request.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityAttributePolicy(entityClass = Request.class, attributes = {"dustAmountRequired", "requestStatus", "waterRequired", "additionalInfo", "requiredPentaHelp", "diseaseCase", "helper"}, action = EntityAttributePolicyAction.MODIFY)
    @ScreenPolicy(screenIds = {"Request.healer-create", "Request.healer-browse"})
    @MenuPolicy(menuIds = "Request.healer-browse")
    void request();

    @EntityPolicy(entityClass = Helper.class, actions = {EntityPolicyAction.READ})
    @EntityAttributePolicy(entityClass = Helper.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @ScreenPolicy(screenIds = {"Helper.browse"})
    void helper();

    @EntityPolicy(entityClass = God.class, actions = {EntityPolicyAction.READ})
    @EntityAttributePolicy(entityClass = God.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @ScreenPolicy(screenIds = {"God.browse"})
    void god();

    @EntityPolicy(entityClass = Disease.class, actions = {EntityPolicyAction.READ})
    @EntityAttributePolicy(entityClass = Disease.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void disease();

    @EntityPolicy(entityClass = Patient.class, actions = {EntityPolicyAction.READ})
    @EntityAttributePolicy(entityClass = Patient.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void patient();

    @EntityPolicy(entityClass = Healer.class, actions = {EntityPolicyAction.READ})
    @EntityAttributePolicy(entityClass = Healer.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void healer();

    @EntityPolicy(entityClass = Administrator.class, actions = {EntityPolicyAction.READ})
    @EntityAttributePolicy(entityClass = Administrator.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void administrator();


}