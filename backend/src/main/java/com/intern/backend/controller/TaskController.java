package com.intern.backend.controller;

import com.intern.backend.dto.MessageResponse;
import com.intern.backend.dto.TaskDto;
import com.intern.backend.model.Task;
import com.intern.backend.model.User;
import com.intern.backend.repository.TaskRepository;
import com.intern.backend.repository.UserRepository;
import com.intern.backend.security.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }

    @GetMapping
    public ResponseEntity<?> getAllTasks() {
        User user = getCurrentUser();
        List<Task> tasks;
        if (user.getRole().equals("ROLE_ADMIN")) {
            tasks = taskRepository.findAll();
        } else {
            tasks = taskRepository.findByUser(user);
        }

        List<TaskDto> taskDtos = tasks.stream().map(task -> {
            TaskDto dto = new TaskDto();
            dto.setId(task.getId());
            dto.setTitle(task.getTitle());
            dto.setDescription(task.getDescription());
            dto.setStatus(task.getStatus());
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(taskDtos);
    }

    @PostMapping
    public ResponseEntity<?> createTask(@Valid @RequestBody TaskDto taskDto) {
        User user = getCurrentUser();
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStatus(taskDto.getStatus() != null ? taskDto.getStatus() : "PENDING");
        task.setUser(user);

        taskRepository.save(task);

        return ResponseEntity.ok(new MessageResponse("Task created successfully!"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @Valid @RequestBody TaskDto taskDto) {
        User user = getCurrentUser();
        Task task = taskRepository.findById(id).orElse(null);

        if (task == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Task not found."));
        }

        if (!user.getRole().equals("ROLE_ADMIN") && !task.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(403).body(new MessageResponse("Error: Unauthorized to update this task."));
        }

        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        if (taskDto.getStatus() != null) task.setStatus(taskDto.getStatus());

        taskRepository.save(task);

        return ResponseEntity.ok(new MessageResponse("Task updated successfully!"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        User user = getCurrentUser();
        Task task = taskRepository.findById(id).orElse(null);

        if (task == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Task not found."));
        }

        if (!user.getRole().equals("ROLE_ADMIN") && !task.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(403).body(new MessageResponse("Error: Unauthorized to delete this task."));
        }

        taskRepository.delete(task);

        return ResponseEntity.ok(new MessageResponse("Task deleted successfully!"));
    }
}
