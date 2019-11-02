package cn.edu.cug.cs.gtl.mybatis;

import cn.edu.cug.cs.gtl.common.Variant;
import cn.edu.cug.cs.gtl.protos.*;
import cn.edu.cug.cs.gtl.util.StringUtils;
import com.google.protobuf.InvalidProtocolBufferException;

public class ValueExtractor {
    /**
     *
     * @param v
     * @return
     */
    public static String extractString(Value v) {
        try {
            switch (v.getType().getId()){
                case TEXT:{
                    TextValue textValue= v.getData().unpack(TextValue.class);
                    return textValue.getValue();
                }
                case INTEGER:{
                    extractInteger(v).toString();
                }
                case REAL:{
                    extractReal(v).toString();
                }
                case BLOB:{
                    BlobValue blobValue= v.getData().unpack(BlobValue.class);
                    byte[] bs = blobValue.getValue().toByteArray();
                    return StringUtils.encodeToString(bs);
                }
                case TIME:{
                    TimeValue timeValue= v.getData().unpack(TimeValue.class);
                    return String.format("%d-%d-%d %d:%d:%d",
                            timeValue.getYear(),timeValue.getMonth(),timeValue.getDay(),
                            timeValue.getHour(),timeValue.getMinute(),timeValue.getSecond());
                }
                case UDT:{
                    return v.getData().unpack(UDTValue.class).getValue();
                }
                default:{// NULL:
                    return "null";
                }
            }
        }
        catch (InvalidProtocolBufferException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param v
     * @return
     */
    public static byte[] extractBytes(Value v) {
        try {
            switch (v.getType().getId()){
                case TEXT:{
                    TextValue textValue= v.getData().unpack(TextValue.class);
                    return StringUtils.decodeToBytes(textValue.getValue());
                }
                case INTEGER:{
                    IntegerValue integerValue=v.getData().unpack(IntegerValue.class);
                    return Variant.longToByteArray(integerValue.getValue());
                }
                case REAL:{
                    RealValue realValue=v.getData().unpack(RealValue.class);
                    return Variant.doubleToByteArray(realValue.getValue());
                }
                case BLOB:{
                    BlobValue blobValue= v.getData().unpack(BlobValue.class);
                    return blobValue.getValue().toByteArray();
                }
                case TIME:{
                    TimeValue timeValue= v.getData().unpack(TimeValue.class);
                    return timeValue.toByteArray();
                }
                case UDT:{
                    return v.getData().unpack(UDTValue.class).toByteArray();
                }
                default:{// NULL:
                    return v.getData().unpack(NullValue.class).toByteArray();
                }
            }
        }
        catch (InvalidProtocolBufferException  e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param v
     * @return
     */
    public static Long extractInteger(Value v) {
        try {
            IntegerValue integerValue= v.getData().unpack(IntegerValue.class);
            return integerValue.getValue();
        }
        catch (InvalidProtocolBufferException  e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param v
     * @return
     */
    public static Double extractReal(Value v) {
        try {
            RealValue d= v.getData().unpack(RealValue.class);
            return d.getValue();
        }
        catch (InvalidProtocolBufferException  e){
            e.printStackTrace();
            return null;
        }
    }

}
