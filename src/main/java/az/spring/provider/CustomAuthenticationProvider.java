package az.spring.provider;

import az.spring.dto.login.LoginRequestDto;
import az.spring.services.user.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final MyUserDetailsService myUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final LoginRequestDto loginRequest = (LoginRequestDto) authentication.getPrincipal();
        String username = loginRequest.getEmail();
        UserDetails user = myUserDetailsService.loadUserByUsername(username);
        return createSuccessfullAuthentication(authentication, user);

    }

    private Authentication createSuccessfullAuthentication(final Authentication authentication
            ,final UserDetails user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user
                , user.getPassword(), user.getAuthorities());
        final String rawPassword = (String) authentication.getCredentials();
        boolean isAuthenticated = passwordEncoder.matches(rawPassword, user.getPassword());
        if (!isAuthenticated){
            throw new BadCredentialsException("Password not correct");
        }
        token.setDetails(authentication.getDetails());
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
