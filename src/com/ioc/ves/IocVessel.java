package com.ioc.ves;

import com.ioc.info.ClassInfo;
import com.ioc.info.InstanceType;
import com.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * IOC 容器。单例
 */
public final class IocVessel {

    private Object application;     //程序执行入口
    private Map<String, ClassInfo> vessel;
    private static IocVessel iocVessel = new IocVessel();

    private IocVessel() {
        this.vessel = new HashMap<String, ClassInfo>();
    }

    /**
     * 获取实例
     * @param key
     * @param <T>
     * @return
     */
    public <T> T getObject(String key) {
        Object obj = this.vessel.get(key).getObject();

        return null != obj?(T)obj:null;
    }

    /**
     * 获取class信息
     * @param key
     * @return
     */
    public ClassInfo getClassInfo(String key) {
        return this.vessel.get(key);
    }

    /**
     * 指定key -> info添加
     * @param key
     * @param info
     * @throws Exception
     */
    public void addClass(String key, ClassInfo info) throws Exception {
        ClassInfo _info = this.getClassInfo(key);
        if(null == _info) {
            this.vessel.put(key, info);
        } else {
            System.err.println(" error msg : please choose one in [" + _info.getClassPath() + "] and [" + info.getClassPath() + "] change the component name");
            throw new Exception(" Failed to initialize the component... ");
        }
    }

    public static IocVessel getInstance() {

        return iocVessel;
    }

    public Map<String, ClassInfo> getVessel() {
        return vessel;
    }

    public Object getApplication() {
        return application;
    }

    public void setApplication(Object application) {
        this.application = application;
    }
}
