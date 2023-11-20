package com.example.sd_41.repository.ChuongTrinhGiamGia;

import com.example.sd_41.model.ChuongTrinhGiamGiaGiayTheThao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChuongTrinhGiamGiaGiayTheThaoRepository extends JpaRepository<ChuongTrinhGiamGiaGiayTheThao, UUID> {

    Page<ChuongTrinhGiamGiaGiayTheThao> findByTenChuongTrinhContaining(String name, Pageable pageable);

    Page<ChuongTrinhGiamGiaGiayTheThao> findAllByTrangThai(int tt, Pageable pageable);
    @Query("SELECT gg FROM ChuongTrinhGiamGiaGiayTheThao gg WHERE gg.trangThai = :trangThai AND gg.ngayBatDau >= :ngayBatDau AND gg.ngayKetThuc <= :ngayKetThuc")
    Page<ChuongTrinhGiamGiaGiayTheThao> filterByTrangThaiAndDate(@Param("trangThai") int tt,
                                                                 @Param("ngayBatDau") String nbd, @Param("ngayKetThuc") String nkt, Pageable pageable);
    @Query("SELECT gg FROM ChuongTrinhGiamGiaGiayTheThao gg WHERE gg.ngayBatDau >= :ngayBatDau AND gg.ngayKetThuc <= :ngayKetThuc")
    Page<ChuongTrinhGiamGiaGiayTheThao> filterByDate(@Param("ngayBatDau") String nbd, @Param("ngayKetThuc") String nkt, Pageable pageable);


}
