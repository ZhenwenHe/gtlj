// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: cn/edu/cug/cs/gtl/protos/series.proto

package cn.edu.cug.cs.gtl.protos;

public interface SeriesdOrBuilder extends
    // @@protoc_insertion_point(interface_extends:cn.edu.cug.cs.gtl.protos.Seriesd)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>repeated double data = 1;</code>
   * @return A list containing the data.
   */
  java.util.List<java.lang.Double> getDataList();
  /**
   * <code>repeated double data = 1;</code>
   * @return The count of data.
   */
  int getDataCount();
  /**
   * <code>repeated double data = 1;</code>
   * @param index The index of the element to return.
   * @return The data at the given index.
   */
  double getData(int index);

  /**
   * <code>string label = 2;</code>
   * @return The label.
   */
  java.lang.String getLabel();
  /**
   * <code>string label = 2;</code>
   * @return The bytes for label.
   */
  com.google.protobuf.ByteString
      getLabelBytes();
}
