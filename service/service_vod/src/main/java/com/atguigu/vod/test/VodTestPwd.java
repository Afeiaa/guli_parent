package com.atguigu.vod.test;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.vod.utils.initVod;


public class VodTestPwd {

    /*获取播放凭证函数*/
    public static GetVideoPlayAuthResponse getVideoPlayAuth(DefaultAcsClient client) throws Exception {
        // 获取 request-凭证
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        // 阿里云 id
        request.setVideoId("959d598a20864b668e5ce92ae4b90081");
        // 返回 response
        return client.getAcsResponse(request);
    }

    /*以下为调用示例*/
    public static void main(String[] argv) throws ClientException {
        // 初始化对象
        DefaultAcsClient client = initVod.initVodClient("key", "pwd");
        // 新建 response
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        try {
            // 获取response
            response = getVideoPlayAuth(client);
            // 打印播放凭证
            System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
            // 打印VideoMeta信息
            System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }




}
