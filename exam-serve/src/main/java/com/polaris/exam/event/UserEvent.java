package com.polaris.exam.event;

import com.polaris.exam.pojo.UserEventLog;
import org.springframework.context.ApplicationEvent;

/**
 * @author CNPolaris
 * @version 1.0
 */
public class UserEvent extends ApplicationEvent {

    private final UserEventLog userEventLog;

    public UserEvent(final UserEventLog userEventLog) {
        super(userEventLog);
        this.userEventLog = userEventLog;
    }

    public UserEventLog getUserEventLog() {
        return userEventLog;
    }
}
