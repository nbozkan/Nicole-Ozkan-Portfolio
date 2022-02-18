/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvdlibrary.dao;

import com.sg.dvdlibrary.dto.DVD;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author nicoleozkan
 */
public class DVDLibraryDaoFileImplTest 
{
    DVDLibraryDao testDao;
    
    public DVDLibraryDaoFileImplTest() 
    {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        testDao = ctx.getBean("dvdLibraryDao", DVDLibraryDaoFileImpl.class);
    }
    
    @BeforeAll
    public static void setUpClass() 
    {
    }
    
    @AfterAll
    public static void tearDownClass() 
    {
    }
    
    @BeforeEach
    public void setUp() throws Exception
    {
        String testFile = "testlibrary.txt";
        // Use the FileWriter to quickly blank the file
        new FileWriter(testFile);
        testDao = new DVDLibraryDaoFileImpl(testFile);
    }
    
    @AfterEach
    public void tearDown() 
    {
    }

    @Test
    public void testAddGetDVD() throws Exception
    {
        // Create our method test inputs
        String title = "Pride and Prejudice";
        DVD dvd = new DVD(title);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        LocalDate releaseDate = LocalDate.parse("Nov 23 2005", formatter);
        dvd.setReleaseDate(releaseDate);
        dvd.setMpaaRating("PG");
        dvd.setDirectorName("Joe Wright");
        dvd.setStudio("StudioCanal");
        dvd.setUserRating("Favorite movie!");
        
        // Add the DVD to the DAO
        testDao.addDVD(title, dvd);
        // Get the DVD from the DAO
        DVD retrievedDVD = testDao.getDVD(title);
        
        // Check that the data is equal
        assertEquals(dvd.getTitle(), retrievedDVD.getTitle(), "Checking title.");
        assertEquals(dvd.getReleaseDate(), retrievedDVD.getReleaseDate(), "Checking release date.");
        assertEquals(dvd.getMpaaRating(), retrievedDVD.getMpaaRating(), "Checking MPAA rating.");
        assertEquals(dvd.getDirectorName(), retrievedDVD.getDirectorName(), "Checking director name.");
        assertEquals(dvd.getStudio(), retrievedDVD.getStudio(), "Checking studio.");
        assertEquals(dvd.getUserRating(), retrievedDVD.getUserRating(), "Checking user rating.");
    }
    
    @Test
    public void testAddGetAllDVDs() throws Exception
    {
        // Create our first DVD
        DVD firstDVD = new DVD("Pride and Prejudice");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        LocalDate firstDVDReleaseDate = LocalDate.parse("Nov 23 2005", formatter);
        firstDVD.setReleaseDate(firstDVDReleaseDate);
        firstDVD.setMpaaRating("PG");
        firstDVD.setDirectorName("Joe Wright");
        firstDVD.setStudio("StudioCanal");
        firstDVD.setUserRating("Favorite movie!");
        
        // Create our second DVD
        DVD secondDVD = new DVD("Zootopia");
        LocalDate secondDVDReleaseDate = LocalDate.parse("Mar 04 2016", formatter);
        secondDVD.setReleaseDate(secondDVDReleaseDate);
        secondDVD.setMpaaRating("PG");
        secondDVD.setDirectorName("Byron Howard and Rich Moore");
        secondDVD.setStudio("Walt Disney Animation Studios");
        secondDVD.setUserRating("Great family movie.");
        
        // Add both our DVDs to the DAO
        testDao.addDVD(firstDVD.getTitle(), firstDVD);
        testDao.addDVD(secondDVD.getTitle(), secondDVD);
        
        // Retrieve the list of all DVDs within the DAO
        List<DVD> allDVDs = testDao.getAllDVDs();
        
        // First check the general contents of the list
        assertNotNull(allDVDs, "The list of DVDs must not be null.");
        assertEquals(2, allDVDs.size(), "List of DVDs should have 2 DVDs.");
        
        // Then the specifics
        assertTrue(testDao.getAllDVDs().contains(firstDVD), "The list of DVDs should include Pride and Prejudice.");
        assertTrue(testDao.getAllDVDs().contains(secondDVD), "The list of DVDs should include Zootopia.");
    }
    
    @Test
    public void testRemoveDVD() throws Exception
    {
        // Create two new DVDs
        DVD firstDVD = new DVD("Pride and Prejudice");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        LocalDate firstDVDReleaseDate = LocalDate.parse("Nov 23 2005", formatter);
        firstDVD.setReleaseDate(firstDVDReleaseDate);
        firstDVD.setMpaaRating("PG");
        firstDVD.setDirectorName("Joe Wright");
        firstDVD.setStudio("StudioCanal");
        firstDVD.setUserRating("Favorite movie!");
        
        DVD secondDVD = new DVD("Zootopia");
        LocalDate secondDVDReleaseDate = LocalDate.parse("Mar 04 2016", formatter);
        secondDVD.setReleaseDate(secondDVDReleaseDate);
        secondDVD.setMpaaRating("PG");
        secondDVD.setDirectorName("Byron Howard and Rich Moore");
        secondDVD.setStudio("Walt Disney Animation Studios");
        secondDVD.setUserRating("Great family movie.");
        
        // Add both to the DAO
        testDao.addDVD(firstDVD.getTitle(), firstDVD);
        testDao.addDVD(secondDVD.getTitle(), secondDVD);
        
        // remove the first DVD
        DVD removedDVD = testDao.removeDVD(firstDVD.getTitle());
        
        // Check that the correct object was removed
        assertEquals(removedDVD, firstDVD, "The removed DVD should be Pride and Prejudice.");
        
        // Get all the DVDs
        List<DVD> allDVDs = testDao.getAllDVDs();
        
        // First check the general contents of the list
        assertNotNull(allDVDs, "All DVDs list should not be null.");
        assertEquals(1, allDVDs.size(), "All DVDs should only have 1 DVD.");
        
        // Then the specifics
        assertFalse(allDVDs.contains(firstDVD), "All DVDs should NOT include Pride and Prejudice.");
        assertTrue(allDVDs.contains(secondDVD), "All DVDs should NOT include Zootopia.");
        
        // Remove the second DVD
        removedDVD = testDao.removeDVD(secondDVD.getTitle());
        
        // Check that the correct object was removed
        assertEquals(removedDVD, secondDVD, "The removed DVD should be Zootopia.");
        
        // retrieve all of the DVDs again, and check the list.
        allDVDs = testDao.getAllDVDs();
        
        // Check the contents of the list - it should be empty
        assertTrue(allDVDs.isEmpty(), "The retrieved list of DVDs should be empty.");
    }
}
