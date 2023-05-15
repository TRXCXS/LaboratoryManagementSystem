package com.example.backend.testUtils.utilClasses.BatchImportUsers;

import com.example.backend.utils.utilClasses.BatchImportUsers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@SpringBootTest
class BackendApplicationTests {
    @Autowired
    private BatchImportUsers batchImportUsers;

    @Test
    void test() throws FileNotFoundException {
        InputStream inputStream = new FileInputStream("C:\\Coding\\JavaProjects\\LaboratoryManagementSystem\\developFiles\\demoUserData.xlsx");
        batchImportUsers.readAndImport(inputStream, false, "Administrator");
    }

}