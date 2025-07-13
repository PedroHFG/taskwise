package com.example.taskwise.service;

import com.example.taskwise.dto.TaskRequestDTO;
import com.example.taskwise.dto.TaskResponseDTO;
import com.example.taskwise.exception.ResourceNotFoundException;
import com.example.taskwise.model.Task;
import com.example.taskwise.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Transactional
    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {

        if (taskRepository.findByTitle(taskRequestDTO.getTitle()).isPresent()) {
            throw new IllegalArgumentException("Uma tarefa com este título já existe");
        }

        Task entity = new Task();
        copyDtoToEntity(taskRequestDTO, entity);

        entity = taskRepository.save(entity);

        return new TaskResponseDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map(x -> new TaskResponseDTO(x)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TaskResponseDTO findById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Tarefa não encontrada com ID: " + id));
        return new TaskResponseDTO(task);
    }

    @Transactional
    public TaskResponseDTO updateTask(Long id, TaskRequestDTO taskRequestDTO) {
        // Primeiro, verifica se a tarefa existe
        Task existingTask = taskRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Tarefa não encontrada com ID: " + id));

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
    }
}
