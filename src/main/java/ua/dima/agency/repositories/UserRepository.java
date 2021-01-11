package ua.dima.agency.repositories;

import ua.dima.agency.domain.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> getByLogin(String login);
}
