package com.rookiefly.open.dictionary.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DatabaseDictionaryServiceTest {

    @Autowired
    private DatabaseDictionaryService databaseDictionaryService;

    @Test
    public void testShowTables() {
    }
}
