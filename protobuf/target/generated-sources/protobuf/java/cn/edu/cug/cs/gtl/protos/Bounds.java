// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: cn/edu/cug/cs/gtl/protos/bound.proto

package cn.edu.cug.cs.gtl.protos;

public final class Bounds {
  private Bounds() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_cn_edu_cug_cs_gtl_protos_Envelope3D_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_cn_edu_cug_cs_gtl_protos_Envelope3D_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_cn_edu_cug_cs_gtl_protos_Envelope2D_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_cn_edu_cug_cs_gtl_protos_Envelope2D_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_cn_edu_cug_cs_gtl_protos_Sphere3D_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_cn_edu_cug_cs_gtl_protos_Sphere3D_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_cn_edu_cug_cs_gtl_protos_BoundingBox_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_cn_edu_cug_cs_gtl_protos_BoundingBox_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_cn_edu_cug_cs_gtl_protos_BoundingSphere_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_cn_edu_cug_cs_gtl_protos_BoundingSphere_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n$cn/edu/cug/cs/gtl/protos/bound.proto\022\030" +
      "cn.edu.cug.cs.gtl.protos\"f\n\nEnvelope3D\022\r" +
      "\n\005min_x\030\001 \001(\001\022\r\n\005min_y\030\002 \001(\001\022\r\n\005min_z\030\003 " +
      "\001(\001\022\r\n\005max_x\030\004 \001(\001\022\r\n\005max_y\030\005 \001(\001\022\r\n\005max" +
      "_z\030\006 \001(\001\"H\n\nEnvelope2D\022\r\n\005min_x\030\001 \001(\001\022\r\n" +
      "\005min_y\030\002 \001(\001\022\r\n\005max_x\030\004 \001(\001\022\r\n\005max_y\030\005 \001" +
      "(\001\";\n\010Sphere3D\022\016\n\006radius\030\001 \001(\001\022\t\n\001x\030\002 \001(" +
      "\001\022\t\n\001y\030\003 \001(\001\022\t\n\001z\030\004 \001(\001\"2\n\013BoundingBox\022\021" +
      "\n\tdimension\030\001 \001(\005\022\020\n\010ordinate\030\002 \003(\001\"E\n\016B" +
      "oundingSphere\022\021\n\tdimension\030\001 \001(\005\022\016\n\006radi" +
      "us\030\002 \001(\001\022\020\n\010ordinate\030\003 \003(\001BB\n\030cn.edu.cug" +
      ".cs.gtl.protosB\006BoundsP\001\370\001\001\252\002\030cn.edu.cug" +
      ".cs.gtl.protosb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_cn_edu_cug_cs_gtl_protos_Envelope3D_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_cn_edu_cug_cs_gtl_protos_Envelope3D_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_cn_edu_cug_cs_gtl_protos_Envelope3D_descriptor,
        new java.lang.String[] { "MinX", "MinY", "MinZ", "MaxX", "MaxY", "MaxZ", });
    internal_static_cn_edu_cug_cs_gtl_protos_Envelope2D_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_cn_edu_cug_cs_gtl_protos_Envelope2D_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_cn_edu_cug_cs_gtl_protos_Envelope2D_descriptor,
        new java.lang.String[] { "MinX", "MinY", "MaxX", "MaxY", });
    internal_static_cn_edu_cug_cs_gtl_protos_Sphere3D_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_cn_edu_cug_cs_gtl_protos_Sphere3D_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_cn_edu_cug_cs_gtl_protos_Sphere3D_descriptor,
        new java.lang.String[] { "Radius", "X", "Y", "Z", });
    internal_static_cn_edu_cug_cs_gtl_protos_BoundingBox_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_cn_edu_cug_cs_gtl_protos_BoundingBox_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_cn_edu_cug_cs_gtl_protos_BoundingBox_descriptor,
        new java.lang.String[] { "Dimension", "Ordinate", });
    internal_static_cn_edu_cug_cs_gtl_protos_BoundingSphere_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_cn_edu_cug_cs_gtl_protos_BoundingSphere_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_cn_edu_cug_cs_gtl_protos_BoundingSphere_descriptor,
        new java.lang.String[] { "Dimension", "Radius", "Ordinate", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
