package az.spring.services.user;

import az.spring.model.User;
import az.spring.repository.UserRepository;
import az.spring.security.user.MyUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          log.info("trying to load user with email: {}", username);
           User user = userRepository.findUserByEmail(username)
                   .orElseThrow(()-> new UsernameNotFoundException(username));
           Set<SimpleGrantedAuthority> roles = new HashSet<>();
           return new MyUserDetails(user.getEmail(), user.getPassword(), roles);
    }
}
