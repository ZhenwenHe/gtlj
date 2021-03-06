// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: cn/edu/cug/cs/gtl/protos/buffer.proto

package cn.edu.cug.cs.gtl.protos;

/**
 * Protobuf type {@code cn.edu.cug.cs.gtl.protos.Float64Buffer}
 */
public  final class Float64Buffer extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:cn.edu.cug.cs.gtl.protos.Float64Buffer)
    Float64BufferOrBuilder {
private static final long serialVersionUID = 0L;
  // Use Float64Buffer.newBuilder() to construct.
  private Float64Buffer(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Float64Buffer() {
    element_ = emptyDoubleList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new Float64Buffer();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private Float64Buffer(
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
          case 9: {
            if (!((mutable_bitField0_ & 0x00000001) != 0)) {
              element_ = newDoubleList();
              mutable_bitField0_ |= 0x00000001;
            }
            element_.addDouble(input.readDouble());
            break;
          }
          case 10: {
            int length = input.readRawVarint32();
            int limit = input.pushLimit(length);
            if (!((mutable_bitField0_ & 0x00000001) != 0) && input.getBytesUntilLimit() > 0) {
              element_ = newDoubleList();
              mutable_bitField0_ |= 0x00000001;
            }
            while (input.getBytesUntilLimit() > 0) {
              element_.addDouble(input.readDouble());
            }
            input.popLimit(limit);
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
        element_.makeImmutable(); // C
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return cn.edu.cug.cs.gtl.protos.Buffers.internal_static_cn_edu_cug_cs_gtl_protos_Float64Buffer_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return cn.edu.cug.cs.gtl.protos.Buffers.internal_static_cn_edu_cug_cs_gtl_protos_Float64Buffer_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            cn.edu.cug.cs.gtl.protos.Float64Buffer.class, cn.edu.cug.cs.gtl.protos.Float64Buffer.Builder.class);
  }

  public static final int ELEMENT_FIELD_NUMBER = 1;
  private com.google.protobuf.Internal.DoubleList element_;
  /**
   * <code>repeated double element = 1;</code>
   * @return A list containing the element.
   */
  public java.util.List<java.lang.Double>
      getElementList() {
    return element_;
  }
  /**
   * <code>repeated double element = 1;</code>
   * @return The count of element.
   */
  public int getElementCount() {
    return element_.size();
  }
  /**
   * <code>repeated double element = 1;</code>
   * @param index The index of the element to return.
   * @return The element at the given index.
   */
  public double getElement(int index) {
    return element_.getDouble(index);
  }
  private int elementMemoizedSerializedSize = -1;

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
    getSerializedSize();
    if (getElementList().size() > 0) {
      output.writeUInt32NoTag(10);
      output.writeUInt32NoTag(elementMemoizedSerializedSize);
    }
    for (int i = 0; i < element_.size(); i++) {
      output.writeDoubleNoTag(element_.getDouble(i));
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    {
      int dataSize = 0;
      dataSize = 8 * getElementList().size();
      size += dataSize;
      if (!getElementList().isEmpty()) {
        size += 1;
        size += com.google.protobuf.CodedOutputStream
            .computeInt32SizeNoTag(dataSize);
      }
      elementMemoizedSerializedSize = dataSize;
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
    if (!(obj instanceof cn.edu.cug.cs.gtl.protos.Float64Buffer)) {
      return super.equals(obj);
    }
    cn.edu.cug.cs.gtl.protos.Float64Buffer other = (cn.edu.cug.cs.gtl.protos.Float64Buffer) obj;

    if (!getElementList()
        .equals(other.getElementList())) return false;
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
    if (getElementCount() > 0) {
      hash = (37 * hash) + ELEMENT_FIELD_NUMBER;
      hash = (53 * hash) + getElementList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static cn.edu.cug.cs.gtl.protos.Float64Buffer parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static cn.edu.cug.cs.gtl.protos.Float64Buffer parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Float64Buffer parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static cn.edu.cug.cs.gtl.protos.Float64Buffer parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Float64Buffer parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static cn.edu.cug.cs.gtl.protos.Float64Buffer parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Float64Buffer parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static cn.edu.cug.cs.gtl.protos.Float64Buffer parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Float64Buffer parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static cn.edu.cug.cs.gtl.protos.Float64Buffer parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Float64Buffer parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static cn.edu.cug.cs.gtl.protos.Float64Buffer parseFrom(
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
  public static Builder newBuilder(cn.edu.cug.cs.gtl.protos.Float64Buffer prototype) {
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
   * Protobuf type {@code cn.edu.cug.cs.gtl.protos.Float64Buffer}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:cn.edu.cug.cs.gtl.protos.Float64Buffer)
      cn.edu.cug.cs.gtl.protos.Float64BufferOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return cn.edu.cug.cs.gtl.protos.Buffers.internal_static_cn_edu_cug_cs_gtl_protos_Float64Buffer_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return cn.edu.cug.cs.gtl.protos.Buffers.internal_static_cn_edu_cug_cs_gtl_protos_Float64Buffer_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              cn.edu.cug.cs.gtl.protos.Float64Buffer.class, cn.edu.cug.cs.gtl.protos.Float64Buffer.Builder.class);
    }

    // Construct using cn.edu.cug.cs.gtl.protos.Float64Buffer.newBuilder()
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
      element_ = emptyDoubleList();
      bitField0_ = (bitField0_ & ~0x00000001);
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return cn.edu.cug.cs.gtl.protos.Buffers.internal_static_cn_edu_cug_cs_gtl_protos_Float64Buffer_descriptor;
    }

    @java.lang.Override
    public cn.edu.cug.cs.gtl.protos.Float64Buffer getDefaultInstanceForType() {
      return cn.edu.cug.cs.gtl.protos.Float64Buffer.getDefaultInstance();
    }

    @java.lang.Override
    public cn.edu.cug.cs.gtl.protos.Float64Buffer build() {
      cn.edu.cug.cs.gtl.protos.Float64Buffer result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public cn.edu.cug.cs.gtl.protos.Float64Buffer buildPartial() {
      cn.edu.cug.cs.gtl.protos.Float64Buffer result = new cn.edu.cug.cs.gtl.protos.Float64Buffer(this);
      int from_bitField0_ = bitField0_;
      if (((bitField0_ & 0x00000001) != 0)) {
        element_.makeImmutable();
        bitField0_ = (bitField0_ & ~0x00000001);
      }
      result.element_ = element_;
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
      if (other instanceof cn.edu.cug.cs.gtl.protos.Float64Buffer) {
        return mergeFrom((cn.edu.cug.cs.gtl.protos.Float64Buffer)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(cn.edu.cug.cs.gtl.protos.Float64Buffer other) {
      if (other == cn.edu.cug.cs.gtl.protos.Float64Buffer.getDefaultInstance()) return this;
      if (!other.element_.isEmpty()) {
        if (element_.isEmpty()) {
          element_ = other.element_;
          bitField0_ = (bitField0_ & ~0x00000001);
        } else {
          ensureElementIsMutable();
          element_.addAll(other.element_);
        }
        onChanged();
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
      cn.edu.cug.cs.gtl.protos.Float64Buffer parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (cn.edu.cug.cs.gtl.protos.Float64Buffer) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private com.google.protobuf.Internal.DoubleList element_ = emptyDoubleList();
    private void ensureElementIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        element_ = mutableCopy(element_);
        bitField0_ |= 0x00000001;
       }
    }
    /**
     * <code>repeated double element = 1;</code>
     * @return A list containing the element.
     */
    public java.util.List<java.lang.Double>
        getElementList() {
      return ((bitField0_ & 0x00000001) != 0) ?
               java.util.Collections.unmodifiableList(element_) : element_;
    }
    /**
     * <code>repeated double element = 1;</code>
     * @return The count of element.
     */
    public int getElementCount() {
      return element_.size();
    }
    /**
     * <code>repeated double element = 1;</code>
     * @param index The index of the element to return.
     * @return The element at the given index.
     */
    public double getElement(int index) {
      return element_.getDouble(index);
    }
    /**
     * <code>repeated double element = 1;</code>
     * @param index The index to set the value at.
     * @param value The element to set.
     * @return This builder for chaining.
     */
    public Builder setElement(
        int index, double value) {
      ensureElementIsMutable();
      element_.setDouble(index, value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated double element = 1;</code>
     * @param value The element to add.
     * @return This builder for chaining.
     */
    public Builder addElement(double value) {
      ensureElementIsMutable();
      element_.addDouble(value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated double element = 1;</code>
     * @param values The element to add.
     * @return This builder for chaining.
     */
    public Builder addAllElement(
        java.lang.Iterable<? extends java.lang.Double> values) {
      ensureElementIsMutable();
      com.google.protobuf.AbstractMessageLite.Builder.addAll(
          values, element_);
      onChanged();
      return this;
    }
    /**
     * <code>repeated double element = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearElement() {
      element_ = emptyDoubleList();
      bitField0_ = (bitField0_ & ~0x00000001);
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


    // @@protoc_insertion_point(builder_scope:cn.edu.cug.cs.gtl.protos.Float64Buffer)
  }

  // @@protoc_insertion_point(class_scope:cn.edu.cug.cs.gtl.protos.Float64Buffer)
  private static final cn.edu.cug.cs.gtl.protos.Float64Buffer DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new cn.edu.cug.cs.gtl.protos.Float64Buffer();
  }

  public static cn.edu.cug.cs.gtl.protos.Float64Buffer getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Float64Buffer>
      PARSER = new com.google.protobuf.AbstractParser<Float64Buffer>() {
    @java.lang.Override
    public Float64Buffer parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new Float64Buffer(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<Float64Buffer> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Float64Buffer> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public cn.edu.cug.cs.gtl.protos.Float64Buffer getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

