// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: cn/edu/cug/cs/gtl/protos/value.proto

package cn.edu.cug.cs.gtl.protos;

public interface ValueOrBuilder extends
    // @@protoc_insertion_point(interface_extends:cn.edu.cug.cs.gtl.protos.Value)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Type type = 1;</code>
   * @return Whether the type field is set.
   */
  boolean hasType();
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Type type = 1;</code>
   * @return The type.
   */
  cn.edu.cug.cs.gtl.protos.Type getType();
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Type type = 1;</code>
   */
  cn.edu.cug.cs.gtl.protos.TypeOrBuilder getTypeOrBuilder();

  /**
   * <code>.google.protobuf.Any data = 2;</code>
   * @return Whether the data field is set.
   */
  boolean hasData();
  /**
   * <code>.google.protobuf.Any data = 2;</code>
   * @return The data.
   */
  com.google.protobuf.Any getData();
  /**
   * <code>.google.protobuf.Any data = 2;</code>
   */
  com.google.protobuf.AnyOrBuilder getDataOrBuilder();
}
