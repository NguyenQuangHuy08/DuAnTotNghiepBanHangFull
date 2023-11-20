package com.example.sd_41.repository.BanHang;

import com.example.sd_41.model.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GioHangRepository extends JpaRepository<GioHang, UUID> {


    @Query("SELECT gh FROM GioHang gh WHERE gh.khachHang.id = :khachHangId")
    GioHang findByKhachHangId(UUID khachHangId);

}
