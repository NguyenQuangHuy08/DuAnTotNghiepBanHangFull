package com.example.sd_41.service.HoaDon;

import com.example.sd_41.model.GiayTheThaoChiTiet;
import com.example.sd_41.model.HoaDon;
import com.example.sd_41.model.HoaDonChiTiet;
import com.example.sd_41.model.KhachHang;
import com.example.sd_41.repository.HoaDon.HoaDonChiTietRepository;
import com.example.sd_41.repository.HoaDon.HoaDonRepository;
import com.example.sd_41.repository.KhachHangRepository;
import com.example.sd_41.repository.SanPham.GiayTheThao.GiayTheThaoChiTietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HoaDonService implements HoaDonServiceImpl{

    @Autowired
    HoaDonRepository hoaDonRepository;

    @Autowired
    private HoaDonChiTietRepository hdctRepo;

    @Autowired
    private KhachHangRepository khRepo;

    @Autowired
    private GiayTheThaoChiTietRepository gttctRepo;

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

    //Todo code bán hàng tại quầy
//    @Override
    public List<HoaDon> hoaDonCho() {

        return hoaDonRepository.findAllByTrangThaiOrderByNgayTaoDesc(0);

    }

    public void removeHDCByID(UUID id){
        HoaDon hd = hoaDonRepository.findById(id).get();
        List<HoaDonChiTiet> listHDCT = hdctRepo.findAllByHoaDon(hd);
        for (HoaDonChiTiet hdct: listHDCT){
            hdctRepo.delete(hdct);
        }

        hoaDonRepository.delete(hd);
    }

    public HoaDon createHD(HoaDon hd){

        KhachHang kh = null;
        if (hd.getKhachHang() != null) {
            kh = khRepo.findById(hd.getKhachHang().getId()).get();

        }
        LocalTime localTime = LocalTime.now();
        hd.setKhachHang(kh);
        // int count = (int) hoaDonRepository.count();
        hd.setMaHoaDon("MaHD" + localTime.getHour() + localTime.getMinute() + localTime.getSecond());
        hd.setGhiChu("N/A");
        hd.setNgayTao(LocalDate.now().toString());
        hd.setNgaySua(LocalDate.now().toString());
        hd.setMess("N/A");
        hd.setTrangThai(0);
        return hoaDonRepository.save(hd);
    }


    public HoaDon thanhToan(UUID id) {
        HoaDon hd = hoaDonRepository.findById(id).get();
        List<HoaDonChiTiet> listHdct = hdctRepo.findAllByHoaDon(hd);
        GiayTheThaoChiTiet gttct = null;
        int sum = 0;
        for (HoaDonChiTiet hdct: listHdct) {
            int donGia = hdct.getDonGia().intValue();
            int soLuong = Integer.parseInt(hdct.getSoLuong());
            int tong = donGia * soLuong;
            sum += tong;
            gttct = gttctRepo.findById(hdct.getGiayTheThaoChiTiet().getId()).get();
            gttct.setSoLuong(String.valueOf(Integer.parseInt(gttct.getSoLuong()) - soLuong));
        }
        BigDecimal thanhTien = new BigDecimal(sum);
        hd.setThanhTien(thanhTien);
        hd.setNgayThanhToan(LocalDate.now().toString());
        hd.setNgaySua(LocalDate.now().toString());
        if (hd == null) {
            return null;
        }
        if(listHdct.size()==0){
            return null;
        }
        hd.setTrangThai(1);
        return hoaDonRepository.save(hd);
    }




}
