// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: cn/edu/cug/cs/gtl/protos/document.proto

package cn.edu.cug.cs.gtl.protos;

public interface ParagraphOrBuilder extends
    // @@protoc_insertion_point(interface_extends:cn.edu.cug.cs.gtl.protos.Paragraph)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string document_title = 1;</code>
   * @return The documentTitle.
   */
  java.lang.String getDocumentTitle();
  /**
   * <code>string document_title = 1;</code>
   * @return The bytes for documentTitle.
   */
  com.google.protobuf.ByteString
      getDocumentTitleBytes();

  /**
   * <code>int64 order = 2;</code>
   * @return The order.
   */
  long getOrder();

  /**
   * <code>string text = 3;</code>
   * @return The text.
   */
  java.lang.String getText();
  /**
   * <code>string text = 3;</code>
   * @return The bytes for text.
   */
  com.google.protobuf.ByteString
      getTextBytes();
}
