package com.pickupluck.ecogging.util;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryKeyValueStore {

    private final Map<String, String> keyValueStore = new ConcurrentHashMap<>();

    public void put(String key, String value) {
        keyValueStore.put(key, value);
    }

    public String get(String key) {
        return keyValueStore.get(key);
    }

    public void remove(String key) {
        keyValueStore.remove(key);
    }
}
