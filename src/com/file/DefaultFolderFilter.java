package com.file;

import com.util.StringUtil;

import java.io.File;
import java.io.FileFilter;

/**
 * 自定义文件夹过滤器
 */
public class DefaultFolderFilter implements FileFilter {

    private String reg;

    public DefaultFolderFilter() {
    }

    public DefaultFolderFilter(String reg) {
        this.reg = reg;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    @Override
    public boolean accept(File pathname) {
        if(!pathname.isDirectory()) {
            return false;
        }

        String fileName = pathname.getName();
        if(!StringUtil.isEmpty(this.reg)) {
            return StringUtil.isIncludeStr(fileName, this.reg, 2);
        }

        return true;
    }
}
