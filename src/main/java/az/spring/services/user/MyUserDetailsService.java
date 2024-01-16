package az.spring.security.user;

import az.spring.model.User;
import az.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
           User user = userRepository.findUserByEmail(username)
                   .orElseThrow(()-> new UsernameNotFoundException(username));
           Set<SimpleGrantedAuthority> roles = new HashSet<>();
           return new MyUserDetails(username, user.getPassword(), roles);

    }
}
