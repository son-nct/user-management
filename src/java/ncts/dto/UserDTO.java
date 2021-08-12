/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ncts.dto;

import java.io.Serializable;

/**
 *
 * @author WIN 10
 */
public class UserDTO implements Serializable {

    private String username;
    private String password;
    private String email;
    private String phone;
    private String fullname;
    private String photo;
    private String role;
    private String status;

    public UserDTO() {
    }

    public UserDTO(String username, String password, String email, String phone, String fullname, String photo, String role, String status) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.fullname = fullname;
        this.photo = photo;
        this.role = role;
        this.status = status;
    }
    
    public UserDTO(String username, String email, String phone, String fullname, String photo, String role) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.fullname = fullname;
        this.photo = photo;
        this.role = role;
    }

    public UserDTO(String username, String password, String email, String phone, String fullname, String photo, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.fullname = fullname;
        this.photo = photo;
        this.role = role;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
