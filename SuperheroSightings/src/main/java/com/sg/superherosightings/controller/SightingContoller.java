/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controller;

import com.sg.superherosightings.dao.LocationDAO;
import com.sg.superherosightings.dao.SightingDAO;
import com.sg.superherosightings.dao.SuperDAO;
import com.sg.superherosightings.dto.Location;
import com.sg.superherosightings.dto.Sighting;
import com.sg.superherosightings.dto.Super;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author nicoleozkan
 */
@Controller
public class SightingContoller {

    @Autowired
    SightingDAO sightingDao;

    @Autowired
    SuperDAO superDao;

    @Autowired
    LocationDAO locationDao;

    Set<ConstraintViolation<Sighting>> violations = new HashSet<>();

    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest request, Model model) {
        Sighting sighting = new Sighting();

        String locId = request.getParameter("locId");
        sighting.setLocation(locationDao.getLocationById(Integer.parseInt(locId)));

        String superId = request.getParameter("superId");
        sighting.setSuperPerson(superDao.getSuperById(Integer.parseInt(superId)));

        LocalDate date = null;
        String dateString = request.getParameter("date");
        if (validDate(dateString)) {
            date = LocalDate.parse(dateString);
        }
        sighting.setDate(date);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sighting);
        model.addAttribute("errors", violations);

        if (violations.isEmpty()) {
            sightingDao.addSighting(sighting);
            return "redirect:/sightings";
        }
        
        List<Sighting> sightings = sightingDao.getAllSightings();
        model.addAttribute("sightings", sightings);
        
        List<Super> supers = superDao.getAllSupers();
        model.addAttribute("supers", supers);
        
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("locations", locations);
        
        return "sightings";
    }

    @GetMapping("sighting")
    public String displaySighting(Integer sightingId, Model model) {
        Sighting sighting = sightingDao.getSightingById(sightingId);
        model.addAttribute("sighting", sighting);
        return "sighting";
    }

    @GetMapping("sightings")
    public String displaySightings(Model model) {
        List<Sighting> sightings = sightingDao.getAllSightings();
        model.addAttribute("sightings", sightings);

        List<Super> supers = superDao.getAllSupers();
        model.addAttribute("supers", supers);

        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("locations", locations);
        
        violations = new HashSet<>();
        model.addAttribute("errors", violations);

        return "sightings";
    }

    @GetMapping("editSighting")
    public String editSighting(Integer sightingId, Model model) {
        Sighting sighting = sightingDao.getSightingById(sightingId);

        String date = sighting.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
        model.addAttribute("date", date);

        model.addAttribute("sighting", sighting);

        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("locations", locations);

        List<Super> supers = superDao.getAllSupers();
        model.addAttribute("supers", supers);
        
        violations = new HashSet<>();
        model.addAttribute("errors", violations);

        return "editSighting";
    }

    @PostMapping("editSighting")
    public String performEditSighting(HttpServletRequest request, Model model) {
        Integer sightingId = Integer.parseInt(request.getParameter("sightingId"));
        Sighting sighting = sightingDao.getSightingById(sightingId);
        
        String locationId = request.getParameter("locId");
        Location location = locationDao.getLocationById(Integer.parseInt(locationId));
        sighting.setLocation(location);

        String superId = request.getParameter("superId");
        Super superPerson = superDao.getSuperById(Integer.parseInt(superId));
        sighting.setSuperPerson(superPerson);
        
        LocalDate date = null;
        String dateString = request.getParameter("date");
        if (validDate(dateString)) {
            date = LocalDate.parse(dateString);
        }
        sighting.setDate(date);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sighting);
        model.addAttribute("errors", violations);

        if (violations.isEmpty()) {
            sightingDao.updateSighting(sighting);
            return "redirect:/sighting?sightingId=" + sighting.getSightingId();
        }
        
        model.addAttribute("supers", superDao.getAllSupers());
        model.addAttribute("locations", locationDao.getAllLocations());
        model.addAttribute("sighting", sighting);
        return "editSighting";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(Integer sightingId, Model model) {
        Sighting sighting = sightingDao.getSightingById(sightingId);
        model.addAttribute("sighting", sighting);
        return "deleteSighting";
    }

    @PostMapping("deleteSighting")
    public String performDeleteSighting(Sighting sighting, HttpServletRequest request) {
        sightingDao.deleteSightingById(sighting.getSightingId());
        return "redirect:/sightings";
    }

    @GetMapping({"/","index"})
    public String displayLatestSightings(Model model) {
        List<Sighting> sightings = sightingDao.getLatestSightings();
        model.addAttribute("sightings", sightings);
        return "index";
    }

    private boolean validDate(String date) {
        try {
            LocalDate.parse(date);
            return true;
        } catch (IllegalArgumentException | DateTimeParseException ex ) {
            return false;
        }
    }
}
