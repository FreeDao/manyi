/**
 * 
 */
package com.leo.jaxrs.fault;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author leili
 * 
 */
public abstract class FaultUtil {

    // private static final Logger logger =
    // LoggerFactory.getLogger(FaultUtil.class);

    // private final static ResourceBundle rb =
    // ResourceBundle.getBundle(FaultUtil.class.getPackage().getName()+
    // ".RcResource");
    // private final static ResourceBundle rb =
    // ResourceBundle.getBundle("META-INF.rc",);
    // private final static List<ResourceBundle> list = new
    // ArrayList<ResourceBundle>();
    private final static String RESOURCE_BUNDLE_FILE_NAME = "error";
    private final static Locale DEFUALT_LANGUAGE = new Locale("en_US");
    private final static Map<Locale, ResourceBundle> rbMap = new HashMap<Locale, ResourceBundle>(3);

    protected static ResourceBundle getRcResourceBundle(Locale locale) {
        if (locale == null)
            locale = DEFUALT_LANGUAGE;

        ResourceBundle rb = rbMap.get(locale);

        if (rb == null) {
            rb = ResourceBundle.getBundle(RESOURCE_BUNDLE_FILE_NAME, locale);
            rbMap.put(locale, rb);
        }

        return rb;
    }

    protected static ResourceBundle getRcResourceBundle() {
        return getRcResourceBundle(null);
    }

    public static String getValue(String key) {
        return getValue(null, key);

    }

    public static String getValue(Locale locale, String key) {
        if (locale == null)
            locale = DEFUALT_LANGUAGE;
        try {
            return FaultUtil.getRcResourceBundle(locale).getString(key);
        } catch (Exception e) {
            return "";
        }

    }

}
