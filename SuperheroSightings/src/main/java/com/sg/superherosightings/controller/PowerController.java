/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controller;

import com.sg.superherosightings.dao.PowerDAO;
import com.sg.superherosightings.dto.Power;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author nicoleozkan
 */
@Controller
public class PowerController {

    @Autowired
    PowerDAO powerDao;

    Set<ConstraintViolation<Power>> violations = new HashSet<>();

    @PostMapping("addPower")
    public String addPower(Power power, Model model) {
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(power);
        model.addAttribute("errors", violations);
        
        if (violations.isEmpty()) {
            powerDao.addPower(power);
            return "redirect:/powers";
        }
        List<Power> powers = powerDao.getAllPowers();
        model.addAttribute("powers", powers);
        return "powers";
    }

    @GetMapping("power")
    public String displayPower(Integer powerId, Model model) {
        Power power = powerDao.getPowerById(powerId);
        model.addAttribute("power", power);
        return "power";
    }

    @GetMapping("powers")
    public String displayPowers(Model model) {
        List<Power> powers = powerDao.getAllPowers();
        model.addAttribute("powers", powers);
        violations = new HashSet<>();
        model.addAttribute("errors", violations);
        return "powers";
    }

    @GetMapping("editPower")
    public String editPower(Integer powerId, Model model) {
        Power power = powerDao.getPowerById(powerId);
        model.addAttribute("power", power);
        model.addAttribute("errors", violations);
        return "editPower";
    }

    @PostMapping("editPower")
    public String performEditPower(@Valid Power power, BindingResult result) {
        if (result.hasErrors()) {
            return "editPower";
        }
        powerDao.updatePower(power);
        return "power";
    }

    @GetMapping("deletePower")
    public String deletePower(Integer powerId, Model model) {
        Power power = powerDao.getPowerById(powerId);
        model.addAttribute("power", power);
        return "deletePower";
    }

    @PostMapping("deletePower")
    public String performDeletePower(Power power, HttpServletRequest request) {
        powerDao.deletePowerById(power.getPowerId());
        return "redirect:/powers";
    }
}
