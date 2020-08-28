package com.ioc.instance;

import com.ioc.anno.Automatic;
import com.ioc.info.ClassInfo;
import com.ioc.ves.IocVessel;
import com.ioc.ves.VesselRelation;
import com.util.ReflectUtil;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

/**
 * 注入工程中所有类标识自动注入的属性
 */
public class AutomaticProperty {

    private static AutomaticProperty automaticProperty = new AutomaticProperty();
    private final IocVessel iocVessel = IocVessel.getInstance();
    private final Map<String, ClassInfo> vessel1 = iocVessel.getVessel();

    public final void run() throws Exception {

        Set<String> keys = vessel1.keySet();
        for(String key : keys) {
            this.automaticProperty(key);
        }

    }

    /**
     * 注入组件属性
     * @param key
     */
    private void automaticProperty(String key) throws Exception {
        ClassInfo info = vessel1.get(key);
        if(null != info) {
            Class<?> cls = info.getCls();

            autoFieldValue(cls, info.getObject());
        }
    }

    /**
     * obj 类中注入组件属性
     * @param cls
     * @param obj
     * @throws Exception
     */
    public static void autoFieldValue(Class<?> cls, Object obj) throws Exception {
        //扫描其需要自动注入的组件属性
        Field[] fields = cls.getDeclaredFields();
        for(Field field : fields) {
            Automatic auto = field.getAnnotation(Automatic.class);
            if(null != auto) {
                String propertyName = field.getName();
                String iocKey = auto.name();
                try {
                    if("".equals(iocKey)) {
                        //获取定义类型
                        String propertyType = field.getType().getName();
                        Set<String> iocKeys = VesselRelation.getInstance().getIocKey(propertyType);
                        if(null == iocKeys || iocKeys.isEmpty()) {
                            throw new Exception("Automatic failed : " + cls.getName() + '.' + propertyName);
                        }
                        if(iocKeys.size() > 1) {
                            throw new Exception("Automatic failed : " + cls.getName() + '.' + propertyName + iocKeys);
                        }
                        iocKey = iocKeys.toArray()[0].toString();
                    }
                    ReflectUtil.setFieldValue(obj, field, IocVessel.getInstance().getObject(iocKey));
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("Automatic failed : " + cls.getName() + '.' + propertyName);
                }

            }
        }
    }

    public static AutomaticProperty getInstance() {
        return automaticProperty;
    }
}
