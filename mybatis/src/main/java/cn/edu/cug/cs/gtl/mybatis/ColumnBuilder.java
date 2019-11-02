package cn.edu.cug.cs.gtl.mybatis;

import cn.edu.cug.cs.gtl.jts.util.StringUtil;
import cn.edu.cug.cs.gtl.protos.*;
import com.google.protobuf.Any;
import com.google.protobuf.ByteString;

import java.util.List;

public class ColumnBuilder {
    /**
     *
     * @param columnName
     * @param texts
     * @return
     */
    public static Column buildTextColumn(String columnName, String[] texts){
        Column.Builder columnBuilder = Column.newBuilder();
        for(String s: texts){
            columnBuilder.addValue(Any.pack(TextValue.newBuilder().setValue(s).build()));
        }

        ColumnInfo.Builder columnInfoBuilder = ColumnInfo.newBuilder();
        columnInfoBuilder.setName(columnName);
        columnInfoBuilder.setType(TypeBuilder.buildTextType());
        ColumnInfo columnInfo = columnInfoBuilder.build();

        return columnBuilder.setInfo(columnInfo).build();
    }

    /**
     *
     * @param columnName
     * @param blobs
     * @return
     */
    public static Column buildBlobColumn(String columnName,List<byte[]> blobs){
        Column.Builder columnBuilder = Column.newBuilder();
        for(byte[] s: blobs){
            columnBuilder.addValue(
                    Any.pack(
                            BlobValue
                                    .newBuilder()
                                    .setValue(ByteString.copyFrom(s))
                                    .build()));
        }

        ColumnInfo.Builder columnInfoBuilder = ColumnInfo.newBuilder();
        columnInfoBuilder.setName(columnName);
        columnInfoBuilder.setType(TypeBuilder.buildBlobType());
        ColumnInfo columnInfo = columnInfoBuilder.build();

        return columnBuilder.setInfo(columnInfo).build();
    }

    /**
     *
     * @param columnName
     * @param values
     * @return
     */
    public static Column buildTimeColumn(String columnName,List<TimeValue> values){
        Column.Builder columnBuilder = Column.newBuilder();
        for(TimeValue s: values){
            columnBuilder.addValue(
                    Any.pack(s));
        }

        ColumnInfo.Builder columnInfoBuilder = ColumnInfo.newBuilder();
        columnInfoBuilder.setName(columnName);
        columnInfoBuilder.setType(TypeBuilder.buildTimeType());
        ColumnInfo columnInfo = columnInfoBuilder.build();

        return columnBuilder.setInfo(columnInfo).build();
    }

    /**
     *
     * @param columnName
     * @param vs
     * @return
     */
    public static Column buildIntegerColumn(String columnName,short[] vs){
        Column.Builder columnBuilder = Column.newBuilder();
        for(short s: vs){
            columnBuilder.addValue(
                    Any.pack(IntegerValue.newBuilder().setValue(s).build()));
        }

        ColumnInfo.Builder columnInfoBuilder = ColumnInfo.newBuilder();
        columnInfoBuilder.setName(columnName);
        columnInfoBuilder.setType(TypeBuilder.buildIntegerType());
        ColumnInfo columnInfo = columnInfoBuilder.build();

        return columnBuilder.setInfo(columnInfo).build();
    }

    /**
     *
     * @param columnName
     * @param vs
     * @return
     */
    public static Column buildIntegerColumn(String columnName,int[] vs){
        Column.Builder columnBuilder = Column.newBuilder();
        for(int s: vs){
            columnBuilder.addValue(
                    Any.pack(IntegerValue.newBuilder().setValue(s).build()));
        }

        ColumnInfo.Builder columnInfoBuilder = ColumnInfo.newBuilder();
        columnInfoBuilder.setName(columnName);
        columnInfoBuilder.setType(TypeBuilder.buildIntegerType());
        ColumnInfo columnInfo = columnInfoBuilder.build();

        return columnBuilder.setInfo(columnInfo).build();
    }

    /**
     *
     * @param columnName
     * @param vs
     * @return
     */
    public static Column buildIntegerColumn(String columnName,long [] vs){
        Column.Builder columnBuilder = Column.newBuilder();
        for(long s: vs){
            columnBuilder.addValue(
                    Any.pack(IntegerValue.newBuilder().setValue(s).build()));
        }

        ColumnInfo.Builder columnInfoBuilder = ColumnInfo.newBuilder();
        columnInfoBuilder.setName(columnName);
        columnInfoBuilder.setType(TypeBuilder.buildIntegerType());
        ColumnInfo columnInfo = columnInfoBuilder.build();

        return columnBuilder.setInfo(columnInfo).build();
    }

    /**
     *
     * @param columnName
     * @param vs
     * @return
     */
    public static Column buildRealColumn(String columnName,float[] vs){

        Column.Builder columnBuilder = Column.newBuilder();
        for(float s: vs){
            columnBuilder.addValue(
                    Any.pack(RealValue.newBuilder().setValue(s).build()));
        }

        ColumnInfo.Builder columnInfoBuilder = ColumnInfo.newBuilder();
        columnInfoBuilder.setName(columnName);
        columnInfoBuilder.setType(TypeBuilder.buildIntegerType());
        ColumnInfo columnInfo = columnInfoBuilder.build();

        return columnBuilder.setInfo(columnInfo).build();
    }

    /**
     *
     * @param columnName
     * @param vs
     * @return
     */
    public static Column buildRealColumn(String columnName,double[] vs){

        Column.Builder columnBuilder = Column.newBuilder();
        for(double s: vs){
            columnBuilder.addValue(
                    Any.pack(RealValue.newBuilder().setValue(s).build()));
        }

        ColumnInfo.Builder columnInfoBuilder = ColumnInfo.newBuilder();
        columnInfoBuilder.setName(columnName);
        columnInfoBuilder.setType(TypeBuilder.buildIntegerType());
        ColumnInfo columnInfo = columnInfoBuilder.build();

        return columnBuilder.setInfo(columnInfo).build();
    }

    /**
     *
     * @param columnName
     * @param udtName
     * @param vs
     * @return
     */
    public static Column buildUDTColumn(String columnName,String udtName, String[] vs){

        Column.Builder columnBuilder = Column.newBuilder();
        for(String s: vs){
            columnBuilder.addValue(
                    Any.pack(UDTValue.newBuilder().setValue(s).build()));
        }

        ColumnInfo.Builder columnInfoBuilder = ColumnInfo.newBuilder();
        columnInfoBuilder.setName(columnName);
        columnInfoBuilder.setType(TypeBuilder.buildUDTType(udtName));
        ColumnInfo columnInfo = columnInfoBuilder.build();

        return columnBuilder.setInfo(columnInfo).build();
    }

}
