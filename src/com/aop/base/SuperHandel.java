package com.aop.base;

import com.aop.info.InterfaceInfo;
import com.aop.info.ProxyInfo;
import com.aop.ves.ProxyRelation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Set;

public abstract class SuperHandel implements InvocationHandler {

    private Object target;  //被代理对象

    public void setTarget(Object target) {
        this.target = target;
    }

    public abstract void before(Method method, Object[] args);

    public abstract void after(Method method, Object[] args);

    public abstract void throwable(Method method, Object[] args);

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        try {
            Object returnValue = null;

            String methodName = method.getName();
            //判断方法是否需要代理执行
            String aspectKey = ProxyRelation.getInstance().getAspectKey(this.target.getClass().getName());
            Set<InterfaceInfo> interfaceInfos = ProxyInfo.getInstance().getInterfaceInfos().get(aspectKey);
            boolean needProxy = false;
            if(!interfaceInfos.isEmpty()) {
                for(InterfaceInfo info : interfaceInfos) {
                    Set<String> methodNames = info.getMethodName();
                    for (String name : methodNames) {
                        if(methodName.equals(name)) {
                            needProxy = true;
                            break;
                        }
                    }
                    if(needProxy) {
                        break;
                    }
                }
            }
            if(needProxy) {
                this.before(method, args);

                if(!method.getReturnType().getName().equals("void")) {
                    returnValue = method.invoke(target, args);
                } else {
                    method.invoke(target, args);
                }

                this.after(method, args);
            } else {
                if(!method.getReturnType().getName().equals("void")) {
                    returnValue = method.invoke(target, args);
                } else {
                    method.invoke(target, args);
                }
            }

            return returnValue;
        } catch (Throwable t) {
            this.throwable(method, args);
            throw t;
        }

    }
}
