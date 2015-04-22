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

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.lang.reflect.Method;
import java.util.*;

/**
 * The default implementation of controller localization rules. It also uses a Path annotation to discover
 * path-&gt;method mappings using the supplied ControllerLookupInterceptor.
 *
 * @author Guilherme Silveira
 */
public class DefaultRouter implements Router {

    private static final Logger logger = LoggerFactory.getLogger(DefaultRouter.class);

    private final Collection<Route> routes = new PriorityRoutesList();
    //private final Evaluator evaluator;


    /**
     * @deprecated CDI eyes only
     */

    @Inject
    public DefaultRouter() {

    }


    /**
     * You can override this method to get notified by all added routes.
     */
    @Override
    public void add(Route r) {
        routes.add(r);
    }

    @Override
    public ControllerMethod parse(String uri, HttpMethod method, HttpRequest request) {
        Collection<Route> routesMatchingUriAndMethod = routesMatchingUriAndMethod(uri, method);

        System.out.println("==="+routesMatchingUriAndMethod);
        Iterator<Route> iterator = routesMatchingUriAndMethod.iterator();

        Route route = iterator.next();
        checkIfThereIsAnotherRoute(uri, method, iterator, route);

        return route.controllerMethod(request, uri);
    }


    private void checkIfThereIsAnotherRoute(String uri, HttpMethod method, Iterator<Route> iterator, Route route) {
        if (iterator.hasNext()) {
            Route otherRoute = iterator.next();

        }
    }

    private Collection<Route> routesMatchingUriAndMethod(String uri, HttpMethod method) {
        Collection<Route> routesMatchingMethod = FluentIterable.from(routesMatchingUri(uri))
                .filter(allow(method)).toSet();

        System.out.println("routesMatchingUri(uri)===>"+routesMatchingUri(uri));
        if (routesMatchingMethod.isEmpty()) {
            EnumSet<HttpMethod> allowed = allowedMethodsFor(uri);

        }
        return routesMatchingMethod;
    }

    @Override
    public EnumSet<HttpMethod> allowedMethodsFor(String uri) {
        EnumSet<HttpMethod> allowed = EnumSet.noneOf(HttpMethod.class);
        for (Route route : routesMatchingUri(uri)) {
            allowed.addAll(route.allowedMethods());
        }
        return allowed;
    }

    private Collection<Route> routesMatchingUri(String uri) {
        System.out.println("=========route>"+Arrays.toString(routes.toArray()));
        Collection<Route> routesMatchingURI = FluentIterable.from(routes)
                .filter(canHandle(uri)).toSet();


        return routesMatchingURI;
    }

    @Override
    public <T> String urlFor(final Class<T> type, final Method method, Object... params) {

        Route route = null;

        logger.debug("Selected route for {} is {}", method, route);
        String url = route.urlFor(type, method, params);
        logger.debug("Returning URL {} for {}", url, route);

        return url;
    }

    @Override
    public List<Route> allRoutes() {
        return Collections.unmodifiableList(new ArrayList<Route>(routes));
    }

    @Override
    public RouteBuilder builderFor(String uri) {
        return new DefaultRouteBuilder( uri);
    }

    private Predicate<Route> canHandle(final Class<?> type, final Method method) {
        return new Predicate<Route>() {
            @Override
            public boolean apply(Route route) {
                return route.canHandle(type, method);
            }
        };
    }

    private Predicate<Route> canHandle(final String uri) {
        return new Predicate<Route>() {
            @Override
            public boolean apply(Route route) {
                return route.canHandle(uri);
            }
        };
    }

    private Predicate<Route> allow(final HttpMethod method) {
        return new Predicate<Route>() {
            @Override
            public boolean apply(Route route) {
                return route.allowedMethods().contains(method);
            }
        };
    }
}