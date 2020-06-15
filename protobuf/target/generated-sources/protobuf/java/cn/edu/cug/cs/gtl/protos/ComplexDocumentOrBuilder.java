// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: cn/edu/cug/cs/gtl/protos/document.proto

package cn.edu.cug.cs.gtl.protos;

public interface ComplexDocumentOrBuilder extends
    // @@protoc_insertion_point(interface_extends:cn.edu.cug.cs.gtl.protos.ComplexDocument)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Title title = 1;</code>
   * @return Whether the title field is set.
   */
  boolean hasTitle();
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Title title = 1;</code>
   * @return The title.
   */
  cn.edu.cug.cs.gtl.protos.Title getTitle();
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Title title = 1;</code>
   */
  cn.edu.cug.cs.gtl.protos.TitleOrBuilder getTitleOrBuilder();

  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Keywords keywords = 2;</code>
   * @return Whether the keywords field is set.
   */
  boolean hasKeywords();
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Keywords keywords = 2;</code>
   * @return The keywords.
   */
  cn.edu.cug.cs.gtl.protos.Keywords getKeywords();
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Keywords keywords = 2;</code>
   */
  cn.edu.cug.cs.gtl.protos.KeywordsOrBuilder getKeywordsOrBuilder();

  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Version version = 3;</code>
   * @return Whether the version field is set.
   */
  boolean hasVersion();
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Version version = 3;</code>
   * @return The version.
   */
  cn.edu.cug.cs.gtl.protos.Version getVersion();
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Version version = 3;</code>
   */
  cn.edu.cug.cs.gtl.protos.VersionOrBuilder getVersionOrBuilder();

  /**
   * <code>.cn.edu.cug.cs.gtl.protos.DocumentType type = 4;</code>
   * @return Whether the type field is set.
   */
  boolean hasType();
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.DocumentType type = 4;</code>
   * @return The type.
   */
  cn.edu.cug.cs.gtl.protos.DocumentType getType();
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.DocumentType type = 4;</code>
   */
  cn.edu.cug.cs.gtl.protos.DocumentTypeOrBuilder getTypeOrBuilder();

  /**
   * <code>.cn.edu.cug.cs.gtl.protos.URI uri = 5;</code>
   * @return Whether the uri field is set.
   */
  boolean hasUri();
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.URI uri = 5;</code>
   * @return The uri.
   */
  cn.edu.cug.cs.gtl.protos.URI getUri();
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.URI uri = 5;</code>
   */
  cn.edu.cug.cs.gtl.protos.URIOrBuilder getUriOrBuilder();

  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Authors author = 6;</code>
   * @return Whether the author field is set.
   */
  boolean hasAuthor();
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Authors author = 6;</code>
   * @return The author.
   */
  cn.edu.cug.cs.gtl.protos.Authors getAuthor();
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Authors author = 6;</code>
   */
  cn.edu.cug.cs.gtl.protos.AuthorsOrBuilder getAuthorOrBuilder();

  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Affiliations affiliations = 7;</code>
   * @return Whether the affiliations field is set.
   */
  boolean hasAffiliations();
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Affiliations affiliations = 7;</code>
   * @return The affiliations.
   */
  cn.edu.cug.cs.gtl.protos.Affiliations getAffiliations();
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Affiliations affiliations = 7;</code>
   */
  cn.edu.cug.cs.gtl.protos.AffiliationsOrBuilder getAffiliationsOrBuilder();

  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Abstract abstract = 8;</code>
   * @return Whether the abstract field is set.
   */
  boolean hasAbstract();
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Abstract abstract = 8;</code>
   * @return The abstract.
   */
  cn.edu.cug.cs.gtl.protos.Abstract getAbstract();
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Abstract abstract = 8;</code>
   */
  cn.edu.cug.cs.gtl.protos.AbstractOrBuilder getAbstractOrBuilder();

  /**
   * <code>string schema = 9;</code>
   * @return The schema.
   */
  java.lang.String getSchema();
  /**
   * <code>string schema = 9;</code>
   * @return The bytes for schema.
   */
  com.google.protobuf.ByteString
      getSchemaBytes();

  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Content content = 10;</code>
   * @return Whether the content field is set.
   */
  boolean hasContent();
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Content content = 10;</code>
   * @return The content.
   */
  cn.edu.cug.cs.gtl.protos.Content getContent();
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Content content = 10;</code>
   */
  cn.edu.cug.cs.gtl.protos.ContentOrBuilder getContentOrBuilder();

  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Raw raw_data = 11;</code>
   * @return Whether the rawData field is set.
   */
  boolean hasRawData();
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Raw raw_data = 11;</code>
   * @return The rawData.
   */
  cn.edu.cug.cs.gtl.protos.Raw getRawData();
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Raw raw_data = 11;</code>
   */
  cn.edu.cug.cs.gtl.protos.RawOrBuilder getRawDataOrBuilder();

  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Attachments attachments = 12;</code>
   * @return Whether the attachments field is set.
   */
  boolean hasAttachments();
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Attachments attachments = 12;</code>
   * @return The attachments.
   */
  cn.edu.cug.cs.gtl.protos.Attachments getAttachments();
  /**
   * <code>.cn.edu.cug.cs.gtl.protos.Attachments attachments = 12;</code>
   */
  cn.edu.cug.cs.gtl.protos.AttachmentsOrBuilder getAttachmentsOrBuilder();
}