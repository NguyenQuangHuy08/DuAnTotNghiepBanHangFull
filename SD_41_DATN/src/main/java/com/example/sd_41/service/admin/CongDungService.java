package com.example.sd_41.service.admin;

import com.example.sd_41.model.CongDung;
import com.example.sd_41.repository.SanPham.AllGiayTheThao.CongDungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

;

@Service
public class CongDungService {
    @Autowired
    private CongDungRepository congDungRepository;

    public List<CongDung> getAll() {
        return congDungRepository.findAll();
    }

    public CongDung getOne(UUID id) {
        return congDungRepository.findById(id).orElse(null);
    }

    public void save(CongDung congDung) {
        this.congDungRepository.save(congDung);
    }

    public void delete(UUID id) {
        this.congDungRepository.deleteById(id);
    }

    public List<CongDung> searchByTen(String tenCongDung) {
        return congDungRepository.findByTenCongDungContainingOrderById(tenCongDung);
    }
}
