package cn.mob.poplar.anno;

import java.lang.annotation.*;

/**
 * @version 1.0 date: 2014/8/15
 * @author: Dempe
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Path {


    String[] value();

    /**
     * Used to decide which path will be tested first.
     * Paths with priority HIGHEST are tested before paths with priority HIGH,
     * which are tested before paths with priority DEFAULT, and so on.
     * <pre>
     \@Path(value="/url", priority=Path.HIGHEST)
     \@Path(value="/url", priority=Path.HIGH)
     \@Path(value="/url", priority=Path.DEFAULT)
     \@Path(value="/url", priority=Path.LOW)
     \@Path(value="/url", priority=Path.LOWEST)
     </pre>
     *
     */
    int priority() default DEFAULT;

    public static final int LOWEST = Integer.MAX_VALUE;
    public static final int LOW = Integer.MAX_VALUE/4*3;
    public static final int DEFAULT = Integer.MAX_VALUE/2;
    public static final int HIGH = Integer.MAX_VALUE/4;
    public static final int HIGHEST = 0;
}
