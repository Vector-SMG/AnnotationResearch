package com.ivan.vectorsmg;

import android.app.Activity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/*
 * @author liuwei
 * @email 13040839537@163.com
 * create at 2018/6/12
 * description:
 */
public class Vector {


    public static UnBinder bind(Activity activity){
        try {
            Class<? extends UnBinder> bindClazz= (Class<? extends UnBinder>) Class.forName(activity.getClass().getName()+"_ViewBinding");

            //构造函数
            Constructor<? extends UnBinder> bindConstructor=bindClazz.getDeclaredConstructor(activity.getClass());

            UnBinder unBinder=bindConstructor.newInstance(activity);
            return unBinder;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return UnBinder.EMPTY;
    }


//    public static void bind(Activity activity) {
//        Class<?> clazz = activity.getClass();
//        BindLayout bindLayout = createBindLayout(clazz);
//        if (bindLayout != null) {
//            int layoutId = bindLayout.value();
//            if (layoutId > 0) {
//                try {
//                    Method setContentView = clazz.getMethod("setContentView", int.class);
//                    setContentView.invoke(activity, layoutId);
//                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        bindObject(activity, clazz);
//    }
//
//
//    /**
//     * 创建bindLayout
//     */
//    private static BindLayout createBindLayout(Class<?> clazz) {
//        return clazz != null ? clazz.getAnnotation(BindLayout.class) : null;
//
//    }
//
//    /**
//     * 绑定view或者事件
//     * */
//    private static void bindObject(Activity activity, Class<?> clazz) {
//        try {
//            bindView(activity,clazz);
//            bindEvent(activity,clazz);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//    /**
//     * 绑定控件
//     * */
//    private static void bindView(Activity activity, Class<?> clazz) throws Exception {
//        Field[] fields=clazz.getDeclaredFields();
//        for(Field field:fields){
//            BindView bindView=field.getAnnotation(BindView.class);
//            if(bindView!=null){
//                View view=activity.findViewById(bindView.value());
//                if(view!=null){
//                    field.setAccessible(true);
//                    try {
//                        field.set(activity,view);
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
//                }else{
//                    throw new Exception("Invalid @Bind for "+
//                    clazz.getSimpleName()+"."+field.getName());
//                }
//            }
//        }
//    }
//
//
//    /**
//     * 绑定事件
//     */
//    private static void bindEvent(Activity activity, Class<?> clazz) throws Exception {
//        Method[] methods=clazz.getMethods();
//        for(Method method:methods){
//            OnClick onClick=method.getAnnotation(OnClick.class);
//            if(onClick!=null){
//                int []views=onClick.value();
//                for(int i=0;i<views.length;i++){
//                    int viewId=views[i];
//                    View view=activity.findViewById(viewId);
//                    if(view!=null){
//                        view.setOnClickListener(new MyOnClickListener(method,activity));
//                    }else{
//                        throw new Exception("Invalid @OnClick for "+
//                                clazz.getSimpleName()+"."+method.getName());
//                    }
//                }
//            }
//
//        }
//    }
//
//
//    private static class MyOnClickListener implements View.OnClickListener{
//        private Method method;
//        private Object handler;
//
//
//        public MyOnClickListener(Method method,Object handler){
//            this.method=method;
//            this.handler=handler;
//        }
//
//        @Override
//        public void onClick(View v) {
//            try {
//                method.setAccessible(true);
//                method.invoke(handler,v);
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            }
//        }
//    }


}
