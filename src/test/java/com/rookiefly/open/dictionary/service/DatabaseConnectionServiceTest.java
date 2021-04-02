package com.rookiefly.open.dictionary.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DatabaseConnectionServiceTest {

    @Autowired
    private DatabaseConnectionService databaseConnectionService;

    @Test
    public void testShowTables() {
    }
}
