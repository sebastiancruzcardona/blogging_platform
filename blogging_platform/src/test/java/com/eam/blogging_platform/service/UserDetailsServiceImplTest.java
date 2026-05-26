package com.eam.blogging_platform.service;

import com.eam.blogging_platform.entity.Role;
import com.eam.blogging_platform.entity.User;
import com.eam.blogging_platform.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    // CP-User-Auth-01 (positivo): retorna UserDetails con username/password esperados.
    @Test
    void loadUserByUsername_existingUser_returnsUserDetails() {
        Role role = new Role(1L, "ROLE_ADMIN", "Admin role");
        User user = new User(1L, "admin123", "admin@demo.com", "hashPassword", LocalDateTime.now(), role);

        when(userRepository.findByUsername("admin123")).thenReturn(Optional.of(user));

        UserDetails result = userDetailsService.loadUserByUsername("admin123");

        assertNotNull(result);
        assertEquals("admin123", result.getUsername());
        assertEquals("hashPassword", result.getPassword());
        verify(userRepository, times(1)).findByUsername("admin123");
    }

    // CP-User-Auth-01 (negativo): lanza UsernameNotFoundException si no existe usuario.
    @Test
    void loadUserByUsername_missingUser_throwsException() {
        when(userRepository.findByUsername("usuarioFalso")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("usuarioFalso"));
        verify(userRepository, times(1)).findByUsername("usuarioFalso");
    }
}
