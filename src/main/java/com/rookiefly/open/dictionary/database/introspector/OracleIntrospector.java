package com.rookiefly.open.dictionary.database.introspector;

import com.rookiefly.open.dictionary.database.DBMetadataHolder;
import com.rookiefly.open.dictionary.database.DatabaseConfig;
import org.apache.commons.lang3.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class OracleIntrospector extends DatabaseIntrospector {

    public OracleIntrospector(DBMetadataHolder dbMetadataHolder) {
        super(dbMetadataHolder);
    }

    public OracleIntrospector(DBMetadataHolder dbMetadataHolder, boolean forceBigDecimals, boolean useCamelCase) {
        super(dbMetadataHolder, forceBigDecimals, useCamelCase);
    }

    /**
     * 获取表名和注释映射
     *
     * @param config
     * @return
     */
    @Override
    protected Map<String, String> getTableComments(DatabaseConfig config) {
        Map<String, String> answer = new HashMap<>();
        try {
            StringBuilder sqlBuilder = new StringBuilder("select table_name tname,comments from all_tab_comments where comments is not null ");
            if (StringUtils.isNotEmpty(config.getSchemaPattern())) {
                sqlBuilder.append(" and owner like :1 ");
            }
            sqlBuilder.append("order by tname ");
            PreparedStatement preparedStatement = dbMetadataHolder.getConnection().prepareStatement(sqlBuilder.toString());
            if (StringUtils.isNotEmpty(config.getSchemaPattern())) {
                preparedStatement.setString(1, config.getSchemaPattern());
            }
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                answer.put(rs.getString(dbMetadataHolder.convertLetterByCase("TNAME")), rs.getString(dbMetadataHolder.convertLetterByCase("COMMENTS")));
            }
            closeResultSet(rs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return answer;
    }

    /**
     * 获取表字段注释
     *
     * @param config
     * @return
     * @throws SQLException
     */
    @Override
    protected Map<String, Map<String, String>> getColumnComments(DatabaseConfig config) throws SQLException {
        Map<String, Map<String, String>> answer = new HashMap<String, Map<String, String>>();
        try {
            StringBuilder sqlBuilder = new StringBuilder("select table_name tname,column_name cname,comments from all_col_comments ");
            sqlBuilder.append("where comments is not null ");
            if (StringUtils.isNotEmpty(config.getSchemaPattern())) {
                sqlBuilder.append(" and owner like :1 ");
            }
            sqlBuilder.append("order by table_name,column_name ");

            PreparedStatement preparedStatement = dbMetadataHolder.getConnection().prepareStatement(sqlBuilder.toString());
            if (StringUtils.isNotEmpty(config.getSchemaPattern())) {
                preparedStatement.setString(1, config.getSchemaPattern());
            }
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String tname = rs.getString(dbMetadataHolder.convertLetterByCase("TNAME"));
                if (!answer.containsKey(tname)) {
                    answer.put(tname, new HashMap<>());
                }
                answer.get(tname).put(rs.getString(dbMetadataHolder.convertLetterByCase("CNAME")), rs.getString(dbMetadataHolder.convertLetterByCase("COMMENTS")));
            }
            closeResultSet(rs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return answer;
    }
}
