/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvdlibrary.controller;

import com.sg.dvdlibrary.dao.DVDLibraryDao;
import com.sg.dvdlibrary.dao.DVDLibraryDaoException;
import com.sg.dvdlibrary.dao.DVDLibraryLambdaStreamAndAggregateDao;
import com.sg.dvdlibrary.dto.DVD;
import com.sg.dvdlibrary.ui.DVDLibraryView;
import java.util.List;

/**
 *
 * @author nicoleozkan
 */
public class DVDLibraryController 
{

    private DVDLibraryDao dao;
    private DVDLibraryLambdaStreamAndAggregateDao secondDao;
    private DVDLibraryView view;
    boolean repeat = true;

    public DVDLibraryController(DVDLibraryDao dao, DVDLibraryView view, DVDLibraryLambdaStreamAndAggregateDao secondDao) 
    {
        this.dao = dao;
        this.view = view;
        this.secondDao = secondDao;
    }

    public void run() 
    {
        boolean keepGoing = true;
        int menuSelection;
        while (keepGoing) 
        {
            try 
            {
                menuSelection = getMenuSelection();

                switch (menuSelection) 
                {
                    case 1:
                        do 
                        {
                            createDVD();
                        } while (repeat);
                        break;
                    case 2:
                        do 
                        {
                            removeDVD();
                        } while (repeat);
                        break;
                    case 3:
                        do 
                        {
                            editDVD();
                        } while (repeat);
                        break;
                    case 4:
                        listDVDs();
                        break;
                    case 5:
                        viewDVD();
                        break;
                    case 6:
                        do
                        {
                            dvdsReleasedInNumYears();
                        } while (repeat);
                        break;
                    case 7:
                        do
                        {
                            dvdsByMpaaRating();
                        } while (repeat);
                        break;
                    case 8:
                        do
                        {
                            dvdsByDirector();
                        } while (repeat);
                        break;
                    case 9:
                        do
                        {
                            dvdsByStudio();
                        } while (repeat);
                        break;
                    case 10:
                        averageAgeDVDs();
                        break;
                    case 11:
                        latestDVD();
                        break;
                    case 12:
                        oldestDVD();
                        break;
                    case 13:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }
            } catch (DVDLibraryDaoException e)
            {
                view.displayErrorMessage(e.getMessage());
            }
        }
        exitMessage();
    }

    private int getMenuSelection() 
    {
        return view.printMenuAndGetSelection();
    }

    private void createDVD() throws DVDLibraryDaoException 
    {
        view.displayCreateDVDBanner();
        DVD newDVD = view.getNewDVDInfo();
        dao.addDVD(newDVD.getTitle(), newDVD);
        view.displayCreateSuccessBanner();
        repeat = view.getRepeatActionChoice();
    }

    private void listDVDs() throws DVDLibraryDaoException 
    {
        view.displayDisplayAllBanner();
        List<DVD> dvdList = dao.getAllDVDs();
        view.displayDVDList(dvdList);
    }

    private void viewDVD() throws DVDLibraryDaoException 
    {
        view.displayDisplayDVDBanner();
        String title = view.getDVDTitleChoice();
        DVD dvd = dao.getDVD(title);
        view.displayDVD(dvd);
    }

    private void removeDVD() throws DVDLibraryDaoException 
    {
        view.displayRemoveDVDBanner();
        String title = view.getDVDTitleChoice();
        DVD removedDVD = dao.removeDVD(title);
        view.displayRemoveResult(removedDVD);
        repeat = view.getRepeatActionChoice();
    }

    private void editDVD() throws DVDLibraryDaoException 
    {
        view.displayEditDVDBanner();
        String oldTitle = view.getEditDVDTitleChoice();
        DVD oldDVD = dao.getDVD(oldTitle);
        DVD newDVD = view.editDVDInfo(oldDVD);
        dao.editDVD(oldTitle, newDVD);
        view.displayEditSuccessBanner();
        repeat = view.getRepeatActionChoice();
    }
    
    private void dvdsReleasedInNumYears()
    {
        int numYears = view.getNumYears();
        view.displayDVDsNumYearsBanner();
        List<DVD> moviesReleasedLastXYears = secondDao.dvdsReleasedNumYears(numYears);
        view.displayDVDList(moviesReleasedLastXYears);
        repeat = view.getRepeatActionChoice();
    }
    
    private void dvdsByMpaaRating()
    {
        String mpaaRating = view.getMpaaRatingChoice();
        view.displayMpaaRatingBanner();
        List<DVD> moviesByMpaaRating = secondDao.dvdsByMpaaRating(mpaaRating);
        view.displayDVDList(moviesByMpaaRating);
        repeat = view.getRepeatActionChoice();
    }
    
    private void dvdsByDirector()
    {
       String director = view.getDirectorChoice();
       view.displayDVDsByDirectorBanner();
       secondDao.dvdsByDirector(director);
       repeat = view.getRepeatActionChoice();
    }
    
    private void dvdsByStudio()
    {
        String studio = view.getStudioChoice();
        view.displayDVDsByStudioBanner();
        List<DVD> dvdsByStudio = secondDao.dvdsByStudio(studio);
        view.displayDVDList(dvdsByStudio);
        repeat = view.getRepeatActionChoice();
    }
    
    private void averageAgeDVDs()
    {
        view.averageAgeBanner();
        System.out.println(secondDao.averageAgeDVDs());
    }
    
    private void latestDVD()
    {
        view.displayLatestReleaseBanner();
        DVD latestDVD = secondDao.newestDVD();
        view.displayDVD(latestDVD);
    }
    
    private void oldestDVD()
    {
        view.displayOldestReleaseBanner();
        DVD oldestDVD = secondDao.oldestDVD();
        view.displayDVD(oldestDVD);
    }

    private void unknownCommand() 
    {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() 
    {
        view.displayExitBanner();
    }
}