// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: cn/edu/cug/cs/gtl/protos/vertex.proto

package cn.edu.cug.cs.gtl.protos;

/**
 * Protobuf type {@code cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d}
 */
public  final class TexturedVertex3d2d extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d)
    TexturedVertex3d2dOrBuilder {
private static final long serialVersionUID = 0L;
  // Use TexturedVertex3d2d.newBuilder() to construct.
  private TexturedVertex3d2d(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private TexturedVertex3d2d() {
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new TexturedVertex3d2d();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private TexturedVertex3d2d(
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
          case 9: {

            x_ = input.readDouble();
            break;
          }
          case 17: {

            y_ = input.readDouble();
            break;
          }
          case 25: {

            z_ = input.readDouble();
            break;
          }
          case 33: {

            u_ = input.readDouble();
            break;
          }
          case 41: {

            v_ = input.readDouble();
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
    return cn.edu.cug.cs.gtl.protos.Vertices.internal_static_cn_edu_cug_cs_gtl_protos_TexturedVertex3d2d_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return cn.edu.cug.cs.gtl.protos.Vertices.internal_static_cn_edu_cug_cs_gtl_protos_TexturedVertex3d2d_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d.class, cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d.Builder.class);
  }

  public static final int X_FIELD_NUMBER = 1;
  private double x_;
  /**
   * <code>double x = 1;</code>
   * @return The x.
   */
  public double getX() {
    return x_;
  }

  public static final int Y_FIELD_NUMBER = 2;
  private double y_;
  /**
   * <code>double y = 2;</code>
   * @return The y.
   */
  public double getY() {
    return y_;
  }

  public static final int Z_FIELD_NUMBER = 3;
  private double z_;
  /**
   * <code>double z = 3;</code>
   * @return The z.
   */
  public double getZ() {
    return z_;
  }

  public static final int U_FIELD_NUMBER = 4;
  private double u_;
  /**
   * <code>double u = 4;</code>
   * @return The u.
   */
  public double getU() {
    return u_;
  }

  public static final int V_FIELD_NUMBER = 5;
  private double v_;
  /**
   * <code>double v = 5;</code>
   * @return The v.
   */
  public double getV() {
    return v_;
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
    if (x_ != 0D) {
      output.writeDouble(1, x_);
    }
    if (y_ != 0D) {
      output.writeDouble(2, y_);
    }
    if (z_ != 0D) {
      output.writeDouble(3, z_);
    }
    if (u_ != 0D) {
      output.writeDouble(4, u_);
    }
    if (v_ != 0D) {
      output.writeDouble(5, v_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (x_ != 0D) {
      size += com.google.protobuf.CodedOutputStream
        .computeDoubleSize(1, x_);
    }
    if (y_ != 0D) {
      size += com.google.protobuf.CodedOutputStream
        .computeDoubleSize(2, y_);
    }
    if (z_ != 0D) {
      size += com.google.protobuf.CodedOutputStream
        .computeDoubleSize(3, z_);
    }
    if (u_ != 0D) {
      size += com.google.protobuf.CodedOutputStream
        .computeDoubleSize(4, u_);
    }
    if (v_ != 0D) {
      size += com.google.protobuf.CodedOutputStream
        .computeDoubleSize(5, v_);
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
    if (!(obj instanceof cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d)) {
      return super.equals(obj);
    }
    cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d other = (cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d) obj;

    if (java.lang.Double.doubleToLongBits(getX())
        != java.lang.Double.doubleToLongBits(
            other.getX())) return false;
    if (java.lang.Double.doubleToLongBits(getY())
        != java.lang.Double.doubleToLongBits(
            other.getY())) return false;
    if (java.lang.Double.doubleToLongBits(getZ())
        != java.lang.Double.doubleToLongBits(
            other.getZ())) return false;
    if (java.lang.Double.doubleToLongBits(getU())
        != java.lang.Double.doubleToLongBits(
            other.getU())) return false;
    if (java.lang.Double.doubleToLongBits(getV())
        != java.lang.Double.doubleToLongBits(
            other.getV())) return false;
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
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        java.lang.Double.doubleToLongBits(getX()));
    hash = (37 * hash) + Y_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        java.lang.Double.doubleToLongBits(getY()));
    hash = (37 * hash) + Z_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        java.lang.Double.doubleToLongBits(getZ()));
    hash = (37 * hash) + U_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        java.lang.Double.doubleToLongBits(getU()));
    hash = (37 * hash) + V_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        java.lang.Double.doubleToLongBits(getV()));
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d parseFrom(
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
  public static Builder newBuilder(cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d prototype) {
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
   * Protobuf type {@code cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d)
      cn.edu.cug.cs.gtl.protos.TexturedVertex3d2dOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return cn.edu.cug.cs.gtl.protos.Vertices.internal_static_cn_edu_cug_cs_gtl_protos_TexturedVertex3d2d_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return cn.edu.cug.cs.gtl.protos.Vertices.internal_static_cn_edu_cug_cs_gtl_protos_TexturedVertex3d2d_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d.class, cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d.Builder.class);
    }

    // Construct using cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d.newBuilder()
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
      x_ = 0D;

      y_ = 0D;

      z_ = 0D;

      u_ = 0D;

      v_ = 0D;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return cn.edu.cug.cs.gtl.protos.Vertices.internal_static_cn_edu_cug_cs_gtl_protos_TexturedVertex3d2d_descriptor;
    }

    @java.lang.Override
    public cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d getDefaultInstanceForType() {
      return cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d.getDefaultInstance();
    }

    @java.lang.Override
    public cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d build() {
      cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d buildPartial() {
      cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d result = new cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d(this);
      result.x_ = x_;
      result.y_ = y_;
      result.z_ = z_;
      result.u_ = u_;
      result.v_ = v_;
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
      if (other instanceof cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d) {
        return mergeFrom((cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d other) {
      if (other == cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d.getDefaultInstance()) return this;
      if (other.getX() != 0D) {
        setX(other.getX());
      }
      if (other.getY() != 0D) {
        setY(other.getY());
      }
      if (other.getZ() != 0D) {
        setZ(other.getZ());
      }
      if (other.getU() != 0D) {
        setU(other.getU());
      }
      if (other.getV() != 0D) {
        setV(other.getV());
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
      cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private double x_ ;
    /**
     * <code>double x = 1;</code>
     * @return The x.
     */
    public double getX() {
      return x_;
    }
    /**
     * <code>double x = 1;</code>
     * @param value The x to set.
     * @return This builder for chaining.
     */
    public Builder setX(double value) {
      
      x_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>double x = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearX() {
      
      x_ = 0D;
      onChanged();
      return this;
    }

    private double y_ ;
    /**
     * <code>double y = 2;</code>
     * @return The y.
     */
    public double getY() {
      return y_;
    }
    /**
     * <code>double y = 2;</code>
     * @param value The y to set.
     * @return This builder for chaining.
     */
    public Builder setY(double value) {
      
      y_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>double y = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearY() {
      
      y_ = 0D;
      onChanged();
      return this;
    }

    private double z_ ;
    /**
     * <code>double z = 3;</code>
     * @return The z.
     */
    public double getZ() {
      return z_;
    }
    /**
     * <code>double z = 3;</code>
     * @param value The z to set.
     * @return This builder for chaining.
     */
    public Builder setZ(double value) {
      
      z_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>double z = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearZ() {
      
      z_ = 0D;
      onChanged();
      return this;
    }

    private double u_ ;
    /**
     * <code>double u = 4;</code>
     * @return The u.
     */
    public double getU() {
      return u_;
    }
    /**
     * <code>double u = 4;</code>
     * @param value The u to set.
     * @return This builder for chaining.
     */
    public Builder setU(double value) {
      
      u_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>double u = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearU() {
      
      u_ = 0D;
      onChanged();
      return this;
    }

    private double v_ ;
    /**
     * <code>double v = 5;</code>
     * @return The v.
     */
    public double getV() {
      return v_;
    }
    /**
     * <code>double v = 5;</code>
     * @param value The v to set.
     * @return This builder for chaining.
     */
    public Builder setV(double value) {
      
      v_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>double v = 5;</code>
     * @return This builder for chaining.
     */
    public Builder clearV() {
      
      v_ = 0D;
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


    // @@protoc_insertion_point(builder_scope:cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d)
  }

  // @@protoc_insertion_point(class_scope:cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d)
  private static final cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d();
  }

  public static cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<TexturedVertex3d2d>
      PARSER = new com.google.protobuf.AbstractParser<TexturedVertex3d2d>() {
    @java.lang.Override
    public TexturedVertex3d2d parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new TexturedVertex3d2d(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<TexturedVertex3d2d> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<TexturedVertex3d2d> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public cn.edu.cug.cs.gtl.protos.TexturedVertex3d2d getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

