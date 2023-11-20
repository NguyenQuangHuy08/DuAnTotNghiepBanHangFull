package com.example.sd_41.repository.SanPham.AllGiayTheThao;

import com.example.sd_41.model.DinhTanGiay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DinhTanGiayRepository extends JpaRepository<DinhTanGiay, UUID> {

    List<DinhTanGiay> findByTenDinhTanGiayContainingOrderById(String tenDinhTanGiay);

}
