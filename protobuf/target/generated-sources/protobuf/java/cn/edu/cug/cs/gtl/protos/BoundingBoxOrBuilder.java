// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: cn/edu/cug/cs/gtl/protos/bound.proto

package cn.edu.cug.cs.gtl.protos;

public interface BoundingBoxOrBuilder extends
    // @@protoc_insertion_point(interface_extends:cn.edu.cug.cs.gtl.protos.BoundingBox)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   *dimension=3
   * </pre>
   *
   * <code>int32 dimension = 1;</code>
   * @return The dimension.
   */
  int getDimension();

  /**
   * <pre>
   *[minx,maxx,miny,maxy,minz,maxz]
   * </pre>
   *
   * <code>repeated double ordinate = 2;</code>
   * @return A list containing the ordinate.
   */
  java.util.List<java.lang.Double> getOrdinateList();
  /**
   * <pre>
   *[minx,maxx,miny,maxy,minz,maxz]
   * </pre>
   *
   * <code>repeated double ordinate = 2;</code>
   * @return The count of ordinate.
   */
  int getOrdinateCount();
  /**
   * <pre>
   *[minx,maxx,miny,maxy,minz,maxz]
   * </pre>
   *
   * <code>repeated double ordinate = 2;</code>
   * @param index The index of the element to return.
   * @return The ordinate at the given index.
   */
  double getOrdinate(int index);
}
