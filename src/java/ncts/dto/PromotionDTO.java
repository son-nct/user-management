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
public class PromotionDTO implements Serializable{
    String idPromotion;
    int rank;
    String username;
    String dateAssign;

    public PromotionDTO() {
    }

    public PromotionDTO(String idPromotion, int rank, String username, String dateAssign) {
        this.idPromotion = idPromotion;
        this.rank = rank;
        this.username = username;
        this.dateAssign = dateAssign;
    }

    public PromotionDTO(int rank, String username) {
        this.rank = rank;
        this.username = username;
    }
    

    public String getIdPromotion() {
        return idPromotion;
    }

    public void setIdPromotion(String idPromotion) {
        this.idPromotion = idPromotion;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDateAssign() {
        return dateAssign;
    }

    public void setDateAssign(String dateAssign) {
        this.dateAssign = dateAssign;
    }

    
}
