package com.sinata.navi_annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface Destination {

    /**
     *
     *
     * @return
     */
    String pageUrl();

    /**
     *
     *
     * @return
     */
    boolean asStarter() default false;
}
