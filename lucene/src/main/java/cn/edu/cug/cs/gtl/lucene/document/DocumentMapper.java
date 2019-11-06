package cn.edu.cug.cs.gtl.lucene.document;

public class   DocumentMapper {
    public static final int DM_DOC_PRE_TEXT_FILE =3;// //提取文件中的文本，为整个文件创建一个文档
    public static final int DM_DOC_PRE_TEXT_PARAGRAPH =2; //提取文件中的文本，为文件中的每个自然段创建一个文档
    public static final int DM_DOC_PRE_TEXT_LINE =1;   //提取文件中的文本，为文件中的每行文本创建一个文档
    public static final int DM_DOC_PRE_RAW_FILE=0;//不提取文本，为整个文件创建一个文档

    private int mappingType;

    private DocumentMapper(int mappingType) {
        this.mappingType = mappingType;
    }

    public int getMappingType() {
        return mappingType;
    }

    public void setMappingType(int mappingType) {
        this.mappingType = mappingType;
    }

    public static DocumentMapper of(int mappingType){
        return new DocumentMapper(mappingType);
    }

    public static DocumentMapper lineMapper(){
        return new DocumentMapper(DM_DOC_PRE_TEXT_LINE);
    }

    public static DocumentMapper fileMapper(){
        return new DocumentMapper(DM_DOC_PRE_TEXT_FILE);
    }

    public static DocumentMapper paragraphMapper(){
        return new DocumentMapper(DM_DOC_PRE_TEXT_PARAGRAPH);
    }

    public static DocumentMapper rawMapper(){
        return new DocumentMapper(DM_DOC_PRE_RAW_FILE);
    }
}
