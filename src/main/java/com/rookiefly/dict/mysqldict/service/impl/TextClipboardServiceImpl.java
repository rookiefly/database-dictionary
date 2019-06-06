package com.rookiefly.dict.mysqldict.service.impl;

import com.google.common.hash.Hashing;
import com.rookiefly.dict.mysqldict.service.TextClipboardService;
import com.rookiefly.dict.mysqldict.util.ConversionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.nio.charset.Charset;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Slf4j
@Service
public class TextClipboardServiceImpl implements TextClipboardService {

    private static Charset UTF8 = Charset.forName("utf-8");

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String saveText(String text) {
        String encodeText = Base64Utils.encodeToUrlSafeString(text.getBytes(UTF8));
        long hash = Hashing.md5().hashString(encodeText, UTF8).padToLong();
        String dbText = queryTextByHash(hash);
        if (dbText == null) {
            insert(hash, encodeText);
        }
        return ConversionUtil.encode(hash);
    }

    public int insert(long hash, String encodeText) {
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
            log.info("auto-generated key: {}", keyHolder.getKey());
        }
        return insertRow;
    }

    @Override
    @Cacheable(value = "clipboardCache", key = "targetClass + methodName + #textId")
    public String queryText(String textId) {
        long hash = ConversionUtil.decode(textId);
        String encodeText = queryTextByHash(hash);
        if (StringUtils.isNotBlank(encodeText)) {
            return new String(Base64Utils.decodeFromUrlSafeString(encodeText));
        }
        return null;
    }

    public String queryTextByHash(long hash) {
        final String sql = "select content from clipboard where hash=?";
        List<String> result = jdbcTemplate.query(sql, new Object[]{hash}, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                if (rs != null) {
                    return rs.getString(1);
                }
                return null;
            }
        });
        if (CollectionUtils.isNotEmpty(result)) {
            return result.get(0);
        }
        return null;
    }
}
