package com.rookiefly.dict.mysqldict.service.impl;

import com.google.common.hash.Hashing;
import com.rookiefly.dict.mysqldict.service.TextClipboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.nio.charset.Charset;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@Service
public class TextClipboardServiceImpl implements TextClipboardService {

    private static Logger logger = LoggerFactory.getLogger(TextClipboardServiceImpl.class);

    private static Charset UTF8 = Charset.forName("utf-8");

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String saveText(String text) {
        String encodeText = Base64Utils.encodeToUrlSafeString(text.getBytes(UTF8));
        long hash = Hashing.murmur3_128().hashString(encodeText, UTF8).asLong();

        final String insertSql = "insert into clipboard (hash, content) values(?,?)";
        //创建自增key的持有器
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int insertRow = jdbcTemplate.update(new PreparedStatementCreator() {

            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                // 获取PreparedStatement，并指定返回自增key
                PreparedStatement ps = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, hash);
                Clob clob = con.createClob();
                clob.setString(1, encodeText);
                ps.setClob(2, clob);
                return ps;
            }
        }, keyHolder);

        if (insertRow > 0) {
            //getKey返回单一自增值
            logger.info("auto-generated key: {}", keyHolder.getKey());
        }
        return String.valueOf(hash);
    }

    @Override
    public String queryText(String textId) {
        long hash = Long.valueOf(textId);
        final String sql = "select content from clipboard where hash=?";
        String encodeText = jdbcTemplate.queryForObject(sql, new Object[]{hash}, String.class);
        return new String(Base64Utils.decodeFromUrlSafeString(encodeText));
    }
}
