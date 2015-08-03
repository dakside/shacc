/*
 *  Copyright (C) 2010 Le Tuan Anh <tuananh.ke@gmail.com>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.dakside.utils;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Resource centre
 *
 * @author LeTuanAnh <tuananh.ke@gmail.com>
 */
public class ResourceCentre {

    private Locale locale;
    private String name;
    private ResourceBundle bundle;
    private static final Logger logger = Logger.getLogger(ResourceBundle.class.getName());

    /**
     * Set application global locale
     *
     * @param locale
     */
    public static final void setGlobalLocale(Locale locale) {
        if (locale != null) {
            Locale.setDefault(locale);
            logger.log(Level.INFO, "Loaded language: {0}", getDefaultLocale());
        }
    }

    private static final Locale getDefaultLocale() {
        return Locale.getDefault();
    }

    /**
     * Hide constructor from outside
     *
     * @param name
     * @param locale
     */
    private ResourceCentre(String name, Locale locale) {
        this.name = name;
        this.locale = locale;
    }
    private static final HashMap<String, ResourceCentre> instances = new HashMap<String, ResourceCentre>();

    /**
     * Get resource manager for an object (context) with default locale)
     *
     * @param context
     * @return
     */
    public static ResourceCentre getInstance(Object context) {
        return getInstance(context, getDefaultLocale());
    }

    /**
     * Locate resource manager object
     *
     * @param context
     * @param locale
     * @return
     */
    public static ResourceCentre getInstance(Object context, Locale locale) {
        if (context == null) {
            return null;
        } else {
            Class<?> cls = context.getClass();
            String pkg = cls.getPackage().getName();
            String fullName = (pkg.isEmpty()) ? "resources." + cls.getSimpleName() : pkg + ".resources."
                    + cls.getSimpleName();

            return getInstance(fullName, locale);
        }
    }

    /**
     * Locate resource manager object
     *
     * @param context
     * @param resourceName
     * @param locale
     * @return
     */
    public static ResourceCentre getInstance(Class<?> context,
            String resourceName, Locale locale) {
        String pkg = context.getPackage().getName();
        String fullName = (pkg.isEmpty()) ? resourceName : pkg + "."
                + resourceName;

        return getInstance(fullName, locale);
    }

    public static ResourceCentre getInstance(String fullName) {
        return getInstance(fullName, getDefaultLocale());
    }

    public static ResourceCentre getInstance(String fullName, Locale locale) {
        String key = (locale != null) ? fullName + "_" + locale.toString()
                : fullName;

        if (!instances.containsKey(key)) {
            ResourceCentre instance = new ResourceCentre(fullName, locale);
            instances.put(key, instance);
        }
        return instances.get(key);
    }

    /**
     * Get resource bundle
     *
     * @return
     */
    public ResourceBundle getBundle() {
        if (bundle == null) {
            try {
                bundle = ResourceBundle.getBundle(name, locale);
            } catch (MissingResourceException mrex) {
                //cannot find bundle
                logger.log(Level.WARNING, "Cannot get bundle." + "(name=[" + name + "]locale=[" + locale + "])", mrex.getMessage());
                // Try to get a default bundle
                try {
                    if (bundle == null) {
                        bundle = ResourceBundle.getBundle(name);
                    }
                } catch (Exception e) {
                }
            }
        }
        return bundle;
    }

    /**
     * Get a string
     *
     * @param key
     * @return
     */
    public String getString(String key) {
        if (key == null || key.trim().isEmpty()) {
            return "";
        } else if (getBundle() == null) {
            return key;
        } else if (getBundle().containsKey(key)) {
            return getBundle().getString(key);
        } else {
            return key;
        }
    }

    public boolean containsKey(String key) {
        return getBundle() != null && getBundle().containsKey(key);
    }

    /**
     * Get config as a line array
     *
     * @param key
     * @return empty array if cannot find resource
     */
    public String[] getLines(String key) {
        return getString(key).split("\n");
    }

    /**
     * Format message using key<br/>
     * key + args = localized message string
     *
     * @param patternKey
     * @param args
     * @return
     */
    public String getMessage(String patternKey, Object[] args) {
        MessageFormat formatter = new MessageFormat("");
        formatter.setLocale(locale);
        formatter.applyPattern(getBundle().getString(patternKey));

        return formatter.format(args);
    }
}
