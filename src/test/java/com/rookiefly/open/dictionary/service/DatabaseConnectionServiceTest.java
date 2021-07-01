package com.rookiefly.open.dictionary.service;

import com.rookiefly.open.dictionary.bo.DatabaseConnectionHistoryBO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DatabaseConnectionServiceTest {

    @Autowired
    private DatabaseConnectionService databaseConnectionService;

    @Test
    public void testListDatabaseConnection() {
        List<DatabaseConnectionHistoryBO> connectionHistoryBOList = databaseConnectionService.listDatabaseConnection();
    }
}
