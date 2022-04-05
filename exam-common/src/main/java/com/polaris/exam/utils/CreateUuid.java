package com.polaris.exam.utils;

import java.util.UUID;

/**
 * @author CNPolaris
 * @version 1.0
 */
public class CreateUuid {
    public static String createUuid(){
        return UUID.randomUUID().toString();
    }
}
