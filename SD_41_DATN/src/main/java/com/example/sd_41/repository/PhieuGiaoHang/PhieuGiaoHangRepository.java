package com.example.sd_41.repository.PhieuGiaoHang;

import com.example.sd_41.model.PhieuGiaoHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PhieuGiaoHangRepository extends JpaRepository<PhieuGiaoHang, UUID> {

    @Query("select phieuGiaoHang from  PhieuGiaoHang phieuGiaoHang  where  phieuGiaoHang.id = ?1")
    PhieuGiaoHang getPhieuGiaoHangById(UUID id);

    Page<PhieuGiaoHang> findByTrangThai (int trangThai, Pageable pageable);



}