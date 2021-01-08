package ua.dima.agency.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ua.dima.agency.domain.Role;
import ua.dima.agency.domain.User;
import ua.dima.agency.exceptions.NoDataException;
import ua.dima.agency.repositories.RoleRepository;
import ua.dima.agency.repositories.UserRepository;

import java.util.List;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserDetailsServiceImpl(UserRepository userRepository,
                                  RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.getByName(username)
                .orElseThrow(() -> new NoDataException("User not found"));
        List<Role> roles = roleRepository.getRoles(user.getId());

        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), roles);
    }

}
