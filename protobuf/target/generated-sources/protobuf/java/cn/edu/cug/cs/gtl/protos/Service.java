// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: cn/edu/cug/cs/gtl/protos/service.proto

package cn.edu.cug.cs.gtl.protos;

public final class Service {
  private Service() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_cn_edu_cug_cs_gtl_protos_Request_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_cn_edu_cug_cs_gtl_protos_Request_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_cn_edu_cug_cs_gtl_protos_Response_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_cn_edu_cug_cs_gtl_protos_Response_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n&cn/edu/cug/cs/gtl/protos/service.proto" +
      "\022\030cn.edu.cug.cs.gtl.protos\032\"cn/edu/cug/c" +
      "s/gtl/protos/sql.proto\032\'cn/edu/cug/cs/gt" +
      "l/protos/document.proto\"\032\n\007Request\022\017\n\007re" +
      "quest\030\002 \001(\014\"\034\n\010Response\022\020\n\010response\030\001 \001(" +
      "\0142b\n\rCommonService\022Q\n\006handle\022!.cn.edu.cu" +
      "g.cs.gtl.protos.Request\032\".cn.edu.cug.cs." +
      "gtl.protos.Response\"\0002\336\003\n\nSqlService\022V\n\007" +
      "execute\022$.cn.edu.cug.cs.gtl.protos.SqlCo" +
      "mmand\032#.cn.edu.cug.cs.gtl.protos.SqlResu" +
      "lt\"\000\022]\n\006insert\022,.cn.edu.cug.cs.gtl.proto" +
      "s.SqlInsertStatement\032#.cn.edu.cug.cs.gtl" +
      ".protos.SqlResult\"\000\022]\n\006delete\022,.cn.edu.c" +
      "ug.cs.gtl.protos.SqlDeleteStatement\032#.cn" +
      ".edu.cug.cs.gtl.protos.SqlResult\"\000\022[\n\005qu" +
      "ery\022+.cn.edu.cug.cs.gtl.protos.SqlQueryS" +
      "tatement\032#.cn.edu.cug.cs.gtl.protos.SqlR" +
      "esult\"\000\022]\n\006update\022,.cn.edu.cug.cs.gtl.pr" +
      "otos.SqlUpdateStatement\032#.cn.edu.cug.cs." +
      "gtl.protos.SqlResult\"\0002\300\003\n\nDocService\022V\n" +
      "\007execute\022$.cn.edu.cug.cs.gtl.protos.SqlC" +
      "ommand\032#.cn.edu.cug.cs.gtl.protos.SqlRes" +
      "ult\"\000\022S\n\006insert\022\".cn.edu.cug.cs.gtl.prot" +
      "os.Document\032#.cn.edu.cug.cs.gtl.protos.S" +
      "qlResult\"\000\022S\n\006delete\022\".cn.edu.cug.cs.gtl" +
      ".protos.Document\032#.cn.edu.cug.cs.gtl.pro" +
      "tos.SqlResult\"\000\022[\n\005query\022+.cn.edu.cug.cs" +
      ".gtl.protos.SqlQueryStatement\032#.cn.edu.c" +
      "ug.cs.gtl.protos.SqlResult\"\000\022S\n\006update\022\"" +
      ".cn.edu.cug.cs.gtl.protos.Document\032#.cn." +
      "edu.cug.cs.gtl.protos.SqlResult\"\000BC\n\030cn." +
      "edu.cug.cs.gtl.protosB\007ServiceP\001\370\001\001\252\002\030cn" +
      ".edu.cug.cs.gtl.protosb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          cn.edu.cug.cs.gtl.protos.Sql.getDescriptor(),
          cn.edu.cug.cs.gtl.protos.Documents.getDescriptor(),
        });
    internal_static_cn_edu_cug_cs_gtl_protos_Request_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_cn_edu_cug_cs_gtl_protos_Request_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_cn_edu_cug_cs_gtl_protos_Request_descriptor,
        new java.lang.String[] { "Request", });
    internal_static_cn_edu_cug_cs_gtl_protos_Response_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_cn_edu_cug_cs_gtl_protos_Response_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_cn_edu_cug_cs_gtl_protos_Response_descriptor,
        new java.lang.String[] { "Response", });
    cn.edu.cug.cs.gtl.protos.Sql.getDescriptor();
    cn.edu.cug.cs.gtl.protos.Documents.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}