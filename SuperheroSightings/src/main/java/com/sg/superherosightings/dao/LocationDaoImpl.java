/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.dto.Address;
import com.sg.superherosightings.dto.Location;
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
public class LocationDaoImpl implements LocationDAO
{
    @Autowired
    JdbcTemplate jdbc;
    
    final String INSERT_ADDRESS = "INSERT INTO address(address1, address2, city, state, zipCode, zipExtension) VALUES(?,?,?,?,?,?)";
    final String INSERT_LOCATION = "INSERT INTO location(locName, locDescription, latitude, longitude, addressId) VALUES(?,?,?,?,?)";
    final String SELECT_LOCATION_BY_ID = "SELECT * FROM location l JOIN address a ON a.addressId = l.addressId WHERE locId = ?";
    final String SELECT_ALL_LOCATIONS = "SELECT * FROM location l JOIN address a ON a.addressId = l.addressId";
    final String UPDATE_ADDRESS = "UPDATE address SET address1 = ?, address2 = ?, city = ?, state = ?, zipCode = ?, zipExtension = ? WHERE addressId = ?";
    final String UPDATE_LOCATION = "UPDATE location SET locName = ?, locDescription = ?, latitude = ?, longitude = ?, addressId = ? WHERE locId = ?";
    final String DELETE_SIGHTING_LOCATION = "DELETE FROM sighting WHERE locId = ?";
    final String DELETE_LOCATION = "DELETE FROM location WHERE locId = ?";
    final String DELETE_ADDRESS = "DELETE FROM address WHERE addressId = ?";

    @Override
    @Transactional
    public Location addLocation(Location location) 
    {
        jdbc.update(INSERT_ADDRESS, location.getAddress().getAddress1(), location.getAddress().getAddress2(), location.getAddress().getCity(), location.getAddress().getState(), location.getAddress().getZipCode(), location.getAddress().getZipExtension());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.getAddress().setAddressId(newId);
        
        jdbc.update(INSERT_LOCATION, location.getLocName(), location.getLocDescription(), location.getLatitude(), location.getLongitude(), location.getAddress().getAddressId());
        
        int newId2 = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setLocId(newId2);
        return location;
    }
    
    @Override
    public Location getLocationById(int locId) 
    {
        try
        {
            return jdbc.queryForObject(SELECT_LOCATION_BY_ID, new LocationMapper(), locId);
        } catch(DataAccessException ex)
        {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() 
    {
        return jdbc.query(SELECT_ALL_LOCATIONS, new LocationMapper());
    }

    @Override
    @Transactional
    public void updateLocation(Location location) 
    {
        jdbc.update(UPDATE_ADDRESS, location.getAddress().getAddress1(), location.getAddress().getAddress2(), location.getAddress().getCity(), location.getAddress().getState(), location.getAddress().getZipCode(), location.getAddress().getZipExtension(), location.getAddress().getAddressId());
        
        jdbc.update(UPDATE_LOCATION, location.getLocName(), location.getLocDescription(), location.getLatitude(), location.getLongitude(), location.getAddress().getAddressId(), location.getLocId());
    }

    @Override
    @Transactional
    public void deleteLocation(Location location) 
    {     
        jdbc.update(DELETE_SIGHTING_LOCATION, location.getLocId());
        
        jdbc.update(DELETE_LOCATION, location.getLocId());
        
        jdbc.update(DELETE_ADDRESS, location.getAddress().getAddressId());
    }
    
        public static final class LocationMapper implements RowMapper<Location>
    {
        @Override
        public Location mapRow(ResultSet rs, int rowNum) throws SQLException 
        {
            Address address = new Address();
            address.setAddressId(rs.getInt("addressId"));
            address.setAddress1(rs.getString("address1"));
            address.setAddress2(rs.getString("address2"));
            address.setCity(rs.getString("city"));
            address.setState(rs.getString("state"));
            address.setZipCode(rs.getString("zipCode"));
            address.setZipExtension(rs.getString("zipExtension"));
            
            Location location = new Location();
            location.setLocId(rs.getInt("locId"));
            location.setLocName(rs.getString("locName"));
            location.setLocDescription(rs.getString("locDescription"));
            location.setLatitude(rs.getBigDecimal("latitude").setScale(6));
            location.setLongitude(rs.getBigDecimal("longitude").setScale(6));
            location.setAddress(address);
                        
            return location;
        }
    }
}