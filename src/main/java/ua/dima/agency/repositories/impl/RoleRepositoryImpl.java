package ua.dima.agency.repositories.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.dima.agency.domain.Role;
import ua.dima.agency.repositories.RoleRepository;

import java.util.List;

@Repository
public class RoleRepositoryImpl implements RoleRepository {
    private static final RowMapper<Role> ROW_MAPPER = new BeanPropertyRowMapper<>(Role.class);
    private final JdbcTemplate jdbcTemplate;

    public RoleRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Role> getRoles(Long userId) {
        return jdbcTemplate.query("SELECT r.id, r.name FROM roles r\n"
                                    + "INNER JOIN users_roles u_r ON r.id = u_r.role_id\n"
                                    + "WHERE u_r.user_id = ?"
                , ROW_MAPPER, userId);
    }
}
