/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.dto.Address;
import com.sg.superherosightings.dto.Location;
import com.sg.superherosightings.dto.Organization;
import com.sg.superherosightings.dto.Power;
import com.sg.superherosightings.dto.Sighting;
import com.sg.superherosightings.dto.Super;
import java.math.BigDecimal;
import java.time.LocalDate;
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
public class LocationDaoImplTest 
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
    
    public LocationDaoImplTest() 
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
     * Test of getLocationById method, of class LocationDaoImpl.
     */
    @Test
    public void testAddAndGetLocationById() 
    {
        // Create address
        Address address = new Address();
        address.setAddress1("Test Location Address1");
        address.setAddress2("Test Location Address2");
        address.setCity("Test Location City");
        address.setState("NY");
        address.setZipCode("00000");
        
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
        
        // Duplicate location
        Location fromDao = locationDao.getLocationById(location.getLocId());
        assertEquals(location, fromDao);
    }

    /**
     * Test of getAllLocations method, of class LocationDaoImpl.
     */
    @Test
    public void testGetAllLocations() 
    {
        // Create first address
        Address address = new Address();
        address.setAddress1("Test Location Address1");
        address.setAddress2("Test Location Address2");
        address.setCity("Test Location City");
        address.setState("NY");
        address.setZipCode("00000");
        
        // Create first location
        Location location = new Location();
        location.setLocName("Test Location");
        location.setLocDescription("Test Location Description");        
        BigDecimal latitude = new BigDecimal("40.712800");
        location.setLatitude(latitude);
        BigDecimal longitude = new BigDecimal("74.006000");
        location.setLongitude(longitude);
        location.setAddress(address);
        location = locationDao.addLocation(location);
        
        // Create second address
        Address address2 = new Address();
        address2.setAddress1("Test Location 2 Address1");
        address2.setAddress2("Test Location 2 Address2");
        address2.setCity("Test Location 2 City");
        address2.setState("CA");
        address2.setZipCode("00000");
        address2.setZipExtension("0000");
        
        // Create second location
        Location location2 = new Location();
        location2.setLocName("Test Location 2 Name");
        location2.setLocDescription("Test Location 2 Description");        
        BigDecimal latitude2 = new BigDecimal("34.052200");
        location2.setLatitude(latitude2);
        BigDecimal longitude2 = new BigDecimal("118.243700");
        location2.setLongitude(longitude2);
        location2.setAddress(address2);
        location2 = locationDao.addLocation(location2);
        
        // Create list of locations
        List<Location> locations = locationDao.getAllLocations();
        
        // Confirm list was populated
        assertEquals(2, locations.size());
        assertTrue(locations.contains(location));
        assertTrue(locations.contains(location2));
    }

    /**
     * Test of updateAddressAndLocation method, of class LocationDaoImpl.
     */
    @Test
    public void testUpdateLocation() 
    {
        // Create address
        Address address = new Address();
        address.setAddress1("Test Location Address1");
        address.setAddress2("Test Location Address2");
        address.setCity("Test Location City");
        address.setState("NY");
        address.setZipCode("00000");
        
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
        
        // Create duplicate
        Location fromDao = locationDao.getLocationById(location.getLocId());
        assertEquals(location, fromDao);
        
        // Change latitude and longitude
        BigDecimal newLatitude = new BigDecimal("34.052200");
        BigDecimal newLongitude = new BigDecimal("118.243700");
        
        // Change address
        address.setAddress1("New Test Address Address1");
        address.setAddress2("New Test Address Address2");
        address.setCity("New Test Address City");
        address.setState("CA");
        address.setZipCode("00000");
        address.setZipExtension("0000");
        
        // Update location
        location.setLocName("New Test Location Name");
        location.setLocDescription("New Test Location Description");
        location.setLatitude(newLatitude);
        location.setLongitude(newLongitude);
        location.setAddress(address);
        locationDao.updateLocation(location);
        
        // Confirm location and fromDao are not same
        assertNotEquals(location, fromDao);
        
        // Make location and fromDao same
        fromDao = locationDao.getLocationById(location.getLocId());
        assertEquals(location, fromDao);
        assertTrue(location.getLocName().equals("New Test Location Name"));
        assertTrue(location.getLocDescription().equals("New Test Location Description"));
        assertTrue(location.getLatitude().equals(newLatitude));
        assertTrue(location.getLongitude().equals(newLongitude));
        assertTrue(location.getAddress().equals(address));
    }

    /**
     * Test of deleteAddressAndLocation method, of class LocationDaoImpl.
     */
    @Test
    public void testDeleteLocation() 
    {
        // Create address
        Address address = new Address();
        address.setAddress1("Test Location Address1");
        address.setAddress2("Test Location Address2");
        address.setCity("Test Location City");
        address.setState("NY");
        address.setZipCode("00000");
        
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
        
        // Create sighting
        Sighting sighting = new Sighting();
        sighting.setSuperPerson(superPerson);
        sighting.setLocation(location);
        sighting.setDate(LocalDate.now());
        sighting = sightingDao.addSighting(sighting);
        
        // Create duplicate location
        Location fromDao = locationDao.getLocationById(location.getLocId());
        assertEquals(location, fromDao);
        
        // Delete location
        locationDao.deleteLocation(location);
        
        // Confirm location has been deleted
        fromDao = locationDao.getLocationById(location.getLocId());
        assertNull(fromDao);
        
        // Update sighting
        sighting.setLocation(fromDao);
        assertNull(sighting.getLocation());
    } 
}