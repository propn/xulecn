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



package org.leixu.iwap.ioc.impl.factory;



import java.util.HashMap;
import java.util.Map;

import org.leixu.iwap.ioc.itf.factory.ILocalFactory;

/**
 * @author Jakob Jenkov - Copyright 2004-2006 Jenkov Development
 */
public class LocalFlyweightFactory extends LocalFactoryBase implements ILocalFactory {

    public ILocalFactory sourceFactory = null;
    public Map<FlyweightKey, Object> instances = new HashMap<FlyweightKey, Object>();


    public LocalFlyweightFactory(ILocalFactory sourceFactory) {
        this.sourceFactory = sourceFactory;
    }

    public ILocalFactory getSourceFactory() {
        return sourceFactory;
    }

    public Class getReturnType() {
        return this.sourceFactory.getReturnType();
    }

    public synchronized Object instance(Object[] parameters, Object[] localProducts) {
        FlyweightKey key = new FlyweightKey(parameters);
        Object instance = this.instances.get(key);
        if(instance == null){
            instance = this.sourceFactory.instance(parameters, localProducts);
            this.instances.put(key, instance);
        }
        return instance;
    }

    public String toString() {
        return "<LocalFlyweightFactory> --> "+ this.sourceFactory.toString();
    }

}