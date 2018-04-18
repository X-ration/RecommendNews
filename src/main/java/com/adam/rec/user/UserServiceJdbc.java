package com.adam.rec.user;

import com.adam.rec.jdbc.JdbcUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.reflect.runtime.SynchronizedSymbols;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * @author adam
 * 创建于 2018-04-16 09:01.
 */
@Service
public class UserServiceJdbc extends UserService{

    private JdbcUtil jdbcUtil;
    @Autowired
    public UserServiceJdbc(JdbcUtil jdbcUtil){
        this.jdbcUtil = jdbcUtil;
    }

    @Override
    public int getMaxUserId() {
        ResultSet resultSet = jdbcUtil.executeQuery("SELECT MAX(user_id) FROM REC_USER");
        int result = 1;
        try {
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Boolean writeUser(User user,String password) {
//        INSERT INTO REC_USER VALUES(USERID.NEXTVAL,'FSDA','ASFD','FSDA','FASD','FASD','FDAS')
        String sql = "INSERT INTO REC_USER VALUES(TO_NUMBER(USERID.NEXTVAL),'"+password+"','"+user.getName()+"','"+
                user.getSex()+"','"+user.getBirthDateString()+"','"+user.getProfession()+"','"+
                user.getArea()+"','"+user.getInterestsString()+"')";

        int result = jdbcUtil.executeUpdate(sql);
        return result >= 0 || result == Statement.SUCCESS_NO_INFO;

    }

    @Override
    public Boolean checkUser(String username, String password) {
        String sql = "SELECT COUNT(*) FROM (SELECT * FROM rec_user WHERE name='" + username + "' AND password='" + password + "')";

        try {
            ResultSet resultSet = jdbcUtil.executeQuery(sql);
            int count = 0;
            if(resultSet.next()) {
                count = resultSet.getInt(1);
            }
            if(count == 1) {
                System.out.println("用户名密码校验正确");
                return true;
            } else if (count > 1) {
                System.out.println("用户名密码正确但存在多种可能，不予通过");
                return false;
            } else {
                System.out.println("用户名密码错误");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据用户名查询数据库获取User对象。由于在数据库设置了用户名唯一约束，因此查询结果应当只有一条。
     * @param username 用户名
     * @return 根据用户名从数据库中查询得到的User对象
     */
    @Override
    public User getUserByName(String username) {
        User user = null;
        String sql = "SELECT * FROM REC_USER WHERE NAME='"+username+"'";
        try {
            ResultSet resultSet = jdbcUtil.executeQuery(sql);
            if(resultSet.next()) {
                user = new User();
                user.setUserId(resultSet.getInt(1));
                user.setPassword(resultSet.getString(2));
                user.setName(resultSet.getString(3));
                user.setSex(resultSet.getString((4)));
                user.setBirthDate(LocalDate.parse(resultSet.getString(5), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                user.setProfession(resultSet.getString(6));
                user.setArea(resultSet.getString((7)));
                user.setInterests(Arrays.asList(resultSet.getString(8).split(",")));
            }
        } catch (SQLException e) {
            System.out.println("无法正常查询得到用户对象");
        }
        return user;
    }

    @Override
    public int getUserIdByName(String username) {
        int userId = -1;
        String sql = "SELECT user_id FROM REC_USER WHERE NAME='"+username+"'";
        try {
            ResultSet resultSet = jdbcUtil.executeQuery(sql);
            if(resultSet.next()) {
                userId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("无法正常查询得到用户对象");
        }
        return userId;
    }

    @Override
    public String getUserCityByName(String username) {
        String city = "";
        String sql = "SELECT area FROM REC_USER WHERE name='"+username+"'";
        try {
            ResultSet resultSet = jdbcUtil.executeQuery(sql);
            if(resultSet.next()) {
                city = resultSet.getString(1);
            }
        } catch (SQLException e) {
            System.out.println("无法正常查询得到用户对象");
        }
        return city;
    }

}
