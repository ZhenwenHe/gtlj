package cn.edu.cug.cs.gtl.mybatis;


import cn.edu.cug.cs.gtl.common.Variant;
import cn.edu.cug.cs.gtl.protos.*;
import cn.edu.cug.cs.gtl.util.StringUtils;
import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.sun.jersey.core.util.Base64;

import java.io.UnsupportedEncodingException;
import java.sql.Blob;

public class ValueBuilder {
    /**
     *
     * @param text
     * @return
     */
    public static Value buildTextValue(String text){
        TextValue v = TextValue.newBuilder().setValue(text).build();
        Value.Builder builder = Value.newBuilder();
        builder.setType(TypeBuilder.buildTextType());
        builder.setData(Any.pack(v));
        return builder.build();
    }

    /**
     *
     * @param d
     * @return
     */
    public static Value buildRealValue(double d){
        RealValue v = RealValue.newBuilder().setValue(d).build();
        Value.Builder builder = Value.newBuilder();
        builder.setType(TypeBuilder.buildRealType());
        builder.setData(Any.pack(v));
        return builder.build();
    }

    /**
     *
     * @param d
     * @return
     */
    public static Value buildIntegerValue(int d){
        IntegerValue v = IntegerValue.newBuilder().setValue(d).build();
        Value.Builder builder = Value.newBuilder();
        builder.setType(TypeBuilder.buildIntegerType());
        builder.setData(Any.pack(v));
        return builder.build();
    }

    /**
     *
     * @param d
     * @return
     */
    public static Value buildBlobValue(byte[] d){
        BlobValue v = BlobValue.newBuilder().setValue(ByteString.copyFrom(d)).build();
        Value.Builder builder = Value.newBuilder();
        builder.setType(TypeBuilder.buildBlobType());
        builder.setData(Any.pack(v));
        return builder.build();
    }

    /**
     *
     * @return null value
     */
    public static Value buildNullValue(){
        NullValue nullValue= NullValue.newBuilder().build();
        Value.Builder builder = Value.newBuilder();
        builder.setType(TypeBuilder.buildNullType());
        builder.setData(Any.pack(nullValue));
        return builder.build();
    }

    /**
     *
     * @param typeName value type name
     * @param value Base64 UTF-8 String
     * @return
     */
    public static Value buildUDTValue(String typeName, String value){
        UDTValue.Builder udtBuilder = UDTValue.newBuilder();
        udtBuilder.setValue(value);
        Value.Builder builder = Value.newBuilder();
        builder.setType(TypeBuilder.buildUDTType(typeName));
        builder.setData(Any.pack(udtBuilder.build()));
        return builder.build();
    }

}
