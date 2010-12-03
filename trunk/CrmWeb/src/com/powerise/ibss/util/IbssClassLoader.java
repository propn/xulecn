package com.powerise.ibss.util;

import java.io.FileInputStream;
import java.util.HashMap;

public class IbssClassLoader extends ClassLoader {
   private HashMap classes = new HashMap();

   public IbssClassLoader() {
   }

   private byte getClassFromFileByte(String className)[] {
      byte result[] =null;
      FileInputStream fi =null;
      char cFileSep = ((String) System.getProperties().get("file.separator")).charAt(0);
      try {
//         HashMap config =SysSet.getTagProps("System");
    	  HashMap config = null ;
         String path =config.get("LoadClassPath")==null?"":config.get("LoadClassPath").toString().trim();
         if(! path.equals(""))
            path += className.replace('.', cFileSep);
         else
            System.out.println("The LoadClassPath config is null...");
         fi = new FileInputStream(path + ".class");
         result = new byte[fi.available()];
         fi.read(result);
      } catch (Exception e) {
         return null;
      }finally{
         try{
            if(fi !=null){
               fi.close();
               fi =null;
            }
         }catch(Exception e){
            e.printStackTrace();
         }
      }
      return result;
   }

   public Class loadClass(String className) throws ClassNotFoundException {
      return (loadClass(className, true));
   }

   public synchronized Class loadClass(String className, boolean resolveIt)
           throws ClassNotFoundException {
      Class result;
      byte classData[];
      if (!className.startsWith("com.powerise.ibss") && !className.startsWith("com.zte.fms") && !className.startsWith("com.ztesoft")) {
         result = Class.forName(className);//super.findSystemClass(className);
         return result;
      }
      //*
      if (className.startsWith("com.powerise.ibss.util") ||
              className.startsWith("com.powerise.ibss.framework")) {
         //result = super.findSystemClass(className);
         result =Class.forName(className);
         //result = super.loadClass(className,false);
         //java.net.class
         return result;
      }
      //*/

      classData = getClassFromFileByte(className);
      if (classData == null) {
         throw new ClassNotFoundException();
      }

      result = defineClass(className,classData, 0, classData.length);
      if (result == null) {
         throw new ClassFormatError();
      }

      if (resolveIt) {
         resolveClass(result);
      }
      return result;
   }
}
