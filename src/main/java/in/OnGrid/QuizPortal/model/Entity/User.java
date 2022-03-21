package in.OnGrid.QuizPortal.model.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import in.OnGrid.QuizPortal.util.Gender;

import javax.persistence.*;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Entity
public class User extends Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(length = 45)
    public String name;

    @Column(length = 10)
    public String mobile;

    @Column(length = 45, unique = true, nullable = false)
    public String email;

    @Column
    public Date dob;

    @Enumerated(EnumType.STRING)
    public Gender gender;

    @Column(nullable = false)
    public String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
