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



package ioc.impl.factory;


import ioc.itf.factory.IGlobalFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * todo fix phase execution on local products
 *
* @author Jakob Jenkov - Copyright 2004-2006 Jenkov Development
 */
public class GlobalThreadSingletonFactory extends GlobalFactoryBase implements IGlobalFactory {

    public Map<Thread, Object[]> localProductMap = new HashMap<Thread, Object[]>();

    public Class getReturnType() {
        return getLocalInstantiationFactory().getReturnType();
    }

    public synchronized Object instance(Object ... parameters) {
        Thread callingThread = Thread.currentThread();
        Object[] threadLocalProducts = this.localProductMap.get(callingThread);
        if(threadLocalProducts != null) return threadLocalProducts[0];

        threadLocalProducts = new Object[getLocalProductCount()];
        threadLocalProducts[0] = getLocalInstantiationFactory().instance(parameters, threadLocalProducts);
        execPhase("config", parameters, threadLocalProducts);
        this.localProductMap.put(callingThread, threadLocalProducts);

        return threadLocalProducts[0];
    }


    public Object[] execPhase(String phase, Object ... parameters) {
        for(Thread thread : this.localProductMap.keySet()){
            Object[] threadLocalProducts = this.localProductMap.get(thread);
            execPhase(phase, parameters, threadLocalProducts);
        }
        return null;
    }

    public String toString() {
        return "<GlobalThreadSingletonFactory> --> "+ getLocalInstantiationFactory().toString();
    }

}
