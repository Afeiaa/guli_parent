package com.atguigu.cms.service.impl;

import com.atguigu.cms.entity.CrmBanner;
import com.atguigu.cms.mapper.CrmBannerMapper;
import com.atguigu.cms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Cacheable(value = "banner", key = "'selectIndexList'")    // banner::selectIndexList
    @Override
    public List<CrmBanner> selectAllBanner() {
        List<CrmBanner> crmBannerList = baseMapper.selectList(null);
        if(crmBannerList != null) {
            return crmBannerList;
        }
        return null;
    }
}
