package com.example.sd_41.service.GiayTheThao;

import com.example.sd_41.model.GiayTheThaoChiTiet;
import com.example.sd_41.repository.SanPham.GiayTheThao.GiayTheThaoChiTietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GiayTheThaoChiTietService {

    @Autowired
    private GiayTheThaoChiTietRepository repo;

    //Todo code bán hàng tại quầy
    public List<GiayTheThaoChiTiet> getAll(){
        return repo.findAll();
    }



}
