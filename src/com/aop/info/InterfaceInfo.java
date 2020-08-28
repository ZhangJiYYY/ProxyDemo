package com.aop.info;

import java.util.HashSet;
import java.util.Set;

/**
 * 记录接口信息以及接口中被代理方法
 */
public class InterfaceInfo {
    private String path;

    private Set<String> methodName;

    private InterfaceInfo() {
    }

    public InterfaceInfo(String path) {
        this.methodName = new HashSet<>();
        this.path = path;
    }

    public void addMethod(String methodName) {
        this.methodName.add(methodName);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Set<String> getMethodName() {
        return methodName;
    }

    public void setMethodName(Set<String> methodName) {
        this.methodName = methodName;
    }

    @Override
    public String toString() {
        return "InterfaceInfo{" +
                "path='" + path + '\'' +
                ", methodName=" + methodName +
                '}';
    }
}
