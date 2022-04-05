package com.polaris.exam.security.component;


import org.springframework.security.access.ConfigAttribute;

import java.util.Map;
/**
 * @author CNPolaris
 * @version 1.0
 */
public interface DynamicSecurityService {
    /**
     * 加载资源ANT通配符和资源对应MAP
     * @return Map<String, ConfigAttribute>
     */
    Map<String, ConfigAttribute> loadDataSource();
}
