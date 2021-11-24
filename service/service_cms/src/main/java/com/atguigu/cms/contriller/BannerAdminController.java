package com.atguigu.cms.contriller;


import com.atguigu.cms.entity.CrmBanner;
import com.atguigu.cms.service.CrmBannerService;
import com.atguigu.commonutils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
//@CrossOrigin
@RequestMapping("/educms/banneradmin")
public class BannerAdminController {

    @Autowired
    private CrmBannerService crmBannerService;

    // 增加banner
    @PostMapping("/addBanner")
    public R saveBanner(@RequestBody CrmBanner crmBanner) {
        boolean flag = crmBannerService.save(crmBanner);
        if(flag == false) {
            return R.error().message("添加失败，请重新尝试！");
        }
        return R.ok();
    }

    // 修改banner
    @PostMapping("/updateBanner")
    public R updateBannerById(@RequestBody CrmBanner crmBanner) {
        boolean flag = crmBannerService.updateById(crmBanner);
        if(flag == false) {
            return R.error().message("更新失败，请重新尝试！");
        }
        return R.ok();
    }

    // 根据id查询banner
    @GetMapping("/getBanner/{bannerId}")
    public R getBannerById(@PathVariable("bannerId") String bannerId) {
        CrmBanner banner = crmBannerService.getById(bannerId);
        if(banner == null) {
            return R.error().message("无此Banner！！");
        }
        return R.ok().data("banner", banner);
    }

    // 分页查询banner
    @PostMapping("/getBannerPage/{current}/{limit}")
    public R getBannerPage(@PathVariable("current") long current,
                           @PathVariable("limit") long limit) {
        Page<CrmBanner> bannerPage = new Page<>(current, limit);
        crmBannerService.page(bannerPage, null);
        if(bannerPage == null) {
            return R.error().message("无法查询到数据！");
        }
        return R.ok()
                .data("total", bannerPage.getTotal())
                .data("items", bannerPage.getRecords());
    }

    // 删除banner
    @DeleteMapping("/deleteBanner/{bannerId}")
    public R deleteBannerById(@PathVariable("bannerId") String bannerId) {
        boolean flag = crmBannerService.removeById(bannerId);
        if(flag == false) {
            return R.error().message("删除失败！");
        }
        return R.ok();
    }

}
