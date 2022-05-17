package com.polaris.exam.service;

import java.io.InputStream;

/**
 * 七牛云对象存储
 * @author polaris
 */

public interface OssAdminService {
    /**
     * 七牛云上传图片
     * @param file FileInputStream
     * @param key String
     * @return String
     */
    public String uploadImage(InputStream file, String key);
}
