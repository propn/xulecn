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

import ioc.itf.factory.ILocalFactory;

/**
 * @author Jakob Jenkov - Copyright 2005 Jenkov Development
 */
public class ValueFactory extends LocalFactoryBase implements ILocalFactory {

    public Object value      = null;

    public ValueFactory(Object value) {
        this.value = value;
    }

    public Class getReturnType() {
        if(this.value == null) return null;
        return this.value.getClass();
    }

    public Object instance(Object[] parameters, Object[] localProducts) {
        return this.value;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder .append("<ValueFactory : ")
                .append(getReturnType())
                .append("> --> <")
                .append(this.value)
                .append(">");

        return builder.toString();
    }
}
