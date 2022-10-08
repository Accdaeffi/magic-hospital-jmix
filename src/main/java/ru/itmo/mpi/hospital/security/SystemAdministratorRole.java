package ru.itmo.mpi.hospital.security;

import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;
import ru.itmo.mpi.hospital.entity.Administrator;
import ru.itmo.mpi.hospital.entity.God;
import ru.itmo.mpi.hospital.entity.Healer;
import ru.itmo.mpi.hospital.entity.Helper;

@ResourceRole(name = "SystemAdministratorRole", code = "system-administrator-role")
public interface SystemAdministratorRole {

    @EntityPolicy(entityClass = Healer.class, actions = {EntityPolicyAction.ALL})
    @EntityAttributePolicy(entityClass = Healer.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @ScreenPolicy(screenIds = {"Healer.browse", "Healer.edit"})
    @MenuPolicy(menuIds = {"Healer.browse"})
    void healer();

    @EntityPolicy(entityClass = Helper.class, actions = {EntityPolicyAction.ALL})
    @EntityAttributePolicy(entityClass = Helper.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @ScreenPolicy(screenIds = {"Helper.browse", "Helper.edit"})
    @MenuPolicy(menuIds = {"Helper.browse"})
    void helper();

    @EntityPolicy(entityClass = Administrator.class, actions = {EntityPolicyAction.ALL})
    @EntityAttributePolicy(entityClass = Administrator.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @ScreenPolicy(screenIds = {"Administrator.browse", "Administrator.edit"})
    @MenuPolicy(menuIds = {"Administrator.browse"})
    void administrator();

    @EntityPolicy(entityClass = God.class, actions = {EntityPolicyAction.ALL})
    @EntityAttributePolicy(entityClass = God.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @ScreenPolicy(screenIds = {"God.browse", "God.edit"})
    @MenuPolicy(menuIds = {"God.browse"})
    void god();

    @EntityPolicy(entityName = "sec_RoleAssignmentEntity", actions = {EntityPolicyAction.ALL})
    @EntityPolicy(entityName = "sec_UserSubstitutionEntity", actions = {EntityPolicyAction.ALL})
    @EntityAttributePolicy(entityName = "sec_RoleAssignmentEntity", attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityAttributePolicy(entityName = "sec_ResourceRoleModel", attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityAttributePolicy(entityName = "sec_RowLevelRoleModel", attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @ScreenPolicy(screenIds = {"sec_RoleAssignmentScreen", "sec_ResourceRoleModel.lookup", "sec_RowLevelRoleModel.lookup"})
    void roles();

    @ScreenPolicy(screenIds = "ChangePasswordDialog")
    void password();

}