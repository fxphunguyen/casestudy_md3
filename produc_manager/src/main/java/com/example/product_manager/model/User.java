package com.example.product_manager.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(fluent = true, chain = true)
public class User {
    private int id;
    @NotEmpty(message = "Tên không được bỏ trống")
    @Length(min = 3, max = 20, message = "Độ dài của tên phải từ 3-20 ký tự ")
    private String username;
    @NotEmpty(message = "Email không được bỏ trống")
    @Email(message = "Email phải đúng định dạng")
    @Length(min = 3, message = "Độ dài của email phải từ 3 ký tự trở lên")
    private String email;
    private int countryId;
    @NotEmpty(message = "Password không được bỏ trống")
    @Length(min = 8, message = "Password phải từ 8 ký tự")
    private String password;
    private String imageUrl;
    private String phone;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + username + '\'' +
                ", email='" + email + '\'' +
                ", idCountry=" + countryId +
                ", password='" + password + '\'' +
                ", urlImg='" + imageUrl + '\'' +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getCountryId() {
        return countryId;
    }

    public String getPassword() {
        return password;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }
}
