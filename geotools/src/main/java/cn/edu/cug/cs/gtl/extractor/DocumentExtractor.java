package cn.edu.cug.cs.gtl.extractor;


import java.io.IOException;

import cn.edu.cug.cs.gtl.filter.*;
import cn.edu.cug.cs.gtl.io.File;
import cn.edu.cug.cs.gtl.protos.Attachment;
import cn.edu.cug.cs.gtl.protos.Attachments;
import cn.edu.cug.cs.gtl.protos.Document;
import com.google.protobuf.ByteString;

public class DocumentExtractor {

    private static Document.Builder parseOfficeFileToDocumentBuilder(String inputFileName) throws Exception{
        Document.Builder b = Document.newBuilder();
        b.setTitle(File.getFileNameWithoutSuffix(inputFileName));
        b.setType(File.getSuffixName(inputFileName));
        String [] ss = TextExtractor.parseToStrings(inputFileName);
        for(String s: ss){
            b.addContent(s);
        }
        return b;
    }

    private static Document.Builder parseTextFileToDocumentBuilder(String inputFileName) throws Exception{
        Document.Builder b = Document.newBuilder();
        b.setTitle(File.getFileNameWithoutSuffix(inputFileName));
        b.setType(File.getSuffixName(inputFileName));
        String [] ss = TextExtractor.parseToStrings(inputFileName);
        for(String s: ss){
            b.addContent(s);
        }
        return b;
    }

    private static Document.Builder parseShapeFileToDocumentBuilder(String inputFileName) throws Exception{
        Document.Builder b = Document.newBuilder();
        b.setTitle(File.getFileNameWithoutSuffix(inputFileName));
        b.setType(File.getSuffixName(inputFileName));
        String [] ss = TextExtractor.parseToStrings(inputFileName);
        for(String s: ss){
            b.addContent(s);
        }
        return b;
    }

    private static Document.Builder parseImageFileToDocumentBuilder(String inputFileName) throws Exception{
        Document.Builder b = Document.newBuilder();
        b.setTitle(File.getFileNameWithoutSuffix(inputFileName));
        b.setType(File.getSuffixName(inputFileName));
        ByteString bs = ByteExtractor.parseToByteString(inputFileName);
        if(bs!=null && !bs.isEmpty())
            b.setRawData(bs);
        return b;
    }

    private static Document.Builder parseRawFileToDocumentBuilder(String inputFileName) throws Exception{
        Document.Builder b = Document.newBuilder();
        b.setTitle(File.getFileNameWithoutSuffix(inputFileName));
        String suffix = File.getSuffixName(inputFileName);
        if(!suffix.isEmpty())
            b.setType(suffix);
        ByteString bs = ByteExtractor.parseToByteString(inputFileName);
        if(bs!=null && !bs.isEmpty())
            b.setRawData(bs);
        return b;
    }

    private static Document.Builder parseToDocumentBuilder(String inputFileName) throws Exception{
        FileFilter ff = FileFilter.autoFileFilter(inputFileName);
        if(ff instanceof TextsFileFilter){
            return parseTextFileToDocumentBuilder(inputFileName);
        }
        else if(ff instanceof OfficesFileFilter){
            return parseOfficeFileToDocumentBuilder(inputFileName);
        }
        else if(ff instanceof ShapesFileFilter){
            return parseShapeFileToDocumentBuilder(inputFileName);
        }
        else if(ff instanceof ImagesFileFilter){
            return parseImageFileToDocumentBuilder(inputFileName);
        }
        else{
            return parseRawFileToDocumentBuilder(inputFileName);
        }
    }

    /**
     *
     * @param inputFileName
     * @return
     * @throws IOException
     */
    public static Document parseToDocument(String inputFileName) throws Exception{
        return parseToDocumentBuilder(inputFileName).build();
    }

    /**
     *
     * @param inputFileName
     * @return
     * @throws IOException
     */
    public static Document parseToDocument(File inputFileName) throws Exception{
        return parseToDocument(inputFileName.getAbsolutePath());
    }

    /**
     *
     * @param inputFileName
     * @return
     * @throws IOException
     */
    public static Document parseToDocument(java.io.File inputFileName) throws Exception{
        return parseToDocument(inputFileName.getAbsolutePath());
    }
    /**
     *
     * @param inputFileName
     * @param attachmentFiles
     * @return
     * @throws Exception
     */
    public static Document parseToDocument(String inputFileName, String ... attachmentFiles) throws Exception{
        Document.Builder b = parseToDocumentBuilder(inputFileName);
        if(attachmentFiles.length>0){
            Attachments.Builder abs = Attachments.newBuilder();
            for(String a : attachmentFiles){
                Attachment.Builder ab = Attachment.newBuilder();
                ab.setName(a);
                ab.setRawData(ByteExtractor.parseToByteString(a));
                abs.addAttachment(ab.build());
            }
            b.setAttachments(abs.build());
        }

        return b.build();
    }


    /**
     *
     * @param inputFileName
     * @param attachmentFiles
     * @return
     * @throws Exception
     */
    public static Document parseToDocument(File inputFileName, File ... attachmentFiles) throws Exception {
        if(attachmentFiles!=null && attachmentFiles.length>0){
            String [] ss = new String[attachmentFiles.length];
            int i=0;
            for(File f: attachmentFiles){
                ss[i]=f.getAbsolutePath();
                ++i;
            }
            return parseToDocument(inputFileName.getAbsolutePath(),ss);
        }
        else
            return parseToDocument(inputFileName.getAbsolutePath());
    }

}
