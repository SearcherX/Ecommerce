package home.ecommerce.service;

import home.ecommerce.entity.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceTest {
    @Autowired
    private  UserService userService;

    @Test
    void addUser() {
//        User user1 = new User();
//        user1.setUsername("new user");
//        user1.setPassword("test");
//        user1.setEmail("test@mail.ru");
//        userService.addUser(user1);
        List<Role> enumValues = new ArrayList<>(EnumSet.allOf(Role.class));
        System.out.println(enumValues);
    }
}