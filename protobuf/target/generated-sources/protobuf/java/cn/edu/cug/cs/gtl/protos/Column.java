// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: cn/edu/cug/cs/gtl/protos/value.proto

package cn.edu.cug.cs.gtl.protos;

/**
 * Protobuf type {@code cn.edu.cug.cs.gtl.protos.Column}
 */
public  final class Column extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:cn.edu.cug.cs.gtl.protos.Column)
    ColumnOrBuilder {
private static final long serialVersionUID = 0L;
  // Use Column.newBuilder() to construct.
  private Column(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Column() {
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new Column();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private Column(
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
          case 10: {
            cn.edu.cug.cs.gtl.protos.ColumnInfo.Builder subBuilder = null;
            if (info_ != null) {
              subBuilder = info_.toBuilder();
            }
            info_ = input.readMessage(cn.edu.cug.cs.gtl.protos.ColumnInfo.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(info_);
              info_ = subBuilder.buildPartial();
            }

            break;
          }
          case 18: {
            com.google.protobuf.Any.Builder subBuilder = null;
            if (values_ != null) {
              subBuilder = values_.toBuilder();
            }
            values_ = input.readMessage(com.google.protobuf.Any.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(values_);
              values_ = subBuilder.buildPartial();
            }

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
    return cn.edu.cug.cs.gtl.protos.Values.internal_static_cn_edu_cug_cs_gtl_protos_Column_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return cn.edu.cug.cs.gtl.protos.Values.internal_static_cn_edu_cug_cs_gtl_protos_Column_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            cn.edu.cug.cs.gtl.protos.Column.class, cn.edu.cug.cs.gtl.protos.Column.Builder.class);
  }

  public static final int INFO_FIELD_NUMBER = 1;
  private cn.edu.cug.cs.gtl.protos.ColumnInfo info_;
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.ColumnInfo info = 1;</code>
   * @return Whether the info field is set.
   */
  public boolean hasInfo() {
    return info_ != null;
  }
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.ColumnInfo info = 1;</code>
   * @return The info.
   */
  public cn.edu.cug.cs.gtl.protos.ColumnInfo getInfo() {
    return info_ == null ? cn.edu.cug.cs.gtl.protos.ColumnInfo.getDefaultInstance() : info_;
  }
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.ColumnInfo info = 1;</code>
   */
  public cn.edu.cug.cs.gtl.protos.ColumnInfoOrBuilder getInfoOrBuilder() {
    return getInfo();
  }

  public static final int VALUES_FIELD_NUMBER = 2;
  private com.google.protobuf.Any values_;
  /**
   * <code>.google.protobuf.Any values = 2;</code>
   * @return Whether the values field is set.
   */
  public boolean hasValues() {
    return values_ != null;
  }
  /**
   * <code>.google.protobuf.Any values = 2;</code>
   * @return The values.
   */
  public com.google.protobuf.Any getValues() {
    return values_ == null ? com.google.protobuf.Any.getDefaultInstance() : values_;
  }
  /**
   * <code>.google.protobuf.Any values = 2;</code>
   */
  public com.google.protobuf.AnyOrBuilder getValuesOrBuilder() {
    return getValues();
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
    if (info_ != null) {
      output.writeMessage(1, getInfo());
    }
    if (values_ != null) {
      output.writeMessage(2, getValues());
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (info_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getInfo());
    }
    if (values_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, getValues());
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
    if (!(obj instanceof cn.edu.cug.cs.gtl.protos.Column)) {
      return super.equals(obj);
    }
    cn.edu.cug.cs.gtl.protos.Column other = (cn.edu.cug.cs.gtl.protos.Column) obj;

    if (hasInfo() != other.hasInfo()) return false;
    if (hasInfo()) {
      if (!getInfo()
          .equals(other.getInfo())) return false;
    }
    if (hasValues() != other.hasValues()) return false;
    if (hasValues()) {
      if (!getValues()
          .equals(other.getValues())) return false;
    }
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
    if (hasInfo()) {
      hash = (37 * hash) + INFO_FIELD_NUMBER;
      hash = (53 * hash) + getInfo().hashCode();
    }
    if (hasValues()) {
      hash = (37 * hash) + VALUES_FIELD_NUMBER;
      hash = (53 * hash) + getValues().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static cn.edu.cug.cs.gtl.protos.Column parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static cn.edu.cug.cs.gtl.protos.Column parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Column parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static cn.edu.cug.cs.gtl.protos.Column parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Column parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static cn.edu.cug.cs.gtl.protos.Column parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Column parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static cn.edu.cug.cs.gtl.protos.Column parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Column parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static cn.edu.cug.cs.gtl.protos.Column parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Column parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static cn.edu.cug.cs.gtl.protos.Column parseFrom(
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
  public static Builder newBuilder(cn.edu.cug.cs.gtl.protos.Column prototype) {
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
   * Protobuf type {@code cn.edu.cug.cs.gtl.protos.Column}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:cn.edu.cug.cs.gtl.protos.Column)
      cn.edu.cug.cs.gtl.protos.ColumnOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return cn.edu.cug.cs.gtl.protos.Values.internal_static_cn_edu_cug_cs_gtl_protos_Column_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return cn.edu.cug.cs.gtl.protos.Values.internal_static_cn_edu_cug_cs_gtl_protos_Column_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              cn.edu.cug.cs.gtl.protos.Column.class, cn.edu.cug.cs.gtl.protos.Column.Builder.class);
    }

    // Construct using cn.edu.cug.cs.gtl.protos.Column.newBuilder()
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
      if (infoBuilder_ == null) {
        info_ = null;
      } else {
        info_ = null;
        infoBuilder_ = null;
      }
      if (valuesBuilder_ == null) {
        values_ = null;
      } else {
        values_ = null;
        valuesBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return cn.edu.cug.cs.gtl.protos.Values.internal_static_cn_edu_cug_cs_gtl_protos_Column_descriptor;
    }

    @java.lang.Override
    public cn.edu.cug.cs.gtl.protos.Column getDefaultInstanceForType() {
      return cn.edu.cug.cs.gtl.protos.Column.getDefaultInstance();
    }

    @java.lang.Override
    public cn.edu.cug.cs.gtl.protos.Column build() {
      cn.edu.cug.cs.gtl.protos.Column result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public cn.edu.cug.cs.gtl.protos.Column buildPartial() {
      cn.edu.cug.cs.gtl.protos.Column result = new cn.edu.cug.cs.gtl.protos.Column(this);
      if (infoBuilder_ == null) {
        result.info_ = info_;
      } else {
        result.info_ = infoBuilder_.build();
      }
      if (valuesBuilder_ == null) {
        result.values_ = values_;
      } else {
        result.values_ = valuesBuilder_.build();
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
      if (other instanceof cn.edu.cug.cs.gtl.protos.Column) {
        return mergeFrom((cn.edu.cug.cs.gtl.protos.Column)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(cn.edu.cug.cs.gtl.protos.Column other) {
      if (other == cn.edu.cug.cs.gtl.protos.Column.getDefaultInstance()) return this;
      if (other.hasInfo()) {
        mergeInfo(other.getInfo());
      }
      if (other.hasValues()) {
        mergeValues(other.getValues());
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
      cn.edu.cug.cs.gtl.protos.Column parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (cn.edu.cug.cs.gtl.protos.Column) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private cn.edu.cug.cs.gtl.protos.ColumnInfo info_;
    private com.google.protobuf.SingleFieldBuilderV3<
        cn.edu.cug.cs.gtl.protos.ColumnInfo, cn.edu.cug.cs.gtl.protos.ColumnInfo.Builder, cn.edu.cug.cs.gtl.protos.ColumnInfoOrBuilder> infoBuilder_;
    /**
     * <code>.cn.edu.cug.cs.gtl.protos.ColumnInfo info = 1;</code>
     * @return Whether the info field is set.
     */
    public boolean hasInfo() {
      return infoBuilder_ != null || info_ != null;
    }
    /**
     * <code>.cn.edu.cug.cs.gtl.protos.ColumnInfo info = 1;</code>
     * @return The info.
     */
    public cn.edu.cug.cs.gtl.protos.ColumnInfo getInfo() {
      if (infoBuilder_ == null) {
        return info_ == null ? cn.edu.cug.cs.gtl.protos.ColumnInfo.getDefaultInstance() : info_;
      } else {
        return infoBuilder_.getMessage();
      }
    }
    /**
     * <code>.cn.edu.cug.cs.gtl.protos.ColumnInfo info = 1;</code>
     */
    public Builder setInfo(cn.edu.cug.cs.gtl.protos.ColumnInfo value) {
      if (infoBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        info_ = value;
        onChanged();
      } else {
        infoBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.cn.edu.cug.cs.gtl.protos.ColumnInfo info = 1;</code>
     */
    public Builder setInfo(
        cn.edu.cug.cs.gtl.protos.ColumnInfo.Builder builderForValue) {
      if (infoBuilder_ == null) {
        info_ = builderForValue.build();
        onChanged();
      } else {
        infoBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.cn.edu.cug.cs.gtl.protos.ColumnInfo info = 1;</code>
     */
    public Builder mergeInfo(cn.edu.cug.cs.gtl.protos.ColumnInfo value) {
      if (infoBuilder_ == null) {
        if (info_ != null) {
          info_ =
            cn.edu.cug.cs.gtl.protos.ColumnInfo.newBuilder(info_).mergeFrom(value).buildPartial();
        } else {
          info_ = value;
        }
        onChanged();
      } else {
        infoBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.cn.edu.cug.cs.gtl.protos.ColumnInfo info = 1;</code>
     */
    public Builder clearInfo() {
      if (infoBuilder_ == null) {
        info_ = null;
        onChanged();
      } else {
        info_ = null;
        infoBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.cn.edu.cug.cs.gtl.protos.ColumnInfo info = 1;</code>
     */
    public cn.edu.cug.cs.gtl.protos.ColumnInfo.Builder getInfoBuilder() {
      
      onChanged();
      return getInfoFieldBuilder().getBuilder();
    }
    /**
     * <code>.cn.edu.cug.cs.gtl.protos.ColumnInfo info = 1;</code>
     */
    public cn.edu.cug.cs.gtl.protos.ColumnInfoOrBuilder getInfoOrBuilder() {
      if (infoBuilder_ != null) {
        return infoBuilder_.getMessageOrBuilder();
      } else {
        return info_ == null ?
            cn.edu.cug.cs.gtl.protos.ColumnInfo.getDefaultInstance() : info_;
      }
    }
    /**
     * <code>.cn.edu.cug.cs.gtl.protos.ColumnInfo info = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        cn.edu.cug.cs.gtl.protos.ColumnInfo, cn.edu.cug.cs.gtl.protos.ColumnInfo.Builder, cn.edu.cug.cs.gtl.protos.ColumnInfoOrBuilder> 
        getInfoFieldBuilder() {
      if (infoBuilder_ == null) {
        infoBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            cn.edu.cug.cs.gtl.protos.ColumnInfo, cn.edu.cug.cs.gtl.protos.ColumnInfo.Builder, cn.edu.cug.cs.gtl.protos.ColumnInfoOrBuilder>(
                getInfo(),
                getParentForChildren(),
                isClean());
        info_ = null;
      }
      return infoBuilder_;
    }

    private com.google.protobuf.Any values_;
    private com.google.protobuf.SingleFieldBuilderV3<
        com.google.protobuf.Any, com.google.protobuf.Any.Builder, com.google.protobuf.AnyOrBuilder> valuesBuilder_;
    /**
     * <code>.google.protobuf.Any values = 2;</code>
     * @return Whether the values field is set.
     */
    public boolean hasValues() {
      return valuesBuilder_ != null || values_ != null;
    }
    /**
     * <code>.google.protobuf.Any values = 2;</code>
     * @return The values.
     */
    public com.google.protobuf.Any getValues() {
      if (valuesBuilder_ == null) {
        return values_ == null ? com.google.protobuf.Any.getDefaultInstance() : values_;
      } else {
        return valuesBuilder_.getMessage();
      }
    }
    /**
     * <code>.google.protobuf.Any values = 2;</code>
     */
    public Builder setValues(com.google.protobuf.Any value) {
      if (valuesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        values_ = value;
        onChanged();
      } else {
        valuesBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.google.protobuf.Any values = 2;</code>
     */
    public Builder setValues(
        com.google.protobuf.Any.Builder builderForValue) {
      if (valuesBuilder_ == null) {
        values_ = builderForValue.build();
        onChanged();
      } else {
        valuesBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.google.protobuf.Any values = 2;</code>
     */
    public Builder mergeValues(com.google.protobuf.Any value) {
      if (valuesBuilder_ == null) {
        if (values_ != null) {
          values_ =
            com.google.protobuf.Any.newBuilder(values_).mergeFrom(value).buildPartial();
        } else {
          values_ = value;
        }
        onChanged();
      } else {
        valuesBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.google.protobuf.Any values = 2;</code>
     */
    public Builder clearValues() {
      if (valuesBuilder_ == null) {
        values_ = null;
        onChanged();
      } else {
        values_ = null;
        valuesBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.google.protobuf.Any values = 2;</code>
     */
    public com.google.protobuf.Any.Builder getValuesBuilder() {
      
      onChanged();
      return getValuesFieldBuilder().getBuilder();
    }
    /**
     * <code>.google.protobuf.Any values = 2;</code>
     */
    public com.google.protobuf.AnyOrBuilder getValuesOrBuilder() {
      if (valuesBuilder_ != null) {
        return valuesBuilder_.getMessageOrBuilder();
      } else {
        return values_ == null ?
            com.google.protobuf.Any.getDefaultInstance() : values_;
      }
    }
    /**
     * <code>.google.protobuf.Any values = 2;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        com.google.protobuf.Any, com.google.protobuf.Any.Builder, com.google.protobuf.AnyOrBuilder> 
        getValuesFieldBuilder() {
      if (valuesBuilder_ == null) {
        valuesBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            com.google.protobuf.Any, com.google.protobuf.Any.Builder, com.google.protobuf.AnyOrBuilder>(
                getValues(),
                getParentForChildren(),
                isClean());
        values_ = null;
      }
      return valuesBuilder_;
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


    // @@protoc_insertion_point(builder_scope:cn.edu.cug.cs.gtl.protos.Column)
  }

  // @@protoc_insertion_point(class_scope:cn.edu.cug.cs.gtl.protos.Column)
  private static final cn.edu.cug.cs.gtl.protos.Column DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new cn.edu.cug.cs.gtl.protos.Column();
  }

  public static cn.edu.cug.cs.gtl.protos.Column getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Column>
      PARSER = new com.google.protobuf.AbstractParser<Column>() {
    @java.lang.Override
    public Column parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new Column(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<Column> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Column> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public cn.edu.cug.cs.gtl.protos.Column getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
