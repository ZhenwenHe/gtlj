// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: cn/edu/cug/cs/gtl/protos/value.proto

package cn.edu.cug.cs.gtl.protos;

public interface ColumnInfoOrBuilder extends
    // @@protoc_insertion_point(interface_extends:cn.edu.cug.cs.gtl.protos.ColumnInfo)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   *FTYPE
   * </pre>
   *
   * <code>.cn.edu.cug.cs.gtl.protos.Type type = 1;</code>
   * @return Whether the type field is set.
   */
  boolean hasType();
  /**
   * <pre>
   *FTYPE
   * </pre>
   *
   * <code>.cn.edu.cug.cs.gtl.protos.Type type = 1;</code>
   * @return The type.
   */
  cn.edu.cug.cs.gtl.protos.Type getType();
  /**
   * <pre>
   *FTYPE
   * </pre>
   *
   * <code>.cn.edu.cug.cs.gtl.protos.Type type = 1;</code>
   */
  cn.edu.cug.cs.gtl.protos.TypeOrBuilder getTypeOrBuilder();

  /**
   * <pre>
   *English Name, FENAME
   * </pre>
   *
   * <code>string name = 2;</code>
   * @return The name.
   */
  java.lang.String getName();
  /**
   * <pre>
   *English Name, FENAME
   * </pre>
   *
   * <code>string name = 2;</code>
   * @return The bytes for name.
   */
  com.google.protobuf.ByteString
      getNameBytes();

  /**
   * <pre>
   *FCODE
   * </pre>
   *
   * <code>string code = 3;</code>
   * @return The code.
   */
  java.lang.String getCode();
  /**
   * <pre>
   *FCODE
   * </pre>
   *
   * <code>string code = 3;</code>
   * @return The bytes for code.
   */
  com.google.protobuf.ByteString
      getCodeBytes();

  /**
   * <pre>
   *Chinese Name,FCNAME
   * </pre>
   *
   * <code>string title = 4;</code>
   * @return The title.
   */
  java.lang.String getTitle();
  /**
   * <pre>
   *Chinese Name,FCNAME
   * </pre>
   *
   * <code>string title = 4;</code>
   * @return The bytes for title.
   */
  com.google.protobuf.ByteString
      getTitleBytes();

  /**
   * <pre>
   *FLENGTH
   * </pre>
   *
   * <code>int32 length = 5;</code>
   * @return The length.
   */
  int getLength();

  /**
   * <pre>
   *FPRECISION,表示字段类型的精度的总长度，如果为null,表示精度的总长度不固定，最长为Length；
   * </pre>
   *
   * <code>int32 precision = 6;</code>
   * @return The precision.
   */
  int getPrecision();

  /**
   * <pre>
   *表示字段类型的精度范围，如果为0,表示只能存储为整数，
   * </pre>
   *
   * <code>int32 scale = 7;</code>
   * @return The scale.
   */
  int getScale();

  /**
   * <pre>
   *FISNULL
   * </pre>
   *
   * <code>bool nullable = 8;</code>
   * @return The nullable.
   */
  boolean getNullable();

  /**
   * <pre>
   *FDEFAULT
   * </pre>
   *
   * <code>string default_value = 9;</code>
   * @return The defaultValue.
   */
  java.lang.String getDefaultValue();
  /**
   * <pre>
   *FDEFAULT
   * </pre>
   *
   * <code>string default_value = 9;</code>
   * @return The bytes for defaultValue.
   */
  com.google.protobuf.ByteString
      getDefaultValueBytes();

  /**
   * <pre>
   *FMAX
   * </pre>
   *
   * <code>string max_value = 10;</code>
   * @return The maxValue.
   */
  java.lang.String getMaxValue();
  /**
   * <pre>
   *FMAX
   * </pre>
   *
   * <code>string max_value = 10;</code>
   * @return The bytes for maxValue.
   */
  com.google.protobuf.ByteString
      getMaxValueBytes();

  /**
   * <pre>
   *FMIN
   * </pre>
   *
   * <code>string min_value = 11;</code>
   * @return The minValue.
   */
  java.lang.String getMinValue();
  /**
   * <pre>
   *FMIN
   * </pre>
   *
   * <code>string min_value = 11;</code>
   * @return The bytes for minValue.
   */
  com.google.protobuf.ByteString
      getMinValueBytes();

  /**
   * <pre>
   *FCHECK
   * </pre>
   *
   * <code>string check = 12;</code>
   * @return The check.
   */
  java.lang.String getCheck();
  /**
   * <pre>
   *FCHECK
   * </pre>
   *
   * <code>string check = 12;</code>
   * @return The bytes for check.
   */
  com.google.protobuf.ByteString
      getCheckBytes();

  /**
   * <pre>
   *FMEMO
   * </pre>
   *
   * <code>string comment = 13;</code>
   * @return The comment.
   */
  java.lang.String getComment();
  /**
   * <pre>
   *FMEMO
   * </pre>
   *
   * <code>string comment = 13;</code>
   * @return The bytes for comment.
   */
  com.google.protobuf.ByteString
      getCommentBytes();

  /**
   * <pre>
   *FTABLENAME
   * </pre>
   *
   * <code>string table_name = 14;</code>
   * @return The tableName.
   */
  java.lang.String getTableName();
  /**
   * <pre>
   *FTABLENAME
   * </pre>
   *
   * <code>string table_name = 14;</code>
   * @return The bytes for tableName.
   */
  com.google.protobuf.ByteString
      getTableNameBytes();

  /**
   * <pre>
   *FTAG
   * </pre>
   *
   * <code>string tag = 15;</code>
   * @return The tag.
   */
  java.lang.String getTag();
  /**
   * <pre>
   *FTAG
   * </pre>
   *
   * <code>string tag = 15;</code>
   * @return The bytes for tag.
   */
  com.google.protobuf.ByteString
      getTagBytes();

  /**
   * <pre>
   *FCHARSET
   * </pre>
   *
   * <code>string char_set = 16;</code>
   * @return The charSet.
   */
  java.lang.String getCharSet();
  /**
   * <pre>
   *FCHARSET
   * </pre>
   *
   * <code>string char_set = 16;</code>
   * @return The bytes for charSet.
   */
  com.google.protobuf.ByteString
      getCharSetBytes();

  /**
   * <pre>
   *FENUM
   * </pre>
   *
   * <code>string enumeration = 17;</code>
   * @return The enumeration.
   */
  java.lang.String getEnumeration();
  /**
   * <pre>
   *FENUM
   * </pre>
   *
   * <code>string enumeration = 17;</code>
   * @return The bytes for enumeration.
   */
  com.google.protobuf.ByteString
      getEnumerationBytes();

  /**
   * <pre>
   *FPROCEDURE
   * </pre>
   *
   * <code>string procedure = 18;</code>
   * @return The procedure.
   */
  java.lang.String getProcedure();
  /**
   * <pre>
   *FPROCEDURE
   * </pre>
   *
   * <code>string procedure = 18;</code>
   * @return The bytes for procedure.
   */
  com.google.protobuf.ByteString
      getProcedureBytes();
}
