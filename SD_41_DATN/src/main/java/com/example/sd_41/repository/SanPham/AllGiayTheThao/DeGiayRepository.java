package com.example.sd_41.repository.SanPham.AllGiayTheThao;

import com.example.sd_41.model.DeGiay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DeGiayRepository extends JpaRepository<DeGiay, UUID> {

    Page<DeGiay> findByTenDeGiayContaining(String ten, Pageable pageable);

}
