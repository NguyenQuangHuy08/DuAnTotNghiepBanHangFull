package com.example.sd_41.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "PhieuGiaoHang")
public class PhieuGiaoHang {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "Id_PhieuGiaoHang")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "Id_KhachHang")
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "Id_User")
    private User user;

    @ManyToOne
    @JoinColumn(name = "Id_HoaDon")
    private HoaDon hoaDon;


    @Column(name = "tenNguoiNhan")
    private String tenNguoiNhan;

    @Column(name = "diaChiNhanHang")
    private String diaChiNhanHang;

    @Column(name = "soDienThoaiNhanHang")
    private String soDienThoaiNhanHang;

    @Column(name = "phiShip")
    private String phiShip;

    @Column(name = "ghiChu")
    private String ghiChu;

    @Column(name = "ngayTao")
    private String ngayTao;

    @Column(name = "ngaySua")
    private String ngaySua;

    @Column(name = "trangThai")
    private int trangThai;



}
