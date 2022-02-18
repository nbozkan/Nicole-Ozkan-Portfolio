/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.dao.PowerDaoImpl.PowerMapper;
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
public class SuperDaoImpl implements SuperDAO
{
    @Autowired
    JdbcTemplate jdbc;
    
    final String INSERT_SUPER = "INSERT INTO super(superId, superName, superDescription, hero) VALUES(?,?,?,?)";
    final String GET_SUPER_BY_ID = "SELECT * FROM super WHERE superId = ?";
    final String GET_ALL_SUPERS = "SELECT * FROM super";
    final String UPDATE_SUPER = "UPDATE super SET superName = ?, superDescription = ?, hero = ? WHERE superId = ?";
    final String DELETE_SUPER_POWERS = "DELETE FROM super_power WHERE superId = ?";
    final String DELETE_SUPER_ORGANIZATION = "DELETE FROM super_organization WHERE superId = ?";
    final String DELETE_SUPER_SIGHTINGS = "DELETE FROM sighting WHERE superId = ?";
    final String DELETE_SUPER = "DELETE FROM super WHERE superId = ?";
    final String INSERT_SUPER_POWERS = "INSERT INTO super_power(superId, powerId) VALUES(?,?)";
    final String SELECT_POWERS_FOR_SUPER = "SELECT * FROM power p JOIN super_power sp ON sp.powerId = p.powerId WHERE sp.superId = ?";
    
    @Override
    @Transactional
    public Super addSuper(Super superPerson) 
    {
        jdbc.update(INSERT_SUPER, superPerson.getSuperId(), superPerson.getSuperName(), superPerson.getSuperDescription(), superPerson.isHero());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superPerson.setSuperId(newId);
        insertSuperPowers(superPerson);
        return superPerson;
    }
    
    @Override
    public Super getSuperById(int superId) 
    {
        try
        {
            Super superPerson = jdbc.queryForObject(GET_SUPER_BY_ID, new SuperMapper(), superId);
            superPerson.setPowers(getPowersForSuper(superId));
            return superPerson;
        } catch(DataAccessException ex)
        {
            return null;
        }
    }

    @Override
    public List<Super> getAllSupers() 
    {
        List<Super> supers = jdbc.query(GET_ALL_SUPERS, new SuperMapper());
        associatePowers(supers);
        return supers;
    }

    @Override
    @Transactional
    public void updateSuper(Super superPerson) 
    {
        jdbc.update(UPDATE_SUPER, superPerson.getSuperName(), superPerson.getSuperDescription(), superPerson.isHero(), superPerson.getSuperId());
        
        jdbc.update(DELETE_SUPER_POWERS, superPerson.getSuperId());
        
        insertSuperPowers(superPerson);
    }

    @Override
    @Transactional
    public void deleteSuperById(int superId) 
    {
        jdbc.update(DELETE_SUPER_POWERS, superId);
        
        jdbc.update(DELETE_SUPER_ORGANIZATION, superId);
        
        jdbc.update(DELETE_SUPER_SIGHTINGS, superId);
        
        jdbc.update(DELETE_SUPER, superId);
    }
    
        private void insertSuperPowers(Super superPerson)
    {
        for (Power power : superPerson.getPowers())
        {
            jdbc.update(INSERT_SUPER_POWERS, superPerson.getSuperId(), power.getPowerId());
        }
    }
    
    private List<Power> getPowersForSuper(int superId)
    {
        return jdbc.query(SELECT_POWERS_FOR_SUPER, new PowerMapper(), superId);
    }
    
    private void associatePowers(List<Super> supers)
    {
        for (Super superPerson : supers)
        {
            superPerson.setPowers(getPowersForSuper(superPerson.getSuperId()));
        }
    }
    
        public static final class SuperMapper implements RowMapper<Super>
    {
        @Override
        public Super mapRow(ResultSet rs, int rowNum) throws SQLException 
        {
            Super superPerson = new Super();
            superPerson.setSuperId(rs.getInt("superId"));
            superPerson.setSuperName(rs.getString("superName"));
            superPerson.setSuperDescription(rs.getString("superDescription"));
            superPerson.setHero(rs.getBoolean("hero"));
            return superPerson;
        }
        
    }
}