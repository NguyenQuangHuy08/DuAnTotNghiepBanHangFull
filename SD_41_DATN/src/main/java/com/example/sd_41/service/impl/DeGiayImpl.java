package com.example.sd_41.service.impl;

import com.example.sd_41.model.DeGiay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface DeGiayImpl {

    public void add(DeGiay deGiay);
    public void update(DeGiay deGiay, UUID id);
    //    public void delete(UUID id);
    public Page<DeGiay> search(Pageable pageable, String name);
    public Page<DeGiay> pagination(Pageable pageable);

}
