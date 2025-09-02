package com.todo.toDoList.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.todo.toDoList.model.Task;

import jakarta.transaction.Transactional;

public interface TaskRepository extends JpaRepository<Task,Integer> {
	 @Modifying
	    @Transactional
	    @Query("DELETE FROM Task t WHERE DATE(t.scheduledTime) = :date")
	    void deleteByScheduledDate(@Param("date") LocalDate date);
}
