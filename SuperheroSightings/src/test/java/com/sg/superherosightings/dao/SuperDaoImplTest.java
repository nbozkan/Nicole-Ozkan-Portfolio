/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.dto.Address;
import com.sg.superherosightings.dto.Contact;
import com.sg.superherosightings.dto.Location;
import com.sg.superherosightings.dto.Organization;
import com.sg.superherosightings.dto.Power;
import com.sg.superherosightings.dto.Sighting;
import com.sg.superherosightings.dto.Super;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author nicoleozkan
 */
@SpringBootTest
public class SuperDaoImplTest 
{
    @Autowired
    PowerDAO powerDao;
    
    @Autowired
    SuperDAO superDao;
    
    @Autowired
    OrganizationDAO organizationDao;
    
    @Autowired
    LocationDAO locationDao;
    
    @Autowired
    SightingDAO sightingDao;
    
    public SuperDaoImplTest() 
    {
    }
    
    @BeforeAll
    public static void setUpClass() 
    {
    }
    
    @AfterAll
    public static void tearDownClass() 
    {
    }
    
    @BeforeEach
    public void setUp() 
    {
        List<Power> powers = powerDao.getAllPowers();
        for(Power power : powers)
        {
           powerDao.deletePowerById(power.getPowerId());
        }
        
        List<Super> supers = superDao.getAllSupers();
        for(Super superPerson : supers)
        {
            superDao.deleteSuperById(superPerson.getSuperId());
        }
        
        List<Organization> organizations = organizationDao.getAllOrganizations();
        for(Organization organization : organizations)
        {
            organizationDao.deleteOrganization(organization);
        }
        
        List<Location> locations = locationDao.getAllLocations();
        for(Location location : locations)
        {
            locationDao.deleteLocation(location);
        }
        
        List<Sighting> sightings = sightingDao.getAllSightings();
        for(Sighting sighting : sightings)
        {
            sightingDao.deleteSightingById(sighting.getSightingId());
        }
    }
    
    @AfterEach
    public void tearDown() 
    {
    }

    /**
     * Test of getSuperById method, of class SuperDaoImpl.
     */
    @Test
    public void testAddAndGetSuperById() 
    {
        // Create power
        Power power = new Power();
        power.setPowerName("Test Power");
        power.setPowerDescription("Test Power Description");
        power = powerDao.addPower(power);
        
        // Create list of powers
        List<Power> powers = powerDao.getAllPowers();
        
        // Create super
        Super superPerson = new Super();
        superPerson.setSuperName("Test Super Name");
        superPerson.setSuperDescription("Test Super Description");
        superPerson.setHero(true);
        superPerson.setPowers(powers);
        superPerson = superDao.addSuper(superPerson);
        
        // Create duplicate super
        Super fromDao = superDao.getSuperById(superPerson.getSuperId());
        assertEquals(superPerson, fromDao);
    }

    /**
     * Test of getAllSupers method, of class SuperDaoImpl.
     */
    @Test
    public void testGetAllSupers() 
    {
        // Create power
        Power power = new Power();
        power.setPowerName("Test Power");
        power.setPowerDescription("Test Power Description");
        power = powerDao.addPower(power);
        
        // Create list of powers
        List<Power> powers = powerDao.getAllPowers();
        
        // Create super
        Super superPerson = new Super();
        superPerson.setSuperName("Test Super Name");
        superPerson.setSuperDescription("Test Super Description");
        superPerson.setHero(true);
        superPerson.setPowers(powers);
        superPerson = superDao.addSuper(superPerson);
        
        //Create second super
        Super superPerson2 = new Super();
        superPerson2.setSuperName("Test Super Name 2");
        superPerson2.setSuperDescription("Test Super Description 2");
        superPerson2.setHero(false);
        superPerson2.setPowers(powers);
        superPerson2 = superDao.addSuper(superPerson2);
        
        // Add supers to list
        List<Super> supers = superDao.getAllSupers();
        
        // Confirm list was populated
        assertEquals(2, supers.size());
        assertTrue(supers.contains(superPerson));
        assertTrue(supers.contains(superPerson2));
    }

    /**
     * Test of updateSuper method, of class SuperDaoImpl.
     */
    @Test
    public void testUpdateSuper() 
    {
        // Create power 1
        Power power = new Power();
        power.setPowerName("Test Power");
        power.setPowerDescription("Test Power Description");
        power = powerDao.addPower(power);
        
        // Create power 2
        Power power2 = new Power();
        power2.setPowerName("Test Power Name 2");
        power2.setPowerDescription("Test Power Description 2");
        power2 = powerDao.addPower(power2);
        
        // Create list of powers
        List<Power> singlePower = new ArrayList<>();
        singlePower.add(power);
        
        // Create super
        Super superPerson = new Super();
        superPerson.setSuperName("Test Super Name");
        superPerson.setSuperDescription("Test Super Description");
        superPerson.setHero(true);
        superPerson.setPowers(singlePower);
        superPerson = superDao.addSuper(superPerson);
        
        // Create duplicate
        Super fromDao = superDao.getSuperById(superPerson.getSuperId());
        assertEquals(superPerson, fromDao);
        
        // Update superPerson
        superPerson.setSuperName("New Test Super Name");
        superPerson.setSuperDescription("New Test Super Description");
        superPerson.setHero(false);
        singlePower.add(power2);
        singlePower.remove(power);
        superPerson.setPowers(singlePower);
        superDao.updateSuper(superPerson);
        
        // Confirm superPerson and fromDao are not same
        assertNotEquals(superPerson, fromDao);
        
        // Make superPerson and fromDao same
        fromDao = superDao.getSuperById(superPerson.getSuperId());
        assertEquals(superPerson, fromDao);
        assertEquals(1, superPerson.getPowers().size());
        assertTrue(superPerson.getPowers().contains(power2));
        assertFalse(superPerson.getPowers().contains(power));
    }

    /**
     * Test of deleteSuperById method, of class SuperDaoImpl.
     */
    @Test
    public void testDeleteSuperById() 
    {
        // Create power
        Power power = new Power();
        power.setPowerName("Test Power");
        power.setPowerDescription("Test Power Description");
        power = powerDao.addPower(power);
        
        // Create list of powers
        List<Power> powers = powerDao.getAllPowers();
        
        // Create super
        Super superPerson = new Super();
        superPerson.setSuperName("Test Super Name");
        superPerson.setSuperDescription("Test Super Description");
        superPerson.setHero(true);
        superPerson.setPowers(powers);
        superPerson = superDao.addSuper(superPerson);
        
        // Create list of supers for organization
        List<Super> supers = superDao.getAllSupers();
        
        // Create contact for organization
        Contact contact = new Contact();
        contact.setContactName("Test Contact Name");
        contact.setEmailAddress("Test Email Address");
        contact.setPhoneNumber("0000000000");
        
        // Create address for organization
        Address address = new Address();
        address.setAddress1("Test Address Address1");
        address.setAddress2("Test Address Address2");
        address.setCity("Gotham");
        address.setState("NY");
        address.setZipCode("00000");
        
        // Create organization
        Organization organization = new Organization();
        organization.setOrgName("Test Organization Name");
        organization.setOrgDescription("Test Organization Description");
        organization.setAddress(address);
        organization.setContact(contact);
        organization.setSupers(supers);
        organization = organizationDao.addOrganization(organization);
        
        // Create location
        Location location = new Location();
        location.setLocName("Test Location");
        location.setLocDescription("Test Location Description");        
        BigDecimal latitude = new BigDecimal("40.712800");
        location.setLatitude(latitude);
        BigDecimal longitude = new BigDecimal("74.006000");
        location.setLongitude(longitude);
        location.setAddress(address);
        location = locationDao.addLocation(location);
        
        // Create sighting
        Sighting sighting = new Sighting();
        sighting.setLocation(location);
        sighting.setSuperPerson(superPerson);
        sighting.setDate(LocalDate.now());
        sighting = sightingDao.addSighting(sighting);
        
        // Create duplicate
        Super fromDao = superDao.getSuperById(superPerson.getSuperId());
        assertEquals(superPerson, fromDao);
        
        // Delete superPerson
        superDao.deleteSuperById(superPerson.getSuperId());
        
        // Update orgMembers list
        supers = superDao.getAllSupers();
        organization.setSupers(supers);
        
        // Confirm superPerson has been deleted
        fromDao = superDao.getSuperById(superPerson.getSuperId());
        assertNull(fromDao);
        assertEquals(0, organization.getSupers().size());
        
        // Update sighting
        sighting.setSuperPerson(fromDao);
        assertNull(sighting.getSuperPerson());
    }
}