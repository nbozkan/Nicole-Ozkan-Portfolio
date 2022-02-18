/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.dao.LocationDaoImpl.LocationMapper;
import com.sg.superherosightings.dao.PowerDaoImpl.PowerMapper;
import com.sg.superherosightings.dao.SuperDaoImpl.SuperMapper;
import com.sg.superherosightings.dto.Location;
import com.sg.superherosightings.dto.Power;
import com.sg.superherosightings.dto.Sighting;
import com.sg.superherosightings.dto.Super;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
public class SightingDaoImpl implements SightingDAO
{
    @Autowired
    JdbcTemplate jdbc;
    
    final String INSERT_SIGHTING = "INSERT INTO sighting(locId, superId, date) VALUES(?,?,?)";
    final String SELECT_SIGHTING_BY_ID = "SELECT * FROM sighting WHERE sightingId = ?";
    final String SELECT_ALL_SIGHTINGS = "SELECT * FROM sighting";
    final String SELECT_SIGHTINGS_FOR_SUPER = "SELECT * FROM sighting WHERE superId = ?";
    final String SELECT_SIGHTINGS_FOR_LOCATION = "SELECT * FROM sighting WHERE locId = ?";
    final String SELECT_SIGHTINGS_FOR_DATE = "SELECT * FROM sighting WHERE date = ?";
    final String UPDATE_SIGHTING = "UPDATE sighting SET locId = ?, superId = ?, date = ? WHERE sightingId = ?";
    final String DELETE_SIGHTING = "DELETE FROM sighting WHERE sightingId = ?";
    final String SELECT_LOCATION_FOR_SIGHTING = "SELECT * FROM location l JOIN address a ON a.addressId = l.addressId JOIN sighting s ON s.locId = l.locId WHERE s.sightingId = ?";
    final String SELECT_SUPER_FOR_SIGHTING = "SELECT * FROM super su JOIN sighting si ON si.superId = su.superId WHERE si.sightingId = ?";
    final String SELECT_POWERS_FOR_SUPER = "SELECT * FROM power p JOIN super_power sp ON sp.powerId = p.powerId WHERE sp.superId = ?";
    final String SELECT_LATEST_SIGHTINGS = "SELECT * FROM sighting ORDER BY date DESC LIMIT 10";
    
    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) 
    {
        jdbc.update(INSERT_SIGHTING, sighting.getLocation().getLocId(), sighting.getSuperPerson().getSuperId(), sighting.getDate());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setSightingId(newId);
        return sighting;
    }
    
    @Override
    public Sighting getSightingById(int sightingId) 
    {
        try
        {
            Sighting sighting = jdbc.queryForObject(SELECT_SIGHTING_BY_ID, new SightingMapper(), sightingId);
            sighting.setLocation(getLocationForSighting(sightingId));
            sighting.setSuperPerson(getSuperForSighting(sightingId));
            return sighting;
        } catch(DataAccessException ex)
        {
            return null;
        }
    }

    @Override
    public List<Sighting> getAllSightings() 
    {
        List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper());
        associateLocationAndSuper(sightings);
        return sightings;
    }
    
    @Override
    public List<Sighting> getSightingsForSuper(Super superPerson) 
    {
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_SUPER, new SightingMapper(), superPerson.getSuperId());
        associateLocationAndSuper(sightings);
        return sightings;
    }

    @Override
    public List<Sighting> getSightingsForLocation(Location location) 
    {
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_LOCATION, new SightingMapper(), location.getLocId());
        associateLocationAndSuper(sightings);
        return sightings;
    }
    
    @Override
    public List<Sighting> getSightingsForDate(LocalDate sightingDate) 
    {
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_DATE, new SightingMapper(), sightingDate);
        associateLocationAndSuper(sightings);
        return sightings;
    }

    @Override
    public void updateSighting(Sighting sighting) 
    {
        jdbc.update(UPDATE_SIGHTING, sighting.getLocation().getLocId(), sighting.getSuperPerson().getSuperId(), sighting.getDate(), sighting.getSightingId());
    }

    @Override
    @Transactional
    public void deleteSightingById(int sightingId) 
    {
        jdbc.update(DELETE_SIGHTING, sightingId);
    }
    
    private Location getLocationForSighting(int sightingId)
    {
        return jdbc.queryForObject(SELECT_LOCATION_FOR_SIGHTING, new LocationMapper(), sightingId);
    }
    
    private Super getSuperForSighting(int sightingId)
    {
        Super superPerson = jdbc.queryForObject(SELECT_SUPER_FOR_SIGHTING, new SuperMapper(), sightingId);
        superPerson.setPowers(getPowersForSuper(superPerson.getSuperId()));
        return superPerson;
    }
    
    private List<Power> getPowersForSuper(int superId)
    {
        return jdbc.query(SELECT_POWERS_FOR_SUPER, new PowerMapper(), superId);
    }
    
    private void associateLocationAndSuper(List<Sighting> sightings)
    {
        for (Sighting sighting : sightings)
        {
            sighting.setLocation(getLocationForSighting(sighting.getSightingId()));
            sighting.setSuperPerson(getSuperForSighting(sighting.getSightingId()));
        }
    }

    @Override
    public List<Sighting> getLatestSightings() {
        List<Sighting> sightings = jdbc.query(SELECT_LATEST_SIGHTINGS, new SightingMapper());
        associateLocationAndSuper(sightings);
        return sightings;
    }
    
        public static final class SightingMapper implements RowMapper<Sighting>
    {
        @Override
        public Sighting mapRow(ResultSet rs, int rowNum) throws SQLException 
        {
            Sighting sighting = new Sighting();
            sighting.setSightingId(rs.getInt("sightingId"));
            sighting.setDate(rs.getDate("date").toLocalDate());
            return sighting;
        }
    }
}