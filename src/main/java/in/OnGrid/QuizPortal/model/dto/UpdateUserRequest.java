package in.OnGrid.QuizPortal.model.dto;

import in.OnGrid.QuizPortal.util.Gender;

import java.util.Date;

public class UpdateUserRequest {
    private String name;
    private String mobile;
    private Date dob;
    private Gender gender;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

}
