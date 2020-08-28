package com.test.handel;

import com.aop.anno.Aspect;
import com.aop.base.SuperHandel;

import java.lang.reflect.Method;

@Aspect( name = "runServiceHandel", execution = "com.test.service", method = {"run*", "add*", "test*"})
public class RunServiceHandel extends SuperHandel {

    @Override
    public void setTarget(Object target) {
        super.setTarget(target);
    }

    @Override
    public void before(Method method, Object[] args) {
        System.out.println("method [ " + method.getName() + " ] start execute...");
    }

    @Override
    public void after(Method method, Object[] args) {
        System.out.println("method [ " + method.getName() + " ] execute finished...");
    }

    @Override
    public void throwable(Method method, Object[] args) {
        System.out.println("method [ " + method.getName() + " ] execute exception...");
    }

}
