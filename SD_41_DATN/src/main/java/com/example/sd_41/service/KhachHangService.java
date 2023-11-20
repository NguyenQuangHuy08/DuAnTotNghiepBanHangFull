package com.example.sd_41.service;

import com.example.sd_41.model.KhachHang;
import com.example.sd_41.repository.KhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class KhachHangService {

    @Autowired
    private KhachHangRepository khachHangRepository;

    public List<KhachHang> findKhachHang(String tenKhachHang) {
        if (tenKhachHang != null) {
            return khachHangRepository.findByTenKhachHang(tenKhachHang);
        } else {
            return khachHangRepository.findAll();
        }
    }

    public Page<KhachHang> searchKH(String keyword, String tenKhachHang, String email, String matKhau, String gioiTinh, String ngaySinh, String soDienThoai, String diaChi, Pageable pageable){
        return khachHangRepository.searchKH(keyword, tenKhachHang, email, matKhau, gioiTinh, ngaySinh, soDienThoai, diaChi, pageable);
    }

    //Tìm trong giỏ hàng
    public KhachHang findById(UUID id){

        return khachHangRepository.findById(id).orElse(null);

    }

}
