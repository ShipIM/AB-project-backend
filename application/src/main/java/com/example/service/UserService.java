package com.example.service;

import com.example.exception.EntityNotFoundException;
import com.example.model.entity.User;
import com.example.model.enumeration.Status;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Page<User> getAllUsers(Pageable pageable) {
        String sort = "id";
        long total = userRepository.countAll();
        List<User> users = userRepository.findAll(sort, pageable.getPageSize(), pageable.getPageNumber());

        return new PageImpl<>(users, pageable, total);
    }

    public void setUserStatus(long id, Status status) {
        if (!isUserExists(id)) {
            throw new EntityNotFoundException("Пользователя с таким идентификатором не существует");
        }

        userRepository.setUserStatus(id, status);
    }

    public boolean isUserExists(long id) {
        return userRepository.existsById(id);
    }
}
