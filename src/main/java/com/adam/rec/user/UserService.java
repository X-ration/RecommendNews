package com.adam.rec.user;

/**
 * @author adam
 * 创建于 2018-04-16 09:00.
 */
public abstract class UserService {

    public abstract int getMaxUserId();
    public abstract Boolean writeUser(User user);
    public abstract Boolean updateUser(User user);
    public abstract Boolean checkUser(String username, String password);
    public abstract User getUserByName(String username);
    public abstract int getUserIdByName(String username);
    public abstract String getUserCityByName(String username);

}
