package com.ioc.info;

import java.util.ArrayList;
import java.util.List;

public class PackageInfo {

    private String basePac; //包路径（com.test.pac）
    private String basePacPath; //包绝对路径（com/test/pac）

    List<String> classs;

    private PackageInfo() {
        this.classs = new ArrayList<>();
    }

    public PackageInfo(String basePac, String basePacPath) {
        this();
        this.basePac = basePac;
        this.basePacPath = basePacPath;
    }

    public String getBasePac() {
        return basePac;
    }

    public void setBasePac(String basePac) {
        this.basePac = basePac;
    }

    public String getBasePacPath() {
        return basePacPath;
    }

    public void setBasePacPath(String basePacPath) {
        this.basePacPath = basePacPath;
    }

    public List<String> getClasss() {
        return classs;
    }

    private void setClasss(List<String> classs) {
        this.classs = classs;
    }

    /**
     * 只需传入Class类名
     * @param className
     */
    public void addClass(String className) {
        this.classs.add(basePac + '.' +className);
    }

    @Override
    public String toString() {
        return "PackageInfo{" +
                "basePac='" + basePac + '\'' +
                ", basePacPath='" + basePacPath + '\'' +
                ", classs=" + classs +
                '}';
    }
}
