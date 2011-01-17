/*
    Copyright 2007-2010 Jenkov Development

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/



package org.leixu.iwap.ioc.resources;



import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.leixu.iwap.ioc.Container;
import org.leixu.iwap.ioc.IContainer;
import org.leixu.iwap.ioc.script.ScriptFactoryBuilder;

public class Resources {

    public static final ThreadLocal<Locale> locale = new ThreadLocal<Locale>();


    public static Locale getThreadLocale(){
        return locale.get();
    }

    public static String getString(Map<Locale, String> texts, Locale paramLocale, Locale threadLocale, Locale defaultLocale){
        if(texts == null) throw new NullPointerException("texts parameter (Map) was null");
        Locale actualLocale = paramLocale;
        if(actualLocale == null) actualLocale = threadLocale;
        if(actualLocale == null) actualLocale = defaultLocale;

        return texts.get(actualLocale);
    }


    public static void main(String[] args) throws NoSuchMethodException {

        IContainer           container = new Container();
        ScriptFactoryBuilder builder   = new ScriptFactoryBuilder(container);

        builder.addFactory("UK = java.util.Locale('en', 'gb'); ");
        builder.addFactory("DK = java.util.Locale('da', 'dk'); ");
        builder.addFactory("threadLocale = com.jenkov.container.resources.Resources.getThreadLocale(); ");
        builder.addFactory("localize = * com.jenkov.container.resources.Resources.getString($1, $0, threadLocale, UK);");
        builder.addFactory("astring  = * localize($0, <DK : 'hej', UK : 'hello'> } "
        );

        String astring = (String) container.instance("astring");

        System.out.println("astring = " + astring);

        

    }




}
