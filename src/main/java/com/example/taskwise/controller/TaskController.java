package com.example.taskwise.controller;

import com.example.taskwise.dto.TaskRequestDTO;
import com.example.taskwise.dto.TaskResponseDTO;
import com.example.taskwise.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        TaskResponseDTO newTask = taskService.createTask(taskRequestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newTask.getId()).toUri();
        return ResponseEntity.created(uri).body(newTask);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Page<TaskResponseDTO>> getAllTasks(
            @RequestParam(value = "completed", required = false) Boolean completed,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "dueDate", required = false) LocalDate dueDate,
            Pageable pageable) {
        Page<TaskResponseDTO> tasks = taskService.getAllTasks(completed, title, dueDate, pageable);
        return ResponseEntity.ok(tasks);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id) {
        TaskResponseDTO task = taskService.findById(id);
        return ResponseEntity.ok(task);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        TaskResponseDTO updatedTask = taskService.updateTask(id, taskRequestDTO);
        return ResponseEntity.ok(updatedTask);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
