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



package ioc;


import ioc.impl.factory.*;
import ioc.itf.factory.IGlobalFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jakob Jenkov - Copyright 2005 Jenkov Development
 */
public class Container implements IContainer {

    protected Map<String, IGlobalFactory> factories = null;

    public Container() {
        this.factories = new ConcurrentHashMap<String, IGlobalFactory>();
    }

    public Container(Map<String, IGlobalFactory> factories) {
        this.factories = factories;
    }


    public void addFactory(String name, IGlobalFactory factory) {
        if(this.factories.containsKey(name)) throw
                new ContainerException(
                        "Container", "FACTORY_ALREADY_EXISTS",
                        "Container already contains a factory with this name: " + name);
        this.factories.put(name, new GlobalFactoryProxy(factory));
    }

    public void addValueFactory(String id, Object value){
        GlobalFactoryBase factory = new GlobalNewInstanceFactory();
        factory.setLocalInstantiationFactory(new ValueFactory(value));
        this.factories.put(id, new GlobalFactoryProxy(factory));
    }

    public IGlobalFactory replaceFactory(String name, IGlobalFactory newFactory){
        GlobalFactoryProxy factoryProxy = (GlobalFactoryProxy) this.factories.get(name);
        if(factoryProxy == null) {
            addFactory(name, newFactory);
            return null;
        } else {
            return factoryProxy.setDelegateFactory(newFactory);
        }
    }

    public void removeFactory(String id) {
        this.factories.remove(id);
    }

    public IGlobalFactory getFactory(String id) {
        IGlobalFactory factory = this.factories.get(id);
        //if(factory == null) throw new ContainerException("Unknown Factory: " + id);
        return factory;
    }

    public Map<String, IGlobalFactory> getFactories() {
        return this.factories;
    }

    public Object instance(String id, Object ... parameters){
        IGlobalFactory factory = this.factories.get(id);
        if(factory == null) throw new ContainerException(
                "Container", "UNKNOWN_FACTORY",                    
                "Unknown Factory: " + id);
        return factory.instance(parameters);
    }

    public void init(){
        for(String key : this.factories.keySet()){
            Object factory = this.factories.get(key);

            if(factory instanceof GlobalFactoryProxy){
                factory = ((GlobalFactoryProxy) factory).getDelegateFactory();
                if(factory instanceof GlobalSingletonFactory){
                    ((GlobalSingletonFactory) factory).instance();
                }
            }
        }
    }

    public void dispose(){
        execPhase("dispose");
    }

    public void execPhase(String phase) {
        for(String key : this.factories.keySet()){
            execPhase(phase, key);
        }
    }

    public void execPhase(String phase, String factoryName) {
        Object factory  = this.factories.get(factoryName);
        if(factory instanceof GlobalFactoryProxy){
            ((GlobalFactoryProxy) factory).execPhase(phase);
        }
    }


}
