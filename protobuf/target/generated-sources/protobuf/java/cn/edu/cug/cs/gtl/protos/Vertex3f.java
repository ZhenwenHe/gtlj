// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: cn/edu/cug/cs/gtl/protos/vertex.proto

package cn.edu.cug.cs.gtl.protos;

/**
 * Protobuf type {@code cn.edu.cug.cs.gtl.protos.Vertex3f}
 */
public  final class Vertex3f extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:cn.edu.cug.cs.gtl.protos.Vertex3f)
    Vertex3fOrBuilder {
private static final long serialVersionUID = 0L;
  // Use Vertex3f.newBuilder() to construct.
  private Vertex3f(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Vertex3f() {
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new Vertex3f();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private Vertex3f(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 13: {

            x_ = input.readFloat();
            break;
          }
          case 21: {

            y_ = input.readFloat();
            break;
          }
          case 29: {

            z_ = input.readFloat();
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return cn.edu.cug.cs.gtl.protos.Vertices.internal_static_cn_edu_cug_cs_gtl_protos_Vertex3f_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return cn.edu.cug.cs.gtl.protos.Vertices.internal_static_cn_edu_cug_cs_gtl_protos_Vertex3f_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            cn.edu.cug.cs.gtl.protos.Vertex3f.class, cn.edu.cug.cs.gtl.protos.Vertex3f.Builder.class);
  }

  public static final int X_FIELD_NUMBER = 1;
  private float x_;
  /**
   * <code>float x = 1;</code>
   * @return The x.
   */
  public float getX() {
    return x_;
  }

  public static final int Y_FIELD_NUMBER = 2;
  private float y_;
  /**
   * <code>float y = 2;</code>
   * @return The y.
   */
  public float getY() {
    return y_;
  }

  public static final int Z_FIELD_NUMBER = 3;
  private float z_;
  /**
   * <code>float z = 3;</code>
   * @return The z.
   */
  public float getZ() {
    return z_;
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (x_ != 0F) {
      output.writeFloat(1, x_);
    }
    if (y_ != 0F) {
      output.writeFloat(2, y_);
    }
    if (z_ != 0F) {
      output.writeFloat(3, z_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (x_ != 0F) {
      size += com.google.protobuf.CodedOutputStream
        .computeFloatSize(1, x_);
    }
    if (y_ != 0F) {
      size += com.google.protobuf.CodedOutputStream
        .computeFloatSize(2, y_);
    }
    if (z_ != 0F) {
      size += com.google.protobuf.CodedOutputStream
        .computeFloatSize(3, z_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof cn.edu.cug.cs.gtl.protos.Vertex3f)) {
      return super.equals(obj);
    }
    cn.edu.cug.cs.gtl.protos.Vertex3f other = (cn.edu.cug.cs.gtl.protos.Vertex3f) obj;

    if (java.lang.Float.floatToIntBits(getX())
        != java.lang.Float.floatToIntBits(
            other.getX())) return false;
    if (java.lang.Float.floatToIntBits(getY())
        != java.lang.Float.floatToIntBits(
            other.getY())) return false;
    if (java.lang.Float.floatToIntBits(getZ())
        != java.lang.Float.floatToIntBits(
            other.getZ())) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + X_FIELD_NUMBER;
    hash = (53 * hash) + java.lang.Float.floatToIntBits(
        getX());
    hash = (37 * hash) + Y_FIELD_NUMBER;
    hash = (53 * hash) + java.lang.Float.floatToIntBits(
        getY());
    hash = (37 * hash) + Z_FIELD_NUMBER;
    hash = (53 * hash) + java.lang.Float.floatToIntBits(
        getZ());
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static cn.edu.cug.cs.gtl.protos.Vertex3f parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static cn.edu.cug.cs.gtl.protos.Vertex3f parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Vertex3f parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static cn.edu.cug.cs.gtl.protos.Vertex3f parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Vertex3f parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static cn.edu.cug.cs.gtl.protos.Vertex3f parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Vertex3f parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static cn.edu.cug.cs.gtl.protos.Vertex3f parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Vertex3f parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static cn.edu.cug.cs.gtl.protos.Vertex3f parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Vertex3f parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static cn.edu.cug.cs.gtl.protos.Vertex3f parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(cn.edu.cug.cs.gtl.protos.Vertex3f prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code cn.edu.cug.cs.gtl.protos.Vertex3f}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:cn.edu.cug.cs.gtl.protos.Vertex3f)
      cn.edu.cug.cs.gtl.protos.Vertex3fOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return cn.edu.cug.cs.gtl.protos.Vertices.internal_static_cn_edu_cug_cs_gtl_protos_Vertex3f_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return cn.edu.cug.cs.gtl.protos.Vertices.internal_static_cn_edu_cug_cs_gtl_protos_Vertex3f_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              cn.edu.cug.cs.gtl.protos.Vertex3f.class, cn.edu.cug.cs.gtl.protos.Vertex3f.Builder.class);
    }

    // Construct using cn.edu.cug.cs.gtl.protos.Vertex3f.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      x_ = 0F;

      y_ = 0F;

      z_ = 0F;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return cn.edu.cug.cs.gtl.protos.Vertices.internal_static_cn_edu_cug_cs_gtl_protos_Vertex3f_descriptor;
    }

    @java.lang.Override
    public cn.edu.cug.cs.gtl.protos.Vertex3f getDefaultInstanceForType() {
      return cn.edu.cug.cs.gtl.protos.Vertex3f.getDefaultInstance();
    }

    @java.lang.Override
    public cn.edu.cug.cs.gtl.protos.Vertex3f build() {
      cn.edu.cug.cs.gtl.protos.Vertex3f result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public cn.edu.cug.cs.gtl.protos.Vertex3f buildPartial() {
      cn.edu.cug.cs.gtl.protos.Vertex3f result = new cn.edu.cug.cs.gtl.protos.Vertex3f(this);
      result.x_ = x_;
      result.y_ = y_;
      result.z_ = z_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof cn.edu.cug.cs.gtl.protos.Vertex3f) {
        return mergeFrom((cn.edu.cug.cs.gtl.protos.Vertex3f)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(cn.edu.cug.cs.gtl.protos.Vertex3f other) {
      if (other == cn.edu.cug.cs.gtl.protos.Vertex3f.getDefaultInstance()) return this;
      if (other.getX() != 0F) {
        setX(other.getX());
      }
      if (other.getY() != 0F) {
        setY(other.getY());
      }
      if (other.getZ() != 0F) {
        setZ(other.getZ());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      cn.edu.cug.cs.gtl.protos.Vertex3f parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (cn.edu.cug.cs.gtl.protos.Vertex3f) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private float x_ ;
    /**
     * <code>float x = 1;</code>
     * @return The x.
     */
    public float getX() {
      return x_;
    }
    /**
     * <code>float x = 1;</code>
     * @param value The x to set.
     * @return This builder for chaining.
     */
    public Builder setX(float value) {
      
      x_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>float x = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearX() {
      
      x_ = 0F;
      onChanged();
      return this;
    }

    private float y_ ;
    /**
     * <code>float y = 2;</code>
     * @return The y.
     */
    public float getY() {
      return y_;
    }
    /**
     * <code>float y = 2;</code>
     * @param value The y to set.
     * @return This builder for chaining.
     */
    public Builder setY(float value) {
      
      y_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>float y = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearY() {
      
      y_ = 0F;
      onChanged();
      return this;
    }

    private float z_ ;
    /**
     * <code>float z = 3;</code>
     * @return The z.
     */
    public float getZ() {
      return z_;
    }
    /**
     * <code>float z = 3;</code>
     * @param value The z to set.
     * @return This builder for chaining.
     */
    public Builder setZ(float value) {
      
      z_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>float z = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearZ() {
      
      z_ = 0F;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:cn.edu.cug.cs.gtl.protos.Vertex3f)
  }

  // @@protoc_insertion_point(class_scope:cn.edu.cug.cs.gtl.protos.Vertex3f)
  private static final cn.edu.cug.cs.gtl.protos.Vertex3f DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new cn.edu.cug.cs.gtl.protos.Vertex3f();
  }

  public static cn.edu.cug.cs.gtl.protos.Vertex3f getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Vertex3f>
      PARSER = new com.google.protobuf.AbstractParser<Vertex3f>() {
    @java.lang.Override
    public Vertex3f parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new Vertex3f(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<Vertex3f> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Vertex3f> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public cn.edu.cug.cs.gtl.protos.Vertex3f getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

