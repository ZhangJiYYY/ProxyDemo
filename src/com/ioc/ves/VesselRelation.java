package com.ioc.ves;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 记录接口、抽象类、组件与IocVessel -> key的关系
 */
public final class VesselRelation {

    private Map<Integer, Set<String>> rel = null;

    private static VesselRelation ourInstance = new VesselRelation();

    public static VesselRelation getInstance() {
        return ourInstance;
    }

    private VesselRelation() {
        rel = new HashMap<>();
    }

    public Map<Integer, Set<String>> getRel() {
        return rel;
    }

    public void addRel(String classPath, String iocKey) {
        int key = classPath.hashCode();
        Set<String> set = rel.get(key);
        if(null == set) {
            set = new HashSet<>();
            set.add(iocKey);

            rel.put(key, set);
        } else {
            set.add(iocKey);
        }
    }

    public Set<String> getIocKey(String classPath) {

        return rel.get(classPath.hashCode());
    }
}
