/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controller;

import com.sg.superherosightings.dao.PowerDAO;
import com.sg.superherosightings.dao.SuperDAO;
import com.sg.superherosightings.dto.Power;
import com.sg.superherosightings.dto.Super;
import java.util.ArrayList;
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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author nicoleozkan
 */
@Controller
public class SuperController {

    @Autowired
    SuperDAO superDao;

    @Autowired
    PowerDAO powerDao;

    Set<ConstraintViolation<Super>> violations = new HashSet<>();

    @PostMapping("addSuper")
    public String addSuper(Super superPerson, HttpServletRequest request, Model model) {
        String[] powerIds = request.getParameterValues("powerId");
        List<Power> powers = new ArrayList<>();
        if(powerIds != null)
        {
            for (String powerId : powerIds) {
                powers.add(powerDao.getPowerById(Integer.parseInt(powerId)));
            }
        }
        superPerson.setPowers(powers);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(superPerson);
        model.addAttribute("errors", violations);

        if (violations.isEmpty()) {
            superDao.addSuper(superPerson);
            return "redirect:/supers";
        }
                
        List<Super> supers = superDao.getAllSupers();
        model.addAttribute("supers", supers);
        
        powers = powerDao.getAllPowers();
        model.addAttribute("powers", powers);
        
        return "supers";
    }

    @GetMapping("super")
    public String displaySuper(Integer superId, Model model) {
        Super superPerson = superDao.getSuperById(superId);
        model.addAttribute("super", superPerson);
        return "super";
    }

    @GetMapping("supers")
    public String displaySupers(Model model) {
        List<Super> supers = superDao.getAllSupers();
        model.addAttribute("supers", supers);
        List<Power> powers = powerDao.getAllPowers();
        model.addAttribute("powers", powers);
        violations = new HashSet<>();
        model.addAttribute("errors", violations);

        return "supers";
    }

    @GetMapping("editSuper")
    public String editSuper(Integer superId, Model model) {
        Super superPerson = superDao.getSuperById(superId);
        model.addAttribute("super", superPerson);
        List<Power> powers = powerDao.getAllPowers();
        model.addAttribute("powers", powers);
        violations = new HashSet<>();
        model.addAttribute("errors", violations);
        return "editSuper";
    }

    @PostMapping("editSuper")
    public String performEditSuper(Super superPerson, BindingResult result, HttpServletRequest request, Model model) {
        String[] powerIds = request.getParameterValues("powerId");
        List<Power> powers = new ArrayList<>();
        if (powerIds != null) {
            for (String powerId : powerIds) {
                powers.add(powerDao.getPowerById(Integer.parseInt(powerId)));
            }
        }
        superPerson.setPowers(powers);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(superPerson);
        model.addAttribute("errors", violations);

        if (!violations.isEmpty()) {
            model.addAttribute("powers", powerDao.getAllPowers());
            model.addAttribute("super", superPerson);
            return "editSuper";
        }

        superDao.updateSuper(superPerson);
        return "super";
    }

    @GetMapping("deleteSuper")
    public String deleteSuper(Integer superId, Model model) {
        Super superPerson = superDao.getSuperById(superId);
        model.addAttribute("super", superPerson);
        return "deleteSuper";
    }

    @PostMapping("deleteSuper")
    public String performDeleteSuper(Super superPerson, HttpServletRequest request) {
        superDao.deleteSuperById(superPerson.getSuperId());
        return "redirect:/supers";
    }
}
