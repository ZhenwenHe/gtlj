// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: cn/edu/cug/cs/gtl/protos/bound.proto

package cn.edu.cug.cs.gtl.protos;

public interface BoundingSphereOrBuilder extends
    // @@protoc_insertion_point(interface_extends:cn.edu.cug.cs.gtl.protos.BoundingSphere)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int32 dimension = 1;</code>
   * @return The dimension.
   */
  int getDimension();

  /**
   * <code>double radius = 2;</code>
   * @return The radius.
   */
  double getRadius();

  /**
   * <code>repeated double ordinate = 3;</code>
   * @return A list containing the ordinate.
   */
  java.util.List<java.lang.Double> getOrdinateList();
  /**
   * <code>repeated double ordinate = 3;</code>
   * @return The count of ordinate.
   */
  int getOrdinateCount();
  /**
   * <code>repeated double ordinate = 3;</code>
   * @param index The index of the element to return.
   * @return The ordinate at the given index.
   */
  double getOrdinate(int index);
}
