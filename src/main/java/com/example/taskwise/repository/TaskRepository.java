package com.example.taskwise.repository;

import com.example.taskwise.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByTitle(String title);

    @Query("""
            SELECT obj FROM Task obj
            WHERE (:completed IS NULL OR obj.completed = :completed)
            AND (:title IS NULL OR LOWER(obj.title) LIKE LOWER(CONCAT('%', :title, '%')))
            AND (:dueDate IS NULL OR obj.dueDate <= :dueDate)
            """)
    Page<Task> findTaskWithFilters(
            @Param("completed") Boolean completed,
            @Param("title") String title,
            @Param("dueDate") LocalDate dueDate,
            Pageable pageable);
}
