package ua.dima.agency.repositories;

import ua.dima.agency.domain.Role;

import java.util.List;

public interface RoleRepository {
    List<Role> getRoles(Long id);
}
