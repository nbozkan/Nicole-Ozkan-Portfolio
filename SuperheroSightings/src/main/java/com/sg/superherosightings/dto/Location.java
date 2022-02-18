/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dto;

import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author nicoleozkan
 */
public class Location 
{
    private int locId;
    
    @NotBlank(message = "Location name cannot be empty.")
    @Size(max = 50, message = "Location name must be less than 50 characters.")
    private String locName;
    
    @NotBlank(message = "Location description cannot be empty.")
    @Size(max = 150, message = "Location description must be less than 150 characters.")
    private String locDescription;
    
    @NotNull(message = "Latitude must be between -90 and 90.")
    @DecimalMin(value = "-90.0", message = "Latitude value cannot be below -90.")
    @DecimalMax(value = "90.0", message = "Latitude value cannot be above 90.")
    @Digits(integer = 2, fraction = 6, message = "Latitude must follow format: (-)11.111111(max 2 integral digits, max 6 fractional digits).")
    private BigDecimal latitude;
    
    @NotNull(message = "Longitude must be between -180 and 180.")
    @DecimalMin(value = "-180.0", message = "Longitude value cannot be below -180.")
    @DecimalMax(value = "180.0", message = "Longitude value cannot be above 180.")
    @Digits(integer = 3, fraction = 6, message = "Longitude must follow format: (-)111.111111(max 3 integral digits, max 6 fractional digits).")
    private BigDecimal longitude;
    
    @Valid
    private Address address;

    public int getLocId() 
    {
        return locId;
    }

    public void setLocId(int locId) 
    {
        this.locId = locId;
    }

    public String getLocName() 
    {
        return locName;
    }

    public void setLocName(String locName) 
    {
        this.locName = locName;
    }

    public String getLocDescription() 
    {
        return locDescription;
    }

    public void setLocDescription(String locDescription) 
    {
        this.locDescription = locDescription;
    }

    public BigDecimal getLatitude() 
    {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) 
    {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() 
    {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) 
    {
        this.longitude = longitude;
    }

    public Address getAddress() 
    {
        return address;
    }

    public void setAddress(Address address) 
    {
        this.address = address;
    }

    @Override
    public int hashCode() 
    {
        int hash = 3;
        hash = 89 * hash + this.locId;
        hash = 89 * hash + Objects.hashCode(this.locName);
        hash = 89 * hash + Objects.hashCode(this.locDescription);
        hash = 89 * hash + Objects.hashCode(this.latitude);
        hash = 89 * hash + Objects.hashCode(this.longitude);
        hash = 89 * hash + Objects.hashCode(this.address);
        return hash;
    }

    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj) 
        {
            return true;
        }
        if (obj == null) 
        {
            return false;
        }
        if (getClass() != obj.getClass()) 
        {
            return false;
        }
        final Location other = (Location) obj;
        if (this.locId != other.locId) 
        {
            return false;
        }
        if (!Objects.equals(this.locName, other.locName)) 
        {
            return false;
        }
        if (!Objects.equals(this.locDescription, other.locDescription)) 
        {
            return false;
        }
        if (!Objects.equals(this.latitude, other.latitude)) 
        {
            return false;
        }
        if (!Objects.equals(this.longitude, other.longitude)) 
        {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) 
        {
            return false;
        }
        return true;
    }
    @Override
    public String toString() 
    {
        return "Location{" + "locId=" + locId + ", locName=" + locName + ", locDescription=" + locDescription + ", latitude=" + latitude + ", longitude=" + longitude + ", address=" + address + '}';
    }
}