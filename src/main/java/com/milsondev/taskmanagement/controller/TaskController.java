package com.milsondev.taskmanagement.controller;

import com.milsondev.taskmanagement.api.TaskDto;
import com.milsondev.taskmanagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller("/")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(final TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView("index");
        List<TaskDto> taskDtoList = taskService.getTaskList();
        mv.addObject("taskDtoList", taskDtoList);
        return mv;
    }

    @GetMapping("/add-new-task")
    public ModelAndView pageNewTask() {
        ModelAndView modelAndView = new ModelAndView("new-task");
        return modelAndView;

    }

}
