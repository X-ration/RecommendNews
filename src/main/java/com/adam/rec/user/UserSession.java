package com.adam.rec.user;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * @author adam
 * 创建于 2018-04-16 15:06.
 */
@Component
@Scope(value = "session",proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserSession {

    private User userSession;
    private Boolean isConnected = false;

    public User getUserSession() {
        return userSession;
    }

    public int getUserIdSession() {
        if(userSession != null)  return userSession.getUserId();
        return -1;
    }

    public Boolean isConnected() {
        return isConnected;
    }

    public void login(User user) {
        this.userSession = user;
        isConnected = true;
    }

    public void logout() {
        this.userSession = null;
        isConnected = false;
    }

}
