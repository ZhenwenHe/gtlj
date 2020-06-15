// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: cn/edu/cug/cs/gtl/protos/value.proto

package cn.edu.cug.cs.gtl.protos;

/**
 * Protobuf type {@code cn.edu.cug.cs.gtl.protos.Tuple}
 */
public  final class Tuple extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:cn.edu.cug.cs.gtl.protos.Tuple)
    TupleOrBuilder {
private static final long serialVersionUID = 0L;
  // Use Tuple.newBuilder() to construct.
  private Tuple(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Tuple() {
    element_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new Tuple();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private Tuple(
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
            if (!((mutable_bitField0_ & 0x00000001) != 0)) {
              element_ = new java.util.ArrayList<com.google.protobuf.Any>();
              mutable_bitField0_ |= 0x00000001;
            }
            element_.add(
                input.readMessage(com.google.protobuf.Any.parser(), extensionRegistry));
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
        element_ = java.util.Collections.unmodifiableList(element_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return cn.edu.cug.cs.gtl.protos.Values.internal_static_cn_edu_cug_cs_gtl_protos_Tuple_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return cn.edu.cug.cs.gtl.protos.Values.internal_static_cn_edu_cug_cs_gtl_protos_Tuple_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            cn.edu.cug.cs.gtl.protos.Tuple.class, cn.edu.cug.cs.gtl.protos.Tuple.Builder.class);
  }

  public static final int ELEMENT_FIELD_NUMBER = 1;
  private java.util.List<com.google.protobuf.Any> element_;
  /**
   * <code>repeated .google.protobuf.Any element = 1;</code>
   */
  public java.util.List<com.google.protobuf.Any> getElementList() {
    return element_;
  }
  /**
   * <code>repeated .google.protobuf.Any element = 1;</code>
   */
  public java.util.List<? extends com.google.protobuf.AnyOrBuilder> 
      getElementOrBuilderList() {
    return element_;
  }
  /**
   * <code>repeated .google.protobuf.Any element = 1;</code>
   */
  public int getElementCount() {
    return element_.size();
  }
  /**
   * <code>repeated .google.protobuf.Any element = 1;</code>
   */
  public com.google.protobuf.Any getElement(int index) {
    return element_.get(index);
  }
  /**
   * <code>repeated .google.protobuf.Any element = 1;</code>
   */
  public com.google.protobuf.AnyOrBuilder getElementOrBuilder(
      int index) {
    return element_.get(index);
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
    for (int i = 0; i < element_.size(); i++) {
      output.writeMessage(1, element_.get(i));
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < element_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, element_.get(i));
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
    if (!(obj instanceof cn.edu.cug.cs.gtl.protos.Tuple)) {
      return super.equals(obj);
    }
    cn.edu.cug.cs.gtl.protos.Tuple other = (cn.edu.cug.cs.gtl.protos.Tuple) obj;

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

  public static cn.edu.cug.cs.gtl.protos.Tuple parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static cn.edu.cug.cs.gtl.protos.Tuple parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Tuple parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static cn.edu.cug.cs.gtl.protos.Tuple parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Tuple parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static cn.edu.cug.cs.gtl.protos.Tuple parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Tuple parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static cn.edu.cug.cs.gtl.protos.Tuple parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Tuple parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static cn.edu.cug.cs.gtl.protos.Tuple parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Tuple parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static cn.edu.cug.cs.gtl.protos.Tuple parseFrom(
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
  public static Builder newBuilder(cn.edu.cug.cs.gtl.protos.Tuple prototype) {
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
   * Protobuf type {@code cn.edu.cug.cs.gtl.protos.Tuple}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:cn.edu.cug.cs.gtl.protos.Tuple)
      cn.edu.cug.cs.gtl.protos.TupleOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return cn.edu.cug.cs.gtl.protos.Values.internal_static_cn_edu_cug_cs_gtl_protos_Tuple_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return cn.edu.cug.cs.gtl.protos.Values.internal_static_cn_edu_cug_cs_gtl_protos_Tuple_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              cn.edu.cug.cs.gtl.protos.Tuple.class, cn.edu.cug.cs.gtl.protos.Tuple.Builder.class);
    }

    // Construct using cn.edu.cug.cs.gtl.protos.Tuple.newBuilder()
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
        getElementFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (elementBuilder_ == null) {
        element_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        elementBuilder_.clear();
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return cn.edu.cug.cs.gtl.protos.Values.internal_static_cn_edu_cug_cs_gtl_protos_Tuple_descriptor;
    }

    @java.lang.Override
    public cn.edu.cug.cs.gtl.protos.Tuple getDefaultInstanceForType() {
      return cn.edu.cug.cs.gtl.protos.Tuple.getDefaultInstance();
    }

    @java.lang.Override
    public cn.edu.cug.cs.gtl.protos.Tuple build() {
      cn.edu.cug.cs.gtl.protos.Tuple result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public cn.edu.cug.cs.gtl.protos.Tuple buildPartial() {
      cn.edu.cug.cs.gtl.protos.Tuple result = new cn.edu.cug.cs.gtl.protos.Tuple(this);
      int from_bitField0_ = bitField0_;
      if (elementBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          element_ = java.util.Collections.unmodifiableList(element_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.element_ = element_;
      } else {
        result.element_ = elementBuilder_.build();
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
      if (other instanceof cn.edu.cug.cs.gtl.protos.Tuple) {
        return mergeFrom((cn.edu.cug.cs.gtl.protos.Tuple)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(cn.edu.cug.cs.gtl.protos.Tuple other) {
      if (other == cn.edu.cug.cs.gtl.protos.Tuple.getDefaultInstance()) return this;
      if (elementBuilder_ == null) {
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
      } else {
        if (!other.element_.isEmpty()) {
          if (elementBuilder_.isEmpty()) {
            elementBuilder_.dispose();
            elementBuilder_ = null;
            element_ = other.element_;
            bitField0_ = (bitField0_ & ~0x00000001);
            elementBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getElementFieldBuilder() : null;
          } else {
            elementBuilder_.addAllMessages(other.element_);
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
      cn.edu.cug.cs.gtl.protos.Tuple parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (cn.edu.cug.cs.gtl.protos.Tuple) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private java.util.List<com.google.protobuf.Any> element_ =
      java.util.Collections.emptyList();
    private void ensureElementIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        element_ = new java.util.ArrayList<com.google.protobuf.Any>(element_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        com.google.protobuf.Any, com.google.protobuf.Any.Builder, com.google.protobuf.AnyOrBuilder> elementBuilder_;

    /**
     * <code>repeated .google.protobuf.Any element = 1;</code>
     */
    public java.util.List<com.google.protobuf.Any> getElementList() {
      if (elementBuilder_ == null) {
        return java.util.Collections.unmodifiableList(element_);
      } else {
        return elementBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .google.protobuf.Any element = 1;</code>
     */
    public int getElementCount() {
      if (elementBuilder_ == null) {
        return element_.size();
      } else {
        return elementBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .google.protobuf.Any element = 1;</code>
     */
    public com.google.protobuf.Any getElement(int index) {
      if (elementBuilder_ == null) {
        return element_.get(index);
      } else {
        return elementBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .google.protobuf.Any element = 1;</code>
     */
    public Builder setElement(
        int index, com.google.protobuf.Any value) {
      if (elementBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureElementIsMutable();
        element_.set(index, value);
        onChanged();
      } else {
        elementBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .google.protobuf.Any element = 1;</code>
     */
    public Builder setElement(
        int index, com.google.protobuf.Any.Builder builderForValue) {
      if (elementBuilder_ == null) {
        ensureElementIsMutable();
        element_.set(index, builderForValue.build());
        onChanged();
      } else {
        elementBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .google.protobuf.Any element = 1;</code>
     */
    public Builder addElement(com.google.protobuf.Any value) {
      if (elementBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureElementIsMutable();
        element_.add(value);
        onChanged();
      } else {
        elementBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .google.protobuf.Any element = 1;</code>
     */
    public Builder addElement(
        int index, com.google.protobuf.Any value) {
      if (elementBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureElementIsMutable();
        element_.add(index, value);
        onChanged();
      } else {
        elementBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .google.protobuf.Any element = 1;</code>
     */
    public Builder addElement(
        com.google.protobuf.Any.Builder builderForValue) {
      if (elementBuilder_ == null) {
        ensureElementIsMutable();
        element_.add(builderForValue.build());
        onChanged();
      } else {
        elementBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .google.protobuf.Any element = 1;</code>
     */
    public Builder addElement(
        int index, com.google.protobuf.Any.Builder builderForValue) {
      if (elementBuilder_ == null) {
        ensureElementIsMutable();
        element_.add(index, builderForValue.build());
        onChanged();
      } else {
        elementBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .google.protobuf.Any element = 1;</code>
     */
    public Builder addAllElement(
        java.lang.Iterable<? extends com.google.protobuf.Any> values) {
      if (elementBuilder_ == null) {
        ensureElementIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, element_);
        onChanged();
      } else {
        elementBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .google.protobuf.Any element = 1;</code>
     */
    public Builder clearElement() {
      if (elementBuilder_ == null) {
        element_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        elementBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .google.protobuf.Any element = 1;</code>
     */
    public Builder removeElement(int index) {
      if (elementBuilder_ == null) {
        ensureElementIsMutable();
        element_.remove(index);
        onChanged();
      } else {
        elementBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .google.protobuf.Any element = 1;</code>
     */
    public com.google.protobuf.Any.Builder getElementBuilder(
        int index) {
      return getElementFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .google.protobuf.Any element = 1;</code>
     */
    public com.google.protobuf.AnyOrBuilder getElementOrBuilder(
        int index) {
      if (elementBuilder_ == null) {
        return element_.get(index);  } else {
        return elementBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .google.protobuf.Any element = 1;</code>
     */
    public java.util.List<? extends com.google.protobuf.AnyOrBuilder> 
         getElementOrBuilderList() {
      if (elementBuilder_ != null) {
        return elementBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(element_);
      }
    }
    /**
     * <code>repeated .google.protobuf.Any element = 1;</code>
     */
    public com.google.protobuf.Any.Builder addElementBuilder() {
      return getElementFieldBuilder().addBuilder(
          com.google.protobuf.Any.getDefaultInstance());
    }
    /**
     * <code>repeated .google.protobuf.Any element = 1;</code>
     */
    public com.google.protobuf.Any.Builder addElementBuilder(
        int index) {
      return getElementFieldBuilder().addBuilder(
          index, com.google.protobuf.Any.getDefaultInstance());
    }
    /**
     * <code>repeated .google.protobuf.Any element = 1;</code>
     */
    public java.util.List<com.google.protobuf.Any.Builder> 
         getElementBuilderList() {
      return getElementFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        com.google.protobuf.Any, com.google.protobuf.Any.Builder, com.google.protobuf.AnyOrBuilder> 
        getElementFieldBuilder() {
      if (elementBuilder_ == null) {
        elementBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            com.google.protobuf.Any, com.google.protobuf.Any.Builder, com.google.protobuf.AnyOrBuilder>(
                element_,
                ((bitField0_ & 0x00000001) != 0),
                getParentForChildren(),
                isClean());
        element_ = null;
      }
      return elementBuilder_;
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


    // @@protoc_insertion_point(builder_scope:cn.edu.cug.cs.gtl.protos.Tuple)
  }

  // @@protoc_insertion_point(class_scope:cn.edu.cug.cs.gtl.protos.Tuple)
  private static final cn.edu.cug.cs.gtl.protos.Tuple DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new cn.edu.cug.cs.gtl.protos.Tuple();
  }

  public static cn.edu.cug.cs.gtl.protos.Tuple getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Tuple>
      PARSER = new com.google.protobuf.AbstractParser<Tuple>() {
    @java.lang.Override
    public Tuple parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new Tuple(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<Tuple> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Tuple> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public cn.edu.cug.cs.gtl.protos.Tuple getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
