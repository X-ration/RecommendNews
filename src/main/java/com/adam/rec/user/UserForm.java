package com.adam.rec.user;

/**
 * @author adam
 * 创建于 2018-04-12 23:09.
 */
public class UserForm {

    private String name;
    private String sex;
    private String birthDate;
    private String profession;
    private String area;
    private String interests;
    private String password;
    private String confirmPassword;

    @Override
    public String toString() {
        return "[UserForm]name="+name+
                "sex="+sex +
                "birthDate="+birthDate+
                "profession="+profession+
                "area="+area+
                "interests="+ interests+
                "password="+password+
                "confirm="+confirmPassword;
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
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

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
