package ua.dima.agency.repositories.impl;

import org.springframework.stereotype.Repository;
import ua.dima.agency.domain.Role;
import ua.dima.agency.repositories.RoleRepository;

import java.util.Arrays;
import java.util.List;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    @Override
    public List<Role> getRoles(Long id) {
        return Arrays.asList(new Role(1L, "ADMIN"),
                             new Role(2L, "MANAGER"),
                             new Role(3L, "DEFAULT"));
    }
}
