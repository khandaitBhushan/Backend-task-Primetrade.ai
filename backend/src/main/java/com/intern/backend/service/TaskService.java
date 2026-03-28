package com.intern.backend.service;

import com.intern.backend.dto.TaskDto;
import com.intern.backend.entity.Task;
import com.intern.backend.entity.User;
import com.intern.backend.repository.TaskRepository;
import com.intern.backend.repository.UserRepository;
import com.intern.backend.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }

    public List<TaskDto> getAllTasks() {
        User user = getCurrentUser();
        List<Task> tasks;
        if (user.getRole().equals("ROLE_ADMIN")) {
            tasks = taskRepository.findAll();
        } else {
            tasks = taskRepository.findByUser(user);
        }

        return tasks.stream().map(task -> {
            TaskDto dto = new TaskDto();
            dto.setId(task.getId());
            dto.setTitle(task.getTitle());
            dto.setDescription(task.getDescription());
            dto.setStatus(task.getStatus());
            return dto;
        }).collect(Collectors.toList());
    }

    public void createTask(TaskDto taskDto) {
        User user = getCurrentUser();
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStatus(taskDto.getStatus() != null ? taskDto.getStatus() : "PENDING");
        task.setUser(user);

        taskRepository.save(task);
    }

    public void updateTask(Long id, TaskDto taskDto) {
        User user = getCurrentUser();
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: Task not found."));

        if (!user.getRole().equals("ROLE_ADMIN") && !task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Error: Unauthorized to update this task.");
        }

        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        if (taskDto.getStatus() != null) task.setStatus(taskDto.getStatus());

        taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        User user = getCurrentUser();
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: Task not found."));

        if (!user.getRole().equals("ROLE_ADMIN") && !task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Error: Unauthorized to delete this task.");
        }

        taskRepository.delete(task);
    }
}
