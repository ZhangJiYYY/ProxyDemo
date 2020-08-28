package com.test.dao.imp;

import com.ioc.anno.Component;
import com.test.dao.IRunDao;

@Component(name = "runDaoImp")
public class RunDaoImp implements IRunDao {

    @Override
    public void runDao(String msg) {

        System.out.println("RunDaoImp.runDao get MSG : " + msg);
    }
}
