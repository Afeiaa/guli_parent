package com.atguigu.vod.test;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.atguigu.vod.utils.initVod;

import java.util.List;

public class VodTestNoPwd {

    /*获取播放地址函数*/
    public static GetPlayInfoResponse getPlayInfo(DefaultAcsClient client) throws Exception {
        // 新建request
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        // 设置id，阿里云上的视频id
        request.setVideoId("959d598a20864b668e5ce92ae4b90081");
        // 得到response
        return client.getAcsResponse(request);
    }

    /*以下为调用示例*/
    public static void main(String[] argv) throws ClientException {
        // 创建初始化对象
        DefaultAcsClient client = initVod.initVodClient("key", "pwd");
        // 新建response对象
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        try {
            // 获取response
            response = getPlayInfo(client);
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            // 得到播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
            }
            // Base信息
            System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

}
