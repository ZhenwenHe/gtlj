// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: cn/edu/cug/cs/gtl/protos/geometry.proto

package cn.edu.cug.cs.gtl.protos;

/**
 * Protobuf type {@code cn.edu.cug.cs.gtl.protos.Rectangle2D}
 */
public  final class Rectangle2D extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:cn.edu.cug.cs.gtl.protos.Rectangle2D)
    Rectangle2DOrBuilder {
private static final long serialVersionUID = 0L;
  // Use Rectangle2D.newBuilder() to construct.
  private Rectangle2D(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Rectangle2D() {
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new Rectangle2D();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private Rectangle2D(
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

            left_ = input.readDouble();
            break;
          }
          case 17: {

            right_ = input.readDouble();
            break;
          }
          case 25: {

            bottom_ = input.readDouble();
            break;
          }
          case 33: {

            top_ = input.readDouble();
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
    return cn.edu.cug.cs.gtl.protos.Geometries.internal_static_cn_edu_cug_cs_gtl_protos_Rectangle2D_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return cn.edu.cug.cs.gtl.protos.Geometries.internal_static_cn_edu_cug_cs_gtl_protos_Rectangle2D_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            cn.edu.cug.cs.gtl.protos.Rectangle2D.class, cn.edu.cug.cs.gtl.protos.Rectangle2D.Builder.class);
  }

  public static final int LEFT_FIELD_NUMBER = 1;
  private double left_;
  /**
   * <code>double left = 1;</code>
   * @return The left.
   */
  public double getLeft() {
    return left_;
  }

  public static final int RIGHT_FIELD_NUMBER = 2;
  private double right_;
  /**
   * <code>double right = 2;</code>
   * @return The right.
   */
  public double getRight() {
    return right_;
  }

  public static final int BOTTOM_FIELD_NUMBER = 3;
  private double bottom_;
  /**
   * <code>double bottom = 3;</code>
   * @return The bottom.
   */
  public double getBottom() {
    return bottom_;
  }

  public static final int TOP_FIELD_NUMBER = 4;
  private double top_;
  /**
   * <code>double top = 4;</code>
   * @return The top.
   */
  public double getTop() {
    return top_;
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
    if (left_ != 0D) {
      output.writeDouble(1, left_);
    }
    if (right_ != 0D) {
      output.writeDouble(2, right_);
    }
    if (bottom_ != 0D) {
      output.writeDouble(3, bottom_);
    }
    if (top_ != 0D) {
      output.writeDouble(4, top_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (left_ != 0D) {
      size += com.google.protobuf.CodedOutputStream
        .computeDoubleSize(1, left_);
    }
    if (right_ != 0D) {
      size += com.google.protobuf.CodedOutputStream
        .computeDoubleSize(2, right_);
    }
    if (bottom_ != 0D) {
      size += com.google.protobuf.CodedOutputStream
        .computeDoubleSize(3, bottom_);
    }
    if (top_ != 0D) {
      size += com.google.protobuf.CodedOutputStream
        .computeDoubleSize(4, top_);
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
    if (!(obj instanceof cn.edu.cug.cs.gtl.protos.Rectangle2D)) {
      return super.equals(obj);
    }
    cn.edu.cug.cs.gtl.protos.Rectangle2D other = (cn.edu.cug.cs.gtl.protos.Rectangle2D) obj;

    if (java.lang.Double.doubleToLongBits(getLeft())
        != java.lang.Double.doubleToLongBits(
            other.getLeft())) return false;
    if (java.lang.Double.doubleToLongBits(getRight())
        != java.lang.Double.doubleToLongBits(
            other.getRight())) return false;
    if (java.lang.Double.doubleToLongBits(getBottom())
        != java.lang.Double.doubleToLongBits(
            other.getBottom())) return false;
    if (java.lang.Double.doubleToLongBits(getTop())
        != java.lang.Double.doubleToLongBits(
            other.getTop())) return false;
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
    hash = (37 * hash) + LEFT_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        java.lang.Double.doubleToLongBits(getLeft()));
    hash = (37 * hash) + RIGHT_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        java.lang.Double.doubleToLongBits(getRight()));
    hash = (37 * hash) + BOTTOM_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        java.lang.Double.doubleToLongBits(getBottom()));
    hash = (37 * hash) + TOP_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        java.lang.Double.doubleToLongBits(getTop()));
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static cn.edu.cug.cs.gtl.protos.Rectangle2D parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static cn.edu.cug.cs.gtl.protos.Rectangle2D parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Rectangle2D parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static cn.edu.cug.cs.gtl.protos.Rectangle2D parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Rectangle2D parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static cn.edu.cug.cs.gtl.protos.Rectangle2D parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Rectangle2D parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static cn.edu.cug.cs.gtl.protos.Rectangle2D parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Rectangle2D parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static cn.edu.cug.cs.gtl.protos.Rectangle2D parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Rectangle2D parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static cn.edu.cug.cs.gtl.protos.Rectangle2D parseFrom(
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
  public static Builder newBuilder(cn.edu.cug.cs.gtl.protos.Rectangle2D prototype) {
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
   * Protobuf type {@code cn.edu.cug.cs.gtl.protos.Rectangle2D}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:cn.edu.cug.cs.gtl.protos.Rectangle2D)
      cn.edu.cug.cs.gtl.protos.Rectangle2DOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return cn.edu.cug.cs.gtl.protos.Geometries.internal_static_cn_edu_cug_cs_gtl_protos_Rectangle2D_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return cn.edu.cug.cs.gtl.protos.Geometries.internal_static_cn_edu_cug_cs_gtl_protos_Rectangle2D_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              cn.edu.cug.cs.gtl.protos.Rectangle2D.class, cn.edu.cug.cs.gtl.protos.Rectangle2D.Builder.class);
    }

    // Construct using cn.edu.cug.cs.gtl.protos.Rectangle2D.newBuilder()
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
      left_ = 0D;

      right_ = 0D;

      bottom_ = 0D;

      top_ = 0D;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return cn.edu.cug.cs.gtl.protos.Geometries.internal_static_cn_edu_cug_cs_gtl_protos_Rectangle2D_descriptor;
    }

    @java.lang.Override
    public cn.edu.cug.cs.gtl.protos.Rectangle2D getDefaultInstanceForType() {
      return cn.edu.cug.cs.gtl.protos.Rectangle2D.getDefaultInstance();
    }

    @java.lang.Override
    public cn.edu.cug.cs.gtl.protos.Rectangle2D build() {
      cn.edu.cug.cs.gtl.protos.Rectangle2D result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public cn.edu.cug.cs.gtl.protos.Rectangle2D buildPartial() {
      cn.edu.cug.cs.gtl.protos.Rectangle2D result = new cn.edu.cug.cs.gtl.protos.Rectangle2D(this);
      result.left_ = left_;
      result.right_ = right_;
      result.bottom_ = bottom_;
      result.top_ = top_;
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
      if (other instanceof cn.edu.cug.cs.gtl.protos.Rectangle2D) {
        return mergeFrom((cn.edu.cug.cs.gtl.protos.Rectangle2D)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(cn.edu.cug.cs.gtl.protos.Rectangle2D other) {
      if (other == cn.edu.cug.cs.gtl.protos.Rectangle2D.getDefaultInstance()) return this;
      if (other.getLeft() != 0D) {
        setLeft(other.getLeft());
      }
      if (other.getRight() != 0D) {
        setRight(other.getRight());
      }
      if (other.getBottom() != 0D) {
        setBottom(other.getBottom());
      }
      if (other.getTop() != 0D) {
        setTop(other.getTop());
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
      cn.edu.cug.cs.gtl.protos.Rectangle2D parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (cn.edu.cug.cs.gtl.protos.Rectangle2D) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private double left_ ;
    /**
     * <code>double left = 1;</code>
     * @return The left.
     */
    public double getLeft() {
      return left_;
    }
    /**
     * <code>double left = 1;</code>
     * @param value The left to set.
     * @return This builder for chaining.
     */
    public Builder setLeft(double value) {
      
      left_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>double left = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearLeft() {
      
      left_ = 0D;
      onChanged();
      return this;
    }

    private double right_ ;
    /**
     * <code>double right = 2;</code>
     * @return The right.
     */
    public double getRight() {
      return right_;
    }
    /**
     * <code>double right = 2;</code>
     * @param value The right to set.
     * @return This builder for chaining.
     */
    public Builder setRight(double value) {
      
      right_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>double right = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearRight() {
      
      right_ = 0D;
      onChanged();
      return this;
    }

    private double bottom_ ;
    /**
     * <code>double bottom = 3;</code>
     * @return The bottom.
     */
    public double getBottom() {
      return bottom_;
    }
    /**
     * <code>double bottom = 3;</code>
     * @param value The bottom to set.
     * @return This builder for chaining.
     */
    public Builder setBottom(double value) {
      
      bottom_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>double bottom = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearBottom() {
      
      bottom_ = 0D;
      onChanged();
      return this;
    }

    private double top_ ;
    /**
     * <code>double top = 4;</code>
     * @return The top.
     */
    public double getTop() {
      return top_;
    }
    /**
     * <code>double top = 4;</code>
     * @param value The top to set.
     * @return This builder for chaining.
     */
    public Builder setTop(double value) {
      
      top_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>double top = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearTop() {
      
      top_ = 0D;
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


    // @@protoc_insertion_point(builder_scope:cn.edu.cug.cs.gtl.protos.Rectangle2D)
  }

  // @@protoc_insertion_point(class_scope:cn.edu.cug.cs.gtl.protos.Rectangle2D)
  private static final cn.edu.cug.cs.gtl.protos.Rectangle2D DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new cn.edu.cug.cs.gtl.protos.Rectangle2D();
  }

  public static cn.edu.cug.cs.gtl.protos.Rectangle2D getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Rectangle2D>
      PARSER = new com.google.protobuf.AbstractParser<Rectangle2D>() {
    @java.lang.Override
    public Rectangle2D parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new Rectangle2D(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<Rectangle2D> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Rectangle2D> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public cn.edu.cug.cs.gtl.protos.Rectangle2D getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

