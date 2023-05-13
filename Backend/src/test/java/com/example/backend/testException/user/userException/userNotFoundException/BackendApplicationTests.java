package com.example.backend.testException.user.userException.userNotFoundException;

import com.example.backend.exception.user.userException.UserNotExistException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {

    @Test
    void testUserNotFoundException() {
        // 这个方法模拟的是服务层查找用户的方法
        // ...查找代码...

        boolean isFound = false;
        if (isFound) {
            // 查找到了返回user对象
            System.out.println("找到了用户");
        } else {
            // 没有查找到则抛出异常，等待controller层的处理
            throw new UserNotExistException("用户未找到！");
        }
    }

}