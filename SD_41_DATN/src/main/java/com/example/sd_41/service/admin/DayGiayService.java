package com.example.sd_41.service.admin;

import com.example.sd_41.model.DayGiay;
import com.example.sd_41.repository.SanPham.AllGiayTheThao.DayGiayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

;

@Service
public class DayGiayService {
    @Autowired
    private DayGiayRepository dayGiayRepository;

    public List<DayGiay> getAll() {
        return dayGiayRepository.findAll();
    }

    public DayGiay getOne(UUID id) {
        return dayGiayRepository.findById(id).orElse(null);
    }

    public void save(DayGiay dayGiay) {
        this.dayGiayRepository.save(dayGiay);
    }

    public void delete(UUID id) {
        this.dayGiayRepository.deleteById(id);
    }

    public List<DayGiay> searchByTen(String tenDayGiay) {
        return dayGiayRepository.findByTenDayGiayContainingOrderById(tenDayGiay);
    }
}
