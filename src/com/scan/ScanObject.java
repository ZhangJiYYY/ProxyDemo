package com.scan;

import com.aop.anno.Aspect;
import com.aop.info.InterfaceInfo;
import com.aop.info.ProxyInfo;
import com.ioc.anno.Component;
import com.file.DefaultFolderFilter;
import com.ioc.info.ClassInfo;
import com.ioc.info.PackageInfo;
import com.ioc.info.ScanInfo;
import com.ioc.ves.IocVessel;
import com.ioc.ves.VesselRelation;
import com.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.List;

/**
 * 扫描指定路径下的实现类
 * 根据条件进行代理
 */
public class ScanObject {

    private ScanInfo scanInfo;  //记录扫描包级包内类信息

    private ClassLoader loader;

    private String basePath;    //G:/Workspace/IDEA/ProxyDemo/out/production/ProxyDemo/

    private boolean needScan = false;   //是否需要扫描

    private boolean ignoreCase = true;  //扫描包忽略大小写
    private int ignoreCaseInt = 2;

    private ScanType scanType = ScanType.COMPONENT;    //扫描类型，默认扫描Class

    /**
     * 唯一构造器
     * @param scanPackage 扫描包路径，多个用";"分割
     */
    public ScanObject(String scanPackage) {

        basePath = this.getClass().getResource("/").getPath().substring(1);

        this.setScanPackage(scanPackage);
        loader = this.getClass().getClassLoader();
    }

    /**
     * 设置扫描包路径
     * @param scanPackage 扫描包路径，多个用";"分割
     */
    public void setScanPackage(String scanPackage) {
        if(null == scanPackage || scanPackage.length() == 0) {
            return ;
        }
        this.needScan = true;

        String[] scanPacArray = StringUtil.toArray(scanPackage, ";");
        this.scanInfo = new ScanInfo();
        for(String pac : scanPacArray) {
            this.handlePac(pac);
        }

    }

    /**
     * 处理需要扫描的包
     * @param pac
     */
    private void handlePac(String pac) {

        int oneIndex = pac.indexOf(".*");
        if(oneIndex > -1) {
            String reg = StringUtil.filterStr(pac, "/", "\\.", 1);
            reg = StringUtil.filterStr(reg, ".*", "\\*\\*|\\*", 1);

            String basePacPath = StringUtil.dotToSplash(pac.substring(0, oneIndex));

            File file = new File(this.basePath + basePacPath);
            if(!file.exists() || !file.isDirectory()) {
                return ;
            }

            this.handlePac(file, reg);
        } else {
            String basePacPath = StringUtil.dotToSplash(pac);
            this.scanInfo.addPackageInfo(new PackageInfo(pac, basePacPath));
        }

    }

    private void handlePac(File file, String reg) {
        File[] files = file.listFiles(new DefaultFolderFilter());
        for(File f : files) {
            if(f.listFiles(new DefaultFolderFilter()).length == 0) {
                String basePacPath = StringUtil.filterStr(f.getAbsolutePath(), "/", "\\\\", 1).substring(basePath.length());
                if(!StringUtil.isIncludeStr(basePacPath, reg, this.ignoreCaseInt)) {
                    continue;
                }
                String basePac = StringUtil.filterStr(basePacPath, ".", "/", 2);

                System.out.println("INFO : handle package -> " + basePac);
                this.scanInfo.addPackageInfo(new PackageInfo(basePac, basePacPath));
            } else {
                this.handlePac(f, reg);
            }
        }

    }

    public String getBasePath() {
        return basePath;
    }

    public List<String> getClassLists() {
        return needScan?this.scanInfo.getClassList():null;
    }

    /**
     * 启动扫描
     */
    public void run() {
        if(!this.needScan) {
            return ;
        }

        try {
            for(PackageInfo pac : this.scanInfo.getPackageInfoList()) {
                this.scan(pac);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 扫描实际工作
     * @param pac
     * @throws Exception
     */
    private void scan(PackageInfo pac) throws Exception {

        try{
            URL url = loader.getResource(pac.getBasePacPath());
            String filePath = StringUtil.getRootPath(url);

            if (isJarFile(filePath)) {// 先判断是否是jar包，如果是jar包，通过JarInputStream产生的JarEntity去递归查询所有类
                // TODO handle JAR
                //readFromJarFile(filePath, pac.getBasePacPath());
            } else {

                readFromDirectory(filePath, pac);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("not found package : " + pac.getBasePac());
        }

    }

//    private List<String> readFromJarFile(String jarPath, String splashedPackageName) throws IOException {
//
//        JarInputStream jarIn = new JarInputStream(new FileInputStream(jarPath));
//        JarEntry entry = jarIn.getNextJarEntry();
//        List<String> nameList = new ArrayList<String>();
//        while (null != entry) {
//            String name = entry.getName();
//            if (name.startsWith(splashedPackageName) && isClassFile(name)) {
//                nameList.add(name);
//            }
//
//            entry = jarIn.getNextJarEntry();
//        }
//
//        return nameList;
//    }

    /**
     * 扫描本地配置类
     * @param filePath
     * @param pac
     * @throws Exception
     */
    private void readFromDirectory(String filePath, PackageInfo pac) throws Exception {
        System.out.println("INFO : scan package -> " + pac.getBasePac());
        File file = new File(filePath);
        String[] names = file.list();

        for(String name : names) {
            String className = judgeFile(name);
            if(null != className) {
                try{
                    String classPath = pac.getBasePac() + '.' +className;
                    Class<?> cls = Class.forName(classPath);

                    if(ScanType.COMPONENT == this.scanType) {
                        //获取Component注解
                        Component annotation = cls.getAnnotation(Component.class);
                        if(null == annotation) {
                            continue;
                        }

                        try {
                            String key = annotation.value();
                            if("".equals(key)) {
                                key = annotation.name();
                            }
                            key = StringUtil.isEmpty(key)?StringUtil.lowerFirst(className):key;

                            //管理其继承的接口、抽象类
                            Class<?>[] interfaces = cls.getInterfaces();
                            for (Class<?> intCls : interfaces) {
                                VesselRelation.getInstance().addRel(intCls.getName(), key);
                            }

                            ClassInfo info = new ClassInfo(classPath);
                            info.setCls(cls);
                            System.out.println("INFO : scan component -> " + classPath);

                            IocVessel.getInstance().addClass(key, info);
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw e;
                        }
                        pac.addClass(classPath);
                    }
                    if(ScanType.INTERFACE == this.scanType && cls.isInterface()) {  //扫描接口、抽象类
                        if(cls.isInterface() || Modifier.isAbstract(cls.getModifiers())) {
                            pac.addClass(classPath);
                        }
                    }
                    if(ScanType.ASPECT == this.scanType) {
                        Aspect aspect = cls.getAnnotation(Aspect.class);
                        if(null == aspect) {
                            continue;
                        }
                        String aspectName = aspect.value();
                        if("".equals(aspectName)) {
                            aspectName = aspect.name();
                        }
                        aspectName = StringUtil.isEmpty(aspectName)?StringUtil.lowerFirst(className):aspectName;

                        ProxyInfo.getInstance().initAspectInfo(aspectName);

                        ClassInfo info = new ClassInfo(classPath);
                        info.setCls(cls);
                        System.out.println("INFO : scan aspect -> " + classPath);

                        //添加到容器中，需要实例化
                        IocVessel.getInstance().addClass(aspectName, info);

                        pac.addClass(classPath);

                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String judgeFile(String name) {
        int dotIndex = name.lastIndexOf('.');
        if(-1 == dotIndex) {
            return null;
        }

        String suffix = name.substring(dotIndex + 1);
        if(!"class".equalsIgnoreCase(suffix)) {
            return null;
        }

        return name.substring(0, dotIndex);
    }

    private boolean isClassFile(String name) {
        return name.endsWith(".class");
    }

    private boolean isJarFile(String name) {
        return name.endsWith(".jar");
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
        this.ignoreCaseInt = ignoreCase?2:1;
    }

    public void setScanType(ScanType scanType) {
        this.scanType = scanType;
    }
}
