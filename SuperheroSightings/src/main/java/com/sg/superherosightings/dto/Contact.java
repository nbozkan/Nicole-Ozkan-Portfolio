/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dto;

import java.util.Objects;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author nicoleozkan
 */
public class Contact 
{
    private int contactId;
    
    @NotBlank(message = "Contact name must not be empty.")
    @Size(max = 50, message = "Contact name must be less than 50 characters.")
    private String contactName;
    
    @NotBlank(message = "Email address must not be empty.")
    @Email(message = "Email address must be a valid email address.")
    @Size(max = 50, message = "Email address must be less than 50 characters.")
    private String emailAddress;
    
    @NotBlank(message = "Phone number must not be empty.")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be a valid phone number.")
    @Size(min = 10, max = 10, message = "Phone number must be 10 digits.")
    private String phoneNumber;

    public Contact() 
    {
    }

    public Contact(String string) 
    {
        
    }

    public int getContactId() 
    {
        return contactId;
    }

    public void setContactId(int contactId) 
    {
        this.contactId = contactId;
    }

    public String getContactName() 
    {
        return contactName;
    }

    public void setContactName(String contactName) 
    {
        this.contactName = contactName;
    }

    public String getEmailAddress() 
    {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) 
    {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() 
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) 
    {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int hashCode() 
    {
        int hash = 5;
        hash = 73 * hash + this.contactId;
        hash = 73 * hash + Objects.hashCode(this.contactName);
        hash = 73 * hash + Objects.hashCode(this.emailAddress);
        hash = 73 * hash + Objects.hashCode(this.phoneNumber);
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
        final Contact other = (Contact) obj;
        if (this.contactId != other.contactId) 
        {
            return false;
        }
        if (!Objects.equals(this.contactName, other.contactName)) 
        {
            return false;
        }
        if (!Objects.equals(this.emailAddress, other.emailAddress)) 
        {
            return false;
        }
        if (!Objects.equals(this.phoneNumber, other.phoneNumber)) 
        {
            return false;
        }
        return true;
    }
    @Override
    public String toString() 
    {
        return "Contact{" + "contactId=" + contactId + ", contactName=" + contactName + ", emailAddress=" + emailAddress + ", phoneNumber=" + phoneNumber + '}';
    }
}
