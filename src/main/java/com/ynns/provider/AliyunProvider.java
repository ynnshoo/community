package com.ynns.provider;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class AliyunProvider {
    //连接区域地址
    @Value("${endpoint}")
    private String endpoint;

    //需要存储的bucketName
    @Value("${bucketName}")
    private String bucketName ;

    //图片保存路径
    @Value("${picLocation}")
    private  String picLocation;

    //连接keyId
    @Value("${accessKeyId}")
    private String accessKeyId;

    //连接秘钥
    @Value("${accessKeySecret}")
    private String accessKeySecret;

    //
    public String upload(){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建PutObjectRequest对象。
        // 填写Bucket名称、Object完整路径和本地文件的完整路径。Object完整路径中不能包含Bucket名称。
        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件。
        PutObjectRequest putObjectRequest = new PutObjectRequest("examplebucket", "exampleobject.txt", new File("D:\\localpath\\examplefile.txt"));

        // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
        // ObjectMetadata metadata = new ObjectMetadata();
        // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        // metadata.setObjectAcl(CannedAccessControlList.Private);
        // putObjectRequest.setMetadata(metadata);

        // 上传文件。
        ossClient.putObject(putObjectRequest);

        // 关闭OSSClient。
        ossClient.shutdown();
        return "";
    }

}
