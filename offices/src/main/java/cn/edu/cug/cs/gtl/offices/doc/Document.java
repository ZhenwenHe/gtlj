package cn.edu.cug.cs.gtl.offices.doc;

public class Document {
    Title title;
    Keywords keywords;
    Version version;
    Type type;
    URI uri;
    Content content;
    Authors authors;
    Affiliations affiliations;
    Abstract anAbstract;
    Raw raw;

    public Authors getAuthors() {
        return authors;
    }

    public void setAuthors(Authors authors) {
        this.authors = authors;
    }

    public Affiliations getAffiliations() {
        return affiliations;
    }

    public void setAffiliations(Affiliations affiliations) {
        this.affiliations = affiliations;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Keywords getKeywords() {
        return keywords;
    }

    public void setKeywords(Keywords keywords) {
        this.keywords = keywords;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public URI getURI() {
        return uri;
    }

    public void setURI(URI uri) {
        this.uri = uri;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Abstract getAbstract() {
        return anAbstract;
    }

    public void setAbstract(Abstract anAbstract) {
        this.anAbstract = anAbstract;
    }

    public Raw getRaw() {
        return this.raw;
    }

    public void setRaw(Raw raw) {
        this.raw = raw;
    }
}
