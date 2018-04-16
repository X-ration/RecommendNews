package com.adam.rec.user;

/**
 * @author adam
 * 创建于 2018-04-16 09:00.
 */
public abstract class UserService {

    abstract int getMaxUserId();
    abstract Boolean writeUser(User user,String password);
    abstract Boolean checkUser(String username, String password);

}
