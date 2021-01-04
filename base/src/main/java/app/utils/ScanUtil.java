package app.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author pickjob@126.com
 * @time 2019-04-23
 */
public class ScanUtil {
    private static final Logger logger = LogManager.getLogger(ScanUtil.class);

    public static Set<String> scanClassWithClass(Class<?> className) {
        return scanClassWithPackageAndClass(null, className);
    }

    public static Set<String> scanClassWithPackage(String basePackage) {
        return scanClassWithPackageAndClass(basePackage, null);
    }

    public static Set<String> scanClassWithPackageAndClass(String basePackage, Class targetClass) {
        Set<String> clsList = new HashSet<>();
        for (String clsName : scanAllClasses()) {
            if (StringUtils.isNotBlank(basePackage) && clsName.startsWith(basePackage + ".")) {
                try {
                    Class cls = Class.forName(clsName);
                    if (targetClass.isAssignableFrom(cls)) {
                        clsList.add(clsName);
                    }
                } catch (Throwable e) {
                    if (logger.isDebugEnabled()) logger.debug(e.getMessage(), e);
                }
            }
        }
        return clsList;
    }

    private static Set<String> scanAllClasses() {
        Set<String> clsList = new HashSet<>();
        String modulePath = System.getProperty(JDK_MODULE_PATH);
        if (logger.isDebugEnabled()) logger.debug("modulePath: {}", modulePath);
        if (StringUtils.isNotBlank(modulePath)) {
            String[] paths = modulePath.split(File.pathSeparator);
            for (int i = 0; i < paths.length; i++) {
                try {
                    File file = new File(paths[i]);
                    if (file.exists()) {
                        if (file.isDirectory()) {
                            clsList.addAll(scanClassesFromDirectory(file.getCanonicalPath(), file));
                        } else {
                            if (file.getName().endsWith(".jar")) {
                                JarFile jarFile = new JarFile(file);
                                clsList.addAll(scanClassesFromJarFile(jarFile));
                            }
                        }
                    }
                } catch (Exception e) {
                    if (logger.isDebugEnabled()) logger.error(e.getMessage(), e);
                }
            }
        }
        if (logger.isDebugEnabled()) logger.debug("clsList: {}", clsList);
        return clsList;
    }

    private static List<String> scanClassesFromDirectory(String rootPath, File directory) throws Exception {
        String currentPath = directory.getCanonicalPath();
        if (logger.isDebugEnabled()) logger.debug("scan directory: {}", currentPath);
        List<String> clsList = new ArrayList<>();
        File[] childFiles = directory.listFiles();
        for (File childFile : childFiles) {
            if (childFile.isDirectory()) {
                clsList.addAll(scanClassesFromDirectory(rootPath, childFile));
            } else {
                String fileName = childFile.getName();
                if (fileName.endsWith(".class") && !fileName.contains("$")) {
                    String clsName = currentPath
                            .substring(rootPath.length())
                            .replaceAll("/", ".")
                            .replaceAll("\\\\", ".")
                            + "." + fileName
                            ;
                    if (clsName.startsWith(".")) clsName = clsName.substring(1);
                    if (clsName.endsWith(".class")) clsName = clsName.substring(0, clsName.length() - 6);
                    clsList.add(clsName);
                } else if (fileName.endsWith(".jar")) {
                    JarFile jarFile = new JarFile(childFile);
                    clsList.addAll(scanClassesFromJarFile(jarFile));
                }
            }
        }
        return clsList;
    }

    private static List<String> scanClassesFromJarFile(JarFile jarFile) {
        if (logger.isDebugEnabled()) logger.debug("scan jarFile: {}", jarFile.getName());
        List<String> clsList = new ArrayList<>();
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            String entryName = jarEntry.getName();
            if (entryName.endsWith(".class") && !entryName.contains("$")) {
                String clsName = entryName
                        .replaceAll("/", ".")
                        .replaceAll("\\\\", ".");
                if (clsName.startsWith(".")) clsName = clsName.substring(1);
                if (clsName.endsWith(".class")) clsName = clsName.substring(0, clsName.length() - 6);
                clsList.add(clsName);
            }
        }
        return clsList;
    }

    static final String JDK_MODULE_PATH = "jdk.module.path";
}
