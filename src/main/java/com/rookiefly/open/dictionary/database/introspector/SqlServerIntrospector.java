package com.rookiefly.open.dictionary.database.introspector;

import com.rookiefly.open.dictionary.database.DBMetadataHolder;
import com.rookiefly.open.dictionary.database.DatabaseConfig;
import org.apache.commons.lang3.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SqlServerIntrospector extends DatabaseIntrospector {

    public SqlServerIntrospector(DBMetadataHolder dbMetadataHolder) {
        super(dbMetadataHolder);
    }

    public SqlServerIntrospector(DBMetadataHolder dbMetadataHolder, boolean forceBigDecimals, boolean useCamelCase) {
        super(dbMetadataHolder, forceBigDecimals, useCamelCase);
    }

    /**
     * 获取表名和注释映射
     *
     * @param config
     * @return
     * @throws SQLException
     */
    @Override
    protected Map<String, String> getTableComments(DatabaseConfig config) throws SQLException {
        Map<String, String> answer = new HashMap<>();
        try {
            StringBuilder sqlBuilder = new StringBuilder("select a.name tname, b.value comments ");
            sqlBuilder.append("from sys.tables a left join sys.extended_properties b on a.object_id=b.major_id    ");
            sqlBuilder.append("where b.minor_id=0 and b.name = 'MS_Description'   ");
            sqlBuilder.append("and a.schema_id in (   ");
            sqlBuilder.append("    select schema_id from sys.schemas  ");
            if (StringUtils.isNotEmpty(config.getSchemaPattern())) {
                sqlBuilder.append(" where name like ?   ");
            }
            sqlBuilder.append(")  and b.value is not null ");
            PreparedStatement preparedStatement = dbMetadataHolder.getConnection().prepareStatement(sqlBuilder.toString());
            if (StringUtils.isNotEmpty(config.getSchemaPattern())) {
                preparedStatement.setString(1, config.getSchemaPattern());
            }
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                answer.put(rs.getString(dbMetadataHolder.convertLetterByCase("tname")), rs.getString(dbMetadataHolder.convertLetterByCase("comments")));
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
        Map<String, Map<String, String>> answer = new HashMap<>();
        try {
            StringBuilder sqlBuilder = new StringBuilder("select a.name tname, b.name cname, c.value as comments    ");
            sqlBuilder.append("from sys.tables a left join sys.columns b on a.object_id=b.object_id   ");
            sqlBuilder.append("left join sys.extended_properties c on a.object_id=c.major_id   ");
            sqlBuilder.append("where c.minor_id<>0 and b.column_id=c.minor_id   ");
            sqlBuilder.append("and a.schema_id in (   ");
            sqlBuilder.append("    select schema_id from sys.schemas  ");
            if (StringUtils.isNotEmpty(config.getSchemaPattern())) {
                sqlBuilder.append(" where name like ?   ");
            }
            sqlBuilder.append(")  and c.value is not null");

            PreparedStatement preparedStatement = dbMetadataHolder.getConnection().prepareStatement(sqlBuilder.toString());
            if (StringUtils.isNotEmpty(config.getSchemaPattern())) {
                preparedStatement.setString(1, config.getSchemaPattern());
            }
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String tname = rs.getString(dbMetadataHolder.convertLetterByCase("tname"));
                if (!answer.containsKey(tname)) {
                    answer.put(tname, new HashMap<>());
                }
                answer.get(tname).put(rs.getString(dbMetadataHolder.convertLetterByCase("cname")), rs.getString(dbMetadataHolder.convertLetterByCase("comments")));
            }
            closeResultSet(rs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return answer;
    }
}
