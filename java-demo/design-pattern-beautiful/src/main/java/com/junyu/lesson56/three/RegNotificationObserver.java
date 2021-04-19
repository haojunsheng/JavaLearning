package com.junyu.lesson56.three;


/**
 * @author haojunsheng
 * @date 2021/4/18 23:55
 */
public class RegNotificationObserver implements RegObserver {
    private NotificationService notificationService;

    @Override
    public void handleRegSuccess(long userId) {
        notificationService.sendInboxMessage(userId, "Welcome...");
    }
}
