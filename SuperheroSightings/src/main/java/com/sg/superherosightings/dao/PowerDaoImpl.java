/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.dto.Power;
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
public class PowerDaoImpl implements PowerDAO
{
    @Autowired
    JdbcTemplate jdbc;
    
    final String INSERT_POWER = "INSERT INTO power(powerName, powerDescription) VALUES(?,?)";
    final String SELECT_POWER_BY_ID = "SELECT * FROM power WHERE powerId = ?";
    final String SELECT_ALL_POWERS = "SELECT * FROM power";
    final String UPDATE_POWER = "UPDATE power SET powerName = ?, powerDescription = ? WHERE powerId = ?";
    final String DELETE_SUPER_POWER = "DELETE FROM super_power WHERE powerId = ?";
    final String DELETE_POWER = "DELETE FROM power WHERE powerId = ?";
    
    @Override
    @Transactional
    public Power addPower(Power power) 
    {
        jdbc.update(INSERT_POWER, power.getPowerName(), power.getPowerDescription());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        power.setPowerId(newId);
        return power;
    }
    
    @Override
    public Power getPowerById(int powerId) 
    {
        try
        {
            return jdbc.queryForObject(SELECT_POWER_BY_ID, new PowerMapper(), powerId);
        } catch(DataAccessException ex)
        {
            return null;
        }
    }
    
    @Override
    public List<Power> getAllPowers() 
    {
        return jdbc.query(SELECT_ALL_POWERS, new PowerMapper());
    }

    @Override
    @Transactional
    public void updatePower(Power power) 
    {
        jdbc.update(UPDATE_POWER, power.getPowerName(), power.getPowerDescription(), power.getPowerId());
    }

    @Override
    @Transactional
    public void deletePowerById(int powerId) 
    {
        jdbc.update(DELETE_SUPER_POWER, powerId);
        
        jdbc.update(DELETE_POWER, powerId);
    }
    
    public static final class PowerMapper implements RowMapper<Power>
    {
        @Override
        public Power mapRow(ResultSet rs, int rowNum) throws SQLException 
        {
            Power power = new Power();
            power.setPowerId(rs.getInt("powerId"));
            power.setPowerName(rs.getString("powerName"));
            power.setPowerDescription(rs.getString("powerDescription"));
            return power;
        }
    }
}