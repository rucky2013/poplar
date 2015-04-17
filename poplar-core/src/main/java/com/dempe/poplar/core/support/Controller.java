/***
 * Copyright (c) 2009 Caelum - www.caelum.com.br/opensource
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dempe.poplar.core.support;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Stereotype;
import java.lang.annotation.*;

/**
 * Controllers are entry points for requests; i.e, requests are handled by VRaptor Controllers.
 *
 * @author Guilherme Silveira
 * @author Fabio Kung
 */
@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Stereotype
@RequestScoped
public @interface Controller {
}
