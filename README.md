poplar
===================================
  基于netty的java中间件<br/>
  
profile
-----------------------------------

1.轻量的中间件服务<br/>
2.基于spring容器的依赖注入<br/>
3.提供restful接口<br/>

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




