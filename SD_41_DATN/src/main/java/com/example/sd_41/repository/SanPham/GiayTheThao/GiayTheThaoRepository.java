package com.example.sd_41.repository.SanPham.GiayTheThao;

import com.example.sd_41.model.GiayTheThao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GiayTheThaoRepository extends JpaRepository<GiayTheThao, UUID> {

    @Query("select giayTheThao from  GiayTheThao  giayTheThao where  giayTheThao.id = ?1")
    GiayTheThao getGiayTheThaoById(UUID id);

    Page<GiayTheThao> findByTrangThai (int trangThai, Pageable pageable);

    //Tìm kiếm tên giầy thể thao
    List<GiayTheThao> findByTenGiayTheThao(String tenGiayTheThao);

    @Query("SELECT g FROM GiayTheThao g WHERE g.id NOT IN (SELECT c.giayTheThao.id FROM ChuongTrinhGiamGiaChiTietGiayTheThao c where c.chuongTrinhGiamGiaGiayTheThao.id=:id)")
    List<GiayTheThao> getAllWithoutInCTGGCTSP(@Param("id") UUID id);

}
