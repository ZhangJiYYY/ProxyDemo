package com.aop.info;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ProxyInfo {

    private static ProxyInfo proxyInfo = new ProxyInfo();

    /*
    记录切面与接口的对应关系，key->IOC容器中的标识，values->对应的接口集合
     */
    private Map<String, Set<InterfaceInfo>> interfaceInfos;

    private ProxyInfo() {
        this.interfaceInfos = new HashMap<>();
    }

    public Map<String, Set<InterfaceInfo>> getInterfaceInfos() {
        return interfaceInfos;
    }

    public void setInterfaceInfos(Map<String, Set<InterfaceInfo>> interfaceInfos) {
        this.interfaceInfos = interfaceInfos;
    }

    public void initAspectInfo(String aspect) throws Exception {
        Set<InterfaceInfo> infos = this.interfaceInfos.get(aspect);
        if(null == infos) {
            infos = new HashSet<>();
            this.interfaceInfos.put(aspect, infos);
        } else {
            throw new Exception("aspect [" +aspect + "] is exists...");
        }
    }

    public void addInterfaceInfo(String aspect, String interfacePath) {
        Set<InterfaceInfo> infos = this.interfaceInfos.get(aspect);
        if(null == infos) {
            infos = new HashSet<>();
            this.interfaceInfos.put(aspect, infos);
        }

        infos.add(new InterfaceInfo(interfacePath));
    }

    public void addInterfaceInfo(String aspect, InterfaceInfo info) {
        Set<InterfaceInfo> infos = this.interfaceInfos.get(aspect);
        if(null == infos) {
            infos = new HashSet<>();
            this.interfaceInfos.put(aspect, infos);
        }

        infos.add(info);
    }

    @Override
    public String toString() {
        return "ProxyInfo{" +
                ", interfaceInfos=" + interfaceInfos +
                '}';
    }

    public static ProxyInfo getInstance() {
        return proxyInfo;
    }
}
