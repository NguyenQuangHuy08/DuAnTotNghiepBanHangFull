package com.example.sd_41.controller.ThongKe;

import com.example.sd_41.model.HoaDon;
import com.example.sd_41.repository.HoaDon.HoaDonChiTietRepository;
import com.example.sd_41.repository.HoaDon.HoaDonRepository;
import com.example.sd_41.repository.SanPham.GiayTheThao.GiayTheThaoChiTietRepository;
import com.example.sd_41.repository.SanPham.GiayTheThao.GiayTheThaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class ThongKeHoaDonController {

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    private GiayTheThaoRepository giayTheThaoRepository;

    @Autowired
    private GiayTheThaoChiTietRepository giayTheThaoChiTietRepository;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @GetMapping("/thongKeChuanData")
    public String thongKeChuanData(Model model){

        //Tổng danh thu cho sản phẩm đã bán
        BigDecimal tongDoanhThu = hoaDonRepository.tinhTongDoanhThu();
        model.addAttribute("tongDoanhThu",tongDoanhThu);

        //Tổng đơn hàng cho sản phẩm đã bán
        Integer tongDonHang = hoaDonRepository.tongDonHang();
        model.addAttribute("tongDonHang",tongDonHang);

        //Tổng số lượng sản phẩm sẵn có trong kho
        Integer totalQuantity = giayTheThaoChiTietRepository.sumSoLuong();
        model.addAttribute("totalQuantity",totalQuantity);

        //sản phẩm bán chạy nhất

//        String maSanPhamBanChayNhat = hoaDonRepository.timSanPhamBanChayNhat();
//        model.addAttribute("maSanPhamBanChayNhat",maSanPhamBanChayNhat);


        return "/ThongKe/test";

    }



    //Trả dữ liệu về dạng json

    //Todo code thống kê data hóa đơn

    @GetMapping("/thongke-data-hoadon")
    @ResponseBody
    public String thongKeDataHD() {
        List<HoaDon> hdct = hoaDonRepository.findAll();

        ObjectMapper objectMapper = new ObjectMapper();
        try {

            String jsonData = objectMapper.writeValueAsString(hdct);
            return jsonData;

        } catch (Exception e) {

            e.printStackTrace();
            return "";

        }
    }

//    //Todo code
//    @GetMapping("/thongke-data-hoadon")
//    @ResponseBody
//    public String thongKeDataHD() {
//        List<HoaDon> hdct = hoaDonRepository.findAll();
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            String jsonData = objectMapper.writeValueAsString(hdct);
//            return jsonData;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//    }



}
