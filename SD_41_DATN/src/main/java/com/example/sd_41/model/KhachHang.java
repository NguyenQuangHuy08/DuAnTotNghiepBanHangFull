package com.example.sd_41.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.apache.poi.ss.formula.functions.Na;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "KhachHang")
public class KhachHang {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "Id_KhachHang")
    private UUID id;

    @Column(name = "maKhachHang")
    private String maKhachHang;

    @Column(name = "Url_Image")
    private String link;

    @Email(message = "Email không hợp lệ", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @Column(name = "email")
    private String email;

    @Column(name = "matKhau")
    private String matKhau;

    @Column(name = "tenKhachHang")
    private String tenKhachHang;

    @Column(name = "gioiTinh")
    private String gioiTinh;

    @Column(name = "ngaySinh")
    @Temporal(TemporalType.DATE)
    private String ngaySinh;

    @Column(name = "soDienThoai")
    private String soDienThoai;

    @Column(name = "diaChi")
    private String diaChi;

    @Column(name = "ghiChu")
    private String ghiChu;

    @Column(name = "ngayTao")
    private String ngayTao;

    @Column(name = "ngaySua")
    private String ngaySua;

    @Column(name = "trangThai")
    private int trangThai;

}
