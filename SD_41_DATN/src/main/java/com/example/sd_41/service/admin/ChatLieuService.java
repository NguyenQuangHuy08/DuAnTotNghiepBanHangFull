package com.example.sd_41.service.admin;

import com.example.sd_41.model.ChatLieu;
import com.example.sd_41.repository.SanPham.AllGiayTheThao.ChatLieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ChatLieuService {
    @Autowired
    private ChatLieuRepository chatLieuRepository;

    public List<ChatLieu> getAll() {
        return chatLieuRepository.findAll();
    }

    public ChatLieu getOne(UUID id) {
        return chatLieuRepository.findById(id).orElse(null);
    }

    public void save(ChatLieu chatLieu) {
        this.chatLieuRepository.save(chatLieu);
    }

    public void delete(UUID id) {
        this.chatLieuRepository.deleteById(id);
    }

    public List<ChatLieu> searchByTen(String tenChatLieu) {
        return chatLieuRepository.findByTenChatLieuContainingOrderById(tenChatLieu);
    }

}
