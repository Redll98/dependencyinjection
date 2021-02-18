package org.example;

import org.gulyamovrustam.dependencyinjection.beans.factory.BeanFactory;
import org.gulyamovrustam.dependencyinjection.utils.StringUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, IOException, URISyntaxException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        System.out.println(StringUtils.getClassNameByObject(new ProductService()));
    }
}
