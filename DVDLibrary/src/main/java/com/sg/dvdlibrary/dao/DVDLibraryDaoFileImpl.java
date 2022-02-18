/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvdlibrary.dao;

import com.sg.dvdlibrary.dto.DVD;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author nicoleozkan
 */
public class DVDLibraryDaoFileImpl implements DVDLibraryDao
{
    private Map<String, DVD> dvds = new HashMap<>();
    
    private final String LIBRARY_FILE;
    public static final String DELIMITER = "::";
    
    public DVDLibraryDaoFileImpl()
    {
        LIBRARY_FILE = "library.txt";
    }
    
    public DVDLibraryDaoFileImpl(String libraryTextFile)
    {
        LIBRARY_FILE = libraryTextFile;
    }
    
    @Override
    public DVD addDVD(String title, DVD dvd) throws DVDLibraryDaoException
    {
        loadLibrary();
        DVD newDVD = dvds.put(title, dvd);
        writeLibrary();
        return newDVD;
    }

    @Override
    public DVD removeDVD(String title) throws DVDLibraryDaoException
    {
        loadLibrary();
        DVD removedDVD = dvds.remove(title);
        writeLibrary();
        return removedDVD;
    }

    @Override
    public DVD editDVD(String title, DVD dvd) throws DVDLibraryDaoException
    {
        loadLibrary();
        dvds.remove(title);
        DVD newDVD = dvds.put(dvd.getTitle(), dvd);
        writeLibrary();
        return newDVD;
    }

    @Override
    public List<DVD> getAllDVDs() throws DVDLibraryDaoException
    {
        loadLibrary();
        return new ArrayList<DVD>(dvds.values());
    }

    @Override
    public DVD getDVD(String title) throws DVDLibraryDaoException
    {
        loadLibrary();
        DVD dvd = dvds.get(title);
        if (dvd == null)
        {
            throw new DVDLibraryDaoException("DVD does not exist.");
        }
        return dvd;
    }
    
    private DVD unmarshallDVD(String dvdAsText)
    {
        // dvdAsText is expecting a line read in from our file.
        // For example, it might look like this:
        // Title::ReleaseDate::MPAARating::Director::Studio::UserRating
        // 
        // We then split that line on our DELIMITER - which we are using ::
        // Leaving us with an array of Strings, stored in dvdTokens.
        // Which should look like this:
        // ---------------------------------------------------------
        // |     |           |          |        |      |          |
        // |Title|ReleaseDate|MPAARating|Director|Studio|UserRating|
        // |     |           |          |        |      |          |
        // ---------------------------------------------------------
        //   [0]      [1]        [2]       [3]     [4]       [5]
        String[] dvdTokens = dvdAsText.split(DELIMITER);
        
        // Given the pattern above, the title is in index 0 of the array.
        String title = dvdTokens[0];
        
        // Which we can then use to create a new DVD object to satisfy
        // the requirements of the DVD constructor
        DVD dvdFromFile = new DVD(title);
        
        // However, there are 5 remaining tokens that need to be set into the
        // new DVD object. Do this manually by using the appropriate setters.
        
        // Index 1 - ReleaseDate
        String releaseDateString = dvdTokens[1];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        LocalDate ld = LocalDate.parse(releaseDateString, formatter);
        dvdFromFile.setReleaseDate(ld);
        
        // Index 2 - MPAARating
        dvdFromFile.setMpaaRating(dvdTokens[2]);
        
        // Index 3 - DirectorName
        dvdFromFile.setDirectorName(dvdTokens[3]);
        
        // Index 4 - Studio
        dvdFromFile.setStudio(dvdTokens[4]);
        
        // Index 5 - UserRating
        dvdFromFile.setUserRating(dvdTokens[5]);
        
        // We have now created a DVD! Return it!
        return dvdFromFile;
    }
    
    private void loadLibrary() throws DVDLibraryDaoException
    {
        Scanner scanner;
        
        try
        {
            // Create Scanner for reading the file
            scanner = new Scanner(new BufferedReader(new FileReader(LIBRARY_FILE)));
        }
        catch (FileNotFoundException e)
        {
            throw new DVDLibraryDaoException("-_- Could not load library data into memory.", e);
        }
        // currentLine holds the most recent line read from the file
        String currentLine;
        // currentDVD holds the most recent DVD unmarshalled
        DVD currentDVD;
        // Go through LIBRARY_FILE line by line, decoding each line into a
        // DVD object by calling the unmarshalledDVD method.
        // Process while we have more lines in the file
        while (scanner.hasNextLine())
        {
            // get the next line in the file
            currentLine = scanner.nextLine();
            // unmarshall the line into a DVD
            currentDVD = unmarshallDVD(currentLine);
            
            // We are going to use the title as the map key for our dvd object.
            // Put currentDVD into the map using title as the key
            dvds.put(currentDVD.getTitle(), currentDVD);
        }
        // close scanner
        scanner.close();
    }
    private String marshallDVD(DVD aDVD)
    {
        // We need to turn a DVD object into a line of text for our file.
        // For example, we need an in memory object to end up like this:
        // Title::ReleaseDate::MPAARating::Director::Studio::UserRating
        
        // It's not a complicated process. Just get out each property,
        // and concatenate with our DELIMITER as a kind of spacer.
        
        // Start with the title, since that's supposed to be first.
        String dvdAsText = aDVD.getTitle() + DELIMITER;
        
        // ReleaseDate
        dvdAsText += aDVD.getReleaseDate().format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + DELIMITER;
        
        // MPAARating
        dvdAsText += aDVD.getMpaaRating() + DELIMITER;
        
        // DirectorName
        dvdAsText += aDVD.getDirectorName() + DELIMITER;
        
        // Studio
        dvdAsText += aDVD.getStudio() + DELIMITER;
        
        // UserRating
        dvdAsText += aDVD.getUserRating();
        
        // We have now turned a DVD to text! Return it!
        return dvdAsText;
    }
    
    /**
     * Writes all DVDs in the library out to a LIBRARY_FILE. See loadLibrary for file format.
     * 
     * @throws DVDLibraryDaoException if an error occurs writing to the file
     */
    private void writeLibrary() throws DVDLibraryDaoException
    {
        // NOTE FOR APPRENTICES: We are not handling the IOException - but
        // we are translating it to an application specific exception and
        // then simple throwing it (i.e. 'reporting' it) to the code that
        // called us. It is the responsibility of the calling code to
        // handle any errors that occur.
        PrintWriter out;
        
        try
        {
            out = new PrintWriter(new FileWriter(LIBRARY_FILE));
        }
        catch (IOException e)
        {
            throw new DVDLibraryDaoException("Could not save DVD data.", e);
        }
        
        // Write out the DVD objects to the library file.
        // NOTE TO THE APPRENTICES: We could just grab the dvd map,
        // get the Collection of DVDs and iterate over them but we've
        // already created a method that gets a List of DVDs so we'll reuse it
        String dvdAsText;
        List<DVD> dvdList = this.getAllDVDs();
        for (DVD currentDVD : dvdList)
        {
            // turn a DVD into a String
            dvdAsText = marshallDVD(currentDVD);
            // write the DVD object to the file
            out.println(dvdAsText);
            // force PrintWriter to write line to the file
            out.flush();
        }
        // Clean up
        out.close();
    }
}
