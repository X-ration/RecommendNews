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
        user.setPassword(userForm.getPassword());
        user.setName(userForm.getName());
        user.setSex((userForm.getSex().contains("男"))?"M":"F");
        user.setBirthDate(LocalDate.parse(userForm.getBirthDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        user.setProfession(userForm.getProfession());
        user.setArea(userForm.getArea());
        user.setInterests(generateInterests(userForm.getInterests()));
        return user;
    }

    public static UserForm convertToUserForm(User user) {
        UserForm userForm = new UserForm();
        userForm.setName(user.getName());
        userForm.setSex(user.getSex());
        userForm.setBirthDate(user.getBirthDateString());
        userForm.setProfession(user.getProfession());
        userForm.setArea(user.getArea());
        userForm.setInterests(user.getInterestsString());
        return userForm;
    }

    public static String analyzeUserFormValidity(UserForm userForm) {
        if(userForm.getName().length()>12) return "用户名应限制在12个字符以内";
        if(!userForm.getPassword().equals(userForm.getConfirmPassword())) return "前后输入的密码不一致";
        if(!(userForm.getSex().contains("男") || userForm.getSex().contains("女"))) return "性别输入错误";
        if(userForm.getInterests() == null || userForm.getInterests().equals("")) return "请先指定兴趣标签";
        return null;
    }

    private static List<String> generateInterests(String interestsString) {
        if(interestsString == null || interestsString.equals("")) return new ArrayList<>();
        else {
            return Arrays.asList(interestsString.split(","));
        }
    }


}
