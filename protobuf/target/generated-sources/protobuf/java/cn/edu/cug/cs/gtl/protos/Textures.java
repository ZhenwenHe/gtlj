// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: cn/edu/cug/cs/gtl/protos/texture.proto

package cn.edu.cug.cs.gtl.protos;

public final class Textures {
  private Textures() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_cn_edu_cug_cs_gtl_protos_Texture_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_cn_edu_cug_cs_gtl_protos_Texture_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n&cn/edu/cug/cs/gtl/protos/texture.proto" +
      "\022\030cn.edu.cug.cs.gtl.protos\032)cn/edu/cug/c" +
      "s/gtl/protos/identifier.proto\"\232\001\n\007Textur" +
      "e\0228\n\nidentifier\030\001 \001(\0132$.cn.edu.cug.cs.gt" +
      "l.protos.Identifier\022\014\n\004name\030\002 \001(\t\022\014\n\004typ" +
      "e\030\003 \001(\005\022\021\n\twrap_mode\030\004 \001(\005\022\022\n\nimage_name" +
      "\030\005 \001(\t\022\022\n\nimage_data\030\006 \001(\014BD\n\030cn.edu.cug" +
      ".cs.gtl.protosB\010TexturesP\001\370\001\001\252\002\030cn.edu.c" +
      "ug.cs.gtl.protosb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          cn.edu.cug.cs.gtl.protos.Identifiers.getDescriptor(),
        });
    internal_static_cn_edu_cug_cs_gtl_protos_Texture_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_cn_edu_cug_cs_gtl_protos_Texture_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_cn_edu_cug_cs_gtl_protos_Texture_descriptor,
        new java.lang.String[] { "Identifier", "Name", "Type", "WrapMode", "ImageName", "ImageData", });
    cn.edu.cug.cs.gtl.protos.Identifiers.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
