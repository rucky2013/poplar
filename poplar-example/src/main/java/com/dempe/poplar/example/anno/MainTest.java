package com.dempe.poplar.example.anno;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: Dempe
 * Date: 2015/4/13
 * Time: 20:06
 * To change this template use File | Settings | File Templates.
 */
public class MainTest {
    public static void main(String[] args) {
        Controller controller =IndexController.class.getAnnotation(Controller.class);
        Method[]  methods = IndexController.class.getDeclaredMethods();
        for (Method method : methods) {
            //System.out.println("path:"+method.getAnnotation(Path.class));
            Annotation[]  annotations =  method.getAnnotations();
            for (Annotation annotation : annotations) {
              //  System.out.println(annotation.annotationType().getSimpleName());
                if ("Path".equals(annotation.annotationType().getSimpleName())){
                    System.out.println("===="+annotation.annotationType());
                }
            }

        }
//        System.out.println(Arrays.toString(new IndexController().getClass().getAnnotations()));
//        System.out.println(controller);

    }
}
