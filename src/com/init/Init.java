package com.init;

import com.aop.ves.AopProxy;
import com.ioc.instance.AutomaticProperty;
import com.ioc.instance.InstanceComponent;
import com.scan.ScanObject;
import com.ioc.ves.IocVessel;
import com.scan.ScanType;

final public class Init {

    /**
     * 工程跟路径
     */
    private static String ROOT_PAC = "com.**";

    /**
     * 组件扫描包
     */
    private static String COM_SCAN_PAC = "com.test.*.imp";

    /**
     * 切面扫描包
     */
    private static String ASPECT_SCAN_PAC = "com.test.handel";

    /**
     * 程序执行入口类
     */
    private static String MAIN_CLASS_PATH = "com.MainApplication";

    private static Init init = new Init();

    private Init(){}

    /**
     * 启动初始化
     */
    public void init() throws Exception {

        try {
            //扫描组件
            ScanObject comScan = new ScanObject(COM_SCAN_PAC);
            comScan.run();

            //扫描切面
            ScanObject aspectScan = new ScanObject(ASPECT_SCAN_PAC);
            aspectScan.setScanType(ScanType.ASPECT);
            aspectScan.run();

            //组件扫描完成，实例化组件
            InstanceComponent.getInstance().run();

            //自动注入工程组件中对应组件属性
            AutomaticProperty.getInstance().run();

            //进行动态代理
            AopProxy.getInstance().run();

            //实例化程序执行入口
            this.initApplication();
        } catch (Exception e) {
           e.printStackTrace();
           throw new Exception("Init failed!!!");
        }
    }

    private void initApplication() throws Exception{
        //自动注入程序执行入口类组件
        Class<?> applicationCls = Class.forName(Init.getMainClassPath());
        Object application = applicationCls.newInstance();

        AutomaticProperty.autoFieldValue(applicationCls, application);

        IocVessel.getInstance().setApplication(application);
    }

    public static Init getInstance(){

        return init;
    }

    public static String getMainClassPath() {
        return MAIN_CLASS_PATH;
    }
}
