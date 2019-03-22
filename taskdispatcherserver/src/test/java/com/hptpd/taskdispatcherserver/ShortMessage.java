package com.hptpd.taskdispatcherserver;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-03-21 10:10
 * \*
 * \* Description:
 * \
 *
 * @author liucheng
 */
public class ShortMessage {

    //初始化ascClient需要的几个参数
    final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
    final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
    //替换成你的AK
    final String accessKeyId = "LTAIGya4xgre5Ppj";//你的accessKeyId,参考本文档步骤2
    final String accessKeySecret = "426HxRIt17gfQd8QDFbpqfdjtCHAoq";//你的accessKeySecret，参考本文档步骤2

    public void sendMsg(){
        DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        int random = (int) ((Math.random()*9+1)*100000);
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setProduct(product);
        request.setDomain(domain);
        request.setVersion("2017-05-25");
        request.setAction("QuerySendDetails");

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }



}
