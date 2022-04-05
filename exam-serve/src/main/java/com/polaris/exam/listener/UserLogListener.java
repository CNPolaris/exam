package com.polaris.exam.listener;

import com.polaris.exam.event.UserEvent;
import com.polaris.exam.service.IUserEventLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author CNPolaris
 * @version 1.0
 */
@Component
public class UserLogListener implements ApplicationListener<UserEvent> {
    private final IUserEventLogService userEventLogService;
    @Autowired
    public UserLogListener(IUserEventLogService userEventLogService) {
        this.userEventLogService = userEventLogService;
    }

    @Override
    public void onApplicationEvent(UserEvent event) {
        userEventLogService.insertEventLog(event.getUserEventLog());
    }
}
