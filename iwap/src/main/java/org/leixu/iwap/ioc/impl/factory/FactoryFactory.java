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



import java.lang.reflect.Proxy;

import org.leixu.iwap.ioc.IContainer;
import org.leixu.iwap.ioc.itf.factory.FactoryException;
import org.leixu.iwap.ioc.itf.factory.IGlobalFactory;
import org.leixu.iwap.ioc.itf.factory.ILocalFactory;

/**

 */
public class FactoryFactory extends LocalFactoryBase implements ILocalFactory {

    protected IContainer container              = null;
    protected Class      customFactoryInterface = null;
    protected String     defaultFactoryName     = null; //name of factory to inject / call, when interface method is named "instance()";

    public FactoryFactory(IContainer returnValue, String defaultFactoryName) {
        this.container          = returnValue;
        this.defaultFactoryName = defaultFactoryName;
    }

    public void setCustomFactoryInterface(Class customFactoryInterface) {
        if(!customFactoryInterface.isInterface()) {
            throw new FactoryException(
                    "FactoryFactory", "CONTAINER_INTERFACE_ADAPTATION",
                    "Can only adapt container to an interface. Method parameter " +
                    "to inject container into was : " + customFactoryInterface);
        }

        this.customFactoryInterface = customFactoryInterface;
    }

    public Class getReturnType() {
        if(this.customFactoryInterface != null) return this.customFactoryInterface;
        return IGlobalFactory.class;
    }

    public Object instance(Object[] parameters, Object[] localProducts) {
        Class returnType = getReturnType();

        if(IGlobalFactory.class.equals(returnType) || FactoryUtil.isSubstitutableFor(returnType, IGlobalFactory.class)){
            return this.container.getFactory(this.defaultFactoryName);
        }

        if(FactoryUtil.isSubstitutableFor(returnType, IContainer.class)){
            return this.container;
        }

        return Proxy.newProxyInstance(returnType.getClassLoader(), new Class[]{returnType},
                new FactoryInterfaceAdapter(container, this.defaultFactoryName));
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("<FactoryFactory : ")
                .append(getReturnType())
                .append("> --> ")
                .append(this.container);

        return builder.toString();
    }

}
