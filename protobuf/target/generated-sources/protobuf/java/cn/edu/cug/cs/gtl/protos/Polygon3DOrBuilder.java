// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: cn/edu/cug/cs/gtl/protos/geometry.proto

package cn.edu.cug.cs.gtl.protos;

public interface Polygon3DOrBuilder extends
    // @@protoc_insertion_point(interface_extends:cn.edu.cug.cs.gtl.protos.Polygon3D)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.cn.edu.cug.cs.gtl.protos.LinearRing3D shell = 1;</code>
   * @return Whether the shell field is set.
   */
  boolean hasShell();
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.LinearRing3D shell = 1;</code>
   * @return The shell.
   */
  cn.edu.cug.cs.gtl.protos.LinearRing3D getShell();
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.LinearRing3D shell = 1;</code>
   */
  cn.edu.cug.cs.gtl.protos.LinearRing3DOrBuilder getShellOrBuilder();

  /**
   * <code>repeated .cn.edu.cug.cs.gtl.protos.LinearRing3D ring = 2;</code>
   */
  java.util.List<cn.edu.cug.cs.gtl.protos.LinearRing3D> 
      getRingList();
  /**
   * <code>repeated .cn.edu.cug.cs.gtl.protos.LinearRing3D ring = 2;</code>
   */
  cn.edu.cug.cs.gtl.protos.LinearRing3D getRing(int index);
  /**
   * <code>repeated .cn.edu.cug.cs.gtl.protos.LinearRing3D ring = 2;</code>
   */
  int getRingCount();
  /**
   * <code>repeated .cn.edu.cug.cs.gtl.protos.LinearRing3D ring = 2;</code>
   */
  java.util.List<? extends cn.edu.cug.cs.gtl.protos.LinearRing3DOrBuilder> 
      getRingOrBuilderList();
  /**
   * <code>repeated .cn.edu.cug.cs.gtl.protos.LinearRing3D ring = 2;</code>
   */
  cn.edu.cug.cs.gtl.protos.LinearRing3DOrBuilder getRingOrBuilder(
      int index);
}
