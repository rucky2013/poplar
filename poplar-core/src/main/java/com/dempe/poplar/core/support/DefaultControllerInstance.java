/***
 * Copyright (c) 2009 Caelum - www.caelum.com.br/opensource All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.dempe.poplar.core.support;

import javax.enterprise.inject.Vetoed;

public class DefaultControllerInstance implements ControllerInstance {

    private Object controller;

    public DefaultControllerInstance(Object instance) {
        this.controller = instance;
    }

    @Override
    public Object getController() {
        return controller;
    }

    @Override
    public BeanClass getBeanClass() {
        return new DefaultBeanClass(controller.getClass());
    }

}