package com.jathursh.sb.controller;

import com.jathursh.sb.dto.AuthResponseDto;
import com.jathursh.sb.dto.LoginDto;
import com.jathursh.sb.dto.RegisterDto;
import com.jathursh.sb.model.Role;
import com.jathursh.sb.model.UserEntity;
import com.jathursh.sb.repository.RoleRepository;
import com.jathursh.sb.repository.UserRepository;
import com.jathursh.sb.security.JwtGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtGenerator jwtGenerator;

    @Test
    void register() {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername("testUserName");
        registerDto.setPassword("testPass");

        UserEntity user = new UserEntity();
        user.setUsername("testUserName");

        Role role = new Role();
        role.setName("USER");

        // Method 1
        // for different usernames
        Mockito.when(userRepository.existsByUsername(registerDto.getUsername())).thenReturn(false);
        Mockito.when(passwordEncoder.encode(registerDto.getPassword())).thenReturn("encodedPassword");
        Mockito.when(roleRepository.findByName(role.getName())).thenReturn(Optional.of(role));

        Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(user);  //this is also working but better to use ->
        // Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(UserEntity.class));

        ResponseEntity<String> responseEntity = authController.register(registerDto);
        assertNotNull(responseEntity);

        // for username already exists (UAE) case
        Mockito.when(userRepository.existsByUsername(registerDto.getUsername())).thenReturn(true);
        ResponseEntity<String> responseEntityUAE = authController.register(registerDto);

        assertEquals("username is taken already!!", responseEntityUAE.getBody());

        //  // Method 2, Below code also working fine
//        Mockito.when(userRepository.existsByUsername(registerDto.getUsername())).thenReturn(false);
//        Mockito.when(roleRepository.findByName("USER")).thenReturn(Optional.of(role));
//        Mockito.when(passwordEncoder.encode(registerDto.getPassword())).thenReturn("encodedPassword");
//
//        ResponseEntity<String> response = authController.register(registerDto);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("User registered successfully!!", response.getBody());
//        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(UserEntity.class));

    }

    @Test
    void login() {
        LoginDto loginDto = new LoginDto();

        loginDto.setUsername("testUserName");
        loginDto.setPassword("testPassword");

        Authentication authentication = new Authentication() {
            private static final long serialVersionUID = 1L;
            private String name = "user" + new Random().nextInt(1000);
            private boolean authenticated = true;

            @Override
            public String getName() {
                return name;
            }

            @Override
            public boolean isAuthenticated() {
                return authenticated;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
                this.authenticated = isAuthenticated;
            }
        };;

        Mockito.when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()))).thenReturn(authentication);
        Mockito.when(jwtGenerator.generateToken(authentication)).thenReturn("token");

        ResponseEntity<AuthResponseDto> authResponseDtoResponseEntity = authController.login(loginDto);

        assertNotNull(authResponseDtoResponseEntity);
    }
}