/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.dao.SuperDaoImpl.SuperMapper;
import com.sg.superherosightings.dto.Address;
import com.sg.superherosightings.dto.Contact;
import com.sg.superherosightings.dto.Organization;
import com.sg.superherosightings.dto.Power;
import com.sg.superherosightings.dto.Super;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nicoleozkan
 */
@Repository
public class OrganizationDaoImpl implements OrganizationDAO
{
    @Autowired
    JdbcTemplate jdbc;
    
    final String INSERT_CONTACT = "INSERT INTO contact(contactName, emailAddress, phoneNumber) VALUES(?,?,?)";
    final String INSERT_ADDRESS = "INSERT INTO address(address1, address2, city, state, zipCode, zipExtension) VALUES(?,?,?,?,?,?)";
    final String INSERT_ORGANIZATION = "INSERT INTO organization(orgName, orgDescription, addressId, contactId) VALUES(?,?,?,?)";
    final String SELECT_ORGANIZATION_BY_ID = "SELECT * FROM organization o JOIN contact c ON c.contactId = o.contactId JOIN address a ON a.addressId = o.addressId WHERE orgId = ?";
    final String SELECT_ALL_ORGANIZATIONS = "SELECT * FROM organization o JOIN contact c ON c.contactId = o.contactId JOIN address a ON a.addressId = o.addressId";
    final String SELECT_ORGANIZATIONS_FOR_SUPER = "SELECT * FROM organization o JOIN contact c ON c.contactId = o.contactId JOIN address a ON a.addressId = o.addressId JOIN super_organization so ON so.orgId = o.orgId JOIN super s ON so.superId = s.superId WHERE s.superId = ?";
    final String UPDATE_CONTACT = "UPDATE contact SET contactName = ?, emailAddress = ?, phoneNumber = ? WHERE contactId = ?";
    final String UPDATE_ADDRESS = "UPDATE address SET address1 = ?, address2 = ?, city = ?, state = ?, zipCode = ?, zipExtension = ? WHERE addressId = ?";
    final String UPDATE_ORGANIZATION = "UPDATE organization SET orgName = ?, orgDescription = ?, addressId = ?, contactId = ? WHERE orgId = ?";
    final String DELETE_SUPER_ORGANIZATION = "DELETE FROM super_organization WHERE orgId = ?";
    final String DELETE_ORGANIZATION = "DELETE FROM organization WHERE orgId = ?";
    final String DELETE_CONTACT = "DELETE FROM contact WHERE contactId = ?";
    final String DELETE_ADDRESS = "DELETE FROM address WHERE addressId = ?";
    final String SELECT_SUPERS_FOR_ORGANIZATION = "SELECT s.* FROM super s JOIN super_organization so ON so.superId = s.superId WHERE so.orgId = ?";
    final String SELECT_POWERS_FOR_SUPER = "SELECT p.* FROM power p JOIN super_power sp ON sp.powerId = p.powerId WHERE sp.superId = ?";
    final String INSERT_ORG_MEMBERS = "INSERT INTO super_organization(orgId, superId) VALUES(?,?)";
    
    @Override
    @Transactional
    public Organization addOrganization(Organization organization) 
    {
        jdbc.update(INSERT_CONTACT, organization.getContact().getContactName(), organization.getContact().getEmailAddress(), organization.getContact().getPhoneNumber());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organization.getContact().setContactId(newId);
        
        jdbc.update(INSERT_ADDRESS, organization.getAddress().getAddress1(), organization.getAddress().getAddress2(), organization.getAddress().getCity(), organization.getAddress().getState(), organization.getAddress().getZipCode(), organization.getAddress().getZipExtension());
        
        int newId2 = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organization.getAddress().setAddressId(newId2);
        
        jdbc.update(INSERT_ORGANIZATION, organization.getOrgName(), organization.getOrgDescription(), organization.getAddress().getAddressId(), organization.getContact().getContactId());
        
        int newId3 = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organization.setOrgId(newId3);
        insertOrgMembers(organization);
        return organization;
    }
    
    @Override
    public Organization getOrganizationById(int orgId) 
    {
        try
        {            
            Organization organization = jdbc.queryForObject(SELECT_ORGANIZATION_BY_ID, new OrganizationMapper(), orgId);
            organization.setSupers(getSupersForOrganization(orgId));
            return organization;
        } catch(DataAccessException ex)
        {
            return null;
        }
    }

    @Override
    public List<Organization> getAllOrganizations() 
    {
        List<Organization> organizations = jdbc.query(SELECT_ALL_ORGANIZATIONS, new OrganizationMapper());
        associateSupers(organizations);
        return organizations;
    }
    
    @Override
    public List<Organization> getOrganizationsForSuper(Super superPerson) 
    {
        List<Organization> organizations = jdbc.query(SELECT_ORGANIZATIONS_FOR_SUPER, new OrganizationMapper(), superPerson.getSuperId());
        associateSupers(organizations);
        return organizations;
    }

    @Override
    @Transactional
    public void updateOrganization(Organization organization) 
    {
        jdbc.update(UPDATE_CONTACT, organization.getContact().getContactName(), organization.getContact().getEmailAddress(), organization.getContact().getPhoneNumber(), organization.getContact().getContactId());
        
        jdbc.update(UPDATE_ADDRESS, organization.getAddress().getAddress1(), organization.getAddress().getAddress2(), organization.getAddress().getCity(), organization.getAddress().getState(), organization.getAddress().getZipCode(), organization.getAddress().getZipExtension(), organization.getAddress().getAddressId());
        
        jdbc.update(UPDATE_ORGANIZATION, organization.getOrgName(), organization.getOrgDescription(), organization.getAddress().getAddressId(), organization.getContact().getContactId(), organization.getOrgId());
        
        jdbc.update(DELETE_SUPER_ORGANIZATION, organization.getOrgId());
        
        insertOrgMembers(organization);
    }

    @Override
    @Transactional
    public void deleteOrganization(Organization organization) 
    {
        jdbc.update(DELETE_SUPER_ORGANIZATION, organization.getOrgId());
        
        jdbc.update(DELETE_ORGANIZATION, organization.getOrgId());
        
        jdbc.update(DELETE_CONTACT, organization.getContact().getContactId());
        
        jdbc.update(DELETE_ADDRESS, organization.getAddress().getAddressId());
    }
    
    private List<Super> getSupersForOrganization(int orgId)
    {
        List<Super> supers = jdbc.query(SELECT_SUPERS_FOR_ORGANIZATION, new SuperMapper(), orgId);
        associatePowers(supers);
        return supers;
    }
    
    private List<Power> getPowersForSuper(int superId)
    {
        return jdbc.query(SELECT_POWERS_FOR_SUPER, new PowerDaoImpl.PowerMapper(), superId);
    }
    
    private void associatePowers(List<Super> supers)
    {
        for (Super superPerson : supers)
        {
            superPerson.setPowers(getPowersForSuper(superPerson.getSuperId()));
        }
    }
    
        private void associateSupers(List<Organization> organizations)
    {
        for (Organization organization : organizations)
        {
            organization.setSupers(getSupersForOrganization(organization.getOrgId()));
        }
    }
    
    private void insertOrgMembers(Organization organization)
    {
        for (Super superPerson : organization.getSupers())
        {
            jdbc.update(INSERT_ORG_MEMBERS, organization.getOrgId(), superPerson.getSuperId());
        }
    }
    
        public static final class OrganizationMapper implements RowMapper<Organization>
    {
        @Override
        public Organization mapRow(ResultSet rs, int rowNum) throws SQLException 
        {
            Address address = new Address();
            address.setAddressId(rs.getInt("addressId"));
            address.setAddress1(rs.getString("address1"));
            address.setAddress2(rs.getString("address2"));
            address.setCity(rs.getString("city"));
            address.setState(rs.getString("state"));
            address.setZipCode(rs.getString("zipCode"));
            address.setZipExtension(rs.getString("zipExtension"));
            
            Contact contact = new Contact();
            contact.setContactId(rs.getInt("contactId"));
            contact.setContactName(rs.getString("contactName"));
            contact.setEmailAddress(rs.getString("emailAddress"));
            contact.setPhoneNumber(rs.getString("phoneNumber"));
            
            Organization organization = new Organization();
            organization.setOrgId(rs.getInt("orgId"));
            organization.setOrgName(rs.getString("orgName"));
            organization.setOrgDescription(rs.getString("orgDescription"));
            organization.setAddress(address);
            organization.setContact(contact);
            
            return organization;
        }
    }
}