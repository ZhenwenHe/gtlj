// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: cn/edu/cug/cs/gtl/protos/document.proto

package cn.edu.cug.cs.gtl.protos;

/**
 * Protobuf type {@code cn.edu.cug.cs.gtl.protos.Authors}
 */
public  final class Authors extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:cn.edu.cug.cs.gtl.protos.Authors)
    AuthorsOrBuilder {
private static final long serialVersionUID = 0L;
  // Use Authors.newBuilder() to construct.
  private Authors(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Authors() {
    author_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new Authors();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private Authors(
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
              author_ = new java.util.ArrayList<cn.edu.cug.cs.gtl.protos.Author>();
              mutable_bitField0_ |= 0x00000001;
            }
            author_.add(
                input.readMessage(cn.edu.cug.cs.gtl.protos.Author.parser(), extensionRegistry));
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
        author_ = java.util.Collections.unmodifiableList(author_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return cn.edu.cug.cs.gtl.protos.Documents.internal_static_cn_edu_cug_cs_gtl_protos_Authors_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return cn.edu.cug.cs.gtl.protos.Documents.internal_static_cn_edu_cug_cs_gtl_protos_Authors_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            cn.edu.cug.cs.gtl.protos.Authors.class, cn.edu.cug.cs.gtl.protos.Authors.Builder.class);
  }

  public static final int AUTHOR_FIELD_NUMBER = 1;
  private java.util.List<cn.edu.cug.cs.gtl.protos.Author> author_;
  /**
   * <code>repeated .cn.edu.cug.cs.gtl.protos.Author author = 1;</code>
   */
  public java.util.List<cn.edu.cug.cs.gtl.protos.Author> getAuthorList() {
    return author_;
  }
  /**
   * <code>repeated .cn.edu.cug.cs.gtl.protos.Author author = 1;</code>
   */
  public java.util.List<? extends cn.edu.cug.cs.gtl.protos.AuthorOrBuilder> 
      getAuthorOrBuilderList() {
    return author_;
  }
  /**
   * <code>repeated .cn.edu.cug.cs.gtl.protos.Author author = 1;</code>
   */
  public int getAuthorCount() {
    return author_.size();
  }
  /**
   * <code>repeated .cn.edu.cug.cs.gtl.protos.Author author = 1;</code>
   */
  public cn.edu.cug.cs.gtl.protos.Author getAuthor(int index) {
    return author_.get(index);
  }
  /**
   * <code>repeated .cn.edu.cug.cs.gtl.protos.Author author = 1;</code>
   */
  public cn.edu.cug.cs.gtl.protos.AuthorOrBuilder getAuthorOrBuilder(
      int index) {
    return author_.get(index);
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
    for (int i = 0; i < author_.size(); i++) {
      output.writeMessage(1, author_.get(i));
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < author_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, author_.get(i));
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
    if (!(obj instanceof cn.edu.cug.cs.gtl.protos.Authors)) {
      return super.equals(obj);
    }
    cn.edu.cug.cs.gtl.protos.Authors other = (cn.edu.cug.cs.gtl.protos.Authors) obj;

    if (!getAuthorList()
        .equals(other.getAuthorList())) return false;
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
    if (getAuthorCount() > 0) {
      hash = (37 * hash) + AUTHOR_FIELD_NUMBER;
      hash = (53 * hash) + getAuthorList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static cn.edu.cug.cs.gtl.protos.Authors parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static cn.edu.cug.cs.gtl.protos.Authors parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Authors parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static cn.edu.cug.cs.gtl.protos.Authors parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Authors parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static cn.edu.cug.cs.gtl.protos.Authors parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Authors parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static cn.edu.cug.cs.gtl.protos.Authors parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Authors parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static cn.edu.cug.cs.gtl.protos.Authors parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static cn.edu.cug.cs.gtl.protos.Authors parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static cn.edu.cug.cs.gtl.protos.Authors parseFrom(
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
  public static Builder newBuilder(cn.edu.cug.cs.gtl.protos.Authors prototype) {
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
   * Protobuf type {@code cn.edu.cug.cs.gtl.protos.Authors}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:cn.edu.cug.cs.gtl.protos.Authors)
      cn.edu.cug.cs.gtl.protos.AuthorsOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return cn.edu.cug.cs.gtl.protos.Documents.internal_static_cn_edu_cug_cs_gtl_protos_Authors_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return cn.edu.cug.cs.gtl.protos.Documents.internal_static_cn_edu_cug_cs_gtl_protos_Authors_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              cn.edu.cug.cs.gtl.protos.Authors.class, cn.edu.cug.cs.gtl.protos.Authors.Builder.class);
    }

    // Construct using cn.edu.cug.cs.gtl.protos.Authors.newBuilder()
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
        getAuthorFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (authorBuilder_ == null) {
        author_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        authorBuilder_.clear();
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return cn.edu.cug.cs.gtl.protos.Documents.internal_static_cn_edu_cug_cs_gtl_protos_Authors_descriptor;
    }

    @java.lang.Override
    public cn.edu.cug.cs.gtl.protos.Authors getDefaultInstanceForType() {
      return cn.edu.cug.cs.gtl.protos.Authors.getDefaultInstance();
    }

    @java.lang.Override
    public cn.edu.cug.cs.gtl.protos.Authors build() {
      cn.edu.cug.cs.gtl.protos.Authors result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public cn.edu.cug.cs.gtl.protos.Authors buildPartial() {
      cn.edu.cug.cs.gtl.protos.Authors result = new cn.edu.cug.cs.gtl.protos.Authors(this);
      int from_bitField0_ = bitField0_;
      if (authorBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          author_ = java.util.Collections.unmodifiableList(author_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.author_ = author_;
      } else {
        result.author_ = authorBuilder_.build();
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
      if (other instanceof cn.edu.cug.cs.gtl.protos.Authors) {
        return mergeFrom((cn.edu.cug.cs.gtl.protos.Authors)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(cn.edu.cug.cs.gtl.protos.Authors other) {
      if (other == cn.edu.cug.cs.gtl.protos.Authors.getDefaultInstance()) return this;
      if (authorBuilder_ == null) {
        if (!other.author_.isEmpty()) {
          if (author_.isEmpty()) {
            author_ = other.author_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureAuthorIsMutable();
            author_.addAll(other.author_);
          }
          onChanged();
        }
      } else {
        if (!other.author_.isEmpty()) {
          if (authorBuilder_.isEmpty()) {
            authorBuilder_.dispose();
            authorBuilder_ = null;
            author_ = other.author_;
            bitField0_ = (bitField0_ & ~0x00000001);
            authorBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getAuthorFieldBuilder() : null;
          } else {
            authorBuilder_.addAllMessages(other.author_);
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
      cn.edu.cug.cs.gtl.protos.Authors parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (cn.edu.cug.cs.gtl.protos.Authors) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private java.util.List<cn.edu.cug.cs.gtl.protos.Author> author_ =
      java.util.Collections.emptyList();
    private void ensureAuthorIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        author_ = new java.util.ArrayList<cn.edu.cug.cs.gtl.protos.Author>(author_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        cn.edu.cug.cs.gtl.protos.Author, cn.edu.cug.cs.gtl.protos.Author.Builder, cn.edu.cug.cs.gtl.protos.AuthorOrBuilder> authorBuilder_;

    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.Author author = 1;</code>
     */
    public java.util.List<cn.edu.cug.cs.gtl.protos.Author> getAuthorList() {
      if (authorBuilder_ == null) {
        return java.util.Collections.unmodifiableList(author_);
      } else {
        return authorBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.Author author = 1;</code>
     */
    public int getAuthorCount() {
      if (authorBuilder_ == null) {
        return author_.size();
      } else {
        return authorBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.Author author = 1;</code>
     */
    public cn.edu.cug.cs.gtl.protos.Author getAuthor(int index) {
      if (authorBuilder_ == null) {
        return author_.get(index);
      } else {
        return authorBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.Author author = 1;</code>
     */
    public Builder setAuthor(
        int index, cn.edu.cug.cs.gtl.protos.Author value) {
      if (authorBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureAuthorIsMutable();
        author_.set(index, value);
        onChanged();
      } else {
        authorBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.Author author = 1;</code>
     */
    public Builder setAuthor(
        int index, cn.edu.cug.cs.gtl.protos.Author.Builder builderForValue) {
      if (authorBuilder_ == null) {
        ensureAuthorIsMutable();
        author_.set(index, builderForValue.build());
        onChanged();
      } else {
        authorBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.Author author = 1;</code>
     */
    public Builder addAuthor(cn.edu.cug.cs.gtl.protos.Author value) {
      if (authorBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureAuthorIsMutable();
        author_.add(value);
        onChanged();
      } else {
        authorBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.Author author = 1;</code>
     */
    public Builder addAuthor(
        int index, cn.edu.cug.cs.gtl.protos.Author value) {
      if (authorBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureAuthorIsMutable();
        author_.add(index, value);
        onChanged();
      } else {
        authorBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.Author author = 1;</code>
     */
    public Builder addAuthor(
        cn.edu.cug.cs.gtl.protos.Author.Builder builderForValue) {
      if (authorBuilder_ == null) {
        ensureAuthorIsMutable();
        author_.add(builderForValue.build());
        onChanged();
      } else {
        authorBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.Author author = 1;</code>
     */
    public Builder addAuthor(
        int index, cn.edu.cug.cs.gtl.protos.Author.Builder builderForValue) {
      if (authorBuilder_ == null) {
        ensureAuthorIsMutable();
        author_.add(index, builderForValue.build());
        onChanged();
      } else {
        authorBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.Author author = 1;</code>
     */
    public Builder addAllAuthor(
        java.lang.Iterable<? extends cn.edu.cug.cs.gtl.protos.Author> values) {
      if (authorBuilder_ == null) {
        ensureAuthorIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, author_);
        onChanged();
      } else {
        authorBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.Author author = 1;</code>
     */
    public Builder clearAuthor() {
      if (authorBuilder_ == null) {
        author_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        authorBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.Author author = 1;</code>
     */
    public Builder removeAuthor(int index) {
      if (authorBuilder_ == null) {
        ensureAuthorIsMutable();
        author_.remove(index);
        onChanged();
      } else {
        authorBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.Author author = 1;</code>
     */
    public cn.edu.cug.cs.gtl.protos.Author.Builder getAuthorBuilder(
        int index) {
      return getAuthorFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.Author author = 1;</code>
     */
    public cn.edu.cug.cs.gtl.protos.AuthorOrBuilder getAuthorOrBuilder(
        int index) {
      if (authorBuilder_ == null) {
        return author_.get(index);  } else {
        return authorBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.Author author = 1;</code>
     */
    public java.util.List<? extends cn.edu.cug.cs.gtl.protos.AuthorOrBuilder> 
         getAuthorOrBuilderList() {
      if (authorBuilder_ != null) {
        return authorBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(author_);
      }
    }
    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.Author author = 1;</code>
     */
    public cn.edu.cug.cs.gtl.protos.Author.Builder addAuthorBuilder() {
      return getAuthorFieldBuilder().addBuilder(
          cn.edu.cug.cs.gtl.protos.Author.getDefaultInstance());
    }
    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.Author author = 1;</code>
     */
    public cn.edu.cug.cs.gtl.protos.Author.Builder addAuthorBuilder(
        int index) {
      return getAuthorFieldBuilder().addBuilder(
          index, cn.edu.cug.cs.gtl.protos.Author.getDefaultInstance());
    }
    /**
     * <code>repeated .cn.edu.cug.cs.gtl.protos.Author author = 1;</code>
     */
    public java.util.List<cn.edu.cug.cs.gtl.protos.Author.Builder> 
         getAuthorBuilderList() {
      return getAuthorFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        cn.edu.cug.cs.gtl.protos.Author, cn.edu.cug.cs.gtl.protos.Author.Builder, cn.edu.cug.cs.gtl.protos.AuthorOrBuilder> 
        getAuthorFieldBuilder() {
      if (authorBuilder_ == null) {
        authorBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            cn.edu.cug.cs.gtl.protos.Author, cn.edu.cug.cs.gtl.protos.Author.Builder, cn.edu.cug.cs.gtl.protos.AuthorOrBuilder>(
                author_,
                ((bitField0_ & 0x00000001) != 0),
                getParentForChildren(),
                isClean());
        author_ = null;
      }
      return authorBuilder_;
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


    // @@protoc_insertion_point(builder_scope:cn.edu.cug.cs.gtl.protos.Authors)
  }

  // @@protoc_insertion_point(class_scope:cn.edu.cug.cs.gtl.protos.Authors)
  private static final cn.edu.cug.cs.gtl.protos.Authors DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new cn.edu.cug.cs.gtl.protos.Authors();
  }

  public static cn.edu.cug.cs.gtl.protos.Authors getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Authors>
      PARSER = new com.google.protobuf.AbstractParser<Authors>() {
    @java.lang.Override
    public Authors parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new Authors(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<Authors> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Authors> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public cn.edu.cug.cs.gtl.protos.Authors getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

