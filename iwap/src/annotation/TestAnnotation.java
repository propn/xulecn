package annotation;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class TestAnnotation {    
       public static void main(String[] args) throws Exception {    
       String  CLASS_NAME = "annotation.JavaEyer";    
       Class  test = Class.forName(CLASS_NAME);    
       Method[] method = test.getMethods();    
       boolean flag = test.isAnnotationPresent(Description.class);    
        if(flag)    
        {    
            Description des = (Description)test.getAnnotation(Description.class);    
            System.out.println("sss:"+des.value());    
            System.out.println("-----------------");    
        }    
            
       
        Set<Method> set = new HashSet<Method>();    
        for(int i=0;i<method.length;i++)    
        {    
            boolean otherFlag = method[i].isAnnotationPresent(Name.class);    
            if(otherFlag) set.add(method[i]);    
        }    
        for(Method m: set)    
        {    
            Name name = m.getAnnotation(Name.class);    
            System.out.println(name.originate());    
            System.out.println("sss:"+name.community());    
        }    
     }    
}   

