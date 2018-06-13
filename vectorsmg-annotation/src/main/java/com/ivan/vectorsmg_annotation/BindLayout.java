package com.ivan.vectorsmg_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * @author liuwei
 * @email 13040839537@163.com
 * create at 2018/6/12
 * description:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BindLayout {
    int value();
}
