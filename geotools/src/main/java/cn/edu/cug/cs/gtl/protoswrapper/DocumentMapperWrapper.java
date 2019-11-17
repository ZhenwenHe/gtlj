package cn.edu.cug.cs.gtl.protoswrapper;

import cn.edu.cug.cs.gtl.protos.DocumentMapper;

public class DocumentMapperWrapper {
    public static DocumentMapper paragraphMapper(){
        return DocumentMapper.newBuilder().setMappingType(DocumentMapper.MappingType.DOC_PRE_TEXT_PARAGRAPH_VALUE).build();
    }

    public static DocumentMapper lineMapper(){
        return DocumentMapper.newBuilder().setMappingType(DocumentMapper.MappingType.DOC_PRE_TEXT_LINE_VALUE).build();
    }

    public static DocumentMapper fileMapper(){
        return DocumentMapper.newBuilder().setMappingType(DocumentMapper.MappingType.DOC_PRE_TEXT_FILE_VALUE).build();
    }

    public static DocumentMapper rawMapper(){
        return DocumentMapper.newBuilder().setMappingType(DocumentMapper.MappingType.DOC_PRE_RAW_FILE_VALUE).build();
    }
}
