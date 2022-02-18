/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.dto.Location;
import com.sg.superherosightings.dto.Organization;
import com.sg.superherosightings.dto.Power;
import com.sg.superherosightings.dto.Sighting;
import com.sg.superherosightings.dto.Super;
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
public class PowerDaoImplTest 
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
    
    public PowerDaoImplTest() 
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
     * Test of getPowerById method, of class PowerDaoImpl.
     */
    @Test
    public void testAddAndGetPowerById() 
    {
        // Create power
        Power power = new Power();
        power.setPowerName("Test Power");
        power.setPowerDescription("Test Power Description");
        power = powerDao.addPower(power);
        
        // Duplicate power
        Power fromDao = powerDao.getPowerById(power.getPowerId());
        assertEquals(power, fromDao);
    }

    /**
     * Test of updatePower method, of class PowerDaoImpl.
     */
    @Test
    public void testUpdatePower() 
    {
        // Create power
        Power power = new Power();
        power.setPowerName("Test Power");
        power.setPowerDescription("Test Power Description");
        power = powerDao.addPower(power);
        
        // Create duplicate
        Power fromDao = powerDao.getPowerById(power.getPowerId());
        assertEquals(power, fromDao);
        
        // Update power
        power.setPowerName("New Power Name");
        powerDao.updatePower(power);
        
        // Confirm power and fromDao are not same
        assertNotEquals(power, fromDao);
        
        // Make power and fromDao same
        fromDao = powerDao.getPowerById(power.getPowerId());
        assertEquals(power, fromDao);
    }

    /**
     * Test of deletePowerById method, of class PowerDaoImpl.
     */
    @Test
    public void testDeletePowerById() 
    {
        // Create power 1
        Power power = new Power();
        power.setPowerName("Test Power Name");
        power.setPowerDescription("Test Power Description");
        power = powerDao.addPower(power);
        
        // Create power 2
        Power power2 = new Power();
        power2.setPowerName("Test Power Name 2");
        power2.setPowerDescription("Test Power Description 2");
        power2 = powerDao.addPower(power2);
        
        // Create list
        List<Power> powers = powerDao.getAllPowers();
        
        // Create supers 1
        Super superPerson = new Super();
        superPerson.setSuperName("Test Super Name");
        superPerson.setSuperDescription("Test Super Description");
        superPerson.setHero(true);
        superPerson.setPowers(powers);
        superPerson = superDao.addSuper(superPerson);
        
        // Create duplicate power
        Power fromDao = powerDao.getPowerById(power.getPowerId());
        assertEquals(power, fromDao);
        
        // Delete power
        powerDao.deletePowerById(power.getPowerId());
        
        // Update super
        superPerson = superDao.getSuperById(superPerson.getSuperId());
        
        // Confirm power has been deleted
        fromDao = powerDao.getPowerById(power.getPowerId());
        assertNull(fromDao);
        assertTrue(superPerson.getPowers().contains(power2));
        assertFalse(superPerson.getPowers().contains(power));
    }

    /**
     * Test of getAllPowers method, of class PowerDaoImpl.
     */
    @Test
    public void testGetAllPowers() 
    {
        // Create first power
        Power power = new Power();
        power.setPowerName("Test Power");
        power.setPowerDescription("Test Power Description");
        power = powerDao.addPower(power);
        
        // Create second power
        Power power2 = new Power();
        power2.setPowerName("Test Power Name 2");
        power2.setPowerDescription("Test Power Description 2");
        power2 = powerDao.addPower(power2);
        
        // Create list
        List<Power> powers = powerDao.getAllPowers();
        
        // Confirm list was populated
        assertEquals(2, powers.size());
        assertTrue(powers.contains(power));
        assertTrue(powers.contains(power2));
    }
}