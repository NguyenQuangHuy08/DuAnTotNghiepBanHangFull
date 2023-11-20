package com.example.sd_41.service.GioHang;

import com.example.sd_41.model.GioHangChiTiet;
import com.example.sd_41.repository.BanHang.GioHangChiTietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GioHangChiTietService {

    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    //Todo code delete từng sản phẩm trong giỏ hàng
    //id là của giỏ hàng chi tiết
    public void delete(UUID id){

        if(gioHangChiTietRepository.existsById(id)){

            gioHangChiTietRepository.deleteById(id);

        }

    }


    public void themVaoGioHangChiTiet(GioHangChiTiet gioHangChiTiet){

        gioHangChiTietRepository.save(gioHangChiTiet);

    }

}
