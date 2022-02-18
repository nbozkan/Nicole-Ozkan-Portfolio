/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.dto.Power;
import com.sg.superherosightings.dto.Super;
import java.util.List;

/**
 *
 * @author nicoleozkan
 */

public interface PowerDAO 
{
    Power getPowerById(int powerId);
    List<Power> getAllPowers();
    Power addPower(Power power);
    void updatePower(Power power);
    void deletePowerById(int powerId);
}