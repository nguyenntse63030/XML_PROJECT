/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DB.DBUtils;
import DTO.SanPhamDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author NguyenNTSE63030
 */
public class SanPhamDAO {

    Connection con = null;
    PreparedStatement stm = null;

    public boolean insertSanPham(SanPhamDTO sanPham) throws SQLException {
        boolean result = false;

        try {
            con = DBUtils.makeConnection();
            String sql = "INSERT INTO sanPham(tenSanPham, maSanPham, donGia, chiTiet, photoURL) VALUES(?,?,?,?,?)";
            stm = con.prepareCall(sql);

            stm.setString(1, sanPham.getTenSanPham());
            stm.setString(2, sanPham.getMaSanPham());
            stm.setInt(3, sanPham.getDonGia());
            stm.setString(4, sanPham.getChiTiet());
            stm.setString(5, sanPham.getPhotoURL());

            result = stm.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

}
