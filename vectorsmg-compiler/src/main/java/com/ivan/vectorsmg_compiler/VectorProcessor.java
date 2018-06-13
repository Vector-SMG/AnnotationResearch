package com.ivan.vectorsmg_compiler;

import com.google.auto.service.AutoService;
import com.ivan.vectorsmg_annotation.BindView;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/*
 * @author liuwei
 * @email 13040839537@163.com
 * create at 2018/6/12
 * description:
 */
@AutoService(Processor.class)
public class VectorProcessor extends AbstractProcessor{
    private Elements elementsUtils;//元素相关的辅助类
    private Filer filer;//文件相关的辅助类
    private Messager messager;//日志相关的辅助类

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elementsUtils=processingEnvironment.getElementUtils();
        filer=processingEnvironment.getFiler();
        messager=processingEnvironment.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        //返回所有被注解了bindview的元素的列表
        Set<? extends Element> elements=roundEnvironment.getElementsAnnotatedWith(BindView.class);
        // 将获取到的bindview细分到每个class
        Map<Element, List<Element>> elementMap = new LinkedHashMap<>();
        for(Element element:elements){
            //如activity
            Element enclosingElement=element.getEnclosingElement();
            List<Element> bindViewElements=elementMap.get(enclosingElement);
            if(bindViewElements==null){
                bindViewElements=new ArrayList<>();
                elementMap.put(enclosingElement,bindViewElements);
            }
            bindViewElements.add(element);
        }
        for(Map.Entry<Element,List<Element>> entrySet:elementMap.entrySet()){
            Element enclosingElement=entrySet.getKey();
            List<Element> bindViewElements=entrySet.getValue();

            //
            String activityClassNameStr=enclosingElement.getSimpleName().toString();
            System.out.println("------>"+activityClassNameStr);

            ClassName activityClassName=ClassName.bestGuess(activityClassNameStr);
            ClassName unBinderClassName=ClassName.get("com.ivan.vectorsmg","UnBinder");
            TypeSpec.Builder classBuilder=TypeSpec.classBuilder(activityClassNameStr+"_ViewBinding")
                    .addModifiers(Modifier.FINAL,Modifier.PUBLIC)
                    .addSuperinterface(unBinderClassName)
                    .addField(activityClassName,"target",Modifier.PRIVATE);

            //unbind()
            ClassName callSuperClassName=ClassName.get("android.support.annotation","CallSuper");
            MethodSpec.Builder unbindMethodBuilder=MethodSpec.methodBuilder("unBind").addAnnotation(Override.class).addAnnotation(callSuperClassName).addModifiers(Modifier.PUBLIC,Modifier.FINAL);

            //构造函数
            MethodSpec.Builder contructorMethodBuilder=MethodSpec.constructorBuilder().addParameter(activityClassName,"target")
                    .addModifiers(Modifier.PUBLIC)
                    .addStatement("this.target=target");
            for(Element bindViewElement:bindViewElements){
                //textView
                String fieldName=bindViewElement.getSimpleName().toString();
                //utils
                ClassName utilsClassName=ClassName.get("com.ivan.vectorsmg","Utils");
                int resourceId=bindViewElement.getAnnotation(BindView.class).value();
                contructorMethodBuilder.addStatement("target.$L=$T.findViewById(target,$L)",fieldName,utilsClassName,resourceId);
                unbindMethodBuilder.addStatement("target.$L=null",fieldName);
            }
            classBuilder.addMethod(unbindMethodBuilder.build()).addMethod(contructorMethodBuilder.build());
            //获取包名
            String packageName=elementsUtils.getPackageOf(enclosingElement).getQualifiedName().toString();
            try {
                JavaFile.builder(packageName, classBuilder.build()).addFileComment("自己写的ButterKnife生成的代码，不要修改!!").build().writeTo(filer);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return false;
    }
    //指定sourceVersion
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    //指定注解处理器处理哪些注解
    @Override
    public Set<String> getSupportedAnnotationTypes(){
        Set<String> types=new LinkedHashSet<>();
        types.add(BindView.class.getCanonicalName());
        return types;
    }

}
