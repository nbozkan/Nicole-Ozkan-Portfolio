/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvdlibrary.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author nicoleozkan
 */
public class UserIOConsoleImpl implements UserIO
{
    Scanner userInput = new Scanner(System.in);
        
    @Override
    public void print(String message) 
    {
        System.out.println(message);
    }

    @Override
    public String readString(String prompt) 
    {
        System.out.println(prompt);
        String messagePrinted = userInput.nextLine();
        return messagePrinted;
    }

    @Override
    public int readInt(String prompt) 
    {
        System.out.println(prompt);
        int userNumber = Integer.parseInt(userInput.nextLine());
        return userNumber;
    }

    @Override
    public int readInt(String prompt, int min, int max) 
    {
        System.out.println(prompt);
        int userNumber = Integer.parseInt(userInput.nextLine());
        while (userNumber < min || userNumber > max)
        {
            System.out.println("Hey! " + userNumber + " is not between " + min + " and " + max + ". Please enter a number that is between " + min + " and " + max);
            userNumber = Integer.parseInt(userInput.nextLine());
        }
        return userNumber;
    }

    @Override
    public double readDouble(String prompt) 
    {
        System.out.println(prompt);
        double userNumber = Double.parseDouble(userInput.nextLine());
        return userNumber;
        
    }

    @Override
    public double readDouble(String prompt, double min, double max) 
    {
        System.out.println(prompt);
        double userNumber = Double.parseDouble(userInput.nextLine());
        while (userNumber < min || userNumber > max)
        {
            System.out.println("Hey! " + userNumber + " is not between " + min + " and " + max + ". Please enter a number between " + min + " and " + max);
            userNumber = Double.parseDouble(userInput.nextLine());
        }
        return userNumber;
    }

    @Override
    public float readFloat(String prompt) 
    {
        System.out.println(prompt);
        float userNumber = Float.parseFloat(userInput.nextLine());
        return userNumber;
    }

    @Override
    public float readFloat(String prompt, float min, float max) 
    {
        System.out.println(prompt);
        float userNumber = Float.parseFloat(userInput.nextLine());
        while (userNumber < min || userNumber > max)
        {
            System.out.println("Hey! " + userNumber + " is not between " + min + " and " + max + ". Please enter a number between " + min + " and " + max);
            userNumber = Float.parseFloat(userInput.nextLine());
        }
        return userNumber;
    }

    @Override
    public long readLong(String prompt) 
    {
        System.out.println(prompt);
        long userNumber = Long.parseLong(userInput.nextLine());
        return userNumber;
    }

    @Override
    public long readLong(String prompt, long min, long max) 
    {
        System.out.println(prompt);
        long userNumber = userInput.nextLong();
        while (userNumber < min || userNumber > max)
        {
            System.out.println("Hey! " + userNumber + " is not between " + min + " and " + max + ". Please enter a number between " + min + " and " + max);
            userNumber = Long.parseLong(userInput.nextLine());
        }
        return userNumber; 
    }

    @Override
    public LocalDate readLocalDate(String prompt) 
    {
        System.out.println(prompt);
        String date = userInput.nextLine();
        LocalDate ld = LocalDate.parse(date, DateTimeFormatter.ofPattern("MMM dd yyyy"));
        return ld;
    }
}
