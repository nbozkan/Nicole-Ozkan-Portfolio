/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dto;

import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author nicoleozkan
 */
public class Super 
{
    private int superId;
    
    @NotBlank(message = "Super name must not be empty.")
    @Size(max = 50, message = "Super name must be less than 50 characters.")
    private String superName;
    
    @NotBlank(message = "Super description must not be empty.")
    @Size(max = 150, message = "Super description must be less than 150 characters.")
    private String superDescription;
    
    @NotNull(message = "Hero or Villian must be chosen.")
    private Boolean hero;
    
    @NotNull(message = "Choose at least one power.")
    @NotEmpty(message = "Choose at least one power.")
    private List<Power> powers;

    public int getSuperId() 
    {
        return superId;
    }

    public void setSuperId(int superId) 
    {
        this.superId = superId;
    }

    public String getSuperName() 
    {
        return superName;
    }

    public void setSuperName(String superName) 
    {
        this.superName = superName;
    }

    public String getSuperDescription() 
    {
        return superDescription;
    }

    public void setSuperDescription(String superDescription) 
    {
        this.superDescription = superDescription;
    }

    public boolean isHero() {
        return hero;
    }

    public void setHero(boolean hero) {
        this.hero = hero;
    }

    public List<Power> getPowers() 
    {
        return powers;
    }

    public void setPowers(List<Power> powers) 
    {
        this.powers = powers;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.superId;
        hash = 79 * hash + Objects.hashCode(this.superName);
        hash = 79 * hash + Objects.hashCode(this.superDescription);
        hash = 79 * hash + (this.hero ? 1 : 0);
        hash = 79 * hash + Objects.hashCode(this.powers);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Super other = (Super) obj;
        if (this.superId != other.superId) {
            return false;
        }
        if (this.hero != other.hero) {
            return false;
        }
        if (!Objects.equals(this.superName, other.superName)) {
            return false;
        }
        if (!Objects.equals(this.superDescription, other.superDescription)) {
            return false;
        }
        if (!Objects.equals(this.powers, other.powers)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Super{" + "superId=" + superId + ", superName=" + superName + ", superDescription=" + superDescription + ", hero=" + hero + ", powers=" + powers + '}';
    }
}