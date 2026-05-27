package com.eam.blogging_platform.service;

import com.eam.blogging_platform.dto.PostDto;
import com.eam.blogging_platform.dto.PostDtoGetPostPut;
import com.eam.blogging_platform.dto.PostLikesDislikesDTO;
import com.eam.blogging_platform.dto.PostUpdateDTO;
import com.eam.blogging_platform.entity.Post;
import com.eam.blogging_platform.entity.Role;
import com.eam.blogging_platform.entity.Status;
import com.eam.blogging_platform.entity.User;
import com.eam.blogging_platform.repository.CategoriesPostRepository;
import com.eam.blogging_platform.repository.CategoryRepository;
import com.eam.blogging_platform.repository.PostRepository;
import com.eam.blogging_platform.repository.StatusRepository;
import com.eam.blogging_platform.repository.TagRepository;
import com.eam.blogging_platform.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StatusRepository statusRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private CategoriesPostRepository categoriesPostRepository;

    @InjectMocks
    private PostService postService;

    // CP-10: retorna DTO presente con likes/dislikes inicializados en 0.
    @Test
    void save_createsPostWithInitialCounters() {
        PostDto dto = new PostDto();
        dto.setTitle("New Post");
        dto.setContent("Post content");
        dto.setUserId(1L);
        dto.setStatusId(1L);

        Role role = new Role(1L, "ROLE_ADMIN", "Admin role");
        User user = new User(1L, "admin123", "admin@demo.com", "hash", LocalDateTime.now(), role);
        Status status = new Status(1, "Draft");

        Post savedPost = new Post(1L, user, "New Post", "Post content", 5, 5, LocalDateTime.now(), LocalDateTime.now(), null);
        savedPost.setStatus(status);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(statusRepository.findById(1L)).thenReturn(Optional.of(status));
        when(postRepository.save(any(Post.class))).thenReturn(savedPost);

        Optional<PostDtoGetPostPut> result = postService.save(dto);

        assertTrue(result.isPresent());
        assertEquals(0, result.get().getLikes());
        assertEquals(0, result.get().getDislikes());
        verify(postRepository, times(1)).save(any(Post.class));
    }

    // CP-11: retorna DTO con titulo, contenido y status actualizados.
    @Test
    void update_updatesTitleContentAndStatus() {
        PostUpdateDTO dto = new PostUpdateDTO();
        dto.setTitle("Updated Title");
        dto.setContent("Updated Content");
        dto.setStatus_id(2L);

        Role role = new Role(1L, "ROLE_ADMIN", "Admin role");
        User user = new User(1L, "admin123", "admin@demo.com", "hash", LocalDateTime.now(), role);
        Status oldStatus = new Status(1, "Draft");
        Status newStatus = new Status(2, "Published");

        Post existingPost = new Post(10L, user, "Old Title", "Old Content", 0, 0, LocalDateTime.now(), LocalDateTime.now(), null);
        existingPost.setStatus(oldStatus);

        Post updatedPost = new Post(10L, user, "Updated Title", "Updated Content", 0, 0, LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
        updatedPost.setStatus(newStatus);

        when(postRepository.findById(10L)).thenReturn(Optional.of(existingPost));
        when(statusRepository.findById(2L)).thenReturn(Optional.of(newStatus));
        when(postRepository.save(any(Post.class))).thenReturn(updatedPost);

        Optional<PostDtoGetPostPut> result = postService.update(10L, dto);

        assertTrue(result.isPresent());
        assertEquals("Updated Title", result.get().getTitle());
        assertEquals("Updated Content", result.get().getContent());
        assertEquals(2L, result.get().getStatusId());
    }

    // CP-12: retorna true y ejecuta deleteById.
    @Test
    void deleteById_existingPost_deletesAndReturnsTrue() {
        Role role = new Role(1L, "ROLE_ADMIN", "Admin role");
        User user = new User(1L, "admin123", "admin@demo.com", "hash", LocalDateTime.now(), role);
        Post post = new Post(99L, user, "Title", "Content", 0, 0, LocalDateTime.now(), LocalDateTime.now(), null);

        when(postRepository.findById(99L)).thenReturn(Optional.of(post));

        boolean result = postService.deleteById(99L);

        assertTrue(result);
        verify(postRepository, times(1)).deleteById(99L);
    }

    // CP-13: retorna lista de DTOs con el mismo tamano que el repositorio.
    @Test
    void findAll_returnsListOfDtos() {
        Role role = new Role(1L, "ROLE_ADMIN", "Admin role");
        User user = new User(1L, "admin123", "admin@demo.com", "hash", LocalDateTime.now(), role);
        Status status = new Status(2, "Published");

        Post post1 = new Post(1L, user, "Title 1", "Content 1", 0, 0, LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
        post1.setStatus(status);
        Post post2 = new Post(2L, user, "Title 2", "Content 2", 0, 0, LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
        post2.setStatus(status);

        when(postRepository.findAll()).thenReturn(Arrays.asList(post1, post2));

        List<PostDtoGetPostPut> result = postService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(postRepository, times(1)).findAll();
    }

    // CP-14: retorna DTO presente y mapeado desde la entidad.
    @Test
    void findById_existingPost_returnsDto() {
        Role role = new Role(1L, "ROLE_ADMIN", "Admin role");
        User user = new User(1L, "admin123", "admin@demo.com", "hash", LocalDateTime.now(), role);
        Status status = new Status(2, "Published");

        Post post = new Post(1L, user, "Title 1", "Content 1", 0, 0, LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
        post.setStatus(status);

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        Optional<PostDtoGetPostPut> result = postService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Title 1", result.get().getTitle());
        verify(postRepository, times(1)).findById(1L);
    }

    // CP-15: retorna solo posts con status publicado (id == 2).
    @Test
    void findPublished_returnsOnlyPublishedPosts() {
        Role role = new Role(1L, "ROLE_ADMIN", "Admin role");
        User user = new User(1L, "admin123", "admin@demo.com", "hash", LocalDateTime.now(), role);
        Status published = new Status(2, "Published");
        Status draft = new Status(1, "Draft");

        Post post1 = new Post(1L, user, "Title 1", "Content 1", 0, 0, LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
        post1.setStatus(published);
        Post post2 = new Post(2L, user, "Title 2", "Content 2", 0, 0, LocalDateTime.now(), LocalDateTime.now(), null);
        post2.setStatus(draft);

        when(postRepository.findAll()).thenReturn(Arrays.asList(post1, post2));

        List<PostDtoGetPostPut> result = postService.findPublished();

        assertEquals(1, result.size());
        assertEquals("Title 1", result.get(0).getTitle());
        verify(postRepository, times(1)).findAll();
    }

    // CP-16: retorna DTO con likes/dislikes actualizados.
    @Test
    void updateLikesDislikes_updatesCounters() {
        PostLikesDislikesDTO dto = new PostLikesDislikesDTO();
        dto.setLikes(5);
        dto.setDislikes(1);

        Role role = new Role(1L, "ROLE_ADMIN", "Admin role");
        User user = new User(1L, "admin123", "admin@demo.com", "hash", LocalDateTime.now(), role);
        Status status = new Status(2, "Published");

        Post existingPost = new Post(15L, user, "Title", "Content", 0, 0, LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
        existingPost.setStatus(status);

        Post updatedPost = new Post(15L, user, "Title", "Content", 5, 1, LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
        updatedPost.setStatus(status);

        when(postRepository.findById(15L)).thenReturn(Optional.of(existingPost));
        when(postRepository.save(any(Post.class))).thenReturn(updatedPost);

        Optional<PostDtoGetPostPut> result = postService.updateLikesDislikes(15L, dto);

        assertTrue(result.isPresent());
        assertEquals(5, result.get().getLikes());
        assertEquals(1, result.get().getDislikes());
    }
}
