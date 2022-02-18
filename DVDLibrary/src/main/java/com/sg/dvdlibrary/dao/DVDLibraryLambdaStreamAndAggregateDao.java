/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvdlibrary.dao;

import com.sg.dvdlibrary.dto.DVD;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nicoleozkan
 */
public interface DVDLibraryLambdaStreamAndAggregateDao
{
       /**
     * Adds the given DVD to the library and associates it with the given title.
     * If there is already a DVD associated with the given
     * DVD it will return that DVD object, otherwise it will return null.
     * 
     * @param title title of DVD
     * @param dvd DVD to be added to library
     * @return the DVD object previously associated with the given DVD
     * if it exists, null otherwise.
     * @throws DVDlibraryDaoException
     */
    DVD addDVD(String title, DVD dvd) throws DVDLibraryDaoException;
    
    /**
     * Removes from the library the DVD associated with the given title.
     * Returns the DVD object that is being removed or null if
     * there is no DVD associated with the given title.
     * 
     * @param title title of DVD to be removed
     * @return DVD object that was removed or null if no DVD
     * was associated with the given title
     * @ throws DVDLibraryDaoException
     */
    DVD removeDVD(String title) throws DVDLibraryDaoException;
    
    /**
     * Edits the given DVD and associates it with the given title.
     * If there is already a DVD associated with the given title
     * it will return that DVD object, otherwise it will return null.
     * 
     * @param title title of edited DVD
     * @param dvd DVD to be added to the library
     * @return the DVD object previously associated with the given
     * title if it exists, null otherwise
     * @throws DVDLibraryDaoException
     */
    DVD editDVD(String title, DVD dvd) throws DVDLibraryDaoException;
    
    /**
     * Returns a List of all DVDs in the library.
     * 
     * @return List containing all DVDs in the library.
     * @throws DVDLibraryDaoException
     */
    List<DVD> getAllDVDs() throws DVDLibraryDaoException;
    
    /**
     * Returns the DVD object associated with the given title.
     * Returns null if no such DVD exists.
     * 
     * @param title title of DVD to retrieve
     * @return the DVD object associated with the given title,
     * null if no such DVD exists
     * @ throws DVDLibraryDaoException
     */
    DVD getDVD(String title) throws DVDLibraryDaoException;
    
    /**
     * Finds all DVDs released within the given number of years.
     * 
     * @param numYears
     * @return List
     */
    List<DVD> dvdsReleasedNumYears(int numYears);
    
    /**
     * Finds all DVDs with given MPAA Rating.
     * 
     * @param mpaaRating
     * @return List
     */
    List<DVD> dvdsByMpaaRating(String mpaaRating);
    
    /**
     * Finds DVDs with given director.
     * 
     * @param director
     * @return List
     */
    List<DVD> dvdsByDirector(String director);
    
    /**
     * Finds DVDs by given Studio.
     * 
     * @param studio
     * @return List
     */
    List<DVD> dvdsByStudio(String studio);
    
    /**
     * Finds average age of DVDs in library.
     * 
     * @return int 
     */
    Double averageAgeDVDs();
    
    /**
     * Finds DVD with most recent release date.
     * 
     * @return DVD
     */
    DVD newestDVD();
    
    /**
     * Finds DVD with oldest release date.
     * 
     * @return DVD
     */
    DVD oldestDVD();
}
