package com.scut.backend.uits;


import java.util.UUID;

public class UuidUtil {
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid;
    }

}
