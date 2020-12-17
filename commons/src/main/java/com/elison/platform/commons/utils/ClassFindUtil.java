package com.elison.platform.commons.utils;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @ProjectName: platform
 * @Package: com.elison.platform.commons.utils
 * @Description: -
 * @Author: elison
 * @CreateDate: 2020/12/3 14:05
 * @UpdateDate: 2020/12/3 14:05
 **/
public class ClassFindUtil {
    public static <T> List<Class<T>> getSonClassList(Class<T> clazz) throws Exception {
        List<Class<T>> classList = new ArrayList<>();
        // 设置class文件所在根路径
        // 例如/usr/java/classes下有一个test.App类，则/usr/java/classes即这个类的根路径，而.class文件的实际位置是/usr/java/classes/test/App.class
        File clazzPath = new File(clazz.getResource("").getPath());

        // 记录加载.class文件的数量
        int clazzCount = 0;

        if (clazzPath.exists() && clazzPath.isDirectory()) {
            // 获取路径长度
            int clazzPathLen = clazzPath.getAbsolutePath().length() + 1;

            Stack<File> stack = new Stack<>();
            stack.push(clazzPath);

            // 遍历类路径
            while (!stack.isEmpty()) {
                File path = stack.pop();
                File[] classFiles = path.listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        return pathname.isDirectory() || pathname.getName().endsWith(".class");
                    }
                });
                for (File subFile : classFiles) {
                    if (subFile.isDirectory()) {
                        stack.push(subFile);
                    } else {
                        if (clazzCount++ == 0) {
                            Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                            boolean accessible = method.isAccessible();
                            try {
                                if (accessible == false) {
                                    method.setAccessible(true);
                                }
                                // 设置类加载器
                                URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
                                // 将当前类路径加入到类加载器中
                                method.invoke(classLoader, clazzPath.toURI().toURL());
                            } finally {
                                method.setAccessible(accessible);
                            }
                        }
                        // 文件名称
                        String className = subFile.getAbsolutePath();
                        className = className.substring(clazzPathLen, className.length() - 6);
                        className = className.replace(File.separatorChar, '.');
                        // 加载Class类
                        Class clz = Class.forName(clazz.getPackage().getName() + "." + className);
                        if (clazz.isAssignableFrom(clz)) {
                            classList.add(clz);
                        }
                    }
                }
            }
        }
        return classList;
    }
}
