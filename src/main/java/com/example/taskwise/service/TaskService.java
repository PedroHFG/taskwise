package com.example.taskwise.service;

import com.example.taskwise.dto.TaskRequestDTO;
import com.example.taskwise.dto.TaskResponseDTO;
import com.example.taskwise.exception.ResourceNotFoundException;
import com.example.taskwise.model.Task;
import com.example.taskwise.model.User;
import com.example.taskwise.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Transactional
    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {

        if (taskRepository.findByTitle(taskRequestDTO.getTitle()).isPresent()) {
            throw new IllegalArgumentException("Uma tarefa com este título já existe");
        }

        Task entity = new Task();
        copyDtoToEntity(taskRequestDTO, entity);

        User userLogged = userService.authenticated();
        entity.setUser(userLogged);

        entity = taskRepository.save(entity);

        return new TaskResponseDTO(entity);
    }

    @Transactional(readOnly = true)
    public Page<TaskResponseDTO> getAllTasks(Boolean completed, String title, LocalDate dueDate, Pageable pageable) {
        User currentUser = userService.authenticated();
        Page<Task> tasksPage;

        if (currentUser.hasRole("ROLE_ADMIN")) {
            tasksPage = taskRepository.findTaskWithFilters(completed, title, dueDate, pageable);
        }
        else {
            tasksPage = taskRepository.findTaskByUserIdAndFilters(currentUser.getId(), completed, title, dueDate, pageable);
        }

        return tasksPage.map(TaskResponseDTO::new);
    }

    @Transactional(readOnly = true)
    public TaskResponseDTO findById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Tarefa não encontrada com ID: " + id));
        authService.validateSelfOrAdmin(task.getUser().getId());
        return new TaskResponseDTO(task);
    }

    @Transactional
    public TaskResponseDTO updateTask(Long id, TaskRequestDTO taskRequestDTO) {
        // Primeiro, verifica se a tarefa existe
        Task existingTask = taskRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Tarefa não encontrada com ID: " + id));

        authService.validateSelfOrAdmin(existingTask.getUser().getId());

        // Validação de título duplicado, mas permitindo que a própria tarefa mantenha seu título
        if (taskRequestDTO.getTitle() != null && !taskRequestDTO.getTitle().equals(existingTask.getTitle())) {
            if (taskRepository.findByTitle(taskRequestDTO.getTitle()).isPresent()) {
                throw new IllegalArgumentException("Uma tarefa com este título já existe");
            }
        }

        copyDtoToEntity(taskRequestDTO, existingTask);

        existingTask = taskRepository.save(existingTask);

        return new TaskResponseDTO(existingTask);
    }

    @Transactional
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tarefa não encontrada com ID: " + id);
        }

        Task task = taskRepository.getReferenceById(id);
        authService.validateSelfOrAdmin(task.getUser().getId());
        taskRepository.deleteById(id);
    }

    private void copyDtoToEntity(TaskRequestDTO taskRequestDTO, Task entity) {
        if (taskRequestDTO.getTitle() != null) {
            entity.setTitle(taskRequestDTO.getTitle());
        }
        if (taskRequestDTO.getDescription() != null) {
            entity.setDescription(taskRequestDTO.getDescription());
        }
        if (taskRequestDTO.getDueDate() != null) {
            entity.setDueDate(taskRequestDTO.getDueDate());
        }

        if (taskRequestDTO.getCompleted() != null) {
            entity.setCompleted(taskRequestDTO.getCompleted());
        }
    }
}
