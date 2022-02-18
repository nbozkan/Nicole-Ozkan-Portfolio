/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controller;

import com.sg.superherosightings.dao.OrganizationDAO;
import com.sg.superherosightings.dao.SuperDAO;
import com.sg.superherosightings.dto.Organization;
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
public class OrganizationController {

    @Autowired
    OrganizationDAO organizationDao;

    @Autowired
    SuperDAO superDao;

    Set<ConstraintViolation<Organization>> violations = new HashSet<>();

    @PostMapping("addOrganization")
    public String addOrganization(Organization organization, HttpServletRequest request, Model model) {
        String[] superIds = request.getParameterValues("superId");
        List<Super> supers = new ArrayList<>();
        if(superIds != null)
        {
            for (String superId : superIds) {
                supers.add(superDao.getSuperById(Integer.parseInt(superId)));
            }
        }
        organization.setSupers(supers);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(organization);
        model.addAttribute("errors", violations);
        
        if (violations.isEmpty()) {
            organizationDao.addOrganization(organization);
            return "redirect:/organizations";
        }
        
        List<Organization> organizations = organizationDao.getAllOrganizations();
        model.addAttribute("organizations", organizations);
        
        supers = superDao.getAllSupers();
        model.addAttribute("supers", supers);
        
        model.addAttribute("states", stateList());
        
        return "organizations";
    }

    @GetMapping("organization")
    public String displayOrganization(Integer orgId, Model model) {
        Organization organization = organizationDao.getOrganizationById(orgId);
        model.addAttribute("organization", organization);
        return "organization";
    }

    @GetMapping("organizations")
    public String displayOrganizations(Model model) {
        List<Organization> organizations = organizationDao.getAllOrganizations();
        model.addAttribute("organizations", organizations);
        List<Super> supers = superDao.getAllSupers();
        model.addAttribute("supers", supers);
        model.addAttribute("states", stateList());
        violations = new HashSet<>();
        model.addAttribute("errors", violations);

        return "organizations";
    }

    @GetMapping("editOrganization")
    public String editOrganization(Integer orgId, Model model) {
        Organization organization = organizationDao.getOrganizationById(orgId);
        model.addAttribute("organization", organization);
        
        model.addAttribute("states", stateList());
        
        List<Super> supers = superDao.getAllSupers();
        model.addAttribute("supers", supers);
        
        violations = new HashSet<>();
        model.addAttribute("errors", violations);

        return "editOrganization";
    }

    @PostMapping("editOrganization")
    public String performEditOrganization(Organization organization, BindingResult result, HttpServletRequest request, Model model) {
        String[] superIds = request.getParameterValues("superId");
        List<Super> supers = new ArrayList<>();
        if (superIds != null) {
            for (String superId : superIds) {
                supers.add(superDao.getSuperById(Integer.parseInt(superId)));
            }
        }
        organization.setSupers(supers);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(organization);
        model.addAttribute("errors", violations);
        
        if (!violations.isEmpty()) {
            model.addAttribute("organization", organization);
            model.addAttribute("supers", superDao.getAllSupers());
            model.addAttribute("states", stateList());
            model.addAttribute("organization", organization);
            return "editOrganization";
        }

        organizationDao.updateOrganization(organization);
        return "organization";
    }

    @GetMapping("deleteOrganization")
    public String deleteOrganization(Integer orgId, Model model) {
        Organization organization = organizationDao.getOrganizationById(orgId);
        model.addAttribute("organization", organization);
        return "deleteOrganization";
    }

    @PostMapping("deleteOrganization")
    public String performDeleteOrganization(Organization organization, HttpServletRequest request) {
        organizationDao.deleteOrganization(organization);
        return "redirect:/organizations";
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
