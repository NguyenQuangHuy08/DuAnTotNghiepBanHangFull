package com.example.sd_41.service.admin;

import com.example.sd_41.model.User;
import com.example.sd_41.repository.SanPham.AllGiayTheThao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getOne(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    public void save(User user) {

        this.userRepository.save(user);

    }

    public void delete(UUID id) {
        this.userRepository.deleteById(id);
    }

    public List<User> searchByTen(String tenUser) {
        return userRepository.findByTenUserContainingOrderById(tenUser);
    }

    public List<User> searchByTrangThai(Integer trangThai) {
        return userRepository.findByTrangThaiContainingOrderById(trangThai);
    }

}
