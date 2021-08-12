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
import ncts.dto.RoleDTO;
import ncts.dto.UserDTO;

/**
 *
 * @author WIN 10
 */
public class UserDAO {

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

    public UserDTO checkLogin(String username, String password) throws NamingException, SQLException {
        UserDTO user = null;
        try {
            con = ConnectDatabase.makeConnection();
            if (con != null) {
                String sql = "select re.password,re.email,re.phone,re.fullname,re.photo,ro.role "
                        + "from registration re join role ro on re.idRole = ro.idRole "
                        + "where re.username= ? and re.password= ? and status = 'active'";
                ps = con.prepareStatement(sql);
                ps.setString(1, username);
                ps.setString(2, password);

                rs = ps.executeQuery();

                if (rs.next()) {
                    
                    String password_encode = rs.getString("password");
                    String email = rs.getString("email");
                    String phone = rs.getString("phone");
                    String fullname = rs.getString("fullname");
                    String photo = rs.getString("photo");
                    String role = rs.getString("role");

                    user = new UserDTO(username, password_encode, email, phone, fullname, photo, role);
                }
            }
        } finally {
            closeConnection();
        }
        return user;
    }

    public String checkUsername(String userId) throws NamingException, SQLException {
        try {
            con = ConnectDatabase.makeConnection();
            if (con != null) {
                String sql = "select username "
                        + "from registration "
                        + "where username = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, userId);

                rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getString("username");
                }
            }
        } finally {
            closeConnection();
        }
        return null;
    }

    public List<RoleDTO> getListRole() throws NamingException, SQLException {
        List<RoleDTO> listRole = null;
        try {
            con = ConnectDatabase.makeConnection();
            if (con != null) {
                String sql = "select idRole,role "
                        + "from role";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                if (listRole == null) {
                    listRole = new ArrayList<>();
                }
                while (rs.next()) {
                    String idRole = rs.getString("idRole");
                    String role = rs.getString("role");

                    RoleDTO dto = new RoleDTO(idRole, role);
                    listRole.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
        return listRole;
    }

    public boolean createUser(UserDTO dto) throws NamingException, SQLException {
        try {
            con = ConnectDatabase.makeConnection();
            if (con != null) {
                String sql = "Insert into registration "
                        + "values(?,?,?,?,?,?,?,?)";
                ps = con.prepareStatement(sql);
                ps.setString(1, dto.getUsername());
                ps.setString(2, dto.getPassword());
                ps.setString(3, dto.getEmail());
                ps.setString(4, dto.getPhone());
                ps.setString(5, dto.getFullname());
                ps.setString(6, dto.getPhoto());
                ps.setString(7, dto.getRole());
                ps.setString(8, dto.getStatus());

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

    public List<UserDTO> getListUser(String tabRole, String searchValue) throws NamingException, SQLException {
        List<UserDTO> listUser = null;
        try {
            con = ConnectDatabase.makeConnection();
            if (con != null) {
                String sql = "select re.username,re.email,re.phone,re.fullname,r.role,re.photo "
                        + "from registration re join role r on re.idRole = r.idRole "
                        + "where re.status != 'Inactive' and re.idRole like ? and re.fullname like ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, '%' + tabRole + '%');
                ps.setNString(2, '%' + searchValue + '%');
                rs = ps.executeQuery();

                if (listUser == null) {
                    listUser = new ArrayList<>();
                }

                while (rs.next()) {
                    String username = rs.getString("username");
                    String email = rs.getString("email");
                    String phone = rs.getString("phone");
                    String fullname = rs.getString("fullname");
                    String role = rs.getString("role");
                    String photo = rs.getString("photo");

                    UserDTO dto = new UserDTO(username, email, phone, fullname, photo, role);
                    listUser.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
        return listUser;
    }

    public boolean updateUserWithPassword(UserDTO dto) throws NamingException, SQLException {
        try {
            con = ConnectDatabase.makeConnection();
            if (con != null) {
                String sql = "Update registration "
                        + "Set password = ?, email = ?, phone = ?, fullname = ?, photo = ?, idRole = ? "
                        + "Where username = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, dto.getPassword());
                ps.setString(2, dto.getEmail());
                ps.setString(3, dto.getPhone());
                ps.setString(4, dto.getFullname());
                ps.setString(5, dto.getPhoto());
                ps.setString(6, dto.getRole());
                ps.setString(7, dto.getUsername());

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

    public boolean updateEmployeeWithPassword(UserDTO dto) throws NamingException, SQLException {
        try {
            con = ConnectDatabase.makeConnection();
            if (con != null) {
                String sql = "Update registration "
                        + "Set password = ?, email = ?, phone = ?, fullname = ?, photo = ? "
                        + "Where username = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, dto.getPassword());
                ps.setString(2, dto.getEmail());
                ps.setString(3, dto.getPhone());
                ps.setString(4, dto.getFullname());
                ps.setString(5, dto.getPhoto());
                ps.setString(6, dto.getUsername());

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

    public boolean updateEmployeeNoPassword(UserDTO dto) throws NamingException, SQLException {
        try {
            con = ConnectDatabase.makeConnection();
            if (con != null) {
                String sql = "Update registration "
                        + "Set email = ?, phone = ?, fullname = ?, photo = ? "
                        + "Where username = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, dto.getEmail());
                ps.setString(2, dto.getPhone());
                ps.setString(3, dto.getFullname());
                ps.setString(4, dto.getPhoto());
                ps.setString(5, dto.getUsername());

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

    public boolean updateUserNoPassword(UserDTO dto) throws NamingException, SQLException {
        try {
            con = ConnectDatabase.makeConnection();
            if (con != null) {
                String sql = "Update registration "
                        + "Set email = ?, phone = ?, fullname = ?, photo = ?, idRole = ? "
                        + "Where username = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, dto.getEmail());
                ps.setString(2, dto.getPhone());
                ps.setString(3, dto.getFullname());
                ps.setString(4, dto.getPhoto());
                ps.setString(5, dto.getRole());
                ps.setString(6, dto.getUsername());

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

    public boolean deleteUser(String username, String status) throws NamingException, SQLException {
        try {
            con = ConnectDatabase.makeConnection();
            if (con != null) {
                String sql = "Update registration "
                        + "Set status = ? "
                        + "Where username = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, status);
                ps.setString(2, username);

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

    public int getRank(String username) throws NamingException, SQLException {
        try {
            con = ConnectDatabase.makeConnection();
            if (con != null) {
                String sql = "Select rank "
                        + "From promotion "
                        + "Where username = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, username);

                rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getInt("rank");
                }
            }
        } finally {
            closeConnection();
        }
        return -1;
    }

}
