/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvdlibrary.dao;

import com.sg.dvdlibrary.dto.DVD;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

/**
 *
 * @author nicoleozkan
 */
public class DVDLibraryLambdaStreamAndAggregateDaoFileImpl implements DVDLibraryLambdaStreamAndAggregateDao
{
    private Map<String, DVD> dvds = new HashMap<>();
    
    @Override
    public DVD addDVD(String title, DVD dvd) throws DVDLibraryDaoException 
    {
        DVD newDVD = dvds.put(title, dvd);
        return newDVD;
    }

    @Override
    public DVD removeDVD(String title) throws DVDLibraryDaoException 
    {
        DVD removedDVD = dvds.remove(title);
        return removedDVD;
    }

    @Override
    public DVD editDVD(String title, DVD dvd) throws DVDLibraryDaoException 
    {
        dvds.remove(title);
        DVD newDVD = dvds.put(dvd.getTitle(), dvd);
        return newDVD;
    }

    @Override
    public List<DVD> getAllDVDs() throws DVDLibraryDaoException 
    {
        return new ArrayList<DVD>(dvds.values());
    }

    @Override
    public DVD getDVD(String title) throws DVDLibraryDaoException 
    {
        DVD dvd = dvds.get(title);
        if (dvd == null)
        {
            throw new DVDLibraryDaoException("DVD does not exist.");
        }
        return dvd;
    }

    @Override
    public List<DVD> dvdsReleasedNumYears(int numYears) 
    {
        LocalDate ldNumYears = LocalDate.now().minusYears(numYears);
        
        List<DVD> releaseDateNumYears = dvds.values().stream()
                                                 .filter(dvd -> dvd.getReleaseDate().isAfter(ldNumYears))
                                                 .collect(Collectors.toList());
        
        return releaseDateNumYears;
    }

    @Override
    public List<DVD> dvdsByMpaaRating(String mpaaRating) 
    {
        List<DVD> dvdsByMpaaRating = dvds.values().stream()
                                                  .filter(dvd -> dvd.getMpaaRating().equals(mpaaRating))
                                                  .collect(Collectors.toList());
        return dvdsByMpaaRating;
    }

    @Override
    public List<DVD> dvdsByDirector(String director) 
    {
        List<DVD> dvdsByDirector = dvds.values().stream()
                                                .filter(dvd -> dvd.getDirectorName().equals(director))
                                                .sorted(Comparator.comparing(DVD::getMpaaRating))
                                                .collect(Collectors.toList());
        return dvdsByDirector;
    }

    @Override
    public List<DVD> dvdsByStudio(String studio) 
    {
        List<DVD> dvdsByStudio = dvds.values().stream()
                                     .filter(dvd -> dvd.getStudio().equals(studio))
                                     .collect(Collectors.toList());
        return dvdsByStudio;
    }

    @Override
    public Double averageAgeDVDs() 
    {
        double thisYear = LocalDate.now().getYear();
        double averageAge = dvds.values().stream()
                                      .mapToDouble(dvd -> dvd.getReleaseDate().getYear())
                                      .average().getAsDouble();
        return thisYear-averageAge;
    }

    @Override
    public DVD newestDVD() 
    {
       DVD newestDVD = dvds.values().stream()
                                    .sorted(Comparator.comparing(d -> d.getReleaseDate().getYear()))
                                    .collect(Collectors.toList()).get(dvds.values().size()-1);
       return newestDVD;
    }

    @Override
    public DVD oldestDVD() 
    {
        DVD oldestDVD = dvds.values().stream()
                                    .sorted(Comparator.comparing(d -> d.getReleaseDate().getYear()))
                                    .collect(Collectors.toList()).get(0);
        return oldestDVD;
    }
}
