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

import org.jboss.netty.handler.codec.http.HttpRequest;

import java.lang.annotation.Annotation;

public enum HttpMethod {
    GET(Get.class), POST(Post.class), PUT(Put.class), DELETE(Delete.class), TRACE(Trace.class), HEAD(Head.class), OPTIONS(Options.class), PATCH(Patch.class);

    private static final String METHOD_PARAMETER = "_method";
    private final Class<? extends Annotation> type;

    HttpMethod(Class<? extends Annotation> type) {
        this.type = type;
    }

    public Class<? extends Annotation> getAnnotation() {
        return type;
    }

    public static HttpMethod of(HttpRequest request) {

        String methodName = request.getMethod().getName();

        try {
            return valueOf(methodName.toUpperCase());
        } catch (IllegalArgumentException e) {
            // funny, but we need a better explanation, to support sendError
            throw new IllegalArgumentException("HTTP Method not known: " + methodName, e);
        }
    }
}
