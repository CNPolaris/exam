package com.polaris.exam.security.annotation;

import java.lang.annotation.*;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheException {
}
