package com.example.sd_41.service.HoaDon;

import com.example.sd_41.model.HoaDon;
import com.example.sd_41.repository.HoaDon.HoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class HoaDonService implements HoaDonServiceImpl{

    @Autowired
    HoaDonRepository hoaDonRepository;

    //

    @Override
    public Page<HoaDon> listHoaDonFindByKhachHangAndTrangThai(UUID idKhachHang, int trangThai, Integer pageNo, Integer size){

        Pageable pageable = PageRequest.of(pageNo,size);
        return hoaDonRepository.listHoaDonFindByKhachHangAndTrangThai(idKhachHang,trangThai,pageable);

    }

    @Override
    public HoaDon findId(UUID id){

        Optional<HoaDon> optionalHoaDon = hoaDonRepository.findById(id);
        return optionalHoaDon.orElse(null);

    }

    //Todo code update cho hóa đơn khi bị hủy
    @Override
    public void update(UUID id, HoaDon updateHoaDon){

        Optional<HoaDon>  optionalHoaDon = hoaDonRepository.findById(id);

        if(optionalHoaDon.isPresent()){

            HoaDon hoaDon = optionalHoaDon.get();

            hoaDon.setMaHoaDon(updateHoaDon.getMaHoaDon());
            hoaDon.setThanhTien(updateHoaDon.getThanhTien());
            hoaDon.setNgayTao(updateHoaDon.getNgayTao());
            hoaDon.setNgaySua(updateHoaDon.getNgaySua());
            hoaDon.setNgayThanhToan(updateHoaDon.getNgayThanhToan());
            hoaDon.setUser(updateHoaDon.getUser());
            hoaDon.setGhiChu(updateHoaDon.getGhiChu());
            hoaDon.setTrangThai(updateHoaDon.getTrangThai());

            hoaDonRepository.save(hoaDon);

        }

    }

    @Override
    public Page<HoaDon> listHoaDonFindByTrangThai(Integer pageNo, Integer size, int trangThai) {
        Pageable pageable = PageRequest.of(pageNo, size);
        return hoaDonRepository.listHoaDonFindByTrangThai(trangThai, pageable);

    }

    @Override
    public String tongSl(UUID id) {

        return hoaDonRepository.tongSl(id);

    }



}
