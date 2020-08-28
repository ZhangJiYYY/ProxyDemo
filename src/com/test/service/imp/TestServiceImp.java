package com.test.service.imp;

import com.ioc.anno.Component;
import com.test.service.ITestService;

@Component("testServiceImp")
public class TestServiceImp implements ITestService {

    @Override
    public void testFun() {

        System.out.println("TestServiceImp testFun execute...");
    }
}
