package com.ioc.instance;

import com.ioc.info.ClassInfo;
import com.ioc.ves.IocVessel;
import com.ioc.ves.VesselRelation;

import java.util.Map;
import java.util.Set;

/**
 * 完成实例化组件工作
 */
final public class InstanceComponent {

    private static InstanceComponent instanceComponent = new InstanceComponent();
    private final IocVessel iocVessel = IocVessel.getInstance();
    private final Map<String, ClassInfo> vessel1 = iocVessel.getVessel();
    private final VesselRelation rel = VesselRelation.getInstance();

    private InstanceComponent(){}

    public final void run() throws Exception {

        Set<String> keys = vessel1.keySet();
        //先实例化全部组件
        for(String key : keys) {
            this.instance(key);
        }
    }

    /**
     * 实例化
     * @param key
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    protected void instance(final String key) throws Exception {

        ClassInfo info = vessel1.get(key);
        try{
            if(null != info) {
                Class<?> cls = info.getCls();

                Object obj = cls.newInstance();
                System.out.println("INFO : instance class -> " + obj);

                info.setObj(obj);

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("instance [" + info.getClassPath() + "] failed...");
        }
    }

    public static InstanceComponent getInstance() {
        return instanceComponent;
    }
}
