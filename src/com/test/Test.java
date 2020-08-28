package com.test;

import com.test.handel.RunServiceHandel;
import com.test.service.IRunService;
import com.test.service.imp.RunServiceImp;

import java.lang.reflect.Proxy;

public class Test {

    public static void main(String[] args) {
        IRunService runService = new RunServiceImp();

        RunServiceHandel handel = new RunServiceHandel();

        handel.setTarget(runService);

        IRunService run = (IRunService) Proxy.newProxyInstance(IRunService.class.getClassLoader(), runService.getClass().getInterfaces(), handel);

        System.out.println(run.addObj("p:test str..."));
//-------------------------------------------------------------------------------------------------------
//        String pacs = "com.service.imp";
//
//        ScanObject scanObject = new ScanObject(pacs);
//        scanObject.doScan();
//        List<String> pacLists = scanObject.getClassLists();
//
//        System.out.println("\n\nscan class : ");
//        PrintUtil.printList(pacLists, true);
//
//        System.out.println("*******************************");
//        System.out.println(".......Start IOC Test......\n");
//        IocVessel ioc = IocVessel.getIocVessel();
//
//        IRunService run = ioc.getObject("runServiceImp");
//
//        System.out.println(run.addObj("test str..."));
//
//        System.out.println("\n.......Finish IOC Test......");
//        System.out.println("*******************************");

//-------------------------------------------------------------------------------------------------------
//        try{
//
//            Class<?> daoCls = Class.forName("com.dao.imp.RunDaoImp");
//            IRunDao dao = (RunDaoImp)daoCls.newInstance();
//
//            Class<?> serviceCls = Class.forName("com.service.imp.RunServiceImp");
//            IRunService service = (RunServiceImp)serviceCls.newInstance();
//
//            //扫描其需要自动注入的组件属性
//            Field field = serviceCls.getDeclaredField("runDao");
//            field.setAccessible(true);
//            field.set(service, dao);
//
//            service.runTest("main test");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//-------------------------------------------------------------------------------------------------------
//        try{
//            Class<?> cls = Class.forName("com.service.imp.RunServiceImp");
//            Component component = cls.getAnnotation(Component.class);
//            System.out.println(component.name());
//
//            //扫描其需要自动注入的组件属性
//            Field[] fields = cls.getDeclaredFields();
//            System.out.println("fields : " + fields.length);
//            for(Field field : fields) {
//                String propertyName = field.getName();
//                Automatic auto = field.getAnnotation(Automatic.class);
//                System.out.println("auto : " + auto);
//                if(null != auto) {
//
//                    System.out.println("propertyName : " + propertyName);
//                    String comFlag = auto.name();
//                    System.out.println("comFlag : " + comFlag);
//
//                    System.out.println("field type : " + field.getType().getName());
//
//
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//-------------------------------------------------------------------------------------------------------

//        String basePath = Main.class.getClass().getResource("/").getPath().substring(1);
//        System.out.println(basePath);
//        File f = new File("G:\\Workspace\\IDEA\\ProxyDemo\\out\\production\\ProxyDemo\\com\\service");
//
//        String pac = "com.**.Imp";
//
//        String reg = StringUtil.filterStr(pac, "/", "\\.", 1);
//        reg = StringUtil.filterStr(reg, ".*", "\\*\\*|\\*", 1);
//        System.out.println("reg : " + reg);
//
//        File[] files = f.listFiles(new DefaultFolderFilter());
//        for(File _f : files) {
//            String basePacPath = StringUtil.filterStr(_f.getAbsolutePath(), "/", "\\\\", 1).substring(basePath.length());
//            String basePac = StringUtil.filterStr(basePacPath, ".", "/", 1);
//            System.out.println(basePacPath + " --- " + basePac);
//
//            System.out.println(StringUtil.isIncludeStr(basePacPath, reg, 2));
//        }

//-------------------------------------------------------------------------------------------------------
//        String pac = "com.**.imp";
//
//        ScanObject scanObject = new ScanObject(pac);
//
//        scanObject.run();    //启动扫描
//
//        Map<String, ClassInfo> vessel = IocVessel.getInstance().getVessel();
//        System.out.println("Component : ");
//        PrintUtil.printMap(vessel, true);
//
//        Map<Integer, Set<String>> rel = VesselRelation.getInstance().getRel();
//        System.out.println(" VesselRelation : ");
//        System.out.println("---------------------------------------------\n");
//        Set<Integer> set = rel.keySet();
//        for(Integer key : set) {
//            System.out.println("[key : " + key + " ---> value : " + rel.get(key));
//        }
//        System.out.println("\n---------------------------------------------");

//-------------------------------------------------------------------------------------------------------
//        int key = "com.service.IRunService".hashCode();
//        Set<String> set = new HashSet<>();
//        set.add("runServiceImp");
//        System.out.println(set.size());
//        set.add("runServiceImp");
//        System.out.println(set.size());

//        Set<String> set = new HashSet<>();
//        set.add("123");
//        set.add("2354");
//
//        System.out.println(set);
    }
}
