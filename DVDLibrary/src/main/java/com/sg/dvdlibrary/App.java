/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvdlibrary;

import com.sg.dvdlibrary.controller.DVDLibraryController;
import com.sg.dvdlibrary.dao.DVDLibraryDao;
import com.sg.dvdlibrary.dao.DVDLibraryDaoFileImpl;
import com.sg.dvdlibrary.dao.DVDLibraryLambdaStreamAndAggregateDao;
import com.sg.dvdlibrary.dao.DVDLibraryLambdaStreamAndAggregateDaoFileImpl;
import com.sg.dvdlibrary.ui.DVDLibraryView;
import com.sg.dvdlibrary.ui.UserIO;
import com.sg.dvdlibrary.ui.UserIOConsoleImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 *
 * @author nicoleozkan
 */
public class App 
{
    public static void main(String[] args) 
    {
        // UserIO myIo = new UserIOConsoleImpl();
        // DVDLibraryView myView = new DVDLibraryView(myIo);
        // DVDLibraryDao myDao = new DVDLibraryDaoFileImpl();
        // DVDLibraryLambdaStreamAndAggregateDao mySecondDao = new DVDLibraryLambdaStreamAndAggregateDaoFileImpl();
        // DVDLibraryController controller = new DVDLibraryController(myDao, myView, mySecondDao);
        // controller.run();
        
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        DVDLibraryController controller = ctx.getBean("controller", DVDLibraryController.class);
        controller.run();
    }
}
