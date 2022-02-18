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
public class SightingDaoImplTest 
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
    
    public SightingDaoImplTest() 
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
     * Test of getSightingById method, of class SightingDaoImpl.
     */
    @Test
    public void testAddAndGetSightingById() 
    {
        // Create power
        Power power = new Power();
        power.setPowerName("Test Power");
        power.setPowerDescription("Test Power Description");
        power = powerDao.addPower(power);
        List<Power> powers = powerDao.getAllPowers();
        
        // Create super
        Super superPerson = new Super();
        superPerson.setSuperName("Test Super Name");
        superPerson.setSuperDescription("Test Super Description");
        superPerson.setHero(true);
        superPerson.setPowers(powers);
        superPerson = superDao.addSuper(superPerson);
        
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
        
        // Create sighting
        Sighting sighting = new Sighting();
        sighting.setSuperPerson(superPerson);
        sighting.setLocation(location);
        sighting.setDate(LocalDate.now());
        sighting = sightingDao.addSighting(sighting);
        
        // Create duplicate
        Sighting fromDao = sightingDao.getSightingById(sighting.getSightingId());
        assertEquals(sighting, fromDao);
    }

    /**
     * Test of getAllSightings method, of class SightingDaoImpl.
     */
    @Test
    public void testGetAllSightings() 
    {
        // Create power
        Power power = new Power();
        power.setPowerName("Test Power");
        power.setPowerDescription("Test Power Description");
        power = powerDao.addPower(power);
        List<Power> powers = powerDao.getAllPowers();
        
        // Create super
        Super superPerson = new Super();
        superPerson.setSuperName("Test Super Name");
        superPerson.setSuperDescription("Test Super Description");
        superPerson.setHero(true);
        superPerson.setPowers(powers);
        superPerson = superDao.addSuper(superPerson);
        
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
        
        // Create sighting
        Sighting sighting = new Sighting();
        sighting.setSuperPerson(superPerson);
        sighting.setLocation(location);
        sighting.setDate(LocalDate.now());
        sighting = sightingDao.addSighting(sighting);
        
        //Create second super
        Super superPerson2 = new Super();
        superPerson2.setSuperName("Test Super Name 2");
        superPerson2.setSuperDescription("Test Super Description 2");
        superPerson2.setHero(false);
        superPerson2.setPowers(powers);
        superPerson2 = superDao.addSuper(superPerson2);
        
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
        
        // Create second sighting
        Sighting sighting2 = new Sighting();
        sighting2.setSuperPerson(superPerson2);
        sighting2.setLocation(location2);
        sighting2.setDate(LocalDate.now());
        sighting2 = sightingDao.addSighting(sighting2);
        
        // Create list of sightings
        List<Sighting> sightings = sightingDao.getAllSightings();
        
        // Confirm list has been poulated
        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertTrue(sightings.contains(sighting2));
    }

    /**
     * Test of updateSighting method, of class SightingDaoImpl.
     */
    @Test
    public void testUpdateSighting() 
    {
        // Create power
        Power power = new Power();
        power.setPowerName("Test Power");
        power.setPowerDescription("Test Power Description");
        power = powerDao.addPower(power);
        List<Power> powers = powerDao.getAllPowers();
        
        // Create super
        Super superPerson = new Super();
        superPerson.setSuperName("Test Super Name");
        superPerson.setSuperDescription("Test Super Description");
        superPerson.setHero(true);
        superPerson.setPowers(powers);
        superPerson = superDao.addSuper(superPerson);
        
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
        
        // Create sighting
        Sighting sighting = new Sighting();
        sighting.setSuperPerson(superPerson);
        sighting.setLocation(location);
        sighting.setDate(LocalDate.now());
        sighting = sightingDao.addSighting(sighting);
        
        // Create duplicate
        Sighting fromDao = sightingDao.getSightingById(sighting.getSightingId());
        assertEquals(sighting, fromDao);
        
        //Create second super
        Super superPerson2 = new Super();
        superPerson2.setSuperName("Test Super Name 2");
        superPerson2.setSuperDescription("Test Super Description 2");
        superPerson2.setHero(false);
        superPerson2.setPowers(powers);
        superPerson2 = superDao.addSuper(superPerson2);
        
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
        
        //Update sighting
        sighting.setSuperPerson(superPerson2);
        sighting.setLocation(location2);
        sighting.setDate(LocalDate.of(2022, 1, 1));
        sightingDao.updateSighting(sighting);
        
        // Confirm sighting and fromDao are not same
        assertNotEquals(sighting, fromDao);
        
        // Make sighting and fromDao same
        fromDao = sightingDao.getSightingById(sighting.getSightingId());
        assertEquals(sighting, fromDao);
        assertTrue(sighting.getDate().equals(LocalDate.of(2022, 1, 1)));
        assertTrue(sighting.getSuperPerson().equals(superPerson2));
        assertTrue(sighting.getLocation().equals(location2));
        
    }

    /**
     * Test of deleteSightingById method, of class SightingDaoImpl.
     */
    @Test
    public void testDeleteSightingById() 
    {
        // Create power
        Power power = new Power();
        power.setPowerName("Test Power");
        power.setPowerDescription("Test Power Description");
        power = powerDao.addPower(power);
        List<Power> powers = powerDao.getAllPowers();
        
        // Create super
        Super superPerson = new Super();
        superPerson.setSuperName("Test Super Name");
        superPerson.setSuperDescription("Test Super Description");
        superPerson.setHero(true);
        superPerson.setPowers(powers);
        superPerson = superDao.addSuper(superPerson);
        
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
        
        // Create sighting
        Sighting sighting = new Sighting();
        sighting.setSuperPerson(superPerson);
        sighting.setLocation(location);
        sighting.setDate(LocalDate.now());
        sighting = sightingDao.addSighting(sighting);
        
        // Create duplicate
        Sighting fromDao = sightingDao.getSightingById(sighting.getSightingId());
        assertEquals(sighting, fromDao);
        
        // Delete sighting
        sightingDao.deleteSightingById(sighting.getSightingId());
        
        // Confirm deletion of sighting
        fromDao = sightingDao.getSightingById(sighting.getSightingId());
        assertNull(fromDao);
    }

    /**
     * Test of getSightingsForSuper method, of class SightingDaoImpl.
     */
    @Test
    public void testGetSightingsForSuper() 
    {
        // Create power
        Power power = new Power();
        power.setPowerName("Test Power");
        power.setPowerDescription("Test Power Description");
        power = powerDao.addPower(power);
        List<Power> powers = powerDao.getAllPowers();
        
        // Create super
        Super superPerson = new Super();
        superPerson.setSuperName("Test Super Name");
        superPerson.setSuperDescription("Test Super Description");
        superPerson.setHero(true);
        superPerson.setPowers(powers);
        superPerson = superDao.addSuper(superPerson);
        
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
        
        // Create sighting
        Sighting sighting = new Sighting();
        sighting.setSuperPerson(superPerson);
        sighting.setLocation(location);
        sighting.setDate(LocalDate.now());
        sighting = sightingDao.addSighting(sighting);
        
        //Create second super
        Super superPerson2 = new Super();
        superPerson2.setSuperName("Test Super Name 2");
        superPerson2.setSuperDescription("Test Super Description 2");
        superPerson2.setHero(false);
        superPerson2.setPowers(powers);
        superPerson2 = superDao.addSuper(superPerson2);
        
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
        
        // Create second sighting
        Sighting sighting2 = new Sighting();
        sighting2.setSuperPerson(superPerson2);
        sighting2.setLocation(location2);
        sighting2.setDate(LocalDate.now());
        sighting2 = sightingDao.addSighting(sighting2);
        
        // Create list of sighting by super
        List<Sighting> sightingsForSuper = sightingDao.getSightingsForSuper(superPerson);
        List<Sighting> sightingsForSuper2 = sightingDao.getSightingsForSuper(superPerson2);
        
        // Confirm list was populated
        assertEquals(1, sightingsForSuper.size());
        assertTrue(sightingsForSuper.contains(sighting));
        assertEquals(1, sightingsForSuper2.size());
        assertTrue(sightingsForSuper2.contains(sighting2));
    }

    /**
     * Test of getSightingsForLocation method, of class SightingDaoImpl.
     */
    @Test
    public void testGetSightingsForLocation() 
    {
        // Create power
        Power power = new Power();
        power.setPowerName("Test Power");
        power.setPowerDescription("Test Power Description");
        power = powerDao.addPower(power);
        List<Power> powers = powerDao.getAllPowers();
        
        // Create super
        Super superPerson = new Super();
        superPerson.setSuperName("Test Super Name");
        superPerson.setSuperDescription("Test Super Description");
        superPerson.setHero(true);
        superPerson.setPowers(powers);
        superPerson = superDao.addSuper(superPerson);
        
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
        
        // Create sighting
        Sighting sighting = new Sighting();
        sighting.setSuperPerson(superPerson);
        sighting.setLocation(location);
        sighting.setDate(LocalDate.now());
        sighting = sightingDao.addSighting(sighting);
        
        //Create second super
        Super superPerson2 = new Super();
        superPerson2.setSuperName("Test Super Name 2");
        superPerson2.setSuperDescription("Test Super Description 2");
        superPerson2.setHero(false);
        superPerson2.setPowers(powers);
        superPerson2 = superDao.addSuper(superPerson2);
        
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
        
        // Create second sighting
        Sighting sighting2 = new Sighting();
        sighting2.setSuperPerson(superPerson2);
        sighting2.setLocation(location2);
        sighting2.setDate(LocalDate.now());
        sighting2 = sightingDao.addSighting(sighting2);
        
        // Create list by location
        List<Sighting> sightingsByLocation = sightingDao.getSightingsForLocation(location);
        List<Sighting> sightingsByLocation2 = sightingDao.getSightingsForLocation(location2);
        
        // Confirm list was populated
        assertEquals(1, sightingsByLocation.size());
        assertTrue(sightingsByLocation.contains(sighting));
        assertEquals(1, sightingsByLocation2.size());
        assertTrue(sightingsByLocation2.contains(sighting2));
    }

    /**
     * Test of getSightingsForDate method, of class SightingDaoImpl.
     */
    @Test
    public void testGetSightingsForDate() 
    {
        // Create power
        Power power = new Power();
        power.setPowerName("Test Power 1");
        power.setPowerDescription("Test Power 1 Description");
        power = powerDao.addPower(power);
        List<Power> powers = powerDao.getAllPowers();
        
        // Create super
        Super superPerson = new Super();
        superPerson.setSuperName("Test Super Name");
        superPerson.setSuperDescription("Test Super Description");
        superPerson.setHero(true);
        superPerson.setPowers(powers);
        superPerson = superDao.addSuper(superPerson);
        
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
        
        // Create sighting
        Sighting sighting = new Sighting();
        sighting.setSuperPerson(superPerson);
        sighting.setLocation(location);
        sighting.setDate(LocalDate.now());
        sighting = sightingDao.addSighting(sighting);
        
        //Create second super
        Super superPerson2 = new Super();
        superPerson2.setSuperName("Test Super Name 2");
        superPerson2.setSuperDescription("Test Super Description 2");
        superPerson2.setHero(false);
        superPerson2.setPowers(powers);
        superPerson2 = superDao.addSuper(superPerson2);
        
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
        
        // Create second sighting
        Sighting sighting2 = new Sighting();
        sighting2.setSuperPerson(superPerson2);
        sighting2.setLocation(location2);
        sighting2.setDate(LocalDate.of(2022, 1, 1));
        sighting2 = sightingDao.addSighting(sighting2);
        
        // Create list by date
        List<Sighting> sightingsByDate = sightingDao.getSightingsForDate(sighting.getDate());
        List<Sighting> sightingsByDate2 = sightingDao.getSightingsForDate(sighting2.getDate());
        
        // Confirm list was populated
        assertEquals(1, sightingsByDate.size());
        assertTrue(sightingsByDate.contains(sighting));
        assertEquals(1, sightingsByDate2.size());
        assertTrue(sightingsByDate2.contains(sighting2));
    }
    
    /**
     * Test of getLatestSightings method, of class SightingDaoImpl.
     */
    @Test
    public void testGetLatestSightings()
    {
        // Create power
        Power power = new Power();
        power.setPowerName("Test Power 1");
        power.setPowerDescription("Test Power 1 Description");
        power = powerDao.addPower(power);
        List<Power> powers = powerDao.getAllPowers();
        
        // Create super
        Super superPerson = new Super();
        superPerson.setSuperName("Test Super 1 Name");
        superPerson.setSuperDescription("Test Super 1 Description");
        superPerson.setHero(true);
        superPerson.setPowers(powers);
        superPerson = superDao.addSuper(superPerson);
        
        // Create address
        Address address = new Address();
        address.setAddress1("Test Location 1 Address1");
        address.setAddress2("Test Location 1 Address2");
        address.setCity("Test Location City");
        address.setState("NY");
        address.setZipCode("00000");
        
        // Create location
        Location location = new Location();
        location.setLocName("Test Location 1 Name");
        location.setLocDescription("Test Location 1 Description");        
        BigDecimal latitude = new BigDecimal("40.712800");
        location.setLatitude(latitude);
        BigDecimal longitude = new BigDecimal("74.006000");
        location.setLongitude(longitude);
        location.setAddress(address);
        location = locationDao.addLocation(location);
        
        // Create sighting
        Sighting sighting = new Sighting();
        sighting.setSuperPerson(superPerson);
        sighting.setLocation(location);
        sighting.setDate(LocalDate.now());
        sighting = sightingDao.addSighting(sighting);
        
        //Create second super
        Super superPerson2 = new Super();
        superPerson2.setSuperName("Test Super 2 Name");
        superPerson2.setSuperDescription("Test Super 2 Description");
        superPerson2.setHero(false);
        superPerson2.setPowers(powers);
        superPerson2 = superDao.addSuper(superPerson2);
        
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
        
        // Create second sighting
        Sighting sighting2 = new Sighting();
        sighting2.setSuperPerson(superPerson2);
        sighting2.setLocation(location2);
        sighting2.setDate(LocalDate.of(2022, 1, 1));
        sighting2 = sightingDao.addSighting(sighting2);
        
        // Create second power
        Power power2 = new Power();
        power2.setPowerName("Test Power 2 Name");
        power2.setPowerDescription("Test Power 2 Description");
        power2 = powerDao.addPower(power2);
        powers = powerDao.getAllPowers();
        
        // Create third super
        Super superPerson3 = new Super();
        superPerson3.setSuperName("Test Super 3 Name");
        superPerson3.setSuperDescription("Test Super 3 Description");
        superPerson3.setHero(true);
        superPerson3.setPowers(powers);
        superPerson3 = superDao.addSuper(superPerson3);
        
        // Create third address
        Address address3 = new Address();
        address3.setAddress1("Test Location 3 Address1");
        address3.setAddress2("Test Location 3 Address2");
        address3.setCity("Test Location 3 City");
        address3.setState("MA");
        address3.setZipCode("00000");
        
        // Create third location
        Location location3 = new Location();
        location3.setLocName("Test Location 3 Name");
        location3.setLocDescription("Test Location 3 Description");        
        BigDecimal latitude3 = new BigDecimal("50.712800");
        location3.setLatitude(latitude3);
        BigDecimal longitude3 = new BigDecimal("84.006000");
        location3.setLongitude(longitude3);
        location3.setAddress(address3);
        location3 = locationDao.addLocation(location3);
        
        // Create third sighting
        Sighting sighting3 = new Sighting();
        sighting3.setSuperPerson(superPerson);
        sighting3.setLocation(location);
        sighting3.setDate(LocalDate.of(2021, 12, 25));
        sighting3 = sightingDao.addSighting(sighting3);
        
        //Create fourth super
        Super superPerson4 = new Super();
        superPerson4.setSuperName("Test Super 4 Name");
        superPerson4.setSuperDescription("Test Super 4 Description");
        superPerson4.setHero(false);
        superPerson4.setPowers(powers);
        superPerson4 = superDao.addSuper(superPerson4);
        
        // Create fourth address
        Address address4 = new Address();
        address4.setAddress1("Test Location 4 Address1");
        address4.setAddress2("Test Location 4 Address2");
        address4.setCity("Test Location 4 City");
        address4.setState("WA");
        address4.setZipCode("12345");
        address4.setZipExtension("6789");
        
        // Create fourth location
        Location location4 = new Location();
        location4.setLocName("Test Location 4 Name");
        location4.setLocDescription("Test Location 4 Description");        
        BigDecimal latitude4 = new BigDecimal("38.052200");
        location4.setLatitude(latitude4);
        BigDecimal longitude4 = new BigDecimal("120.243700");
        location4.setLongitude(longitude4);
        location4.setAddress(address4);
        location4 = locationDao.addLocation(location4);
        
        // Create fourth sighting
        Sighting sighting4 = new Sighting();
        sighting4.setSuperPerson(superPerson4);
        sighting4.setLocation(location4);
        sighting4.setDate(LocalDate.of(2022, 1, 2));
        sighting4 = sightingDao.addSighting(sighting4);
        
        // Create third power
        Power power3 = new Power();
        power3.setPowerName("Test Power 3 Name");
        power3.setPowerDescription("Test Power 3 Description");
        power3 = powerDao.addPower(power3);
        powers = powerDao.getAllPowers();
        
        // Create fifth super
        Super superPerson5 = new Super();
        superPerson5.setSuperName("Test Super 5 Name");
        superPerson5.setSuperDescription("Test Super 5 Description");
        superPerson5.setHero(true);
        superPerson5.setPowers(powers);
        superPerson5 = superDao.addSuper(superPerson5);
        
        // Create fifth address
        Address address5 = new Address();
        address5.setAddress1("Test Location 5 Address1");
        address5.setAddress2("Test Location 5 Address2");
        address5.setCity("Test Location 5 City");
        address5.setState("PA");
        address5.setZipCode("11111");
        
        // Create fifth location
        Location location5 = new Location();
        location5.setLocName("Test Location 5 Name");
        location5.setLocDescription("Test Location 5 Description");        
        BigDecimal latitude5 = new BigDecimal("50.712800");
        location5.setLatitude(latitude5);
        BigDecimal longitude5 = new BigDecimal("89.006000");
        location5.setLongitude(longitude5);
        location5.setAddress(address5);
        location5 = locationDao.addLocation(location5);
        
        // Create fifth sighting
        Sighting sighting5 = new Sighting();
        sighting5.setSuperPerson(superPerson5);
        sighting5.setLocation(location5);
        sighting5.setDate(LocalDate.of(2022, 2, 2));
        sighting5 = sightingDao.addSighting(sighting5);
        
        //Create sixth super
        Super superPerson6 = new Super();
        superPerson6.setSuperName("Test Super 6 Name");
        superPerson6.setSuperDescription("Test Super 6 Description");
        superPerson6.setHero(false);
        superPerson6.setPowers(powers);
        superPerson6 = superDao.addSuper(superPerson6);
        
        // Create sixth address
        Address address6 = new Address();
        address6.setAddress1("Test Location 6 Address1");
        address6.setAddress2("Test Location 6 Address2");
        address6.setCity("Test Location 6 City");
        address6.setState("ME");
        address6.setZipCode("22222");
        address6.setZipExtension("1234");
        
        // Create sixth location
        Location location6 = new Location();
        location6.setLocName("Test Location 6 Name");
        location6.setLocDescription("Test Location 6 Description");        
        BigDecimal latitude6 = new BigDecimal("59.052200");
        location6.setLatitude(latitude6);
        BigDecimal longitude6 = new BigDecimal("25.243700");
        location6.setLongitude(longitude6);
        location6.setAddress(address6);
        location6 = locationDao.addLocation(location6);
        
        // Create sixth sighting
        Sighting sighting6 = new Sighting();
        sighting6.setSuperPerson(superPerson6);
        sighting6.setLocation(location6);
        sighting6.setDate(LocalDate.of(2022, 1, 10));
        sighting6 = sightingDao.addSighting(sighting6);
        
        // Create fourth power
        Power power4 = new Power();
        power4.setPowerName("Test Power 4");
        power4.setPowerDescription("Test Power 4 Description");
        power4 = powerDao.addPower(power4);
        powers = powerDao.getAllPowers();
        
        // Create seventh super
        Super superPerson7 = new Super();
        superPerson7.setSuperName("Test Super 7 Name");
        superPerson7.setSuperDescription("Test Super 7 Description");
        superPerson7.setHero(true);
        superPerson7.setPowers(powers);
        superPerson7 = superDao.addSuper(superPerson7);
        
        // Create seventh address
        Address address7 = new Address();
        address7.setAddress1("Test Location 7 Address1");
        address7.setAddress2("Test Location 7 Address2");
        address7.setCity("Test Location 7 City");
        address7.setState("OR");
        address7.setZipCode("33333");
        
        // Create seventh location
        Location location7 = new Location();
        location7.setLocName("Test Location 7 Name");
        location7.setLocDescription("Test Location 7 Description");        
        BigDecimal latitude7 = new BigDecimal("60.712800");
        location7.setLatitude(latitude7);
        BigDecimal longitude7 = new BigDecimal("84.006000");
        location7.setLongitude(longitude7);
        location7.setAddress(address7);
        location7 = locationDao.addLocation(location7);
        
        // Create seventh sighting
        Sighting sighting7 = new Sighting();
        sighting7.setSuperPerson(superPerson7);
        sighting7.setLocation(location7);
        sighting7.setDate(LocalDate.of(2020, 1, 1));
        sighting7 = sightingDao.addSighting(sighting7);
        
        //Create eigth super
        Super superPerson8 = new Super();
        superPerson8.setSuperName("Test Super 8 Name");
        superPerson8.setSuperDescription("Test Super 8 Description");
        superPerson8.setHero(false);
        superPerson8.setPowers(powers);
        superPerson8 = superDao.addSuper(superPerson8);
        
        // Create eigth address
        Address address8 = new Address();
        address8.setAddress1("Test Location 8 Address1");
        address8.setAddress2("Test Location 8 Address2");
        address8.setCity("Test Location 8 City");
        address8.setState("IL");
        address8.setZipCode("55555");
        address8.setZipExtension("4444");
        
        // Create eigth location
        Location location8 = new Location();
        location8.setLocName("Test Location 8 Name");
        location8.setLocDescription("Test Location 8 Description");        
        BigDecimal latitude8 = new BigDecimal("74.052200");
        location8.setLatitude(latitude8);
        BigDecimal longitude8 = new BigDecimal("170.243700");
        location8.setLongitude(longitude8);
        location8.setAddress(address8);
        location8 = locationDao.addLocation(location8);
        
        // Create eigth sighting
        Sighting sighting8 = new Sighting();
        sighting8.setSuperPerson(superPerson8);
        sighting8.setLocation(location8);
        sighting8.setDate(LocalDate.of(2019, 1, 1));
        sighting8 = sightingDao.addSighting(sighting8);
        
        // Create fifth power
        Power power5 = new Power();
        power5.setPowerName("Test Power 5 Name");
        power5.setPowerDescription("Test Power 5 Description");
        power5 = powerDao.addPower(power5);
        powers = powerDao.getAllPowers();
        
        // Create ninth super
        Super superPerson9 = new Super();
        superPerson9.setSuperName("Test Super 9 Name");
        superPerson9.setSuperDescription("Test Super 9 Description");
        superPerson9.setHero(true);
        superPerson9.setPowers(powers);
        superPerson9 = superDao.addSuper(superPerson9);
        
        // Create ninth address
        Address address9 = new Address();
        address9.setAddress1("Test Location 9 Address1");
        address9.setAddress2("Test Location 9 Address2");
        address9.setCity("Test Location 9 City");
        address9.setState("VT");
        address9.setZipCode("66666");
        
        // Create ninth location
        Location location9 = new Location();
        location9.setLocName("Test Location 9 Name");
        location9.setLocDescription("Test Location 9 Description");        
        BigDecimal latitude9 = new BigDecimal("70.712800");
        location9.setLatitude(latitude9);
        BigDecimal longitude9 = new BigDecimal("150.006000");
        location9.setLongitude(longitude9);
        location9.setAddress(address9);
        location9 = locationDao.addLocation(location9);
        
        // Create ninth sighting
        Sighting sighting9 = new Sighting();
        sighting9.setSuperPerson(superPerson9);
        sighting9.setLocation(location9);
        sighting9.setDate(LocalDate.of(2022, 1, 31));
        sighting9 = sightingDao.addSighting(sighting9);
        
        //Create tenth super
        Super superPerson10 = new Super();
        superPerson10.setSuperName("Test Super 10 Name");
        superPerson10.setSuperDescription("Test Super 10 Description");
        superPerson10.setHero(false);
        superPerson10.setPowers(powers);
        superPerson10 = superDao.addSuper(superPerson10);
        
        // Create tenth address
        Address address10 = new Address();
        address10.setAddress1("Test Location 10 Address1");
        address10.setAddress2("Test Location 10 Address2");
        address10.setCity("Test Location 10 City");
        address10.setState("TX");
        address10.setZipCode("77777");
        address10.setZipExtension("8888");
        
        // Create tenth location
        Location location10 = new Location();
        location10.setLocName("Test Location 10 Name");
        location10.setLocDescription("Test Location 10 Description");        
        BigDecimal latitude10 = new BigDecimal("56.052200");
        location10.setLatitude(latitude10);
        BigDecimal longitude10 = new BigDecimal("90.243700");
        location10.setLongitude(longitude10);
        location10.setAddress(address10);
        location10 = locationDao.addLocation(location10);
        
        // Create tenth sighting
        Sighting sighting10 = new Sighting();
        sighting10.setSuperPerson(superPerson10);
        sighting10.setLocation(location10);
        sighting10.setDate(LocalDate.of(2022, 1, 19));
        sighting10 = sightingDao.addSighting(sighting10);
        
        //Create eleventh super
        Super superPerson11 = new Super();
        superPerson11.setSuperName("Test Super 11 Name");
        superPerson11.setSuperDescription("Test Super 11 Description");
        superPerson11.setHero(false);
        superPerson11.setPowers(powers);
        superPerson11 = superDao.addSuper(superPerson11);
        
        // Create eleventh address
        Address address11 = new Address();
        address11.setAddress1("Test Location 11 Address1");
        address11.setAddress2("Test Location 11 Address2");
        address11.setCity("Test Location 11 City");
        address11.setState("GA");
        address11.setZipCode("99999");
        address11.setZipExtension("0000");
        
        // Create eleventh location
        Location location11 = new Location();
        location11.setLocName("Test Location 11 Name");
        location11.setLocDescription("Test Location 11 Description");        
        BigDecimal latitude11 = new BigDecimal("89.052200");
        location11.setLatitude(latitude11);
        BigDecimal longitude11 = new BigDecimal("179.243700");
        location11.setLongitude(longitude11);
        location11.setAddress(address11);
        location11 = locationDao.addLocation(location11);
        
        // Create eleventh sighting
        Sighting sighting11 = new Sighting();
        sighting11.setSuperPerson(superPerson11);
        sighting11.setLocation(location11);
        sighting11.setDate(LocalDate.of(2022, 1, 8));
        sighting11 = sightingDao.addSighting(sighting11);
        
        // Create latest sighting list
        List<Sighting> latestSightings = sightingDao.getLatestSightings();
        
        // Confirm list was populated with correct size and sightings
        assertEquals(10, latestSightings.size());
        assertTrue(latestSightings.contains(sighting));
        assertTrue(latestSightings.contains(sighting2));
        assertTrue(latestSightings.contains(sighting3));
        assertTrue(latestSightings.contains(sighting4));
        assertTrue(latestSightings.contains(sighting5));
        assertTrue(latestSightings.contains(sighting6));
        assertTrue(latestSightings.contains(sighting7));
        assertFalse(latestSightings.contains(sighting8));
        assertTrue(latestSightings.contains(sighting9));
        assertTrue(latestSightings.contains(sighting10));
        assertTrue(latestSightings.contains(sighting11));
    }
}