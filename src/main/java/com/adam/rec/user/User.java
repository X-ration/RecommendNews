package com.adam.rec.user;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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
    private String area;
    private List<String> interests;

    @Override
    public String toString() {
        return "[User]id="+userId+",name="+name+",sex="+sex+",birthDate"+getBirthDateString()+
                ",profession="+profession+",area="+area+",interests="+getInterestsString();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getBirthDateString() {
        return birthDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public List<String> getInterests() {
        return interests;
    }

    public String getInterestsString() {
        return interests.stream().collect(Collectors.joining(","));
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }
}
