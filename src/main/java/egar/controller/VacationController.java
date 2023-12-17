package egar.controller;

import egar.domain.vacation.dto.VacationDtoRead;
import egar.domain.vacation.entity.Vacation;
import egar.enums.VacationStatus;
import egar.service.VacationService;
import egar.utils.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/vacation")
public class VacationController {
    private final VacationService vacationService;

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        Result<VacationDtoRead> vacationRead = vacationService.findById(id);
        if (vacationRead.isError()) {
            model.addAttribute("message", vacationRead.getMessage());
            return vacationRead.getCode();
        } else
            model.addAttribute("vacation", vacationRead.getObject());
        return "vacation/show";
    }

    @GetMapping("/findByEmployeeId/{id}")
    public String findByEmployeeId(@PathVariable Integer id, Model model) {
        Result<List<VacationDtoRead>> vacationList = vacationService.findByEmployeeId(id);
        if (vacationList.isError()) {
            model.addAttribute("message", vacationList.getMessage());
            return vacationList.getCode();
        } else
            model.addAttribute("vacationList", vacationList.getObject());
        return "vacation/showList";
    }

    @GetMapping("/findByStatus/{status}")
    public String findByStatus(@PathVariable VacationStatus status, Model model) {
        Result<List<VacationDtoRead>> vacationList = vacationService.findByStatus(status);
        if (vacationList.isError()) {
            model.addAttribute("message", vacationList.getMessage());
            return vacationList.getCode();
        } else
            model.addAttribute("vacationList", vacationList.getObject());
        return "vacation/showList";
    }

    @GetMapping("/findByEmployeeIdInRange/{id}/{status}/{timeStart}/{timeFinish}")
    public String findByEmployeeIdInRange(@PathVariable Integer id, @PathVariable VacationStatus status, @PathVariable LocalDateTime timeStart, @PathVariable LocalDateTime timeFinish, Model model) {
        Result<List<VacationDtoRead>> vacationList = vacationService.findByEmployeeIdAndStatusInRange(id, status, timeStart, timeFinish);
        if (vacationList.isError()) {
            model.addAttribute("message", vacationList.getMessage());
            return vacationList.getCode();
        } else
            model.addAttribute("vacationList", vacationList.getObject());
        return "vacation/showList";
    }

    @GetMapping("/new")
    public String getNew(Model model) {
        model.addAttribute(new Vacation());
        model.addAttribute("vacationStatus", VacationStatus.values());
        return "vacation/new";
    }

    @GetMapping("/update/{id}")
    public String getUpdateForm(@PathVariable("id") Integer id, Model model) {
        Result<VacationDtoRead> vacationRead = vacationService.findById(id);
        if (vacationRead.isError()) {
            model.addAttribute("message", vacationRead.getMessage());
            return vacationRead.getCode();
        }
        model.addAttribute("vacation", vacationRead.getObject());
        return "vacation/update";
    }

    @GetMapping("/")
    public String getHome(Model model) {
        model.addAttribute("vacationStatus", VacationStatus.values());
        return "vacation/index";
    }

    @PostMapping("/")
    public String create(@ModelAttribute("vacation") Vacation vacation, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", "Error in filled fields");
            return "400";
        }
        Result<VacationDtoRead> savedVacation = vacationService.create(vacation);
        if (savedVacation.isError()) {
            model.addAttribute("message", savedVacation.getMessage());
            return savedVacation.getCode();
        } else
            return "redirect:/vacation/" + savedVacation.getObject().getId();
    }

    @PostMapping("/submit")
    public String inputSubmit(
            @RequestParam(required = false, name = "id") Integer id,
            @RequestParam(required = false, name = "employeeId") Integer employeeId,
            @RequestParam(required = false, name = "status") VacationStatus status
    ) {
        if (id != null)
            return "redirect:/vacation/" + id;
        if (employeeId != null)
            return "redirect:/vacation/findByEmployeeId/" + employeeId;
        if (status != null)
            return "redirect:/vacation/findByStatus/" + status;

        return "redirect:/vacation/";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Integer id, @ModelAttribute("vacation") VacationDtoRead vacation, Model model) {
        Result<String> upload = vacationService.update(vacation);
        if (upload.isError()) {
            model.addAttribute("message", upload.getMessage());
            return upload.getCode();
        } else
            return "redirect:/vacation/" + id;
    }

    @GetMapping("/updateStatus/{id}/{status}")
    public String updateStatus(@PathVariable("id") Integer id, @PathVariable("status") VacationStatus vacationStatus, Model model) {
        Result<String> upload = vacationService.updateStatus(id, vacationStatus);
        if (upload.isError()) {
            model.addAttribute("message", upload.getMessage());
            return upload.getCode();
        } else
            return "redirect:/vacation/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        Result<String> delete = vacationService.delete(id);
        if (delete.isError()) {
            model.addAttribute("message", delete.getMessage());
            return delete.getCode();
        } else {
            model.addAttribute("message", delete.getObject());
            return "redirect:/vacation/";
        }
    }
}
