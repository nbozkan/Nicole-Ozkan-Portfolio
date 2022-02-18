/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controller;

import com.sg.superherosightings.dao.LocationDAO;
import com.sg.superherosightings.dao.SightingDAO;
import com.sg.superherosightings.dto.Address;
import com.sg.superherosightings.dto.Location;
import java.math.BigDecimal;
import java.util.ArrayList;
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
public class LocationController {

    @Autowired
    LocationDAO locationDao;

    @Autowired
    SightingDAO sightingDao;

    Set<ConstraintViolation<Location>> violations = new HashSet<>();

    @PostMapping("addLocation")
    public String addLocation(HttpServletRequest request, Model model) {
        Location location = new Location();
        
        String locName = request.getParameter("locName");
        location.setLocName(locName);
        
        String locDescription = request.getParameter("locDescription");
        location.setLocDescription(locDescription);
        
        String latitudeString = request.getParameter("latitude");
        if (validLatitude(latitudeString)) {
            BigDecimal latitude = new BigDecimal(latitudeString);
            location.setLatitude(latitude);
        }
        
        String longitudeString = request.getParameter("longitude");
        if (validLongitude(longitudeString)) {
            BigDecimal longitude = new BigDecimal(longitudeString);
            location.setLongitude(longitude);
        }
        
        Address address = new Address(request.getParameter("address"));
        address.setAddress1(request.getParameter("address.address1"));
        address.setAddress2(request.getParameter("address.address2"));
        address.setCity(request.getParameter("address.city"));
        address.setState(request.getParameter("address.state"));
        address.setZipCode(request.getParameter("address.zipCode"));
        address.setZipExtension(request.getParameter("address.zipExtension"));
        location.setAddress(address);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(location);
        model.addAttribute("errors", violations);
        
        if (violations.isEmpty()) {
            locationDao.addLocation(location);
            return "redirect:/locations";
        }
        
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("locations", locations);
        model.addAttribute("states", stateList());
        return "locations";
    }

    @GetMapping("location")
    public String locationDetail(Integer locId, Model model) {
        Location location = locationDao.getLocationById(locId);
        model.addAttribute("location", location);
        return "location";
    }

    @GetMapping("locations")
    public String displayLocations(Model model) {
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("locations", locations);
        model.addAttribute("states", stateList());
        violations = new HashSet<>();
        model.addAttribute("errors", violations);
        return "locations";
    }

    @GetMapping("editLocation")
    public String editLocation(Integer locId, Model model) {
        Location location = locationDao.getLocationById(locId);
        model.addAttribute("states", stateList());
        model.addAttribute("location", location);
        violations = new HashSet<>();
        model.addAttribute("errors", violations);
        return "editLocation";
    }

    @PostMapping("editLocation")
    public String performEditLocation(HttpServletRequest request, Model model) {
        Integer locId = Integer.parseInt(request.getParameter("locId"));
        Location location = locationDao.getLocationById(locId);
        
        String locName = request.getParameter("locName");
        location.setLocName(locName);
        
        String locDescription = request.getParameter("locDescription");
        location.setLocDescription(locDescription);
        
        String latitudeString = request.getParameter("latitude");
        if (validLatitude(latitudeString)) {
            BigDecimal latitude = new BigDecimal(latitudeString);
            location.setLatitude(latitude);
        } else {
            location.setLatitude(null);
        }
        
        String longitudeString = request.getParameter("longitude");
        if (validLongitude(longitudeString)) {
            BigDecimal longitude = new BigDecimal(longitudeString);
            location.setLongitude(longitude);
        } else {
            location.setLongitude(null);
        }
        
        Address address = location.getAddress();
        address.setAddress1(request.getParameter("address.address1"));
        address.setAddress2(request.getParameter("address.address2"));
        address.setCity(request.getParameter("address.city"));
        address.setState(request.getParameter("address.state"));
        address.setZipCode(request.getParameter("address.zipCode"));
        address.setZipExtension(request.getParameter("address.zipExtension"));
        location.setAddress(address);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(location);
        model.addAttribute("errors", violations);
        
        if (!violations.isEmpty()) {
            model.addAttribute("location", location);
            model.addAttribute("states", stateList());
            return "editLocation";
        }
        
        locationDao.updateLocation(location);
        return "redirect:/location?locId=" + location.getLocId();
    }

    @GetMapping("deleteLocation")
    public String deleteLocation(Integer locId, Model model) {
        Location location = locationDao.getLocationById(locId);
        model.addAttribute("location", location);
        return "deleteLocation";
    }

    @PostMapping("deleteLocation")
    public String performDeleteLocation(Location location, HttpServletRequest request) {
        locationDao.deleteLocation(location);
        return "redirect:/locations";
    }
    
    private boolean validLatitude(String latitudeString) {
        try {
            BigDecimal latitude = new BigDecimal(latitudeString);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
    
    private boolean validLongitude(String longitudeString) {
        try {
            BigDecimal longitude = new BigDecimal(longitudeString);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
    
    public class State {
        
        private String stateName;
        private String stateAbbrev;

        public State(String stateName, String stateAbbrev) {
            this.stateName = stateName;
            this.stateAbbrev = stateAbbrev;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public String getStateAbbrev() {
            return stateAbbrev;
        }

        public void setStateAbbrev(String stateAbbrev) {
            this.stateAbbrev = stateAbbrev;
        }
    }

    public List<State> stateList() {
        List<State> states = new ArrayList<>();

        State al = new State("Alabama", "AL");
        states.add(al);

        State ak = new State("Alaska", "AK");
        states.add(ak);

        State az = new State("Arizona", "AZ");
        states.add(az);

        State ar = new State("Arkansas", "AR");
        states.add(ar);

        State ca = new State("California", "CA");
        states.add(ca);

        State co = new State("Colorado", "CO");
        states.add(co);

        State ct = new State("Connecticut", "CT");
        states.add(ct);

        State de = new State("Delaware", "DE");
        states.add(de);

        State fl = new State("Florida", "FL");
        states.add(fl);

        State ga = new State("Georgia", "GA");
        states.add(ga);

        State hi = new State("Hawaii", "HI");
        states.add(hi);

        State id = new State("Idaho", "ID");
        states.add(id);

        State il = new State("Illinois", "IL");
        states.add(il);

        State in = new State("Indiana", "IN");
        states.add(in);

        State ia = new State("Iowa", "IA");
        states.add(ia);

        State ks = new State("Kansas", "KS");
        states.add(ks);

        State ky = new State("Kentucky", "KY");
        states.add(ky);

        State la = new State("Louisiana", "LA");
        states.add(la);

        State me = new State("Maine", "ME");
        states.add(me);

        State md = new State("Maryland", "MD");
        states.add(md);

        State ma = new State("Massachusetts", "MA");
        states.add(ma);

        State mi = new State("Michigan", "MI");
        states.add(mi);

        State mn = new State("Minnesota", "MN");
        states.add(mn);

        State ms = new State("Mississippi", "MS");
        states.add(ms);

        State mo = new State("Missouri", "MO");
        states.add(mo);

        State mt = new State("Montana", "MT");
        states.add(mt);

        State ne = new State("Nebraska", "NE");
        states.add(ne);

        State nv = new State("Nevada", "NV");
        states.add(nv);

        State nh = new State("New Hampshire", "NH");
        states.add(nh);

        State nj = new State("New Jersey", "NJ");
        states.add(nj);

        State nm = new State("New Mexico", "NM");
        states.add(nm);

        State ny = new State("New York", "NY");
        states.add(ny);

        State nc = new State("North Carolina", "NC");
        states.add(nc);

        State nd = new State("North Dakota", "ND");
        states.add(nd);

        State oh = new State("Ohio", "OH");
        states.add(oh);

        State ok = new State("Oklahoma", "OK");
        states.add(ok);

        State or = new State("Oregon", "OR");
        states.add(or);

        State pa = new State("Pennsylvania", "PA");
        states.add(pa);

        State ri = new State("Rhode Island", "RI");
        states.add(ri);

        State sc = new State("South Carolina", "SC");
        states.add(sc);

        State sd = new State("South Dakota", "SD");
        states.add(sd);

        State tn = new State("Tennessee", "TN");
        states.add(tn);

        State tx = new State("Texas", "TX");
        states.add(tx);

        State ut = new State("Utah", "UT");
        states.add(ut);

        State vt = new State("Vermont", "VT");
        states.add(vt);

        State va  = new State("Virginia", "VA");
        states.add(va);

        State wa = new State("Washington", "WA");
        states.add(wa);

        State wv = new State("West Virgina", "WV");
        states.add(wv);

        State wi = new State("Wisconsin", "WI");
        states.add(wi);

        State wy = new State("Wyoming", "WY");
        states.add(wy);

        return states;
    }
}
