/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvdlibrary.ui;

import com.sg.dvdlibrary.dto.DVD;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author nicoleozkan
 */
public class DVDLibraryView 
{
    private UserIO io;

    public DVDLibraryView(UserIO io) 
    {
        this.io = io;
    }
    
    public int printMenuAndGetSelection()
    {
        io.print("Main Menu");
        io.print("1. Add a DVD");
        io.print("2. Remove a DVD");
        io.print("3. Edit a DVD");
        io.print("4. List all DVDs");
        io.print("5. Search for DVD by Title");
        io.print("6. Search for DVDs released within the last X years");
        io.print("7. Search for DVDs by their MPAA Rating");
        io.print("8. Search for DVDs by Director");
        io.print("9. Search for DVDs by Studio");
        io.print("10. Average age of movies in the library");
        io.print("11. Latest released movie in the library");
        io.print("12. Oldest released movie in the library");
        io.print("13. Exit");
        
        return io.readInt("Please select from the above choices.", 1, 13);
    }
    
    public DVD getNewDVDInfo()
    {
        String title = io.readString("Please enter the Title");
        LocalDate releaseDate = io.readLocalDate("Please enter the Release Date (MMM dd yyyy)");
        String mpaaRating = io.readString("Please enter the MPAA Rating");
        String directorName = io.readString("Please enter the Director Name");
        String studio = io.readString("Please enter the Studio");
        String userRating = io.readString("Please enter any comments on DVD");
        DVD currentDVD = new DVD(title);
        currentDVD.setReleaseDate(releaseDate);
        currentDVD.setMpaaRating(mpaaRating);
        currentDVD.setDirectorName(directorName);
        currentDVD.setStudio(studio);
        currentDVD.setUserRating(userRating);
        return currentDVD;
    }
    
    public void displayCreateDVDBanner()
    {
        io.print("Create DVD Menu:");
    }
    
    public void displayCreateSuccessBanner()
    {
        io.readString("DVD successfully created. Please hit enter to continue.");
    }
    
    public void displayDVDList(List<DVD> dvdList)
    {
        for (DVD currentDVD : dvdList)
        {
            String dvdInfo = String.format("'%s'\nRelease Date: %s\nMPAA Rating: %s\nDirector: %s\nStudio: %s\nUser Comments: %s\n", currentDVD.getTitle(), (currentDVD.getReleaseDate()).format(DateTimeFormatter.ofPattern("MMM dd yyyy")), currentDVD.getMpaaRating(), currentDVD.getDirectorName(), currentDVD.getStudio(), currentDVD.getUserRating());
            io.print("");
            io.print(dvdInfo);
        }
        io.readString("Please hit enter to continue.");
    }
    
    public void displayDisplayAllBanner()
    {
        io.print("=== Display All DVDs ===");
    }
    
    public void displayDisplayDVDBanner()
    {
        io.print("=== Display DVD ===");
    }
    
    public String getDVDTitleChoice()
    {
        return io.readString("Please enter the Title.");
    }
    
    public void displayDVD(DVD dvd)
    {
        if (dvd != null)
        {
            io.print("'" + dvd.getTitle() + "'");
            io.print("Release Date: " + (dvd.getReleaseDate()).format(DateTimeFormatter.ofPattern("MMM dd yyyy")));
            io.print("MPAA Rating: " + dvd.getMpaaRating());
            io.print("Director: " + dvd.getDirectorName());
            io.print("Studio: " + dvd.getStudio());
            io.print("User Comments: " + dvd.getUserRating());
            io.print("");
        }
        else
        {
            io.print("No such DVD.");
        }
        io.readString("Please hit enter to continue.");
    }
    
    public void displayRemoveDVDBanner()
    {
        io.print("=== Remove DVD ===");
    }
    
    public void displayRemoveResult(DVD dvdRecord)
    {
        if (dvdRecord != null)
        {
            io.print("DVD successfully removed.");
        }
        else
        {
            io.print("No such DVD.");
        }
        io.readString("Please hit enter to continue.");
    }
    
    public void displayEditDVDBanner()
    {
        io.print("=== Edit DVD ===");
    }
    
    public String getEditDVDTitleChoice()
    {
        return io.readString("Which DVD entry would you like to edit?");
    }
    
    public DVD editDVDInfo(DVD existingDvd)
    {
        String title = io.readString("Please enter the Title (" + existingDvd.getTitle() + "): ");
        LocalDate releaseDate = io.readLocalDate("Please enter the Release Date (" + existingDvd.getReleaseDate().format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + "): ");
        String mpaaRating = io.readString("Please enter the MPAA Rating (" + existingDvd.getMpaaRating() + "): ");
        String directorName = io.readString("Please enter the Director Name (" + existingDvd.getDirectorName() + "): ");
        String studio = io.readString("Please enter the Studio Name (" + existingDvd.getStudio() + "): ");
        String userRating = io.readString("Please enter any comments on DVD (" + existingDvd.getUserRating() + "): ");
        DVD currentDVD = new DVD(title);
        currentDVD.setReleaseDate(releaseDate);
        currentDVD.setMpaaRating(mpaaRating);
        currentDVD.setDirectorName(directorName);
        currentDVD.setStudio(studio);
        currentDVD.setUserRating(userRating);
        return currentDVD;
    }
    
    public void displayEditSuccessBanner()
    {
        io.print("DVD successfully edited.");
        io.readString("Please hit enter to continue.");
    }
    
    public void displayDVDsNumYearsBanner()
    {
        io.print("=== DVDs Released in the Last X Years ===");
    }
    
    public int getNumYears()
    {
        return io.readInt("How many years back do you want to see movies that were released? ");
    }
    
    public void displayMpaaRatingBanner()
    {
        io.print("=== DVDs by MPAA Rating ===");
    }
    
    public String getMpaaRatingChoice()
    {
         return io.readString("What MPAA Rating of movies would you like to view? (Ex.: G, PG, PG-13, R) ");
    }
    
    public void displayDVDsByDirectorBanner()
    {
        io.print("=== DVDs by Director ===");
    }
    
    public String getDirectorChoice()
    {
        return io.readString("Which director's movies would you like to view? ");
    }
    
    public void displayDVDsByStudioBanner()
    {
        io.print("=== DVDs by Studio ===");
    }
    
    public String getStudioChoice()
    {
        return io.readString("What studio's movies would you like to see? ");
    }
    
    public void averageAgeBanner()
    {
        io.print("=== Average Age of DVDs ===");
    }
    
    public void displayLatestReleaseBanner()
    {
        io.print("=== Latest Release ===");
    }
    
    public void displayOldestReleaseBanner()
    {
        io.print("=== Oldest Release ===");
    }
    
    public boolean getRepeatActionChoice()
    {
        boolean repeat = true;
        String userAnswer = (io.readString("Would you like to repeat this action with a different entry? (y/n)")).toLowerCase();
        if (userAnswer.equals("y"))
        {
            repeat = true;
        }
        else
        {
            repeat = false;
        }
        return repeat;
    }
    
    public void displayExitBanner()
    {
        io.print("Good Bye.");
    }
    
    public void displayUnknownCommandBanner()
    {
        io.print("Unknown Command.");
    }
    
    public void displayErrorMessage(String errorMsg)
    {
        io.print("=== ERROR ===");
        io.print(errorMsg);
        io.readString("Please hit enter to continue.");
    }
}
