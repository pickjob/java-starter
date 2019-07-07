package utils;

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

    public static Set<String> scanClassWithPackageAndClass(String basePackage, Class className) {
        Set<String> clsList = new HashSet<>();
        for (String clsName : scanAllClasses()) {
            if (StringUtils.isBlank(basePackage) || clsName.startsWith(basePackage)) {
                try {
                    Class cls = Class.forName(clsName);
                    if (!cls.isInterface()) {
                        Object obj = cls.getDeclaredConstructor().newInstance();
                        if (className == null || className.isInstance(obj)) {
                            if (logger.isDebugEnabled()) logger.debug("checkout clsName: {}", clsName);
                            clsList.add(clsName);
                        }
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
        try {
            String modulePath = System.getProperty(JDK_MODULE_PATH);
            if (StringUtils.isNotBlank(modulePath)) {
                String[] paths = modulePath.split(File.pathSeparator);
                for (int i = 0; i < paths.length; i++) {
                    File file = new File(paths[i]);
                    if (file.exists()) {
                        if (logger.isDebugEnabled())  logger.debug("scan file {}", paths[i]);
                        if (file.isDirectory()) {
                            clsList.addAll(scanClassesFromDirectory(paths[i], file));
                        } else {
                            JarFile jarFile = new JarFile(file);
                            clsList.addAll(scanClassesFromJarFile(jarFile));
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return clsList;
    }

    private static List<String> scanClassesFromDirectory(String rootPath, File directory) {
        List<String> clsList = new ArrayList<>();
        File[] childFiles = directory.listFiles();
        for (File childFile : childFiles) {
            if (childFile.isDirectory()) {
                clsList.addAll(scanClassesFromDirectory(rootPath, childFile));
            } else {
                String fileName = childFile.getName();
                if (fileName.endsWith(".class") && !fileName.contains("$")) {
                    String clsName = childFile.getPath()
                            .substring(rootPath.length())
                            .replaceAll("\\".equals(File.separator) ? "\\\\":File.separator, ".")
                            ;
                    if (clsName.startsWith(".")) clsName = clsName.substring(1);
                    if (clsName.endsWith(".class")) clsName = clsName.substring(0, clsName.length() - 6);
                    if (logger.isDebugEnabled())  logger.debug("{} => {}", childFile.getPath(), clsName);
                    clsList.add(clsName);
                }
            }
        }
        return clsList;
    }

    private static List<String> scanClassesFromJarFile(JarFile jarFile) throws Exception {
        List<String> clsList = new ArrayList<>();
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            String entryName = jarEntry.getName();
            if (entryName.endsWith(".class") && !entryName.contains("$")) {
                String clsName = entryName.replaceAll("\\".equals(File.separator) ? "\\\\":File.separator, ".");
                if (clsName.startsWith(".")) clsName = clsName.substring(1);
                if (clsName.endsWith(".class")) clsName = clsName.substring(0, clsName.length() - 6);
                if (logger.isDebugEnabled())  logger.debug("{} => {}", entryName, clsName);
                clsList.add(clsName);
            }
        }
        return clsList;
    }

    static final String JDK_MODULE_PATH = "jdk.module.path";
    static final String PATH_SEPARATOR = "path.separator";
}
