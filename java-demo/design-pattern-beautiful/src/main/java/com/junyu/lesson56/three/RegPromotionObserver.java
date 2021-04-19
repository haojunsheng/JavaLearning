package com.junyu.lesson56.three;

import com.junyu.lesson56.second.PromotionService;

/**
 * @author haojunsheng
 * @date 2021/4/18 23:55
 */
public class RegPromotionObserver implements RegObserver {
    private PromotionService promotionService; // 依赖注入

    @Override
    public void handleRegSuccess(long userId) {
        promotionService.issueNewUserExperienceCash(userId);
    }
}
