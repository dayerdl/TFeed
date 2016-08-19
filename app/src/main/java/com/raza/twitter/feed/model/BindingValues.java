package com.raza.twitter.feed.model;

import java.util.Collections;
import java.util.Map;

/**
 * Created by jaffarraza on 19/08/16.
 */
public class BindingValues {

    private final Map<String, Object> bindingValues;

    public BindingValues() {
        this(Collections.EMPTY_MAP);
    }

    public BindingValues(Map<String, Object> bindingValues) {
        this.bindingValues = Collections.unmodifiableMap(bindingValues);
    }

    /**
     * Returns {@code true} if specified key exists.
     */
    public boolean containsKey(String key) {
        return bindingValues.containsKey(key);
    }

    /**
     * Returns the value for the specified key. Returns {@code null} if key does not exist, or
     * object cannot be cast to return type.
     */
    public <T> T get(String key) {
        try {
            return (T) bindingValues.get(key);
        } catch (ClassCastException ex){
            return null;
        }
    }
}

