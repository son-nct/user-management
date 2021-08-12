/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ncts.dto;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author WIN 10
 */
public class CartObj {

    private Map<UserDTO, Integer> items;

    public Map<UserDTO, Integer> getItems() {
        return items;
    }


    public void addUserToPromotionList(UserDTO userAdded, int rank) {
        if (items == null) {
            items = new HashMap<>();
        }

        items.put(userAdded, rank);
    }

    public void deleteUserFromPromotionList(String username) {
        if (items == null) {
            return;
        }

        for (UserDTO userDTO : items.keySet()) {
            if (userDTO.getUsername().equals(username)) {
                items.remove(userDTO);
                break;
            }
        }
        
        if(this.items.isEmpty()) {
            items = null;
        }
    }
}
