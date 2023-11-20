package com.example.sd_41.service.GiayTheThao;

import com.example.sd_41.model.GiayTheThao;
import com.example.sd_41.repository.SanPham.GiayTheThao.GiayTheThaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GiayTheThaoService implements GiayTheThaoImpl{

    @Autowired
    private GiayTheThaoRepository giayTheThaoRepository;

    public List<GiayTheThao> findGiayTheThao(String tenGiayTheThao){

        if(tenGiayTheThao !=null){

            return giayTheThaoRepository.findByTenGiayTheThao(tenGiayTheThao);

        }else{

             return giayTheThaoRepository.findAll();

        }

    }

    @Override
    public List<GiayTheThao> getAll() {

        return this.giayTheThaoRepository.findAll();
    }

    @Override
    public List<GiayTheThao> getAllWithoutInCTGGCTSP(UUID id) {
        return this.giayTheThaoRepository.getAllWithoutInCTGGCTSP(id);
    }

    @Override
    public GiayTheThao getOne(UUID id) {
        return this.giayTheThaoRepository.findById(id).get();
    }




}
