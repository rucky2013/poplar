package com.dempe.poplar.core;

import com.dempe.poplar.core.support.*;
import com.dempe.poplar.core.utils.PackageUtils;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import javassist.Modifier;
import net.vidageek.mirror.dsl.Mirror;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Predicates.instanceOf;
import static com.google.common.base.Predicates.or;
import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Arrays.asList;

/**
 * Created with IntelliJ IDEA.
 * User: Dempe
 * Date: 2015/4/20
 * Time: 20:48
 * To change this template use File | Settings | File Templates.
 */
public class PoplarContext {

    private final Map<String,ControllerMethod> mapper = new ConcurrentHashMap<String,ControllerMethod>();

    public void initMapper() throws ClassNotFoundException {
        String[] classNames = PackageUtils.findClassesInPackage("com.dempe.*");
        for (String className : classNames) {
            Class<?> clzz =Class.forName(className);
            clzz.getAnnotation(Controller.class);
            if(clzz.isAnnotationPresent(Controller.class)){
                for (Method javaMethod : clzz.getMethods()) {
                    if (isEligible(javaMethod)) {
                        String[] uris = getURIsFor(javaMethod, clzz);
                        for (String uri : uris) {
                            ControllerMethod method = new DefaultControllerMethod(new DefaultBeanClass(clzz),javaMethod);
                            System.out.println(uri);
                            mapper.put(uri,method);
                        }
                    }

                }

            }

        }
    }

    public ControllerMethod parse(String uri){
        return mapper.get(uri);
    }

    protected String[] getURIsFor(Method javaMethod, Class<?> type) {

        if (javaMethod.isAnnotationPresent(Path.class)) {
            String[] uris = javaMethod.getAnnotation(Path.class).value();

            checkArgument(uris.length > 0, "You must specify at least one path on @Path at %s", javaMethod);
            checkArgument(getUris(javaMethod).length == 0,
                    "You should specify paths either in @Path(\"/path\") or @Get(\"/path\") (or @Post, @Put, @Delete), not both at %s", javaMethod);

            fixURIs(type, uris);
            return uris;
        }
        String[] uris = getUris(javaMethod);

        if(uris.length > 0){
            fixURIs(type, uris);
            return uris;
        }

        return new String[] { defaultUriFor(extractControllerNameFrom(type), javaMethod.getName()) };
    }
    protected String extractControllerNameFrom(Class<?> type) {
        String prefix = extractPrefix(type);
        if (isNullOrEmpty(prefix)) {
            String baseName = StringUtils.lowercaseFirst(type.getSimpleName());
            if (baseName.endsWith("Controller")) {
                return "/" + baseName.substring(0, baseName.lastIndexOf("Controller"));
            }
            return "/" + baseName;
        } else {
            return prefix;
        }
    }

    protected String defaultUriFor(String controllerName, String methodName) {
        return controllerName + "/" + methodName;
    }

    protected String[] getUris(Method javaMethod){
        Annotation method = FluentIterable.from(asList(javaMethod.getAnnotations()))
                .filter(instanceOfMethodAnnotation())
                .first().orNull();

        if (method == null) {
            return new String[0];
        }
        return (String[]) new Mirror().on(method).invoke().method("value").withoutArgs();
    }

    protected void fixURIs(Class<?> type, String[] uris) {
        String prefix = extractPrefix(type);
        for (int i = 0; i < uris.length; i++) {
            if (isNullOrEmpty(prefix)) {
                uris[i] = fixLeadingSlash(uris[i]);
            } else if (isNullOrEmpty(uris[i])) {
                uris[i] = prefix;
            } else {
                uris[i] = removeTrailingSlash(prefix) + fixLeadingSlash(uris[i]);
            }
        }
    }

    protected String removeTrailingSlash(String prefix) {
        return prefix.replaceFirst("/$", "");
    }

    protected String extractPrefix(Class<?> type) {
        if (type.isAnnotationPresent(Path.class)) {
            String[] uris = type.getAnnotation(Path.class).value();
            checkArgument(uris.length == 1, "You must specify exactly one path on @Path at %s", type);
            return fixLeadingSlash(uris[0]);
        } else {
            return "";
        }
    }

    private String fixLeadingSlash(String uri) {
        if (!uri.startsWith("/")) {
            return  "/" + uri;
        }
        return uri;
    }


    protected boolean isEligible(Method javaMethod) {
        return Modifier.isPublic(javaMethod.getModifiers())
                && !Modifier.isStatic(javaMethod.getModifiers())
                && !javaMethod.isBridge()
                && !javaMethod.getDeclaringClass().equals(Object.class);
    }


    public static void main(String[] args) throws ClassNotFoundException {
        PoplarContext context = new PoplarContext();
        context.initMapper();
    }

    private Predicate<Annotation> instanceOfMethodAnnotation() {
        return or(instanceOf(Get.class), instanceOf(Post.class), instanceOf(Put.class), instanceOf(Delete.class), instanceOf(Options.class), instanceOf(Patch.class));
    }
}
