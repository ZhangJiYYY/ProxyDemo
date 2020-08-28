package com.ioc.info;

import java.util.ArrayList;
import java.util.List;

public class ScanInfo {

    private List<PackageInfo> packageInfoList;

    public ScanInfo() {
        this.packageInfoList = new ArrayList<>();
    }

    public List<PackageInfo> getPackageInfoList() {
        return packageInfoList;
    }

    public void addPackageInfo(PackageInfo pac) {
        this.packageInfoList.add(pac);
    }

    public List<String> getClassList() {
        List<String> classList = new ArrayList<>();
        for (PackageInfo pac : this.packageInfoList) {
            if(null != pac.getClasss() && !pac.getClasss().isEmpty()) {
                classList.addAll(pac.getClasss());
            }
        }
        return classList;
    }

    @Override
    public String toString() {
        return "ScanInfo{" +
                "packageInfoList=" + packageInfoList +
                '}';
    }
}
