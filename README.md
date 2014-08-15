poplar
===================================
  基于netty的java中间件<br/>

 High Productivity
 -------------
 Learning Curve
 -------------
 Testability
 -------------
 Savings
 -------------
  
profile
-----------------------------------

1.light server<br/>
2.spring DI<br/>
3.REST Controllers<br/>

### example
    import org.springframework.context.ApplicationContext;
    import org.springframework.context.annotation.AnnotationConfigApplicationContext;
    import org.springframework.context.annotation.ComponentScan;
    import org.springframework.context.annotation.Configuration;
    
    @Configuration
    @ComponentScan
    public class Poplar {
        public static void main(String[] args) {
            ApplicationContext context = new AnnotationConfigApplicationContext(Poplar.class);
            PoplarBootstrap serverBootstrap = context.getBean(PoplarBootstrap.class);
            serverBootstrap.startUp();
        }
    }
### link
[DOC : http://dempezheng.github.io/poplar](http://dempezheng.github.io/poplar/)<br />




