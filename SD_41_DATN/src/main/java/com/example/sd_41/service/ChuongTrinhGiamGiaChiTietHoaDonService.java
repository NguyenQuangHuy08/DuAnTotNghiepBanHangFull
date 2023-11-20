package com.example.sd_41.service;

import com.example.sd_41.model.ChuongTrinhGiamGiaHoaDon;
import com.example.sd_41.repository.ChuongTrinhGiamGia.ChuongTrinhGiamGiaChiTietHoaDonRepository;
import com.example.sd_41.service.impl.ChuongTrinhGiamGiaChiTietHoaDonImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChuongTrinhGiamGiaChiTietHoaDonService implements ChuongTrinhGiamGiaChiTietHoaDonImpl {

    @Autowired
    private ChuongTrinhGiamGiaChiTietHoaDonRepository repo;

    @Override
    public List<com.example.sd_41.model.ChuongTrinhGiamGiaChiTietHoaDon> getById(ChuongTrinhGiamGiaHoaDon ctggHD) {
        return repo.findAllByCtggHD(ctggHD);
    }

    @Override
    public List<com.example.sd_41.model.ChuongTrinhGiamGiaChiTietHoaDon> getAllByTrangThaiHD() {
        return repo.getAllByTrangThaiHD();
    }


}
