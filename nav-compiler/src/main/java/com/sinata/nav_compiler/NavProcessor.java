package com.sinata.nav_compiler;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.auto.service.AutoService;
import com.sinata.navi_annotation.Destination;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;


@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({"com.sinata.navi_annotation.Destination"})
public class NavProcessor extends AbstractProcessor {
    public static final String PAGE_TYPE_ACTIVITY = "ACTIVITY";
    public static final String PAGE_TYPE_FRAGMENT = "FRAGMENT";
    public static final String PAGE_TYPE_DIALOG = "DIALOG";
    public static final String OUTPUT_FILE_PATH = "destination.json";
    Messager messager;
    Filer filer;
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        messager.printMessage(Diagnostic.Kind.NOTE,"NavProcessor init start");
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //处理器处理自定义注解的地方
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Destination.class);
        if (!elements.isEmpty()){
            HashMap<String, JSONObject> destMap = new HashMap<>();
            handleDestination(elements,Destination.class,destMap);
            try {
                FileObject resource = filer.createResource(StandardLocation.CLASS_OUTPUT, "", OUTPUT_FILE_PATH);
                // /app/build/intermediates/javac/debug/classes/目录下/
                // /app/src/main/assets
                String resourcePath = resource.toUri().getPath();
                String appPath = resourcePath.substring(0, resourcePath.indexOf("app") + 4);
                String assetsPath = appPath + "/src/main/assets";

                String content = JSON.toJSONString(destMap);
                File file = new File(assetsPath);
                if (file.exists()){
                    file.mkdirs();
                }
                File outputFile = new File(assetsPath,OUTPUT_FILE_PATH);
                if (outputFile.exists()){
                    outputFile.delete();
                }
                outputFile.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
                OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream);
                writer.write(content);
                writer.flush();
                fileOutputStream.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    private void handleDestination(Set<? extends Element> elements, Class<Destination> destinationClass, HashMap<String, JSONObject> destMap) {
        for (Element element : elements){
            TypeElement typeElement = (TypeElement) element;
            //全类名
            String clazzName = typeElement.getQualifiedName().toString();
            Destination destination = typeElement.getAnnotation(destinationClass);
            String pageUrl = destination.pageUrl();
            boolean asStarter = destination.asStarter();
            String desType = getDestinationType(typeElement);
            int id = Math.abs(destination.hashCode());
            if (destMap.containsKey(pageUrl)){
                messager.printMessage(Diagnostic.Kind.ERROR,"不同页面不允许使用相同的pageUrl:" + pageUrl);
            }else{
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("pageUrl",pageUrl);
                jsonObject.put("clazzName",clazzName);
                jsonObject.put("asStarter",asStarter);
                jsonObject.put("desType",desType);
                jsonObject.put("id",id);
                destMap.put(pageUrl,jsonObject);
            }
        }
    }

    private String getDestinationType(TypeElement typeElement) {
        TypeMirror typeMirror = typeElement.getSuperclass();
        //这个父类的类型是类的类型，或者接口
        String supperClazzName = typeMirror.toString();
        if (supperClazzName.contains(PAGE_TYPE_ACTIVITY.toLowerCase())){
            return PAGE_TYPE_ACTIVITY.toLowerCase();
        }else if (supperClazzName.contains(PAGE_TYPE_FRAGMENT.toLowerCase())){
            return PAGE_TYPE_FRAGMENT.toLowerCase();
        }else if (supperClazzName.contains(PAGE_TYPE_DIALOG.toLowerCase())){
            return PAGE_TYPE_DIALOG.toLowerCase();
        }

        if (typeMirror instanceof DeclaredType){
            Element element = ((DeclaredType) typeMirror).asElement();
            if (element instanceof TypeElement){
                getDestinationType((TypeElement) element);
            }
        }
        return null;
    }
}
