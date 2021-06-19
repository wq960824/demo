package com.example.demo.config;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.*;
import java.text.SimpleDateFormat;

/**
 * 主要处理mysql里timestamp类型的字段，用string去接，得到的值末尾有".0"问题
 *

 */
@MappedJdbcTypes(JdbcType.TIMESTAMP)
public class TimestampAsStringTypeHandler extends BaseTypeHandler<String> {

    private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";

    @Override
    public String getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        Timestamp ts = rs.getTimestamp(columnName);
        if (ts == null) {
            return null;
        }

        return ts2Str(ts.getTime());
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        Timestamp ts = rs.getTimestamp(columnIndex);
        if (ts == null) {
            return null;
        }

        return ts2Str(ts.getTime());
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        Timestamp ts = cs.getTimestamp(columnIndex);
        if (ts == null) {
            return null;
        }

        return ts2Str(ts.getTime());
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        Timestamp ts = Timestamp.valueOf(parameter);
        ps.setTimestamp(i, ts);
    }

    private String ts2Str(long ts) {
        Date date = new Date(ts);
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        return df.format(date);
    }

}
