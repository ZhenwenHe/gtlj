// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: cn/edu/cug/cs/gtl/protos/geometry.proto

package cn.edu.cug.cs.gtl.protos;

/**
 * Protobuf type {@code cn.edu.cug.cs.gtl.protos.Polygon2D}
 */
public  final class Polygon2D extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:cn.edu.cug.cs.gtl.protos.Polygon2D)
    Polygon2DOrBuilder {
private static final long serialVersionUID = 0L;
  // Use Polygon2D.newBuilder() to construct.
  private Polygon2D(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Polygon2D() {
    ring_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new Polygon2D();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private Polygon2D(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
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
          case 10: {
            cn.edu.cug.cs.gtl.protos.LinearRing2D.Builder subBuilder = null;
            if (shell_ != null) {
              subBuilder = shell_.toBuilder();
            }
            shell_ = input.readMessage(cn.edu.cug.cs.gtl.protos.LinearRing2D.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(shell_);
              shell_ = subBuilder.buildPartial();
            }

            break;
          }
          case 18: {
            if (!((mutable_bitField0_ & 0x00000001) != 0)) {
              ring_ = new java.util.ArrayList<cn.edu.cug.cs.gtl.protos.LinearRing2D>();
              mutable_bitField0_ |= 0x00000001;
            }
            ring_.add(
                input.readMessage(cn.edu.cug.cs.gtl.protos.LinearRing2D.parser(), extensionRegistry));
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
      if (((mutable_bitField0_ & 0x00000001) != 0)) {
        ring_ = java.util.Collections.unmodifiableList(ring_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return cn.edu.cug.cs.gtl.protos.Geometries.internal_static_cn_edu_cug_cs_gtl_protos_Polygon2D_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return cn.edu.cug.cs.gtl.protos.Geometries.internal_static_cn_edu_cug_cs_gtl_protos_Polygon2D_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            cn.edu.cug.cs.gtl.protos.Polygon2D.class, cn.edu.cug.cs.gtl.protos.Polygon2D.Builder.class);
  }

  public static final int SHELL_FIELD_NUMBER = 1;
  private cn.edu.cug.cs.gtl.protos.LinearRing2D shell_;
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.LinearRing2D shell = 1;</code>
   * @return Whether the shell field is set.
   */
  public boolean hasShell() {
    return shell_ != null;
  }
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.LinearRing2D shell = 1;</code>
   * @return The shell.
   */
  public cn.edu.cug.cs.gtl.protos.LinearRing2D getShell() {
    return shell_ == null ? cn.edu.cug.cs.gtl.protos.LinearRing2D.getDefaultInstance() : shell_;
  }
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.LinearRing2D shell = 1;</code>
   */
  public cn.edu.cug.cs.gtl.protos.LinearRing2DOrBuilder getShellOrBuilder() {
    return getShell();
  }

  public static final int RING_FIELD_NUMBER = 2;
  private java.util.List<cn.edu.cug.cs.gtl.protos.LinearRing2D> ring_;
  /**
   * <code>repeated .cn.edu.cug.cs.gtl.protos.LinearRing2D ring = 2;</code>
   */
  public java.util.List<cn.edu.cug.cs.gtl.protos.LinearRing2D> getRingList() {
    return ring_;
  }
  /**
   * <code>repeated .cn.edu.cug.cs.gtl.protos.LinearRing2D ring = 2;</code>
   */
  public java.util.List<? extends cn.edu.cug.cs.gtl.protos.LinearRing2DOrBuilder> 
      getRingOrBuilderList() {
    return ring_;
  }
  /**
   * <code>repeated .cn.edu.cug.cs.gtl.protos.LinearRing2D ring = 2;</code>
   */
  public int getRingCount() {
    return ring_.size();
  }
  /**
   * <code>repeated .cn.edu.cug.cs.gtl.protos.LinearRing2D ring = 2;</code>
   */
  public cn.edu.cug.cs.gtl.protos.LinearRing2D getRing(int index) {
    return ring_.get(index);
  }
  /**
   * <code>repeated .cn.edu.cug.cs.gtl.protos.LinearRing2D ring = 2;</code>
   */
  public cn.edu.cug.cs.gtl.protos.LinearRing2DOrBuilder getRingOrBuilder(
      int index) {
    return ring_.get(index);
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
    if (shell_ != null) {
      output.writeMessage(1, getShell());
    }
    for (int i = 0; i < ring_.size(); i++) {
      output.writeMessage(2, ring_.get(i));
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (shell_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getShell());
    }
    for (int i = 0; i < ring_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, ring_.get(i));
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
    if (!(obj instanceof cn.edu.cug.cs.gtl.protos.Polygon2D)) {
      return super.equals(obj);
    }
    cn.edu.cug.cs.gtl.protos.Polygon2D other = (cn.edu.cug.cs.gtl.protos.Polygon2D) obj;

    if (hasShell() != other.hasShell()) return false;
    if (hasShell()) {
      if (!getShell()
          .equals(other.getShell())) return false;
    }
    if (!getRingList()
        .equals(other.getRingList())) return false;
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
    if (hasShell()) {
      hash = (37 * hash) + SHELL_FIELD_NUMBER;
      hash = (53 * hash) + getShell().hashCode();
    }
    if (getRingCount() > 0) {
      hash = (37 * hash) + RING_FIELD_NUMBER;
      hash = (53 * hash) + getRingList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static cn.edu.cug.cs.gtl.protos.Polygon2D parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static cn.edu.cug.cs.gtl.protos.Polygon2D parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Polygon2D parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static cn.edu.cug.cs.gtl.protos.Polygon2D parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Polygon2D parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static cn.edu.cug.cs.gtl.protos.Polygon2D parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Polygon2D parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static cn.edu.cug.cs.gtl.protos.Polygon2D parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Polygon2D parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static cn.edu.cug.cs.gtl.protos.Polygon2D parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Polygon2D parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static cn.edu.cug.cs.gtl.protos.Polygon2D parseFrom(
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
  public static Builder newBuilder(cn.edu.cug.cs.gtl.protos.Polygon2D prototype) {
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
   * Protobuf type {@code cn.edu.cug.cs.gtl.protos.Polygon2D}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:cn.edu.cug.cs.gtl.protos.Polygon2D)
      cn.edu.cug.cs.gtl.protos.Polygon2DOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return cn.edu.cug.cs.gtl.protos.Geometries.internal_static_cn_edu_cug_cs_gtl_protos_Polygon2D_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return cn.edu.cug.cs.gtl.protos.Geometries.internal_static_cn_edu_cug_cs_gtl_protos_Polygon2D_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              cn.edu.cug.cs.gtl.protos.Polygon2D.class, cn.edu.cug.cs.gtl.protos.Polygon2D.Builder.class);
    }

    // Construct using cn.edu.cug.cs.gtl.protos.Polygon2D.newBuilder()
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
        getRingFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (shellBuilder_ == null) {
        shell_ = null;
      } else {
        shell_ = null;
        shellBuilder_ = null;
      }
      if (ringBuilder_ == null) {
        ring_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        ringBuilder_.clear();
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return cn.edu.cug.cs.gtl.protos.Geometries.internal_static_cn_edu_cug_cs_gtl_protos_Polygon2D_descriptor;
    }

    @java.lang.Override
    public cn.edu.cug.cs.gtl.protos.Polygon2D getDefaultInstanceForType() {
      return cn.edu.cug.cs.gtl.protos.Polygon2D.getDefaultInstance();
    }

    @java.lang.Override
    public cn.edu.cug.cs.gtl.protos.Polygon2D build() {
      cn.edu.cug.cs.gtl.protos.Polygon2D result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public cn.edu.cug.cs.gtl.protos.Polygon2D buildPartial() {
      cn.edu.cug.cs.gtl.protos.Polygon2D result = new cn.edu.cug.cs.gtl.protos.Polygon2D(this);
      int from_bitField0_ = bitField0_;
      if (shellBuilder_ == null) {
        result.shell_ = shell_;
      } else {
        result.shell_ = shellBuilder_.build();
      }
      if (ringBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          ring_ = java.util.Collections.unmodifiableList(ring_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.ring_ = ring_;
      } else {
        result.ring_ = ringBuilder_.build();
      }
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
      if (other instanceof cn.edu.cug.cs.gtl.protos.Polygon2D) {
        return mergeFrom((cn.edu.cug.cs.gtl.protos.Polygon2D)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(cn.edu.cug.cs.gtl.protos.Polygon2D other) {
      if (other == cn.edu.cug.cs.gtl.protos.Polygon2D.getDefaultInstance()) return this;
      if (other.hasShell()) {
        mergeShell(other.getShell());
      }
      if (ringBuilder_ == null) {
        if (!other.ring_.isEmpty()) {
          if (ring_.isEmpty()) {
            ring_ = other.ring_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureRingIsMutable();
            ring_.addAll(other.ring_);
          }
          onChanged();
        }
      } else {
        if (!other.ring_.isEmpty()) {
          if (ringBuilder_.isEmpty()) {
            ringBuilder_.dispose();
            ringBuilder_ = null;
            ring_ = other.ring_;
            bitField0_ = (bitField0_ & ~0x00000001);
            ringBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getRingFieldBuilder() : null;
          } else {
            ringBuilder_.addAllMessages(other.ring_);
          }
        }
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
      cn.edu.cug.cs.gtl.protos.Polygon2D parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (cn.edu.cug.cs.gtl.protos.Polygon2D) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private cn.edu.cug.cs.gtl.protos.LinearRing2D shell_;
    private com.google.protobuf.SingleFieldBuilderV3<
        cn.edu.cug.cs.gtl.protos.LinearRing2D, cn.edu.cug.cs.gtl.protos.LinearRing2D.Builder, cn.edu.cug.cs.gtl.protos.LinearRing2DOrBuilder> shellBuilder_;
    /**
     * <code>.cn.edu.cug.cs.gtl.protos.LinearRing2D shell = 1;</code>
     * @return Whether the shell field is set.
     */
    public boolean hasShell() {
      return shellBuilder_ != null || shell_ != null;
    }
    /**
     * <code>.cn.edu.cug.cs.gtl.protos.LinearRing2D shell = 1;</code>
     * @return The shell.
     */
    public cn.edu.cug.cs.gtl.protos.LinearRing2D getShell() {
      if (shellBuilder_ == null) {
        return shell_ == null ? cn.edu.cug.cs.gtl.protos.LinearRing2D.getDefaultInstance() : shell_;
      } else {
        return shellBuilder_.getMessage();
      }
    }
    /**
     * <code>.cn.edu.cug.cs.gtl.protos.LinearRing2D shell = 1;</code>
     */
    public Builder setShell(cn.edu.cug.cs.gtl.protos.LinearRing2D value) {
      if (shellBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        shell_ = value;
        onChanged();
      } else {
        shellBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.cn.edu.cug.cs.gtl.protos.LinearRing2D shell = 1;</code>
     */
    public Builder setShell(
        cn.edu.cug.cs.gtl.protos.LinearRing2D.Builder builderForValue) {
      if (shellBuilder_ == null) {
        shell_ = builderForValue.build();
        onChanged();
      } else {
        shellBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.cn.edu.cug.cs.gtl.protos.LinearRing2D shell = 1;</code>
     */
    public Builder mergeShell(cn.edu.cug.cs.gtl.protos.LinearRing2D value) {
      if (shellBuilder_ == null) {
        if (shell_ != null) {
          shell_ =
            cn.edu.cug.cs.gtl.protos.LinearRing2D.newBuilder(shell_).mergeFrom(value).buildPartial();
        } else {
          shell_ = value;
        }
        onChanged();
      } else {
        shellBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.cn.edu.cug.cs.gtl.protos.LinearRing2D shell = 1;</code>
     */
    public Builder clearShell() {
      if (shellBuilder_ == null) {
        shell_ = null;
        onChanged();
      } else {
        shell_ = null;
        shellBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.cn.edu.cug.cs.gtl.protos.LinearRing2D shell = 1;</code>
     */
    public cn.edu.cug.cs.gtl.protos.LinearRing2D.Builder getShellBuilder() {
      
      onChanged();
      return getShellFieldBuilder().getBuilder();
    }
    /**
     * <code>.cn.edu.cug.cs.gtl.protos.LinearRing2D shell = 1;</code>
     */
    public cn.edu.cug.cs.gtl.protos.LinearRing2DOrBuilder getShellOrBuilder() {
      if (shellBuilder_ != null) {
        return shellBuilder_.getMessageOrBuilder();
      } else {
        return shell_ == null ?
            cn.edu.cug.cs.gtl.protos.LinearRing2D.getDefaultInstance() : shell_;
      }
    }
    /**
     * <code>.cn.edu.cug.cs.gtl.protos.LinearRing2D shell = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        cn.edu.cug.cs.gtl.protos.LinearRing2D, cn.edu.cug.cs.gtl.protos.LinearRing2D.Builder, cn.edu.cug.cs.gtl.protos.LinearRing2DOrBuilder> 
        getShellFieldBuilder() {
      if (shellBuilder_ == null) {
        shellBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            cn.edu.cug.cs.gtl.protos.LinearRing2D, cn.edu.cug.cs.gtl.protos.LinearRing2D.Builder, cn.edu.cug.cs.gtl.protos.LinearRing2DOrBuilder>(
                getShell(),
                getParentForChildren(),
                isClean());
        shell_ = null;
      }
      return shellBuilder_;
    }

    private java.util.List<cn.edu.cug.cs.gtl.protos.LinearRing2D> ring_ =
      java.util.Collections.emptyList();
    private void ensureRingIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        ring_ = new java.util.ArrayList<cn.edu.cug.cs.gtl.protos.LinearRing2D>(ring_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        cn.edu.cug.cs.gtl.protos.LinearRing2D, cn.edu.cug.cs.gtl.protos.LinearRing2D.Builder, cn.edu.cug.cs.gtl.protos.LinearRing2DOrBuilder> ringBuilder_;

    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.LinearRing2D ring = 2;</code>
     */
    public java.util.List<cn.edu.cug.cs.gtl.protos.LinearRing2D> getRingList() {
      if (ringBuilder_ == null) {
        return java.util.Collections.unmodifiableList(ring_);
      } else {
        return ringBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.LinearRing2D ring = 2;</code>
     */
    public int getRingCount() {
      if (ringBuilder_ == null) {
        return ring_.size();
      } else {
        return ringBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.LinearRing2D ring = 2;</code>
     */
    public cn.edu.cug.cs.gtl.protos.LinearRing2D getRing(int index) {
      if (ringBuilder_ == null) {
        return ring_.get(index);
      } else {
        return ringBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.LinearRing2D ring = 2;</code>
     */
    public Builder setRing(
        int index, cn.edu.cug.cs.gtl.protos.LinearRing2D value) {
      if (ringBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureRingIsMutable();
        ring_.set(index, value);
        onChanged();
      } else {
        ringBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.LinearRing2D ring = 2;</code>
     */
    public Builder setRing(
        int index, cn.edu.cug.cs.gtl.protos.LinearRing2D.Builder builderForValue) {
      if (ringBuilder_ == null) {
        ensureRingIsMutable();
        ring_.set(index, builderForValue.build());
        onChanged();
      } else {
        ringBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.LinearRing2D ring = 2;</code>
     */
    public Builder addRing(cn.edu.cug.cs.gtl.protos.LinearRing2D value) {
      if (ringBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureRingIsMutable();
        ring_.add(value);
        onChanged();
      } else {
        ringBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.LinearRing2D ring = 2;</code>
     */
    public Builder addRing(
        int index, cn.edu.cug.cs.gtl.protos.LinearRing2D value) {
      if (ringBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureRingIsMutable();
        ring_.add(index, value);
        onChanged();
      } else {
        ringBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.LinearRing2D ring = 2;</code>
     */
    public Builder addRing(
        cn.edu.cug.cs.gtl.protos.LinearRing2D.Builder builderForValue) {
      if (ringBuilder_ == null) {
        ensureRingIsMutable();
        ring_.add(builderForValue.build());
        onChanged();
      } else {
        ringBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.LinearRing2D ring = 2;</code>
     */
    public Builder addRing(
        int index, cn.edu.cug.cs.gtl.protos.LinearRing2D.Builder builderForValue) {
      if (ringBuilder_ == null) {
        ensureRingIsMutable();
        ring_.add(index, builderForValue.build());
        onChanged();
      } else {
        ringBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.LinearRing2D ring = 2;</code>
     */
    public Builder addAllRing(
        java.lang.Iterable<? extends cn.edu.cug.cs.gtl.protos.LinearRing2D> values) {
      if (ringBuilder_ == null) {
        ensureRingIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, ring_);
        onChanged();
      } else {
        ringBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.LinearRing2D ring = 2;</code>
     */
    public Builder clearRing() {
      if (ringBuilder_ == null) {
        ring_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        ringBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.LinearRing2D ring = 2;</code>
     */
    public Builder removeRing(int index) {
      if (ringBuilder_ == null) {
        ensureRingIsMutable();
        ring_.remove(index);
        onChanged();
      } else {
        ringBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.LinearRing2D ring = 2;</code>
     */
    public cn.edu.cug.cs.gtl.protos.LinearRing2D.Builder getRingBuilder(
        int index) {
      return getRingFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.LinearRing2D ring = 2;</code>
     */
    public cn.edu.cug.cs.gtl.protos.LinearRing2DOrBuilder getRingOrBuilder(
        int index) {
      if (ringBuilder_ == null) {
        return ring_.get(index);  } else {
        return ringBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.LinearRing2D ring = 2;</code>
     */
    public java.util.List<? extends cn.edu.cug.cs.gtl.protos.LinearRing2DOrBuilder> 
         getRingOrBuilderList() {
      if (ringBuilder_ != null) {
        return ringBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(ring_);
      }
    }
    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.LinearRing2D ring = 2;</code>
     */
    public cn.edu.cug.cs.gtl.protos.LinearRing2D.Builder addRingBuilder() {
      return getRingFieldBuilder().addBuilder(
          cn.edu.cug.cs.gtl.protos.LinearRing2D.getDefaultInstance());
    }
    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.LinearRing2D ring = 2;</code>
     */
    public cn.edu.cug.cs.gtl.protos.LinearRing2D.Builder addRingBuilder(
        int index) {
      return getRingFieldBuilder().addBuilder(
          index, cn.edu.cug.cs.gtl.protos.LinearRing2D.getDefaultInstance());
    }
    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.LinearRing2D ring = 2;</code>
     */
    public java.util.List<cn.edu.cug.cs.gtl.protos.LinearRing2D.Builder> 
         getRingBuilderList() {
      return getRingFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        cn.edu.cug.cs.gtl.protos.LinearRing2D, cn.edu.cug.cs.gtl.protos.LinearRing2D.Builder, cn.edu.cug.cs.gtl.protos.LinearRing2DOrBuilder> 
        getRingFieldBuilder() {
      if (ringBuilder_ == null) {
        ringBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            cn.edu.cug.cs.gtl.protos.LinearRing2D, cn.edu.cug.cs.gtl.protos.LinearRing2D.Builder, cn.edu.cug.cs.gtl.protos.LinearRing2DOrBuilder>(
                ring_,
                ((bitField0_ & 0x00000001) != 0),
                getParentForChildren(),
                isClean());
        ring_ = null;
      }
      return ringBuilder_;
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


    // @@protoc_insertion_point(builder_scope:cn.edu.cug.cs.gtl.protos.Polygon2D)
  }

  // @@protoc_insertion_point(class_scope:cn.edu.cug.cs.gtl.protos.Polygon2D)
  private static final cn.edu.cug.cs.gtl.protos.Polygon2D DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new cn.edu.cug.cs.gtl.protos.Polygon2D();
  }

  public static cn.edu.cug.cs.gtl.protos.Polygon2D getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Polygon2D>
      PARSER = new com.google.protobuf.AbstractParser<Polygon2D>() {
    @java.lang.Override
    public Polygon2D parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new Polygon2D(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<Polygon2D> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Polygon2D> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public cn.edu.cug.cs.gtl.protos.Polygon2D getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

