package com.adam.rec.user;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author adam
 * 创建于 2018-04-16 11:11.
 */
public class UserUtil {

    public static User convertToUser(UserForm userForm) {
        User user = new User();
        user.setUserId(0);
        user.setName(userForm.getName());
        user.setSex((userForm.getSex().contains("男"))?"M":"F");
        user.setBirthDate(LocalDate.parse(userForm.getBirthDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        user.setProfession(userForm.getProfession());
        user.setArea(userForm.getArea());
        user.setInterests(generateInterests(userForm.getInterests()));
        return user;
    }

    private static List<String> generateInterests(String interestsString) {
        if(interestsString == null || interestsString.equals("")) return new ArrayList<>();
        else {
            return Arrays.asList(interestsString.split(","));
        }
    }


}
