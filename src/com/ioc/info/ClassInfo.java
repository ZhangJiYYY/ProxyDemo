package com.ioc.info;

/**
 * Class信息
 */
public class ClassInfo {
    private String classPath;
    private Object obj;
    private Class<?> cls = null;
    
    public ClassInfo(String classPath) {
        this.classPath = classPath;
    }

    public ClassInfo(String classPath, Object obj) {
        this.classPath = classPath;
        this.obj = obj;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public Object getObject(){

        return this.obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Class<?> getCls() {
        return cls;
    }

    public void setCls(Class<?> cls) {
        this.cls = cls;
    }
}
