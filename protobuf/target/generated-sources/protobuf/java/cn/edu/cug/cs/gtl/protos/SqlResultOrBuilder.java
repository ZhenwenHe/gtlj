// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: cn/edu/cug/cs/gtl/protos/sql.proto

package cn.edu.cug.cs.gtl.protos;

public interface SqlResultOrBuilder extends
    // @@protoc_insertion_point(interface_extends:cn.edu.cug.cs.gtl.protos.SqlResult)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   *返回执行的状态
   * </pre>
   *
   * <code>bool status = 1;</code>
   * @return The status.
   */
  boolean getStatus();

  /**
   * <code>string command_text = 2;</code>
   * @return The commandText.
   */
  java.lang.String getCommandText();
  /**
   * <code>string command_text = 2;</code>
   * @return The bytes for commandText.
   */
  com.google.protobuf.ByteString
      getCommandTextBytes();

  /**
   * <code>.cn.edu.cug.cs.gtl.protos.DataSet dataset = 3;</code>
   * @return Whether the dataset field is set.
   */
  boolean hasDataset();
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.DataSet dataset = 3;</code>
   * @return The dataset.
   */
  cn.edu.cug.cs.gtl.protos.DataSet getDataset();
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.DataSet dataset = 3;</code>
   */
  cn.edu.cug.cs.gtl.protos.DataSetOrBuilder getDatasetOrBuilder();
}
