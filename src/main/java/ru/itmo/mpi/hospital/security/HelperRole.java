package ru.itmo.mpi.hospital.security;

import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;
import ru.itmo.mpi.hospital.entity.DiseaseCase;
import ru.itmo.mpi.hospital.entity.Healer;
import ru.itmo.mpi.hospital.entity.Request;

@ResourceRole(name = "HelperRole", code = "helper-role", scope = "UI")
public interface HelperRole {

    @EntityPolicy(entityClass = Request.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
    @EntityAttributePolicy(entityClass = Request.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityAttributePolicy(entityClass = Request.class, attributes = {"requestStatus"}, action = EntityAttributePolicyAction.MODIFY)
    @ScreenPolicy(screenIds = {"Request.helper-browse", "Request.helper-process"})
    @MenuPolicy(menuIds = "Request.helper-browse")
    void requests();

    @EntityPolicy(entityClass = Healer.class, actions = {EntityPolicyAction.READ})
    @EntityAttributePolicy(entityClass = Healer.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void healer();

    @EntityPolicy(entityClass = DiseaseCase.class, actions = {EntityPolicyAction.READ})
    @EntityAttributePolicy(entityClass = DiseaseCase.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void diseaseCase();

}