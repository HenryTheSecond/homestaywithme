package com.homestaywithme.application.redis.key;

public class HomestayKey {
    private HomestayKey() {
        throw new IllegalStateException();
    }

    public static String getHomestayKey(Long id) {
        return "homestay:" + id;
    }
}
