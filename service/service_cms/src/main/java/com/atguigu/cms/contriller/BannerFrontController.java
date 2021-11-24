package com.atguigu.cms.contriller;


import com.atguigu.cms.entity.CrmBanner;
import com.atguigu.cms.service.CrmBannerService;
import com.atguigu.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@CrossOrigin
@RequestMapping("/educms/bannerfront")
public class BannerFrontController {

    @Autowired
    private CrmBannerService crmBannerService;

    // 查询所有banner
    @GetMapping("/getAllBanner")
    public R getAllBanner() {
        List<CrmBanner> list = crmBannerService.selectAllBanner();
        return R.ok().data("items", list);
    }

}
