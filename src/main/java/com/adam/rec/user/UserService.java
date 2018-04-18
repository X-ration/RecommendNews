package com.adam.rec.user;

/**
 * @author adam
 * 创建于 2018-04-16 09:00.
 */
public abstract class UserService {

    public abstract int getMaxUserId();
    public abstract Boolean writeUser(User user,String password);
    public abstract Boolean checkUser(String username, String password);
    public abstract User getUserByName(String username);

}
