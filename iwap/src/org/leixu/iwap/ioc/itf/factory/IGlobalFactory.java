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



package org.leixu.iwap.ioc.itf.factory;

/**
 *
 * An object factory that can be plugged into an IContainer instance.
 * A factory creates instances with a common interface or super class.
 *
 * <br/><br/>
 * The factory
 * can create instances of many different classes as long as they
 * all either implement a common interface, or extend a common
 * super class.
 * <br/><br/>
 *
 *
 * Copyright 2008 Jenkov Development
 */
public interface IGlobalFactory<T> {

    public Class getReturnType();


    /**
     * Returns an instance of whatever component this global factory produces
     *
     * @param parameters Any parameters needed by the factory to create its instance.
     * @return The object instance as created by the facttory.
     */
    public T instance(Object ... parameters);

    /**
     * This method is called by the container when executing a phase in a factory that supports life cycle phases.
     * The container knows nothing about local products, therefore this method is called. Only the concrete
     * factory knows about cached local products (if any).
     *
     * <br/><br/>
     * This is the method a global factory will override when implementing life cycle phase behaviour, e.g. for
     * cached objects.
     *
     * @param phase       The name of the phase to execute. For instance, "config" or "dispose".
     * @param parameters  The parameters passed to the container when the phase begins. For instance to
     *                    an instance() method call, or an execPhase(phase, factory, parameters) call.
     * @return            Null, or the local products the phase ends up being executed on. If executed
     *                    for several local product arrays (e.g. in pools or flyweights), null will be returned, since it does not
     *                    make sense to return anything. Returning anything would only make sense for
     *                    the "create" phase, but currently this phase does not use the execPhase() method
     *                    to carry out its work. It uses the factory.instance() methods instead.
     */
    public Object[] execPhase(String phase, Object ... parameters);


}
