// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: cn/edu/cug/cs/gtl/protos/value.proto

package cn.edu.cug.cs.gtl.protos;

public interface TableOrBuilder extends
    // @@protoc_insertion_point(interface_extends:cn.edu.cug.cs.gtl.protos.Table)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Identifier identifier = 1;</code>
   * @return Whether the identifier field is set.
   */
  boolean hasIdentifier();
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Identifier identifier = 1;</code>
   * @return The identifier.
   */
  cn.edu.cug.cs.gtl.protos.Identifier getIdentifier();
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Identifier identifier = 1;</code>
   */
  cn.edu.cug.cs.gtl.protos.IdentifierOrBuilder getIdentifierOrBuilder();

  /**
   * <code>.cn.edu.cug.cs.gtl.protos.TableInfo info = 2;</code>
   * @return Whether the info field is set.
   */
  boolean hasInfo();
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.TableInfo info = 2;</code>
   * @return The info.
   */
  cn.edu.cug.cs.gtl.protos.TableInfo getInfo();
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.TableInfo info = 2;</code>
   */
  cn.edu.cug.cs.gtl.protos.TableInfoOrBuilder getInfoOrBuilder();

  /**
   * <code>repeated .cn.edu.cug.cs.gtl.protos.Column column = 3;</code>
   */
  java.util.List<cn.edu.cug.cs.gtl.protos.Column> 
      getColumnList();
  /**
   * <code>repeated .cn.edu.cug.cs.gtl.protos.Column column = 3;</code>
   */
  cn.edu.cug.cs.gtl.protos.Column getColumn(int index);
  /**
   * <code>repeated .cn.edu.cug.cs.gtl.protos.Column column = 3;</code>
   */
  int getColumnCount();
  /**
   * <code>repeated .cn.edu.cug.cs.gtl.protos.Column column = 3;</code>
   */
  java.util.List<? extends cn.edu.cug.cs.gtl.protos.ColumnOrBuilder> 
      getColumnOrBuilderList();
  /**
   * <code>repeated .cn.edu.cug.cs.gtl.protos.Column column = 3;</code>
   */
  cn.edu.cug.cs.gtl.protos.ColumnOrBuilder getColumnOrBuilder(
      int index);
}