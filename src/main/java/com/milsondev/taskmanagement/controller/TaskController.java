package com.milsondev.taskmanagement.controller;

import ch.qos.logback.core.model.Model;
import com.milsondev.taskmanagement.api.Status;
import com.milsondev.taskmanagement.api.TaskDto;
import com.milsondev.taskmanagement.service.TaskService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Stack;
import java.util.UUID;

@Controller("/")
public class TaskController {

    private final TaskService taskService;
    private String globalStatus;
    private static final int PAGE_SIZE = 10;
    private static final int PAGE_NO = 1;
    private Integer SELECTED_PAGE;


    @Autowired
    public TaskController(final TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public ModelAndView home(@ModelAttribute("alertMessage") @Nullable String alertMessage ) {
        ModelAndView mv = new ModelAndView("index");
        if(SELECTED_PAGE == null){
            SELECTED_PAGE = PAGE_NO;
        }
        Page<TaskDto> page = taskService.getTaskListPaginated(SELECTED_PAGE, PAGE_SIZE, globalStatus);
        List<TaskDto> taskDtoList = page.getContent();
        mv.addObject("taskDtoList", taskDtoList);
        mv.addObject("currentPage", SELECTED_PAGE);
        mv.addObject("totalPages", page.getTotalPages());
        mv.addObject("totalItems", page.getTotalElements());
        mv.addObject("alertMessage", alertMessage);
        SELECTED_PAGE = null;
        return mv;
    }

    @GetMapping("/add-new-task")
    public ModelAndView pageNewTask() {
        ModelAndView mv = new ModelAndView("new-task");
        mv.addObject("taskDto", new TaskDto());
        mv.addObject("priorities", taskService.getPriorities());
        mv.addObject("statusList", taskService.getStatus());
        mv.addObject("alertMessage", "");
        return mv;
    }

    @PostMapping("/add-or-update-task")
    public ModelAndView addOrUpdateTask(final @Valid TaskDto taskDto,
                                        final BindingResult bindResult,
                                        final RedirectAttributes redirectAttributes) {

        if(bindResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("new-task");
            mv.addObject("taskDto", taskDto);
            mv.addObject("priorities", taskService.getPriorities());
            mv.addObject("statusList", taskService.getStatus());
            mv.addObject("alertMessage", "Error, please fill the form correctly");
            return mv;
        }

        if(taskDto.getId() == null){
            taskService.saveTask(taskDto);
            redirectAttributes.addFlashAttribute("alertMessage", "New task was been successfully saved");
        } else {
            taskService.updateTask(taskDto);
            redirectAttributes.addFlashAttribute("alertMessage", "Task was been successfully updated");
        }


        return new ModelAndView("redirect:/");
    }

    @GetMapping("/edit-task/{id}")
    public ModelAndView editTask(@PathVariable ("id") UUID id, RedirectAttributes redirectAttributes ) {
        TaskDto taskDto = taskService.getTaskById(id);
        redirectAttributes.addFlashAttribute("taskDto", taskDto);
        return new ModelAndView("redirect:/edit-task");
    }

    @GetMapping("/edit-task")
    public ModelAndView editTaskRedirect (Model model, @ModelAttribute ("taskDto") TaskDto taskDto ) {
        ModelAndView mv = new ModelAndView("new-task");
        mv.addObject("taskDto", taskDto);
        mv.addObject("priorities", taskService.getPriorities());
        mv.addObject("statusList", taskService.getStatus());
        mv.addObject("alertMessage", "");
        return mv;
    }

    @DeleteMapping("/delete-task/{id}")
    public ModelAndView deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id);
        ModelAndView mv = new ModelAndView("components/task-card");
        Page<TaskDto> page = taskService.getTaskListPaginated(SELECTED_PAGE != null ? SELECTED_PAGE : 1, PAGE_SIZE, globalStatus);
        List<TaskDto> taskDtoList = page.getContent();
        mv.addObject("taskDtoList", taskDtoList);
        mv.addObject("currentPage", SELECTED_PAGE != null ? SELECTED_PAGE : 1);
        mv.addObject("totalPages", page.getTotalPages());
        mv.addObject("totalItems", page.getTotalElements());
        mv.addObject("taskDtoList", taskDtoList);
        return mv;
    }

    @GetMapping("/task-by-status")
    public ModelAndView getTaskListByStatus(@RequestParam(name = "status", required = false) String status) {
        ModelAndView mv = new ModelAndView("redirect:/");
        globalStatus = status;
        return mv;
    }

    @GetMapping("/page/{pageNo}")
    public ModelAndView findPaginated(@PathVariable (value = "pageNo") int pageNo) {
        ModelAndView mv = new ModelAndView("components/task-card");
        SELECTED_PAGE = pageNo;
        Page<TaskDto> page = taskService.getTaskListPaginated(pageNo, PAGE_SIZE, globalStatus);
        List<TaskDto> taskDtoList = page.getContent();
        mv.addObject("taskDtoList", taskDtoList);
        mv.addObject("currentPage", pageNo);
        mv.addObject("totalPages", page.getTotalPages());
        mv.addObject("totalItems", page.getTotalElements());
        return mv;
    }


}