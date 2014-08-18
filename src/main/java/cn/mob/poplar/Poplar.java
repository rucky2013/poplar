package cn.mob.poplar;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0 date: 2014/8/15
 * @author: Dempe
 */
@Configuration
@ComponentScan
public class Poplar {

    private static final Logger LOGGER = Logger.getLogger(PoplarServer.class);

    public static void main(String[] args) {
        LOGGER.info("POPLAR START");
        ApplicationContext context = new AnnotationConfigApplicationContext(Poplar.class);
        PoplarServer server = context.getBean(PoplarServer.class);
        server.startUp();

    }

}
