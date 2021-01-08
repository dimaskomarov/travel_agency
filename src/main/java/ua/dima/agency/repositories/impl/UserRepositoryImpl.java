package ua.dima.agency.repositories.impl;

import org.springframework.stereotype.Repository;
import ua.dima.agency.domain.User;
import ua.dima.agency.repositories.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static Map<String, User> users = new HashMap<>();
    static {
        users.put("admin", new User(1L, "admin", "$2y$12$4WxWj9TVLRsgEo/j9L1sUezd.uf38YCCCCdIAKB0gxm/5KF0VJet."));
        users.put("customer", new User(2L, "customer", "$2y$12$yp4cGHgL0WA6Jv66FKAkjOJkT5GdwepP5kw/63.LsJQhXcJqI.SPi"));
    }

    @Override
    public Optional<User> getByName(String userName) {
        if(users.containsKey(userName)) {
            return Optional.of(users.get(userName));
        }
        return Optional.empty();
    }
}
