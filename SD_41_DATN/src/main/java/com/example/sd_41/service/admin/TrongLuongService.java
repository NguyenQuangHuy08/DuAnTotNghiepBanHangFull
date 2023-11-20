package com.example.sd_41.service.admin;

import com.example.sd_41.model.TrongLuong;
import com.example.sd_41.repository.SanPham.AllGiayTheThao.TrongLuongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TrongLuongService {

    @Autowired
    private TrongLuongRepository trongLuongRepository;

    public List<TrongLuong> getAll() {
        return trongLuongRepository.findAll();
    }

    public TrongLuong getOne(UUID id) {
        return trongLuongRepository.findById(id).orElse(null);
    }

    public void save(TrongLuong trongLuong) {
        this.trongLuongRepository.save(trongLuong);
    }

    public void delete(UUID id) {
        this.trongLuongRepository.deleteById(id);
    }

    public List<TrongLuong> searchByTen(String tenTrongLuong) {
        return trongLuongRepository.findByTenTrongLuongContainingOrderById(tenTrongLuong);
    }

}
