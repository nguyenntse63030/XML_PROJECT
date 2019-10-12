/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author NguyenNTSE63030
 */
@XmlRootElement(name = "sanPham")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SanPham", propOrder = {
    "tenSanPham",
    "maSanPham",
    "donGia",
    "chiTiet",
    "photoURL"
})

public class SanPhamDTO implements Serializable {

    @XmlElement(required = true)
    protected String tenSanPham;

    @XmlElement(required = true)
    protected String maSanPham;

    @XmlElement(required = true)
    @XmlSchemaType(name = "int")
    protected int donGia;

    @XmlElement(required = true)
    protected String chiTiet;

    @XmlElement(required = true)
    protected String photoURL;

    public SanPhamDTO(String tenSanPham, String maSanPham, int donGia, String chiTiet, String photoURL) {
        this.tenSanPham = tenSanPham;
        this.maSanPham = maSanPham;
        this.donGia = donGia;
        this.chiTiet = chiTiet;
        this.photoURL = photoURL;
    }

    public SanPhamDTO() {
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public String getChiTiet() {
        return chiTiet;
    }

    public void setChiTiet(String chiTiet) {
        this.chiTiet = chiTiet;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

}
