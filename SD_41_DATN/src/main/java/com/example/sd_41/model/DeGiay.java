package com.example.sd_41.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "DeGiay")
public class DeGiay {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "Id_DeGiay")
    private UUID id;

    @Column(name = "tenDeGiay")
    private String tenDeGiay;

    @Column(name = "ghiChu")
    private String ghiChu;

    @Column(name = "ngayTao")
    private String ngayTao;

    @Column(name = "ngaySua")
    private String ngaySua;

    @Column(name = "trangThai")
    private int trangThai;

}
