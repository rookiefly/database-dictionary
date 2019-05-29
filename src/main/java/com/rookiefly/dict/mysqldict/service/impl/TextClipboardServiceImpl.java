package com.rookiefly.dict.mysqldict.service.impl;

import com.rookiefly.dict.mysqldict.service.TextClipboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TextClipboardServiceImpl implements TextClipboardService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String saveText(String text) {
        return null;
    }

    @Override
    public String queryText(String textId) {
        return null;
    }
}
