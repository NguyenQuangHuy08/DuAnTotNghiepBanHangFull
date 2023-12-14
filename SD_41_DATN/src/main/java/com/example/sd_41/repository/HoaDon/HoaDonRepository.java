package com.example.sd_41.repository.HoaDon;

import com.example.sd_41.model.HoaDon;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, UUID> {


    @Query("select hd from HoaDon hd where hd.khachHang.id = ?1 and hd.trangThai = ?2")
    Page<HoaDon> listHoaDonFindByKhachHangAndTrangThai(UUID idKhachHang, int trangThai, Pageable pageable);

    @Query("select hd from HoaDon hd where hd.trangThai = ?1")
    Page<HoaDon> listHoaDonFindByTrangThai( int trangThai, Pageable pageable);

    //Todo code dành cho thống kê

    @Query("SELECT SUM(hd.thanhTien) FROM HoaDon hd WHERE hd.trangThai = 4")
    BigDecimal tinhTongDoanhThu();

    @Query("SELECT COUNT(hd.maHoaDon) FROM HoaDon hd WHERE hd.trangThai = 4")
    Integer tongDonHang();

    //Sản phẩm bán chạy nhất
    @Query("SELECT h, gttct FROM HoaDon h " +
            "JOIN HoaDonChiTiet hdct ON h.id = hdct.hoaDon.id " +
            "JOIN GiayTheThaoChiTiet gttct ON hdct.giayTheThaoChiTiet.id = gttct.id " +
            "WHERE h.trangThai = 4")

    String timSanPhamBanChayNhat();

    //Todo code áp dụng cho chương trình khuyến mãi cho hóa đơn
    @Query("select sum(hdct.soLuong) from HoaDonChiTiet hdct where hdct.hoaDon.id = ?1")
    String tongSl(UUID id);

    //Todo code bán hàng tại quầy
    List<HoaDon> findAllByTrangThaiOrderByNgayTaoDesc(int trangThai);


}
