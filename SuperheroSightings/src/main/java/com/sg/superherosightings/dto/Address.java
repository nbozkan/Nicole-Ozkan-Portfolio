/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dto;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author nicoleozkan
 */
public class Address 
{
    private int addressId;
    
    @NotBlank(message = "Address 1 must not be empty.")
    @Size(max = 50, message = "Address 1 must be less than 50 characters.")
    private String address1;
    
    @Size(max = 50, message = "Address 2 must be less than 50 characters.")
    private String address2;
    
    @NotBlank(message = "City must not be empty.")
    @Size(max = 50, message = "City must be less than 50 characters.")
    private String city;
    
    @NotBlank(message = "State must not be empty.")
    @Size(min = 2, max = 2, message = "State must have 2 characters.")
    private String state;
    
    @Pattern(regexp = "^[0-9]{5}$", message = "Zip code must contain exactly 5 numeric values")
    private String zipCode;
    
    @Pattern(regexp = "^(?:[0-9]{4}|)$", message = "Zip extension must contain exactly 4 numeric values")
    private String zipExtension;

    public Address() 
    {
        
    }
    
    public Address(String string) 
    {
        
    }

    public int getAddressId() 
    {
        return addressId;
    }

    public void setAddressId(int addressId) 
    {
        this.addressId = addressId;
    }

    public String getAddress1() 
    {
        return address1;
    }

    public void setAddress1(String address1) 
    {
        this.address1 = address1;
    }

    public String getAddress2() 
    {
        return address2;
    }

    public void setAddress2(String address2) 
    {
        this.address2 = address2;
    }

    public String getCity() 
    {
        return city;
    }

    public void setCity(String city) 
    {
        this.city = city;
    }

    public String getState() 
    {
        return state;
    }

    public void setState(String state) 
    {
        this.state = state;
    }

    public String getZipCode() 
    {
        return zipCode;
    }

    public void setZipCode(String zipCode) 
    {
        this.zipCode = zipCode;
    }

    public String getZipExtension() 
    {
        return zipExtension;
    }

    public void setZipExtension(String zipExtension) 
    {
        this.zipExtension = zipExtension;
    }

    @Override
    public int hashCode() 
    {
        int hash = 7;
        hash = 79 * hash + this.addressId;
        hash = 79 * hash + Objects.hashCode(this.address1);
        hash = 79 * hash + Objects.hashCode(this.address2);
        hash = 79 * hash + Objects.hashCode(this.city);
        hash = 79 * hash + Objects.hashCode(this.state);
        hash = 79 * hash + Objects.hashCode(this.zipCode);
        hash = 79 * hash + Objects.hashCode(this.zipExtension);
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
        final Address other = (Address) obj;
        if (this.addressId != other.addressId) 
        {
            return false;
        }
        if (!Objects.equals(this.address1, other.address1)) 
        {
            return false;
        }
        if (!Objects.equals(this.address2, other.address2)) 
        {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) 
        {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) 
        {
            return false;
        }
        if (!Objects.equals(this.zipCode, other.zipCode)) 
        {
            return false;
        }
        if (!Objects.equals(this.zipExtension, other.zipExtension)) 
        {
            return false;
        }
        return true;
    }
    @Override
    public String toString() 
    {
        return "Address{" + "addressId=" + addressId + ", address1=" + address1 + ", address2=" + address2 + ", city=" + city + ", state=" + state + ", zipCode=" + zipCode + ", zipExtension=" + zipExtension + '}';
    }
}
