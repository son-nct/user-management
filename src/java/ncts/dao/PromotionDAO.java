/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ncts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import ncts.dbUtility.ConnectDatabase;
import ncts.dto.HistoryDTO;
import ncts.dto.PromotionDTO;

/**
 *
 * @author WIN 10
 */
public class PromotionDAO {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    private void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (ps != null) {
            ps.close();
        }
        if (con != null) {
            con.close();
        }
    }

    public boolean updatePromotion(PromotionDTO dto) throws NamingException, SQLException {
        try {
            con = ConnectDatabase.makeConnection();
            if (con != null) {
                String sql = "Insert into promotion "
                        + "Values(?,?,?,?)";
                ps = con.prepareStatement(sql);
                ps.setString(1, dto.getIdPromotion());
                ps.setInt(2, dto.getRank());
                ps.setString(3, dto.getUsername());
                ps.setString(4, dto.getDateAssign());

                int affectedRow = ps.executeUpdate();
                if (affectedRow > 0) {
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public List<PromotionDTO> getListPromotions() throws NamingException, SQLException {
        List<PromotionDTO> listUser = null;
        try {
            con = ConnectDatabase.makeConnection();
            if (con != null) {
                String sql = "Select rank, username "
                        + "From promotion";
                ps = con.prepareStatement(sql);

                rs = ps.executeQuery();

                if (listUser == null) {
                    listUser = new ArrayList<>();
                }

                while (rs.next()) {
                    int rank = rs.getInt("rank");
                    String username = rs.getString("username");

                    PromotionDTO dto = new PromotionDTO(rank, username);
                    listUser.add(dto);
                }

            }
        } finally {
            closeConnection();
        }
        return listUser;
    }

    public List<HistoryDTO> getListHistory() throws NamingException, SQLException {
        List<HistoryDTO> listHistory = null;
        try {
            con = ConnectDatabase.makeConnection();
            if (con != null) {
                String sql = "select re.photo,re.username, re.email, re.phone, re.fullname, ro.role,po.rank,po.dateAssign "
                        + "from role ro join registration re on ro.idRole = re.idRole "
                        + "join promotion po on re.username = po.username "
                        + "where status = 'active'"
                        + "order by po.dateAssign desc";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();

                if (listHistory == null) {
                    listHistory = new ArrayList<>();
                }

                while (rs.next()) {
                    String photo = rs.getString("photo");
                    String username = rs.getString("username");
                    String email = rs.getString("email");
                    String phone = rs.getString("phone");
                    String fullname = rs.getString("fullname");
                    String role = rs.getString("role");
                    int rank = rs.getInt("rank");
                    String dateAssigne = rs.getString("dateAssign");
                    
                    HistoryDTO dto = new HistoryDTO(photo, username, email, phone, fullname, role, rank, dateAssigne);
                    listHistory.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
        return listHistory;
    }
}
