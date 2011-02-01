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



package org.leixu.iwap.ioc.java;

import org.leixu.iwap.ioc.IContainer;
import org.leixu.iwap.ioc.itf.factory.IGlobalFactory;


/**

 */
public class JavaFactory implements IGlobalFactory {

    protected Class      returnType = null;

    public Class getReturnType() {
        return returnType;
    }

    public void setReturnType(Class returnType) {
        this.returnType = returnType;
    }

    public Object instance(Object... parameters) {
        return null;
    }

    public Object[] execPhase(String phase, Object... parameters) {
        return new Object[0];  
    }
}
