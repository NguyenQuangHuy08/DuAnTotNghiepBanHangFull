package com.example.sd_41.service.admin;

import com.example.sd_41.model.KieuBuoc;
import com.example.sd_41.repository.SanPham.AllGiayTheThao.KieuBuocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class KieuBuocService {

    @Autowired
    private KieuBuocRepository kieuBuocRepository;

    public List<KieuBuoc> getAll() {
        return kieuBuocRepository.findAll();
    }

    public KieuBuoc getOne(UUID id) {
        return kieuBuocRepository.findById(id).orElse(null);
    }

    public void save(KieuBuoc kieuBuoc) {
        this.kieuBuocRepository.save(kieuBuoc);
    }

    public void delete(UUID id) {
        this.kieuBuocRepository.deleteById(id);
    }

    public List<KieuBuoc> searchByTen(String tenKieuBuoc) {
        return kieuBuocRepository.findByTenKieuBuocContainingOrderById(tenKieuBuoc);
    }

}
