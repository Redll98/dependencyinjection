package org.gulyamovrustam.dependencyinjection.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReflectionUtils {
    private ReflectionUtils() {}

    public static List<Field> getAnnotatedFields(Class targetClass, Class annotationClass) {
        List<Field> fields = new ArrayList<>();

        for (Field field: targetClass.getDeclaredFields()) {
            if(field.isAnnotationPresent(annotationClass))
                fields.add(field);
        }

        return fields;
    }

    public static List<Field> mapsFieldsWithBean(List<Field> fields, Object bean) {
        List<Field> mapFields = fields.stream()
                .filter(field -> field.getType().equals(bean.getClass()))
                .collect(Collectors.toList());
        return mapFields;
    }

    public static Method getSetter(Object object, Field field) throws NoSuchMethodException {
        String setterName = StringUtils.getSetterName(field.getName());

        return object.getClass().getMethod(setterName);
    }

    public static void inject(Object object, Field field, Object dependency) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method setter = getSetter(object, field);

        setter.invoke(object, dependency);
    }
}
