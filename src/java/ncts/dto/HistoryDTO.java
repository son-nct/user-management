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
public class HistoryDTO implements Serializable{
    String photo;
    String username;
    String email;
    String phone;
    String fullname;
    String role;
    int rank;
    String dateAssign;

    public HistoryDTO() {
    }

    public HistoryDTO(String photo, String username, String email, String phone, String fullname, String role, int rank, String dateAssign) {
        this.photo = photo;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.fullname = fullname;
        this.role = role;
        this.rank = rank;
        this.dateAssign = dateAssign;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getDateAssign() {
        return dateAssign;
    }

    public void setDateAssign(String dateAssign) {
        this.dateAssign = dateAssign;
    }
    
    
}
