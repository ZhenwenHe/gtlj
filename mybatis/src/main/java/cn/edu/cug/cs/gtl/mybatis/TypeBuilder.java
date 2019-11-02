package cn.edu.cug.cs.gtl.mybatis;

import cn.edu.cug.cs.gtl.protos.Type;

public class TypeBuilder {
    public static Type  buildTextType(){
        Type.Builder b = Type.newBuilder();
        b.setAlias("TEXT");
        b.setId(Type.EnumType.TEXT);
        return b.build();
    }
    public static Type  buildBlobType(){
        Type.Builder b = Type.newBuilder();
        b.setAlias("BLOB");
        b.setId(Type.EnumType.BLOB);
        return b.build();
    }
    public static Type  buildIntegerType(){
        Type.Builder b = Type.newBuilder();
        b.setAlias("INTEGER");
        b.setId(Type.EnumType.INTEGER);
        return b.build();
    }
    public static Type  buildRealType(){
        Type.Builder b = Type.newBuilder();
        b.setAlias("REAL");
        b.setId(Type.EnumType.REAL);
        return b.build();
    }
    public static Type  buildTimeType(){
        Type.Builder b = Type.newBuilder();
        b.setAlias("TIME");
        b.setId(Type.EnumType.TIME);
        return b.build();
    }
    public static Type  buildNullType(){
        Type.Builder b = Type.newBuilder();
        b.setAlias("NULL");
        b.setId(Type.EnumType.NULL);
        return b.build();
    }

    /**
     *
     * @param udtTypeName
     * @return
     */
    public static Type  buildUDTType(String udtTypeName){
        Type.Builder b = Type.newBuilder();
        b.setAlias(udtTypeName);
        udtTypeName = udtTypeName.toUpperCase();
        if(udtTypeName.contains("CHAR") || udtTypeName.contains("TEXT") || udtTypeName.contains("STRING")){
            b.setId(Type.EnumType.TEXT);
        }
        else if(udtTypeName.contains("INT")
                || udtTypeName.contains("LONG")
                || udtTypeName.contains("NUMBER")
                || udtTypeName.contains("NUMERIC")) {
            b.setId(Type.EnumType.INTEGER);
        }
        else if(udtTypeName.contains("FLOAT")
                || udtTypeName.contains("DOUBLE")
                || udtTypeName.contains("REAL")
                || udtTypeName.contains("DECIMAL")) {
            b.setId(Type.EnumType.REAL);
        }
        else if(udtTypeName.contains("TIME")
                || udtTypeName.contains("DATE")) {
            b.setId(Type.EnumType.REAL);
        }
        else {
            b.setId(Type.EnumType.BLOB);
        }
        return b.build();
    }
}
