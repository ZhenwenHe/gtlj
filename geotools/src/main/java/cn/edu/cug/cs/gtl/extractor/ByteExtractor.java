package cn.edu.cug.cs.gtl.extractor;

import cn.edu.cug.cs.gtl.io.File;
import com.google.protobuf.ByteString;

import java.nio.ByteBuffer;

public class ByteExtractor {
    /**
     *
     * @param inputFile
     * @return
     * @throws Exception
     */
    public static ByteBuffer parseToByteBuffer(String inputFile) throws Exception{
        return File.readRawFile(inputFile);
    }

    /**
     *
     * @param inputFile
     * @return
     * @throws Exception
     */
    public static ByteString parseToByteString(String inputFile) throws Exception{
        return ByteString.copyFrom(File.readRawFile(inputFile).array());
    }

    /**
     *
     * @param inputFile
     * @return
     * @throws Exception
     */
    public static byte[] parseToBytes(String inputFile) throws Exception{
        return  File.readRawFile(inputFile).array();
    }


}
