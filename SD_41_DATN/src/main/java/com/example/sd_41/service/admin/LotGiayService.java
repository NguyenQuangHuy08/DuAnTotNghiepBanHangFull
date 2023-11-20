package com.example.sd_41.service.admin;

import com.example.sd_41.repository.SanPham.AllGiayTheThao.LotGiayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LotGiayService {
    @Autowired
    private LotGiayRepository lotGiayRepository;
}
