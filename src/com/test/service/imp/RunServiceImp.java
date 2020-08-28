package com.test.service.imp;

import com.ioc.anno.Automatic;
import com.ioc.anno.Component;
import com.test.dao.IRunDao;
import com.test.service.IRunService;

@Component("runServiceImp")
public class RunServiceImp implements IRunService {

    @Automatic
    private IRunDao runDao;

    @Override
    public String runTest(String msg) {

        runDao.runDao(msg);

        System.out.println("runTest MSG ： " + msg);

        return "runTest";
    }

    @Override
    public String runTestTwo(String msg) {
        System.out.println("runTestTwo MSG ： " + msg);

        return "runTestTwo";
    }

    @Override
    public String saveObj(String obj) {
        System.out.println("saveObj MSG ： " + obj);

        return "saveObj";
    }

    @Override
    public String addObj(String obj) {
        System.out.println("addObj MSG ： " + obj);

        return "addObj";
    }

    @Override
    public String delObj(String obj) {
        System.out.println("delObj MSG ： " + obj);

        return "delObj";
    }
}
