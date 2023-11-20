package com.example.sd_41.service;

import com.example.sd_41.model.ChuongTrinhGiamGiaGiayTheThao;
import com.example.sd_41.repository.ChuongTrinhGiamGia.ChuongTrinhGiamGiaGiayTheThaoRepository;
import com.example.sd_41.service.impl.ChuongTrinhGiamGiaGiayTheThaoImpl;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ChuongTrinhGiamGiaGiayTheThaoService implements ChuongTrinhGiamGiaGiayTheThaoImpl {

    @Autowired
    private ChuongTrinhGiamGiaGiayTheThaoRepository repo;

    @Override
    public void add(ChuongTrinhGiamGiaGiayTheThao gg) {
        this.repo.save(gg);
    }

    @Override
    public void update(ChuongTrinhGiamGiaGiayTheThao gg, UUID id) {
        gg.setId(id);
        this.repo.save(gg);
    }

    @Override
    public void addAll(MultipartFile file) throws IOException {
        List<ChuongTrinhGiamGiaGiayTheThao> list = new ArrayList<>();
        try(InputStream in = file.getInputStream()){
            Workbook workbook = new XSSFWorkbook(in);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                ChuongTrinhGiamGiaGiayTheThao ctgg = new ChuongTrinhGiamGiaGiayTheThao();
                ctgg.setTenChuongTrinh(row.getCell(0).getStringCellValue());
                ctgg.setPhanTramGiam((int) row.getCell(1).getNumericCellValue());
                ctgg.setNgayBatDau(sdf.format(row.getCell(2).getDateCellValue()));
                ctgg.setNgayKetThuc(sdf.format(row.getCell(3).getDateCellValue()));
                ctgg.setGhiChu(row.getCell(4).getStringCellValue());
                ctgg.setNgayTao(sdf.format(row.getCell(5).getDateCellValue()));
                ctgg.setNgaySua(sdf.format(row.getCell(6).getDateCellValue()));
                ctgg.setTrangThai((int) row.getCell(7).getNumericCellValue());
                list.add(ctgg);
            }
            workbook.close();
        }
        this.repo.saveAll(list);
    }

    @Override
    public ChuongTrinhGiamGiaGiayTheThao getOne(UUID id) {
        return this.repo.findById(id).get();
    }

    @Override
    public List<ChuongTrinhGiamGiaGiayTheThao> getList() {
        return this.repo.findAll();
    }

    @Override
    public Page<ChuongTrinhGiamGiaGiayTheThao> search(Pageable pageable, String name) {
        return this.repo.findByTenChuongTrinhContaining(name, pageable);
    }

    @Override
    public Page<ChuongTrinhGiamGiaGiayTheThao> filterByTrangThai(Pageable pageable, int name) {
        if(name == 2){
            return this.repo.findAll(pageable);
        }
        return this.repo.findAllByTrangThai(name, pageable);
    }

    @Override
    public Page<ChuongTrinhGiamGiaGiayTheThao> pagination(Pageable pageable) {
        return this.repo.findAll(pageable);
    }

    @Override
    public Page<ChuongTrinhGiamGiaGiayTheThao> filterByTrangThaiAndDate(Pageable pageable, int tt, String nbd,
                                                                        String nkt) {
        if (tt == 2) {
            return this.repo.filterByDate(nbd, nkt, pageable);
        }
        return this.repo.filterByTrangThaiAndDate(tt, nbd, nkt, pageable);
    }

}
