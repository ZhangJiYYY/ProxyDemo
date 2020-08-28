package com.file;

import com.util.StringUtil;

import java.io.File;
import java.io.FileFilter;

/**
 * 自定义文件过滤器
 */
public class DefaultFileFilter implements FileFilter {

    /**
     * 文件名验证表达式
     */
    private String reg;
    /**
     * 后缀名,不需要(.)。如[doc, docx]
     */
    private String[] suffix;

    public DefaultFileFilter() {

    }

    public DefaultFileFilter(String reg) {
        this.reg = reg;
    }

    public DefaultFileFilter(String reg, String[] suffix) {
        this.reg = reg;
        this.suffix = suffix;
    }

    /**
     * 文件名验证表达式
     */
    public String getReg() {
        return reg;
    }

    /**
     * 文件名验证表达式
     */
    public void setReg(String reg) {
        this.reg = reg;
    }

    /**
     * 后缀名数组
     */
    public String[] getSuffix() {
        return suffix;
    }

    /**
     * 后缀名,不需要(.)。如[doc, docx]
     * @param suffix
     */
    public void setSuffix(String[] suffix) {
        this.suffix = suffix;
    }

    @Override
    public boolean accept(File pathname) {
        if(pathname.isDirectory()) {
            return false;
        }

        String fileName = pathname.getName();
        if(!StringUtil.isEmpty(this.reg)) {
            if(!StringUtil.isIncludeStr(fileName, this.reg, 2)) {
                return false;
            }
        }
        if(null != this.suffix && this.suffix.length > 0) {
            String fileSuffix = fileName.substring(fileName.lastIndexOf('.')+1);
            for(String _suffix : suffix) {
                if(!_suffix.equalsIgnoreCase(fileSuffix)) {
                    return false;
                }
            }
        }

        return true;
    }
}
