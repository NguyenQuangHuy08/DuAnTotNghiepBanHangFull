package com.example.sd_41.service.PhieuGiaoHang;

import com.example.sd_41.model.PhieuGiaoHang;
import com.example.sd_41.repository.PhieuGiaoHang.PhieuGiaoHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PhieuGiaoHangService {

    @Autowired
    private PhieuGiaoHangRepository phieuGiaoHangRepository;

    public PhieuGiaoHang getOne(UUID id){

        return phieuGiaoHangRepository.findById(id).orElse(null);

    }

    public void save(PhieuGiaoHang phieuGiaoHang) {

        this.phieuGiaoHangRepository.save(phieuGiaoHang);

    }

}
