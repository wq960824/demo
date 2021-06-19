package com.example.demo.tools;

import java.util.UUID;

public class CodeUtils {
    public static  String uuid() {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            return uuid;
    }

}
