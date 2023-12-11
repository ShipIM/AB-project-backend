package com.example.application.service;

import com.example.application.configuration.BaseTestClass;
import com.example.exception.EntityNotFoundException;
import com.example.model.entity.User;
import com.example.model.enumeration.Role;
import com.example.model.enumeration.Status;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UserServiceTests extends BaseTestClass {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @ParameterizedTest
    @CsvSource({
            "0, 1",
            "0, 2",
            "1, 3"
    })
    public void getAllUsersReturnsCorrectPage(int pageNumber, int pageSize) {
        List<User> savedUsers = saveUserList();
        savedUsers.add(userRepository.findById(1L)
                .orElseThrow(() -> new EntityNotFoundException("Ошибка инициализации теста")));

        int pageStart = pageNumber * pageSize, pageEnd = pageStart + pageSize;

        Page<User> retrievedPage = userService.getAllUsers(PageRequest.of(pageNumber, pageSize));

        Assertions.assertAll("Должен возвращать правильную страницу пользователей",
                () -> Assertions.assertEquals(
                        IntStream.range(0, savedUsers.size())
                                .filter(i -> i >= pageStart && i < pageEnd && i < savedUsers.size())
                                .mapToObj(savedUsers::get)
                                .toList().size(),
                        retrievedPage.getContent().size()
                ),
                () -> Assertions.assertEquals(
                        Math.ceil((float) (savedUsers.size()) / pageSize),
                        retrievedPage.getTotalPages()
                ),
                () -> Assertions.assertEquals(
                        savedUsers.size(),
                        retrievedPage.getTotalElements()
                ));

        for (User user : savedUsers) {
            if (user.getId() != 1) {
                cleanup(user.getId());
            }
        }
    }

    @Test
    public void getAllUsersReturnsCorrectUsers() {
        List<User> savedAudits = saveUserList();

        Page<User> retrievedPage = userService.getAllUsers(PageRequest.of(0, 5));

        Assertions.assertAll("Должна содержать те же объекты, что были сохранены",
                () -> Assertions.assertEquals(savedAudits,
                        retrievedPage.getContent().stream()
                                .filter(user -> user.getId() != 0 && user.getId() != 1)
                                .collect(Collectors.toList())));
    }

    @Test
    public void getById() {
        User user = createUser();

        User retrievedUser = userService.getById(user.getId());

        Assertions.assertEquals(user, retrievedUser);

        cleanup(user.getId());
    }

    @Test
    public void getByNonExistingId() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.getById(-1));
    }

    @Test
    public void getByEmail() {
        User user = createUser();

        User retrievedUser = userService.getByEmail(user.getEmail());

        Assertions.assertEquals(user, retrievedUser);

        cleanup(user.getId());
    }

    @Test
    public void getByNonExistingEmail() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.getByEmail("email"));
    }

    @ParameterizedTest
    @EnumSource(Role.class)
    public void setUserRole(Role role) {
        User user = createUser();

        userService.setUserRole(user.getId(), role);

        Assertions.assertEquals(role, userService.getById(user.getId()).getRole());

        cleanup(user.getId());
    }

    @Test
    public void setUserRoleOnNonExistingUser() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.setUserRole(-1, Role.USER));
    }

    @ParameterizedTest
    @EnumSource(Status.class)
    public void setUserStatus(Status status) {
        User user = createUser();

        userService.setUserStatus(user.getId(), status);

        Assertions.assertEquals(status, userService.getById(user.getId()).getStatus());

        cleanup(user.getId());
    }

    @Test
    public void setUserStatusOnNonExistingUser() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.setUserStatus(-1, Status.ACTIVE));
    }

    @Test
    public void isUserExistsOnExistingUser() {
        User user = createUser();

        Assertions.assertTrue(userService.isUserExists(user.getId()));

        cleanup(user.getId());
    }

    @Test
    public void isUserExistsOnNonExistingUser() {
        Assertions.assertFalse(userService.isUserExists(-1));
    }

    private List<User> saveUserList() {
        Iterable<User> iterableUsers = userRepository.saveAll(
                List.of(
                        new User(null, "first", "first", "first", Role.USER, Status.ACTIVE),
                        new User(null, "second", "second", "second", Role.MODERATOR,
                                Status.ACTIVE),
                        new User(null, "third", "third", "third", Role.ADMIN, Status.ACTIVE)
                )
        );
        List<User> savedUsers = new ArrayList<>();
        iterableUsers.forEach(savedUsers::add);

        return savedUsers;
    }

    private User createUser() {
        User user = new User(
                null,
                "user@gmail.com",
                "12345678",
                "user",
                Role.USER,
                Status.ACTIVE
        );

        return userRepository.save(user);
    }

    private void cleanup(long id) {
        userRepository.deleteById(id);
    }
}
