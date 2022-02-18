/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.dto.Location;
import com.sg.superherosightings.dto.Sighting;
import com.sg.superherosightings.dto.Super;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author nicoleozkan
 */
public interface SightingDAO 
{
    Sighting getSightingById(int sightingId);
    List<Sighting> getAllSightings();
    Sighting addSighting(Sighting sighting);
    void updateSighting(Sighting sighting);
    void deleteSightingById(int sightingId);
    
    List<Sighting> getLatestSightings();
    List<Sighting> getSightingsForSuper(Super superPerson);
    List<Sighting> getSightingsForLocation(Location location);
    List<Sighting> getSightingsForDate(LocalDate sightingDate);
}