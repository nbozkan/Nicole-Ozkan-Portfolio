/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.dto.Super;
import java.util.List;

/**
 *
 * @author nicoleozkan
 */
public interface SuperDAO 
{
    Super getSuperById(int superId);
    List<Super> getAllSupers();
    Super addSuper(Super superPerson);
    void updateSuper(Super superPerson);
    void deleteSuperById(int superId);
}