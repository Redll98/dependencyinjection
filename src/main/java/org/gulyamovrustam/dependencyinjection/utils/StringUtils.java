package org.gulyamovrustam.dependencyinjection.utils;

import java.lang.reflect.Field;

public class StringUtils {
    private StringUtils() {}

    /*public static String getSetterName(Field propertyField) {
        return getSetterName(propertyField.getName());
    }*/

    public static String getSetterNameByFullClassName(String fullClassName) {
        String className = getClassNameByFullClassName(fullClassName);

        return "set" + className;
    }

    public static String getSetterName(String fieldName) {
        return "set" + fieldName.substring(0, 1).toUpperCase() +
                fieldName.substring(1);
    }

    public static String getSetterName(Object propertyInstance) {
        return "set" + getClassNameByObject(propertyInstance);
    }

    /*public static String getPropertyName(Object property) {
        String fullClassName = property.getClass().getName();
        String className = getClassNameByFullClassName(fullClassName);

        return className.substring(0, 1).toLowerCase() + className.substring(1);
    }*/

    public static String getClassNameByObject(Object object) {
        String fullClassName = object.getClass().getName();

        return getClassNameByFullClassName(fullClassName);
    }

    public static String getClassNameByFullClassName(String fullClassName) {
        return fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
    }
}
