package com.eam.blogging_platform.service;

import com.eam.blogging_platform.dto.UserDTO;
import com.eam.blogging_platform.dto.UserDTOGetPostPut;
import com.eam.blogging_platform.dto.UserRegisterUpdateDTO;
import com.eam.blogging_platform.entity.Role;
import com.eam.blogging_platform.entity.User;
import com.eam.blogging_platform.repository.FollowedAuthorsRepository;
import com.eam.blogging_platform.repository.RoleRepository;
import com.eam.blogging_platform.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private FollowedAuthorsRepository followedAuthorsRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    // CP-02: retorna DTO presente y persiste con rol asignado por Admin.
    @Test
    void save_adminCreatesUser_returnsDto() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("admin123");
        userDTO.setEmail("admin@demo.com");
        userDTO.setPassword("Admin123!");
        userDTO.setRoleID(1L);

        Role role = new Role(1L, "ROLE_ADMIN", "Admin role");
        User savedUser = new User(1L, "admin123", "admin@demo.com", "hashed", LocalDateTime.now(), role);

        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(passwordEncoder.encode("Admin123!")).thenReturn("hashed");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        Optional<UserDTOGetPostPut> result = userService.save(userDTO);

        assertTrue(result.isPresent());
        assertEquals("admin123", result.get().getUsername());
        assertEquals("admin@demo.com", result.get().getEmail());
        assertEquals("hashed", result.get().getPassword());
        assertEquals(1L, result.get().getRoleID());
        verify(userRepository, times(1)).save(any(User.class));
    }

    // CP-03: retorna DTO presente con rol por defecto "author".
    @Test
    void saveForUser_publicRegistration_returnsDto() {
        UserRegisterUpdateDTO dto = new UserRegisterUpdateDTO();
        dto.setUsername("user123");
        dto.setEmail("user@demo.com");
        dto.setPassword("User123!");

        Role role = new Role(2L, "author", "Default role");
        User savedUser = new User(2L, "user123", "user@demo.com", "hashed", LocalDateTime.now(), role);

        when(roleRepository.findByRole("author")).thenReturn(Optional.of(role));
        when(passwordEncoder.encode("User123!")).thenReturn("hashed");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        Optional<UserDTOGetPostPut> result = userService.saveForUser(dto);

        assertTrue(result.isPresent());
        assertEquals("user123", result.get().getUsername());
        assertEquals("user@demo.com", result.get().getEmail());
        assertEquals(2L, result.get().getRoleID());
        verify(userRepository, times(1)).save(any(User.class));
    }

    // CP-04: retorna DTO con datos y rol actualizados.
    @Test
    void update_adminUpdatesUser_returnsDto() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("newadmin");
        userDTO.setEmail("newadmin@demo.com");
        userDTO.setPassword("NewAdmin123!");
        userDTO.setRoleID(2L);

        Role oldRole = new Role(1L, "ROLE_ADMIN", "Admin role");
        Role newRole = new Role(2L, "ROLE_EDITOR", "Editor role");

        User existingUser = new User(1L, "admin123", "admin@demo.com", "oldHash", LocalDateTime.now(), oldRole);
        User updatedUser = new User(1L, "newadmin", "newadmin@demo.com", "newHash", LocalDateTime.now(), newRole);

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(roleRepository.findById(2L)).thenReturn(Optional.of(newRole));
        when(passwordEncoder.encode("NewAdmin123!")).thenReturn("newHash");
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        Optional<UserDTOGetPostPut> result = userService.update(1L, userDTO);

        assertTrue(result.isPresent());
        assertEquals("newadmin", result.get().getUsername());
        assertEquals("newadmin@demo.com", result.get().getEmail());
        assertEquals(2L, result.get().getRoleID());
    }

    // CP-05: retorna DTO con campos basicos actualizados y mismo rol.
    @Test
    void updateForUser_updatesBasicFields_keepsRole() {
        UserRegisterUpdateDTO dto = new UserRegisterUpdateDTO();
        dto.setUsername("userUpdated");
        dto.setEmail("userUpdated@demo.com");
        dto.setPassword("UserUpdated123!");

        Role role = new Role(3L, "author", "Author role");
        User existingUser = new User(3L, "user123", "user@demo.com", "oldHash", LocalDateTime.now(), role);
        User updatedUser = new User(3L, "userUpdated", "userUpdated@demo.com", "newHash", LocalDateTime.now(), role);

        when(userRepository.findById(3L)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode("UserUpdated123!")).thenReturn("newHash");
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        Optional<UserDTOGetPostPut> result = userService.updateForUser(3L, dto);

        assertTrue(result.isPresent());
        assertEquals("userUpdated", result.get().getUsername());
        assertEquals("userUpdated@demo.com", result.get().getEmail());
        assertEquals(3L, result.get().getRoleID());
    }

    // CP-06: retorna true y ejecuta deleteById.
    @Test
    void deleteById_existingUser_deletesAndReturnsTrue() {
        Role role = new Role(1L, "ROLE_ADMIN", "Admin role");
        User existingUser = new User(1L, "admin123", "admin@demo.com", "hash", LocalDateTime.now(), role);

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));

        boolean result = userService.deleteById(1L);

        assertTrue(result);
        verify(userRepository, times(1)).deleteById(1L);
    }

    // CP-07: retorna lista de DTOs con el mismo tamano que el repositorio.
    @Test
    void findAll_returnsListOfDtos() {
        Role role = new Role(1L, "ROLE_ADMIN", "Admin role");
        User user1 = new User(1L, "admin123", "admin@demo.com", "hash1", LocalDateTime.now(), role);
        User user2 = new User(2L, "user123", "user@demo.com", "hash2", LocalDateTime.now(), role);

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<UserDTOGetPostPut> result = userService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("admin@demo.com", result.get(0).getEmail());
        verify(userRepository, times(1)).findAll();
    }

    // CP-08: retorna DTO presente y mapeado desde la entidad.
    @Test
    void findById_existingUser_returnsDto() {
        Role role = new Role(1L, "ROLE_ADMIN", "Admin role");
        User user = new User(1L, "admin123", "admin@demo.com", "hash", LocalDateTime.now(), role);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<UserDTOGetPostPut> result = userService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("admin123", result.get().getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    // CP-09: retorna DTO presente al buscar por username.
    @Test
    void findByUsername_existingUser_returnsDto() {
        Role role = new Role(1L, "ROLE_ADMIN", "Admin role");
        User user = new User(1L, "sebastian_admin", "admin@demo.com", "hash", LocalDateTime.now(), role);

        when(userRepository.findByUsername("sebastian_admin")).thenReturn(Optional.of(user));

        Optional<UserDTOGetPostPut> result = userService.findByUsername("sebastian_admin");

        assertTrue(result.isPresent());
        assertEquals("sebastian_admin", result.get().getUsername());
        verify(userRepository, times(1)).findByUsername("sebastian_admin");
    }
    
}
