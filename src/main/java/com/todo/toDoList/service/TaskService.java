package com.todo.toDoList.service;

import org.springframework.stereotype.Service;

import com.todo.toDoList.model.Task;
import com.todo.toDoList.repository.TaskRepository;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskService {


    private final TaskRepository taskRepo;
    public TaskService(TaskRepository taskRepo) {
        this.taskRepo = taskRepo;
    }
    public List<Task> getAllTasks() {
        return taskRepo.findAll();
    }
    public void addTask(Task task) {
        taskRepo.save(task);
    }
    public void markCompleted(Integer id){
        Task task = taskRepo.findById(id).orElseThrow();
        task.setCompleted(true);
        taskRepo.save(task);

    }
    public Map<LocalDate, List<Task>> getTasksGroupedByDate() {
        return taskRepo.findAll().stream()
                .sorted((t1, t2) -> t2.getScheduledTime().compareTo(t1.getScheduledTime())) // latest first
                .collect(Collectors.groupingBy(
                    task -> task.getScheduledTime().toLocalDate(),
                    LinkedHashMap::new,
                    Collectors.toList()
                ));
    }
    public void deleteTasksForDate(LocalDate date) {
        taskRepo.deleteByScheduledDate(date);
    }
  


}
