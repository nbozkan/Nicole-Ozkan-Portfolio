/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dto;

import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author nicoleozkan
 */
public class Organization 
{
    private int orgId;
    
    @NotBlank(message = "Organization name must not be empty.")
    @Size(max = 50, message = "Organization name must be less than 50 characters.")
    private String orgName;
    
    @NotBlank(message = "Organization description must not be empty.")
    @Size(max = 150, message = "Organization description must be less than 150 characters.")
    private String orgDescription;
    
    @Valid
    private Contact contact;
    
    @Valid
    private Address address;
    
    @NotNull(message = "Choose at least one super.")
    @NotEmpty(message = "Choose at least one super.")
    private List<Super> supers;

    public int getOrgId() 
    {
        return orgId;
    }

    public void setOrgId(int orgId) 
    {
        this.orgId = orgId;
    }

    public String getOrgName() 
    {
        return orgName;
    }

    public void setOrgName(String orgName) 
    {
        this.orgName = orgName;
    }

    public String getOrgDescription() 
    {
        return orgDescription;
    }

    public void setOrgDescription(String orgDescription) 
    {
        this.orgDescription = orgDescription;
    }

    public Contact getContact() 
    {
        return contact;
    }

    public void setContact(Contact contact) 
    {
        this.contact = contact;
    }

    public Address getAddress() 
    {
        return address;
    }

    public void setAddress(Address address) 
    {
        this.address = address;
    }

    public List<Super> getSupers() 
    {
        return supers;
    }

    public void setSupers(List<Super> supers) 
    {
        this.supers = supers;
    }

    @Override
    public int hashCode() 
    {
        int hash = 7;
        hash = 97 * hash + this.orgId;
        hash = 97 * hash + Objects.hashCode(this.orgName);
        hash = 97 * hash + Objects.hashCode(this.orgDescription);
        hash = 97 * hash + Objects.hashCode(this.contact);
        hash = 97 * hash + Objects.hashCode(this.address);
        hash = 97 * hash + Objects.hashCode(this.supers);
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
        final Organization other = (Organization) obj;
        if (this.orgId != other.orgId) 
        {
            return false;
        }
        if (!Objects.equals(this.orgName, other.orgName)) 
        {
            return false;
        }
        if (!Objects.equals(this.orgDescription, other.orgDescription)) 
        {
            return false;
        }
        if (!Objects.equals(this.contact, other.contact)) 
        {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) 
        {
            return false;
        }
        if (!Objects.equals(this.supers, other.supers)) 
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString() 
    {
        return "Organization{" + "orgId=" + orgId + ", orgName=" + orgName + ", orgDescription=" + orgDescription + ", contact=" + contact + ", address=" + address + ", supers=" + supers + '}';
    }
}