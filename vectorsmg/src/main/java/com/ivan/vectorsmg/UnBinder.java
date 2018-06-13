package com.ivan.vectorsmg;

import android.support.annotation.UiThread;

/*
 * @author liuwei
 * @email 13040839537@163.com
 * create at 2018/6/13
 * description:
 */
public interface UnBinder {
    @UiThread
    void unBind();

    UnBinder EMPTY=new UnBinder() {
        @Override
        public void unBind() {

        }
    };
}
