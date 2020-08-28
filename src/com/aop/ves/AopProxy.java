package com.aop.ves;

import com.aop.anno.Aspect;
import com.aop.base.SuperHandel;
import com.aop.info.InterfaceInfo;
import com.aop.info.ProxyInfo;
import com.ioc.info.ClassInfo;
import com.ioc.ves.IocVessel;
import com.ioc.ves.VesselRelation;
import com.scan.ScanObject;
import com.util.StringUtil;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Set;

final public class AopProxy {

    private static AopProxy aopProxy = new AopProxy();

    private Map<String, Set<InterfaceInfo>> interfaceInfos = null;

    private AopProxy() {

    }

    /**
     * 启动代理扫描
     */
    public void run() throws Exception {
        this.interfaceInfos = ProxyInfo.getInstance().getInterfaceInfos();

        Set<String> aspect = this.interfaceInfos.keySet();
        for(String key : aspect) {
            this.proxy(key);
        }
    }

    /**
     * 修改容器中被代理接口已实例化对象
     * @param aspectKey
     */
    private void proxy(String aspectKey) throws Exception {

        //切面类实例
        ClassInfo asInfo = IocVessel.getInstance().getClassInfo(aspectKey);
        Class<?> asCls = asInfo.getCls();
        Aspect aspect = asCls.getAnnotation(Aspect.class);

        // TODO 扫描其需要代理的接口
        String execution = aspect.execution();
        String executionPath = StringUtil.dotToSplash(execution);
        String basePath = this.getClass().getResource("/").getPath().substring(1);

        //获取切面类代理的接口
        Set<InterfaceInfo> interfaceInfos = this.interfaceInfos.get(aspectKey);
        if(new File(basePath + executionPath).isDirectory()) {
            // 代理多个接口
            this.scanInterface(aspect, basePath + executionPath, execution, interfaceInfos);
        } else {
            Class<?> cls = Class.forName(execution);

            if(cls.isInterface() || Modifier.isAbstract(cls.getModifiers())) {
                InterfaceInfo info = new InterfaceInfo(execution);
                this.handelMethod(aspect, info);
                interfaceInfos.add(info);
            }

        }

        if(!interfaceInfos.isEmpty()) {
            this.proxyObj(aspectKey, asCls, interfaceInfos);
        }

    }

    /**
     * 扫描包下的全部接口
     * @param aspect
     * @param filePath
     * @param pac
     * @param interfaceInfos
     * @throws Exception
     */
    private void scanInterface(Aspect aspect, String filePath, String pac, Set<InterfaceInfo> interfaceInfos) throws Exception {
        System.out.println("INFO : scan proxy package -> " + pac);
        File file = new File(filePath);
        String[] names = file.list();

        for(String name : names) {
            String className = ScanObject.judgeFile(name);
            if(null != className) {
                try{
                    String classPath = pac + '.' +className;
                    Class<?> cls = Class.forName(classPath);

                    if(cls.isInterface() || Modifier.isAbstract(cls.getModifiers())) {
                        InterfaceInfo info = new InterfaceInfo(classPath);
                        this.handelMethod(aspect, info);
                        interfaceInfos.add(info);
                    }

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void proxyObj(String aspectKey, Class<?> asCls, Set<InterfaceInfo> interfaceInfos) throws Exception {


        for(InterfaceInfo interInfo : interfaceInfos) {

            ClassLoader loader = Class.forName(interInfo.getPath()).getClassLoader();
            //获取接口的实现类
            Set<String> iocKeys = VesselRelation.getInstance().getIocKey(interInfo.getPath());
            for(String iocKey : iocKeys) {

                SuperHandel handel = (SuperHandel) asCls.newInstance();

                ClassInfo info = IocVessel.getInstance().getClassInfo(iocKey);

                //记录代理类与切面的关系
                ProxyRelation.getInstance().add(info.getClassPath(), aspectKey);

                Object obj = info.getObject();
                //进行代理
                handel.setTarget(obj);

                info.setObj(this.proxyObj(loader, obj, handel));

                System.out.println("INFO : proxy class -> " + info.getClassPath());
            }

        }
    }

    /**
     * 记录代理的方法
     * @param aspect
     * @param info
     * @throws ClassNotFoundException
     */
    private void handelMethod(Aspect aspect, InterfaceInfo info) throws ClassNotFoundException {
        Class<?> cls = Class.forName(info.getPath());

        String[] methodNames = aspect.method();
        String[] regs = new String[methodNames.length];
        for(int i=0; i<methodNames.length; i++) {
            regs[i] = StringUtil.filterStr(methodNames[i], ".*", "\\*", 1);
        }
        Method[] methods = cls.getMethods();
        for(Method method : methods) {
            for(String reg : regs) {
                if(StringUtil.isIncludeStr(method.getName(), reg, 2)) {
                    info.addMethod(method.getName());
                }
            }
        }
    }

    /**
     * 修改被代理修改容器中被代理接口实现类
     * @param obj
     * @param handel
     * @return
     */
    private Object proxyObj(ClassLoader loader, Object obj, SuperHandel handel) {
        return Proxy.newProxyInstance(loader, obj.getClass().getInterfaces(), handel);
    }

    public static AopProxy getInstance() {
        return aopProxy;
    }
}
