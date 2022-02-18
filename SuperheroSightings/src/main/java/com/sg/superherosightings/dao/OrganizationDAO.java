/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.dto.Organization;
import com.sg.superherosightings.dto.Super;
import java.util.List;

/**
 *
 * @author nicoleozkan
 */
public interface OrganizationDAO 
{
    Organization getOrganizationById(int orgId);
    List<Organization> getAllOrganizations();
    Organization addOrganization(Organization organization);
    void updateOrganization(Organization organization);
    void deleteOrganization(Organization organization);
    
    List<Organization> getOrganizationsForSuper(Super superPerson);
}