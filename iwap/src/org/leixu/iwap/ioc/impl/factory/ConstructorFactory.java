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



import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.leixu.iwap.ioc.itf.factory.FactoryException;
import org.leixu.iwap.ioc.itf.factory.ILocalFactory;

/**

 */
public class ConstructorFactory extends LocalFactoryBase implements ILocalFactory {

    protected Constructor       constructor             = null;
    protected List<ILocalFactory>    constructorArgFactories = new ArrayList<ILocalFactory>();

    public ConstructorFactory(Constructor constructor){
        this.constructor = constructor;
    }

    public ConstructorFactory(Constructor contructor, List<ILocalFactory> contructorArgFactories) {
        this.constructor             = contructor;
        this.constructorArgFactories = contructorArgFactories;
    }

    public ConstructorFactory(Constructor constructor, ILocalFactory[] constructorArgFactories){
        this.constructor = constructor;
        for(ILocalFactory factory : constructorArgFactories){
            this.constructorArgFactories.add(factory);            
        }
    }

    public Constructor getConstructor() {
        return constructor;
    }

    public List<ILocalFactory> getConstructorArgFactories() {
        return constructorArgFactories;
    }

    public Class getReturnType() {
        return this.constructor.getDeclaringClass();
    }

    public Object instance(Object[] parameters, Object[] localProducts) {
        Object[] arguments = FactoryUtil.toArgumentArray(this.constructorArgFactories, parameters, localProducts);

        Object returnValue = null;
        try {
            returnValue = this.constructor.newInstance(arguments);
        } catch (Throwable t){
            throw new FactoryException(
                    "ConstructorFactory", "CONSTRUCTOR_EXCEPTION",
                    "Error instantiating object from constructor " + this.constructor, t);
        } finally{
            for(int j=0; j<arguments.length; j++)arguments[j] = null;
        }

        return returnValue;
    }

    public String toString() {
        return "<ConstructorFactory : " + getReturnType() + ">";
    }


}
