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
public class OrganizationDaoImplTest 
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
    
    public OrganizationDaoImplTest() 
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
     * Test of getOrganizationById method, of class OrganizationDaoImpl.
     */
    @Test
    public void testAddAndGetOrganizationById() 
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
        
        // Create list of supers
        List<Super> supers = superDao.getAllSupers();
        
        // Create address for organization
        Address address = new Address();
        address.setAddress1("Test Address Address1");
        address.setAddress2("Test Address Address2");
        address.setCity("Gotham");
        address.setState("NY");
        address.setZipCode("00000");
        
        // Create contact for organization
        Contact contact = new Contact();
        contact.setContactName("Test Contact Name");
        contact.setEmailAddress("Test Email Address");
        contact.setPhoneNumber("0000000000");
        
        // Create organization
        Organization organization = new Organization();
        organization.setOrgName("Test Organization Name");
        organization.setOrgDescription("Test Organization Description");
        organization.setAddress(address);
        organization.setContact(contact);
        organization.setSupers(supers);
        organization = organizationDao.addOrganization(organization);
        
        // Create duplicate organization
        Organization fromDao = organizationDao.getOrganizationById(organization.getOrgId());
        assertEquals(organization, fromDao);
    }

    /**
     * Test of getAllOrganizations method, of class OrganizationDaoImpl.
     */
    @Test
    public void testGetAllOrganizations() 
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
        
        // Create list of supers
        List<Super> supers = superDao.getAllSupers();
        
        // Create address for organization
        Address address = new Address();
        address.setAddress1("Test Address Address1");
        address.setAddress2("Test Address Address2");
        address.setCity("Gotham");
        address.setState("NY");
        address.setZipCode("00000");
        
        // Create contact for organization
        Contact contact = new Contact();
        contact.setContactName("Test Contact Name");
        contact.setEmailAddress("Test Email Address");
        contact.setPhoneNumber("0000000000");
        
        // Create organization
        Organization organization = new Organization();
        organization.setOrgName("Test Organization Name");
        organization.setOrgDescription("Test Organization Description");
        organization.setAddress(address);
        organization.setContact(contact);
        organization.setSupers(supers);
        organization = organizationDao.addOrganization(organization);
        
        // Create address for second organization
        Address address2 = new Address();
        address2.setAddress1("Test Address 2 Address1");
        address2.setAddress2("Test Address 2 Address2");
        address2.setCity("Test Address 2 City");
        address2.setState("CA");
        address2.setZipCode("00000");
        address2.setZipExtension("0000");
        
        // Create contact for second organization
        Contact contact2 = new Contact();
        contact2.setContactName("Test Contact Name 2");
        contact2.setEmailAddress("Test Contact Email Address 2");
        contact2.setPhoneNumber("0000000000");
        
        // Create second organization
        Organization organization2 = new Organization();
        organization2.setOrgName("Test Organization 2 Name");
        organization2.setOrgDescription("Test Organization 2 Description");
        organization2.setAddress(address2);
        organization2.setContact(contact2);
        organization2.setSupers(supers);
        organization2 = organizationDao.addOrganization(organization2);
        
        // Create list of organizations
        List<Organization> organizations = organizationDao.getAllOrganizations();
        
        // Confirm list was populated
        assertEquals(2, organizations.size());
        assertTrue(organizations.contains(organization));
        assertTrue(organizations.contains(organization2));
    }

    /**
     * Test of updateContactAddressAndOrganization method, of class OrganizationDaoImpl.
     */
    @Test
    public void testUpdateOrganization() 
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
        
        // Create list of supers
        List<Super> supers = superDao.getAllSupers();
        
        // Create address for organization
        Address address = new Address();
        address.setAddress1("Test Address Address1");
        address.setAddress2("Test Address Address2");
        address.setCity("Gotham");
        address.setState("NY");
        address.setZipCode("00000");
        
        // Create contact for organization
        Contact contact = new Contact();
        contact.setContactName("Test Contact Name");
        contact.setEmailAddress("Test Email Address");
        contact.setPhoneNumber("0000000000");
        
        // Create organization
        Organization organization = new Organization();
        organization.setOrgName("Test Organization Name");
        organization.setOrgDescription("Test Organization Description");
        organization.setAddress(address);
        organization.setContact(contact);
        organization.setSupers(supers);
        organization = organizationDao.addOrganization(organization);
        
        // Create duplicate
        Organization fromDao = organizationDao.getOrganizationById(organization.getOrgId());
        assertEquals(organization, fromDao);
        
        // Create new super
        Super superPerson2 = new Super();
        superPerson2.setSuperName("Test Super Name 2");
        superPerson2.setSuperDescription("Test Super Description 2");
        superPerson2.setHero(false);
        superPerson2.setPowers(powers);
        superPerson2 = superDao.addSuper(superPerson2);
        
        // Update supers list
        supers = superDao.getAllSupers();
        supers.remove(superPerson);
        
        // Change address
        address.setAddress1("New Test Address Address1");
        address.setAddress2("New Test Address Address2");
        address.setCity("New Test Address City");
        address.setState("CA");
        address.setZipCode("00000");
        address.setZipExtension("0000");
        
        // Change contact
        contact.setContactName("New Test Contact Name");
        contact.setEmailAddress("New Test Contact Email Address");
        contact.setPhoneNumber("0000000000");
        
        // Update organization
        organization.setOrgName("New Test Organization Name");
        organization.setOrgDescription("New Test Organization Description");
        organization.setAddress(address);
        organization.setContact(contact);
        organization.setSupers(supers);
        organizationDao.updateOrganization(organization);
        
        // Confirm organization and fromDao are not same
        assertNotEquals(organization, fromDao);
        
        // Make organization and fromDao same
        fromDao = organizationDao.getOrganizationById(organization.getOrgId());
        assertEquals(organization, fromDao);
        assertTrue(organization.getOrgName().equals("New Test Organization Name"));
        assertTrue(organization.getOrgDescription().equals("New Test Organization Description"));
        assertTrue(organization.getAddress().equals(address));
        assertTrue(organization.getContact().equals(contact));
        assertEquals(1, organization.getSupers().size());
        assertTrue(organization.getSupers().contains(superPerson2));
    }

    /**
     * Test of deleteContactAddressAndOrganization method, of class OrganizationDaoImpl.
     */
    @Test
    public void testDeleteOrganization() 
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
        
        // Create list of supers
        List<Super> supers = superDao.getAllSupers();
        
        // Create address for organization
        Address address = new Address();
        address.setAddress1("Test Address Address1");
        address.setAddress2("Test Address Address2");
        address.setCity("Gotham");
        address.setState("NY");
        address.setZipCode("00000");
        
        // Create contact for organization
        Contact contact = new Contact();
        contact.setContactName("Test Contact Name");
        contact.setEmailAddress("Test Email Address");
        contact.setPhoneNumber("0000000000");
        
        // Create organization
        Organization organization = new Organization();
        organization.setOrgName("Test Organization Name");
        organization.setOrgDescription("Test Organization Description");
        organization.setAddress(address);
        organization.setContact(contact);
        organization.setSupers(supers);
        organization = organizationDao.addOrganization(organization);
        
        // Create duplicate
        Organization fromDao = organizationDao.getOrganizationById(organization.getOrgId());
        assertEquals(organization, fromDao);
        
        // Delete organization
        organizationDao.deleteOrganization(organization);
        
        // Confirm organization deletion
        fromDao = organizationDao.getOrganizationById(organization.getOrgId());
        assertNull(fromDao);
    }

    /**
     * Test of getOrganizationsForSuper method, of class OrganizationDaoImpl.
     */
    @Test
    public void testGetOrganizationsForSuper() 
    {
        // Create power
        Power power = new Power();
        power.setPowerName("Test Power");
        power.setPowerDescription("Test Power Description");
        power = powerDao.addPower(power);
        List<Power> powers = new ArrayList<>();
        powers.add(power);
        
        // Create super
        Super superPerson = new Super();
        superPerson.setSuperName("Test Super Name");
        superPerson.setSuperDescription("Test Super Description");
        superPerson.setHero(true);
        superPerson.setPowers(powers);
        superPerson = superDao.addSuper(superPerson);
        List<Super> supers = superDao.getAllSupers();
        
        // Create address for organization
        Address address = new Address();
        address.setAddress1("Test Address Address1");
        address.setAddress2("Test Address Address2");
        address.setCity("Gotham");
        address.setState("NY");
        address.setZipCode("00000");
        
        // Create contact for organization
        Contact contact = new Contact();
        contact.setContactName("Test Contact Name");
        contact.setEmailAddress("Test Email Address");
        contact.setPhoneNumber("0000000000");
        
        // Create organization
        Organization organization = new Organization();
        organization.setOrgName("Test Organization Name");
        organization.setOrgDescription("Test Organization Description");
        organization.setAddress(address);
        organization.setContact(contact);
        organization.setSupers(supers);
        organization = organizationDao.addOrganization(organization);
        
        // Create list of organizations by super
        List<Organization> organizationsBySuper = organizationDao.getOrganizationsForSuper(superPerson);
        
        // Create address for second organization
        Address address2 = new Address();
        address2.setAddress1("Test Address 2 Address1");
        address2.setAddress2("Test Address 2 Address2");
        address2.setCity("Test Address 2 City");
        address2.setState("CA");
        address2.setZipCode("00000");
        address2.setZipExtension("0000");
                
        // Create contact for second organization
        Contact contact2 = new Contact();
        contact2.setContactName("Test Contact Name 2");
        contact2.setEmailAddress("Test Contact Email Address 2");
        contact2.setPhoneNumber("0000000000");
        
        // Create second organization
        Organization organization2 = new Organization();
        organization2.setOrgName("Test Organization 2 Name");
        organization2.setOrgDescription("Test Organization 2 Description");
        organization2.setAddress(address2);
        organization2.setContact(contact2);
        organization2.setSupers(supers);
        organization2 = organizationDao.addOrganization(organization2);
        
        // Confirm list was populated
        assertEquals(1, organizationsBySuper.size());
        assertTrue(organizationsBySuper.contains(organization));
        assertFalse(organizationsBySuper.contains(organization2));
    }
}