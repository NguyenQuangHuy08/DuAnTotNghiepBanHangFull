package com.example.sd_41.service.admin;

import com.example.sd_41.model.DinhTanGiay;
import com.example.sd_41.repository.SanPham.AllGiayTheThao.DinhTanGiayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DinhTanGiayService {

    @Autowired
    private DinhTanGiayRepository dinhTanGiayRepository;

    public List<DinhTanGiay> getAll() {
        return dinhTanGiayRepository.findAll();
    }

    public DinhTanGiay getOne(UUID id) {
        return dinhTanGiayRepository.findById(id).orElse(null);
    }

    public void save(DinhTanGiay dinhTanGiay) {
        this.dinhTanGiayRepository.save(dinhTanGiay);
    }

    public void delete(UUID id) {
        this.dinhTanGiayRepository.deleteById(id);
    }

    public List<DinhTanGiay> searchByTen(String tenDinhTanGiay) {

        return dinhTanGiayRepository.findByTenDinhTanGiayContainingOrderById(tenDinhTanGiay);

    }

}
