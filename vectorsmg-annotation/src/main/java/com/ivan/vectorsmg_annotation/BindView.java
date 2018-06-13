package com.ivan.vectorsmg_annotation;

/*
 * @author liuwei
 * @email 13040839537@163.com
 * create at 2018/6/12
 * description:
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface BindView {
    int value();
}
