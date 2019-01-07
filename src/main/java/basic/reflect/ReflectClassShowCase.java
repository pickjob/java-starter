package basic.reflect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectClassShowCase {
    private static Logger logger = LogManager.getLogger(ReflectClassShowCase.class);

    public static void main(String[] args) {
        try {
            for (String className : classNames) {
                logger.info("=======reflect start============");
                Class<?> c = Class.forName(className);
                logger.info("Class:{}", c.getCanonicalName());
                logger.info("Modifiers:{}", Modifier.toString(c.getModifiers()));
                TypeVariable[] tv = c.getTypeParameters();
                if (tv.length != 0) {
                    String[] names = new String[tv.length];
                    for (int i = 0; i < tv.length; i++)
                        names[i] = tv[i].getName();
                    logger.info("Type Parameters:{}", Arrays.toString(names));
                } else {
                    logger.info("  -- No Type Parameters --");
                }
                Type[] intfs = c.getGenericInterfaces();
                if (intfs.length != 0) {
                    String[] names = new String[intfs.length];
                    for (int i = 0; i < intfs.length; i++)
                        names[i] = intfs[i].getTypeName();
                    logger.info("Implemented Interfaces:{}", Arrays.toString(names));
                } else {
                    logger.info("  -- No Implemented Interfaces --");
                }
                List<Class> l = new ArrayList<Class>();
                printAncestor(c, l);
                if (l.size() != 0) {
                    String[] names = new String[l.size()];
                    for (int i = 0; i < l.size(); i++)
                        names[i] = l.get(i).getCanonicalName();
                    logger.info("Inheritance Path:{}", Arrays.toString(names));
                } else {
                    logger.info("  -- No Super Classes --");
                }
                Annotation[] ann = c.getAnnotations();
                if (ann.length != 0) {
                    String[] names = new String[ann.length];
                    for (int i = 0; i < l.size(); i++)
                        names[i] = ann[i].annotationType().getCanonicalName();
                    logger.info("Annotations:{}", Arrays.toString(names));
                } else {
                    logger.info("  -- No Annotations --");
                }
                printMembers(c.getConstructors());
                printMembers(c.getFields());
                printMembers(c.getMethods());
                logger.info("=======basic.reflect end============");
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    private static void printAncestor(Class<?> c, List<Class> l) {
        Class<?> ancestor = c.getSuperclass();
        if (ancestor != null) {
            l.add(ancestor);
            printAncestor(ancestor, l);
        }
    }

    private static void printMembers(Member[] mbrs) {
        for (Member mbr : mbrs) {
            if (mbr instanceof Field)
                logger.info("Fields:{} Modifier:{}", ((Field)mbr).toGenericString(),Modifier.toString(mbr.getModifiers()));
            else if (mbr instanceof Constructor) {
                logger.info("Constructor:{} Modifier:{}", ((Constructor) mbr).toGenericString(), Modifier.toString(mbr.getModifiers()));
                logger.info("\tParameterType:{} GenericParameterType:{}", ((Constructor) mbr).getParameterTypes(), ((Constructor) mbr).getGenericParameterTypes());
            }else if (mbr instanceof Method) {
                logger.info("Methods:{} Modifier:{}", ((Method) mbr).toGenericString(), Modifier.toString(mbr.getModifiers()));
                logger.info("\tReturnType:{} GenericReturnType:{}", ((Method) mbr).getReturnType(), ((Method) mbr).getGenericReturnType());
                logger.info("\tParameterType:{} GenericParameterType:{}", ((Method) mbr).getParameterTypes(), ((Method) mbr).getGenericParameterTypes());
            }
        }
    }

    private static String[] classNames = {"java.util.concurrent.ConcurrentNavigableMap",
            "java.io.InterruptedIOException",
            "java.security.Identity"};
}