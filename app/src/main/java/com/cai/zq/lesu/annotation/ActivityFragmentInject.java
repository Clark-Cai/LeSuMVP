package com.cai.zq.lesu.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO<注释插入>
 * Created by digiengine
 * DATE: 16/6/21 下午3:03
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ActivityFragmentInject {

    /**
     * activity layout
     * @return int
     */
    int contentViewId() default -1;

}
