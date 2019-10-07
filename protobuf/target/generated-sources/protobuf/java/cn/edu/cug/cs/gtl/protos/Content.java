// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: cn/edu/cug/cs/gtl/protos/document.proto

package cn.edu.cug.cs.gtl.protos;

/**
 * Protobuf type {@code cn.edu.cug.cs.gtl.protos.Content}
 */
public  final class Content extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:cn.edu.cug.cs.gtl.protos.Content)
    ContentOrBuilder {
private static final long serialVersionUID = 0L;
  // Use Content.newBuilder() to construct.
  private Content(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Content() {
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new Content();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private Content(
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
            cn.edu.cug.cs.gtl.protos.Paragraphs.Builder subBuilder = null;
            if (paragraphs_ != null) {
              subBuilder = paragraphs_.toBuilder();
            }
            paragraphs_ = input.readMessage(cn.edu.cug.cs.gtl.protos.Paragraphs.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(paragraphs_);
              paragraphs_ = subBuilder.buildPartial();
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
    return cn.edu.cug.cs.gtl.protos.Documents.internal_static_cn_edu_cug_cs_gtl_protos_Content_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return cn.edu.cug.cs.gtl.protos.Documents.internal_static_cn_edu_cug_cs_gtl_protos_Content_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            cn.edu.cug.cs.gtl.protos.Content.class, cn.edu.cug.cs.gtl.protos.Content.Builder.class);
  }

  public static final int PARAGRAPHS_FIELD_NUMBER = 1;
  private cn.edu.cug.cs.gtl.protos.Paragraphs paragraphs_;
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Paragraphs paragraphs = 1;</code>
   * @return Whether the paragraphs field is set.
   */
  public boolean hasParagraphs() {
    return paragraphs_ != null;
  }
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Paragraphs paragraphs = 1;</code>
   * @return The paragraphs.
   */
  public cn.edu.cug.cs.gtl.protos.Paragraphs getParagraphs() {
    return paragraphs_ == null ? cn.edu.cug.cs.gtl.protos.Paragraphs.getDefaultInstance() : paragraphs_;
  }
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Paragraphs paragraphs = 1;</code>
   */
  public cn.edu.cug.cs.gtl.protos.ParagraphsOrBuilder getParagraphsOrBuilder() {
    return getParagraphs();
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
    if (paragraphs_ != null) {
      output.writeMessage(1, getParagraphs());
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (paragraphs_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getParagraphs());
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
    if (!(obj instanceof cn.edu.cug.cs.gtl.protos.Content)) {
      return super.equals(obj);
    }
    cn.edu.cug.cs.gtl.protos.Content other = (cn.edu.cug.cs.gtl.protos.Content) obj;

    if (hasParagraphs() != other.hasParagraphs()) return false;
    if (hasParagraphs()) {
      if (!getParagraphs()
          .equals(other.getParagraphs())) return false;
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
    if (hasParagraphs()) {
      hash = (37 * hash) + PARAGRAPHS_FIELD_NUMBER;
      hash = (53 * hash) + getParagraphs().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static cn.edu.cug.cs.gtl.protos.Content parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static cn.edu.cug.cs.gtl.protos.Content parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Content parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static cn.edu.cug.cs.gtl.protos.Content parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Content parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static cn.edu.cug.cs.gtl.protos.Content parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Content parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static cn.edu.cug.cs.gtl.protos.Content parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Content parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static cn.edu.cug.cs.gtl.protos.Content parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Content parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static cn.edu.cug.cs.gtl.protos.Content parseFrom(
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
  public static Builder newBuilder(cn.edu.cug.cs.gtl.protos.Content prototype) {
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
   * Protobuf type {@code cn.edu.cug.cs.gtl.protos.Content}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:cn.edu.cug.cs.gtl.protos.Content)
      cn.edu.cug.cs.gtl.protos.ContentOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return cn.edu.cug.cs.gtl.protos.Documents.internal_static_cn_edu_cug_cs_gtl_protos_Content_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return cn.edu.cug.cs.gtl.protos.Documents.internal_static_cn_edu_cug_cs_gtl_protos_Content_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              cn.edu.cug.cs.gtl.protos.Content.class, cn.edu.cug.cs.gtl.protos.Content.Builder.class);
    }

    // Construct using cn.edu.cug.cs.gtl.protos.Content.newBuilder()
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
      if (paragraphsBuilder_ == null) {
        paragraphs_ = null;
      } else {
        paragraphs_ = null;
        paragraphsBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return cn.edu.cug.cs.gtl.protos.Documents.internal_static_cn_edu_cug_cs_gtl_protos_Content_descriptor;
    }

    @java.lang.Override
    public cn.edu.cug.cs.gtl.protos.Content getDefaultInstanceForType() {
      return cn.edu.cug.cs.gtl.protos.Content.getDefaultInstance();
    }

    @java.lang.Override
    public cn.edu.cug.cs.gtl.protos.Content build() {
      cn.edu.cug.cs.gtl.protos.Content result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public cn.edu.cug.cs.gtl.protos.Content buildPartial() {
      cn.edu.cug.cs.gtl.protos.Content result = new cn.edu.cug.cs.gtl.protos.Content(this);
      if (paragraphsBuilder_ == null) {
        result.paragraphs_ = paragraphs_;
      } else {
        result.paragraphs_ = paragraphsBuilder_.build();
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
      if (other instanceof cn.edu.cug.cs.gtl.protos.Content) {
        return mergeFrom((cn.edu.cug.cs.gtl.protos.Content)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(cn.edu.cug.cs.gtl.protos.Content other) {
      if (other == cn.edu.cug.cs.gtl.protos.Content.getDefaultInstance()) return this;
      if (other.hasParagraphs()) {
        mergeParagraphs(other.getParagraphs());
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
      cn.edu.cug.cs.gtl.protos.Content parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (cn.edu.cug.cs.gtl.protos.Content) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private cn.edu.cug.cs.gtl.protos.Paragraphs paragraphs_;
    private com.google.protobuf.SingleFieldBuilderV3<
        cn.edu.cug.cs.gtl.protos.Paragraphs, cn.edu.cug.cs.gtl.protos.Paragraphs.Builder, cn.edu.cug.cs.gtl.protos.ParagraphsOrBuilder> paragraphsBuilder_;
    /**
     * <code>.cn.edu.cug.cs.gtl.protos.Paragraphs paragraphs = 1;</code>
     * @return Whether the paragraphs field is set.
     */
    public boolean hasParagraphs() {
      return paragraphsBuilder_ != null || paragraphs_ != null;
    }
    /**
     * <code>.cn.edu.cug.cs.gtl.protos.Paragraphs paragraphs = 1;</code>
     * @return The paragraphs.
     */
    public cn.edu.cug.cs.gtl.protos.Paragraphs getParagraphs() {
      if (paragraphsBuilder_ == null) {
        return paragraphs_ == null ? cn.edu.cug.cs.gtl.protos.Paragraphs.getDefaultInstance() : paragraphs_;
      } else {
        return paragraphsBuilder_.getMessage();
      }
    }
    /**
     * <code>.cn.edu.cug.cs.gtl.protos.Paragraphs paragraphs = 1;</code>
     */
    public Builder setParagraphs(cn.edu.cug.cs.gtl.protos.Paragraphs value) {
      if (paragraphsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        paragraphs_ = value;
        onChanged();
      } else {
        paragraphsBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.cn.edu.cug.cs.gtl.protos.Paragraphs paragraphs = 1;</code>
     */
    public Builder setParagraphs(
        cn.edu.cug.cs.gtl.protos.Paragraphs.Builder builderForValue) {
      if (paragraphsBuilder_ == null) {
        paragraphs_ = builderForValue.build();
        onChanged();
      } else {
        paragraphsBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.cn.edu.cug.cs.gtl.protos.Paragraphs paragraphs = 1;</code>
     */
    public Builder mergeParagraphs(cn.edu.cug.cs.gtl.protos.Paragraphs value) {
      if (paragraphsBuilder_ == null) {
        if (paragraphs_ != null) {
          paragraphs_ =
            cn.edu.cug.cs.gtl.protos.Paragraphs.newBuilder(paragraphs_).mergeFrom(value).buildPartial();
        } else {
          paragraphs_ = value;
        }
        onChanged();
      } else {
        paragraphsBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.cn.edu.cug.cs.gtl.protos.Paragraphs paragraphs = 1;</code>
     */
    public Builder clearParagraphs() {
      if (paragraphsBuilder_ == null) {
        paragraphs_ = null;
        onChanged();
      } else {
        paragraphs_ = null;
        paragraphsBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.cn.edu.cug.cs.gtl.protos.Paragraphs paragraphs = 1;</code>
     */
    public cn.edu.cug.cs.gtl.protos.Paragraphs.Builder getParagraphsBuilder() {
      
      onChanged();
      return getParagraphsFieldBuilder().getBuilder();
    }
    /**
     * <code>.cn.edu.cug.cs.gtl.protos.Paragraphs paragraphs = 1;</code>
     */
    public cn.edu.cug.cs.gtl.protos.ParagraphsOrBuilder getParagraphsOrBuilder() {
      if (paragraphsBuilder_ != null) {
        return paragraphsBuilder_.getMessageOrBuilder();
      } else {
        return paragraphs_ == null ?
            cn.edu.cug.cs.gtl.protos.Paragraphs.getDefaultInstance() : paragraphs_;
      }
    }
    /**
     * <code>.cn.edu.cug.cs.gtl.protos.Paragraphs paragraphs = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        cn.edu.cug.cs.gtl.protos.Paragraphs, cn.edu.cug.cs.gtl.protos.Paragraphs.Builder, cn.edu.cug.cs.gtl.protos.ParagraphsOrBuilder> 
        getParagraphsFieldBuilder() {
      if (paragraphsBuilder_ == null) {
        paragraphsBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            cn.edu.cug.cs.gtl.protos.Paragraphs, cn.edu.cug.cs.gtl.protos.Paragraphs.Builder, cn.edu.cug.cs.gtl.protos.ParagraphsOrBuilder>(
                getParagraphs(),
                getParentForChildren(),
                isClean());
        paragraphs_ = null;
      }
      return paragraphsBuilder_;
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


    // @@protoc_insertion_point(builder_scope:cn.edu.cug.cs.gtl.protos.Content)
  }

  // @@protoc_insertion_point(class_scope:cn.edu.cug.cs.gtl.protos.Content)
  private static final cn.edu.cug.cs.gtl.protos.Content DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new cn.edu.cug.cs.gtl.protos.Content();
  }

  public static cn.edu.cug.cs.gtl.protos.Content getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Content>
      PARSER = new com.google.protobuf.AbstractParser<Content>() {
    @java.lang.Override
    public Content parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new Content(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<Content> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Content> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public cn.edu.cug.cs.gtl.protos.Content getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
