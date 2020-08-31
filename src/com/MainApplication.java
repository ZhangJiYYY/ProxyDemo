package com;

import com.ioc.anno.Automatic;
import com.init.Init;
import com.ioc.ves.IocVessel;
import com.test.service.IRunService;
import com.test.service.ITestService;

public class MainApplication {

    @Automatic
    private IRunService runService;

    @Automatic
    private ITestService testService;

    public static void main(String[] args) throws Exception {

        Init.getInstance().init();

        MainApplication application = (MainApplication) IocVessel.getInstance().getApplication();

        System.out.println("\n\nrun result :");
        application.runTest("MainApplication run test...");

        System.out.println("\n\nrunProxyTest c: ");
        application.runProxyTest();

        System.out.println("\n\nrun not proxy result :");
        application.runService.delObj("MainApplication run run not proxy...");
        
        System.out.println("\n\nrun not proxy two result :");
        application.runService.delObj("MainApplication run run not proxy two...");
    }

    public void runTest(String msg) {
        this.runService.runTest(msg);
    }

    public void runProxyTest() {
        this.testService.testFun();
    }
}
