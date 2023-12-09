package com.example.sd_41.repository.BanHang;

import com.example.sd_41.model.GiayTheThaoChiTiet;
import com.example.sd_41.model.GioHang;
import com.example.sd_41.model.GioHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTiet, UUID> {

    GioHangChiTiet findByGioHangAndGiayTheThaoChiTiet(GioHang gioHang, GiayTheThaoChiTiet giayTheThaoChiTiet);

    GioHangChiTiet findByGiayTheThaoChiTiet_Id(UUID idGiayTheThaoChiTiet);


}
