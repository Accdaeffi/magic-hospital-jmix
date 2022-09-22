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

@ResourceRole(name = "AdministratorRole", code = "administrator-role", scope = "UI")
public interface AdministratorRole {

    @EntityPolicy(entityClass = Patient.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.CREATE, EntityPolicyAction.UPDATE})
    @EntityAttributePolicy(entityClass = Patient.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @ScreenPolicy(screenIds = {"Patient.browse", "Patient.edit"})
    @MenuPolicy(menuIds = {"Patient.browse"})
    void patient();

    @EntityPolicy(entityClass = DiseaseCase.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.CREATE})
    @EntityAttributePolicy(entityClass = DiseaseCase.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityAttributePolicy(entityClass = DiseaseCase.class, attributes = {"healer", "patient", "registrator", "patientComplaints"}, action = EntityAttributePolicyAction.MODIFY)
    @ScreenPolicy(screenIds = {"DiseaseCase.administrator-browse", "DiseaseCase.administrator-edit"})
    @MenuPolicy(menuIds = "DiseaseCase.administrator-browse")
    void diseaseCase();

    @EntityPolicy(entityClass = Prayer.class, actions = {EntityPolicyAction.READ})
    @EntityAttributePolicy(entityClass = Prayer.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @ScreenPolicy(screenIds = {"Prayer.browse"})
    @MenuPolicy(menuIds = "Prayer.browse")
    void prayer();

    @EntityPolicy(entityClass = Helper.class, actions = {EntityPolicyAction.READ})
    @EntityAttributePolicy(entityClass = Helper.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @ScreenPolicy(screenIds = {"Request.browse"})
    @MenuPolicy(menuIds = {"Request.browse"})
    void request();

    @EntityPolicy(entityClass = Helper.class, actions = {EntityPolicyAction.READ})
    @EntityAttributePolicy(entityClass = Helper.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @ScreenPolicy(screenIds = {"Helper.browse", "Helper.edit"})
    @MenuPolicy(menuIds = {"Helper.browse"})
    void helper();

    @EntityPolicy(entityClass = God.class, actions = {EntityPolicyAction.READ})
    @EntityAttributePolicy(entityClass = God.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @ScreenPolicy(screenIds = {"God.browse", "God.edit"})
    @MenuPolicy(menuIds = {"God.browse"})
    void god();

    @EntityPolicy(entityClass = Healer.class, actions = {EntityPolicyAction.READ})
    @EntityAttributePolicy(entityClass = Healer.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @ScreenPolicy(screenIds = {"Healer.browse", "Healer.edit"})
    @MenuPolicy(menuIds = {"Healer.browse"})
    void healer();

    @EntityPolicy(entityClass = Disease.class, actions = {EntityPolicyAction.READ})
    @EntityAttributePolicy(entityClass = Disease.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void disease();

    @EntityPolicy(entityClass = Administrator.class, actions = {EntityPolicyAction.READ})
    @EntityAttributePolicy(entityClass = Administrator.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void administrator();

}