package com.ivan.annotationresearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ivan.vectorsmg.UnBinder;
import com.ivan.vectorsmg.Vector;
import com.ivan.vectorsmg_annotation.BindView;

/*
 * @author liuwei
 * @email 13040839537@163.com
 * create at 2018/6/13
 * description:
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv)
    TextView tv;
    UnBinder binder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binder= Vector.bind(this);
        tv.setText("hello");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unBind();
    }
}
