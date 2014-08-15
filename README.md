poplar
===================================
  基于netty的java中间件<br/>
  

  
profile
-----------------------------------

1.轻量的中间件服务<br/>
2.基于spring容器的依赖注入<br/>
3.提供restful接口<br/>
  中标题一般显示重点项,类似html的\<h2\><br />
  你只要在标题下面输入------即可
  
### example
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
        
            private static final Logger LOGGER = Logger.getLogger(PoplarBootstrap.class);
        
        
            public static void main(String[] args) {
                LOGGER.info("POPLAR START");
                ApplicationContext context = new AnnotationConfigApplicationContext(Poplar.class);
                PoplarBootstrap serverBootstrap = context.getBean(PoplarBootstrap.class);
                serverBootstrap.startUp();
        
            }
        
        }


### link
[document:](http://dempezheng.github.io/poplar/)<br />


###只是显示图片
![github](http://github.com/unicorn.png "github")

###想点击某个图片进入一个网页,比如我想点击github的icorn然后再进入www.github.com
[![image]](http://www.github.com/)
[image]: http://github.com/github.png "github"

### 文字被些字符包围
> 文字被些字符包围
>
> 只要再文字前面加上>空格即可
>
> 如果你要换行的话,新起一行,输入>空格即可,后面不接文字
> 但> 只能放在行首才有效

### 文字被些字符包围,多重包围
> 文字被些字符包围开始
>
> > 只要再文字前面加上>空格即可
>
>  > > 如果你要换行的话,新起一行,输入>空格即可,后面不接文字
>
> > > > 但> 只能放在行首才有效

###




