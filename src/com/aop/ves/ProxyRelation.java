package com.aop.ves;

import java.util.HashMap;
import java.util.Map;

/**
 * 记录被代理类与接口的关系
 */
public class ProxyRelation {

    private static ProxyRelation proxyRelation = new ProxyRelation();

    private Map<Integer, String> rel; //key->被代理类全路径hashCode, values->切面

    private ProxyRelation() {
        rel = new HashMap<>();
    }

    public void add(String pac, String aspect) {
        int key = pac.hashCode();
        if(null == this.rel.get(key)) {
            this.rel.put(key, aspect);
        }
    }

    public String getAspectKey(String pac) {
        int key = pac.hashCode();
        return this.rel.get(key);
    }

    public static ProxyRelation getInstance(){
        return proxyRelation;
    }
}
