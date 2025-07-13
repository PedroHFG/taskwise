package com.example.taskwise.service;

import com.example.taskwise.dto.TaskRequestDTO;
import com.example.taskwise.dto.TaskResponseDTO;
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

    private void copyDtoToEntity(TaskRequestDTO taskRequestDTO, Task entity) {
        entity.setTitle(taskRequestDTO.getTitle());
        entity.setDescription(taskRequestDTO.getDescription());
        entity.setDueDate(taskRequestDTO.getDueDate());
    }
}
