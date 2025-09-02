package com.todo.toDoList.controller;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.todo.toDoList.model.Task;
import com.todo.toDoList.service.TaskService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
public class TaskController {
	
    private final TaskService service;
    public TaskController(TaskService service) {
        this.service = service;
    }
    @GetMapping("/")
    public String index(Model model) {
    	model.addAttribute("tasksByDate", service.getTasksGroupedByDate());
        return "index";
    }

    @PostMapping("/add")
    public String addTask(@RequestParam String title,
                          @RequestParam String description,
                          @RequestParam String scheduledTime){
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setScheduledTime(LocalDateTime.parse(scheduledTime));
        task.setCompleted(false);
        service.addTask(task);
        return "redirect:/";
    }
    @PostMapping("/complete/{id}")
    public String complete(@PathVariable Integer id) {
        service.markCompleted(id);
        return "redirect:/";
    }
    @PostMapping("/deleteByDate")
    public String deleteTasksByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        service.deleteTasksForDate(date);
        return "redirect:/";
    }
}
