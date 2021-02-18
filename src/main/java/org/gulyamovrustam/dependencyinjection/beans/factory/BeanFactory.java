package org.gulyamovrustam.dependencyinjection.beans.factory;

import org.gulyamovrustam.dependencyinjection.annotations.Autowired;
import org.gulyamovrustam.dependencyinjection.annotations.Component;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class BeanFactory {
    private Map<String, Object> singletons = new HashMap<>();

    public Object getBean(String beanName) {
        return singletons.get(beanName);
    }

    public void instantiate(String basePackage) throws IOException, URISyntaxException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        String path = basePackage.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);

        while(resources.hasMoreElements()) {
            URL resource = resources.nextElement();

            File file = new File(resource.toURI());
            for(File classFile: file.listFiles()) {
                String filename = classFile.getName();

                if(filename.endsWith(".class")) {
                    String className = filename.substring(0, filename.lastIndexOf("."));

                    Class classObject = Class.forName(basePackage + "." +  className);

                    if(classObject.isAnnotationPresent(Component.class)) {
                        System.out.println("Component: " + classObject);
                        String beanName = className.substring(0,  1).toLowerCase() +
                                className.substring(1);

                        singletons.put(beanName, classObject.newInstance());
                    }
                }
            }
        }

        populateProperties();
    }

    //1. Получить поля с аннотациями Autowired
    //2. сопоставить бину некоторое поле:
    //2.1
    //3. Заинжектить его
    public void populateProperties() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.println("==Populate Properties==");

        for (Object object: singletons.values()) {
            for (Field field: object.getClass().getDeclaredFields()) {
                if(field.isAnnotationPresent(Autowired.class)) {
                    for (Object dependency: singletons.values()) {
                        if (field.getType().equals(dependency.getClass())) {
                            String setterName = "set" + field.getName().substring(0, 1).toUpperCase() +
                                    field.getName().substring(1);

                            System.out.println("Setter name: " + setterName);

                            Method setter = object.getClass().getMethod(setterName, dependency.getClass());
                            setter.invoke(object, dependency);
                        }
                    }
                }
            }
        }
    }
}
