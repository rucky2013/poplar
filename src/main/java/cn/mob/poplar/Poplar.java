package cn.mob.poplar;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2014/8/13.
 */


@Configuration
@ComponentScan
public class Poplar {

    private static final Logger LOGGER = Logger.getLogger(PoplarBootstrap.class);


    public static void main(String[] args) {
        LOGGER.info("POPLAR START");
        ApplicationContext context = new AnnotationConfigApplicationContext(Poplar.class);
        PoplarBootstrap serverBootstrap = context.getBean(PoplarBootstrap.class);
        serverBootstrap.startUp();

    }
    
}
