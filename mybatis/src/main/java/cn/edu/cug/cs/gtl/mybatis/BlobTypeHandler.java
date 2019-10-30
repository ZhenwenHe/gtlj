package cn.edu.cug.cs.gtl.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.sql.*;

public class BlobTypeHandler extends BaseTypeHandler<String> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        //声明一个输入流对象
        ByteArrayInputStream bis;
        try {
            //把字符串转为字节流
            bis = new ByteArrayInputStream(parameter.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Blob Encoding Error!");
        }
        ps.setBinaryStream(i, bis, parameter.length());
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Blob blob = (Blob) rs.getBlob(columnName);
        return blobToString(blob);
    }

    @Override
    public String getNullableResult(ResultSet rs, int i) throws SQLException {
        Blob blob = (Blob) rs.getBlob(i);
        return blobToString(blob);
    }

    @Override
    public String getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        Blob blob = (Blob) callableStatement.getBlob(i);
        return blobToString(blob);
    }

    /**
     *
     * @param blob
     * @return
     * @throws SQLException
     */
    private String blobToString(Blob blob) throws SQLException{
        byte[] returnValue = null;
        if (null != blob) {
            returnValue = blob.getBytes(1, (int) blob.length());
        }
        try {
            //将取出的流对象转为utf-8的字符串对象
            return new String(returnValue, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Blob Encoding Error!");
        }
    }
}
