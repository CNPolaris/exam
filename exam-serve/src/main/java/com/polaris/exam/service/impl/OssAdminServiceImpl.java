package com.polaris.exam.service.impl;

import com.google.gson.Gson;
import com.polaris.exam.service.OssAdminService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.InputStream;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Service
public class OssAdminServiceImpl implements OssAdminService {
    @Value("${oss.qiniu.domain}")
    private String domain;
    @Value("${oss.qiniu.accessKey}")
    private String accessKey;
    @Value("${oss.qiniu.secretKey}")
    private String secretKey;
    @Value("${oss.qiniu.bucket}")
    private String bucket;
    @Override
    public String uploadImage(InputStream file, String key) {
        try{
            Auth auth = Auth.create(accessKey, secretKey);
            String token = auth.uploadToken(bucket);
            Configuration cfg = new Configuration();
            UploadManager uploadManager = new UploadManager(cfg);
            Response response = uploadManager.put(file, key, token, null, null);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return domain+"/"+putRet.key;
        } catch (QiniuException e){
            e.printStackTrace();
        }

        return null;
    }
}
