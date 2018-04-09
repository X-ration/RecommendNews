package com.adam.rec.user;

import java.time.LocalDate;
import java.util.List;

/**
 * @author adam
 * 创建于 2018-04-09 20:29.
 */
public class User {

    private int userId;
    private String name;
    private String sex;
    private LocalDate birthDate;
    private String profession;
    private String company;
    private String area;
    private List<String> interests;

    public User(int userId, String name, String sex, LocalDate birthDate, String profession, String company, String area, List<String> interests) {
        this.userId = userId;
        this.name = name;
        this.sex = sex;
        this.birthDate = birthDate;
        this.profession = profession;
        this.company = company;
        this.area = area;
        this.interests = interests;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
