package com.adam.rec.user;

import com.adam.rec.jdbc.JdbcUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

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
    int getMaxUserId() {
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
    Boolean writeUser(User user) {
//        INSERT INTO REC_USER VALUES(USERID.NEXTVAL,'FSDA','ASFD','FSDA','FASD','FASD','FDAS')
        String sql = "INSERT INTO REC_USER VALUES(TO_NUMBER(USERID.NEXTVAL),'"+user.getName()+"','"+
                user.getSex()+"','"+user.getBirthDateString()+"','"+user.getProfession()+"','"+
                user.getArea()+"','"+user.getInterestsString()+"')";

        int result = jdbcUtil.executeUpdate(sql);
        return result >= 0 || result == Statement.SUCCESS_NO_INFO;

    }

}
