package cn.edu.cug.cs.gtl.extractor;


import java.io.IOException;

import cn.edu.cug.cs.gtl.io.File;
import cn.edu.cug.cs.gtl.protos.Attachment;
import cn.edu.cug.cs.gtl.protos.Attachments;
import cn.edu.cug.cs.gtl.protos.Document;

public class DocumentExtractor {

    /**
     *
     * @param inputFileName
     * @return
     * @throws IOException
     */
    public static Document parseToDocument(String inputFileName) throws Exception{
        Document.Builder b = Document.newBuilder();
        b.setTitle(File.getFileNameWithoutSuffix(inputFileName));
        b.setType(File.getSuffixName(inputFileName));
        String [] ss = TextExtractor.parseToStrings(inputFileName);
        for(String s: ss){
            b.addContent(s);
        }
        return b.build();
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
        Document.Builder b = Document.newBuilder();
        b.setTitle(File.getFileNameWithoutSuffix(inputFileName));
        b.setType(File.getSuffixName(inputFileName));
        String [] ss = TextExtractor.parseToStrings(inputFileName);
        for(String s: ss){
            b.addContent(s);
        }
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
