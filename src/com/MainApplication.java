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

        System.out.println("\n\nMainApplication run result :");
        application.runTest("MainApplication run test...");

        System.out.println("\n\nnMainApplication runProxyTest: ");
        application.runProxyTest();

        System.out.println("\n\nMainApplication run not proxy result :");
        application.runService.delObj("MainApplication run not proxy ...");
    }

    public void runTest(String msg) {
        this.runService.runTest(msg);
    }

    public void runProxyTest() {
        this.testService.testFun();
    }
}
