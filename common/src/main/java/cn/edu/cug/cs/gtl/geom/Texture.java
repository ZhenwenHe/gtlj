package cn.edu.cug.cs.gtl.geom;

import cn.edu.cug.cs.gtl.io.DataContent;
import cn.edu.cug.cs.gtl.util.StringUtils;
import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.common.Status;
import cn.edu.cug.cs.gtl.io.DataContent;
import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.util.StringUtils;

import java.io.*;
import java.util.Arrays;

public class Texture implements DataContent {
    public static final long serialVersionUID = 1L;
    /**
     * 纹理ID
     */
    private long textureID;
    /**
     * 纹理名称
     */
    private String textureName;
    /**
     * 纹理类型
     */
    private int textureType;
    /**
     * 纹理内存数据
     */
    private byte[] data;
    /**
     * 纹理WRAP模式
     */
    private int wrapMode;

    private Status status;


    /**
     * 纹理类型
     */
    public static final int TEXTURE_IMAGE_BMP = 0;
    public static final int TEXTURE_IMAGE_DDS = 2; // 为兼用一些老数据，1也为DDS，并且默认的为DDS
    public static final int TEXTURE_IMAGE_TGA = 3;
    public static final int TEXTURE_IMAGE_TIF = 4;
    public static final int TEXTURE_IMAGE_JPG = 5;
    public static final int TEXTURE_IMAGE_PNG = 6;
    public static final int TEXTURE_IMAGE_GIF = 7;
    public static final int TEXTURE_IMAGE_MNG = 8;

    /**
     * WRAP模式
     */
    public static final int WRAP_MODE_CLAMP = 0x2900;
    public static final int WRAP_MODE_REPEAT = 0x2901;

    /**
     * 获得纹理WRAP模式
     *
     * @return：纹理WRAP模式
     */
    public int getWrapMode() {
        return wrapMode;
    }

    /**
     * 设置纹理WRAP模式
     *
     * @param wrapMode：纹理WRAP模式
     */
    public void setWrapMode(int wrapMode) {
        this.wrapMode = wrapMode;
    }

    /**
     * 获得纹理内存数据
     *
     * @return：纹理内存数据
     */
    public byte[] getData() {
        return data;
    }

    /**
     * 设置纹理内存数据
     *
     * @param data：纹理内存数据
     */
    public void setData(byte[] data) {
        this.data = data;
    }

    /**
     * 获得纹理内存数据大小
     *
     * @return：纹理内存数据大小
     */
    public int getDataSize() {
        if (data != null)
            return data.length;
        else return 0;
    }

    /**
     * 获得纹理类型
     *
     * @return：纹理类型
     */
    public int getTextureType() {
        return textureType;
    }

    /**
     * 设置纹理类型
     *
     * @return：纹理类型
     */
    public void setTextureType(int textureType) {
        this.textureType = textureType;
    }

    /**
     * 获得纹理名称
     *
     * @return：纹理名称
     */
    public String getTextureName() {
        return textureName;
    }

    /**
     * 设置纹理名称
     *
     * @return：纹理名称
     */
    public void setTextureName(String textureName) {
        this.textureName = textureName;
    }

    /**
     * 获得纹理ID
     *
     * @return：纹理名称
     */
    public void setTextureID(long textureID) {
        this.textureID = textureID;
    }

    /**
     * 设置纹理ID
     *
     * @return：纹理名称
     */
    public long getTextureID() {
        return textureID;
    }

    /**
     * Texture无参构造函数
     */
    public Texture() {
        textureID = Identifier.create().longValue();
        /** 纹理名称 */
        textureName = null;
        /** 纹理类型 */
        textureType = TEXTURE_IMAGE_BMP;
        /** 纹理内存数据 */
        data = null;
        /** 纹理WRAP模式 */
        wrapMode = WRAP_MODE_REPEAT;
        this.status = new Status();
    }

    /**
     * Texture有参构造函数
     *
     * @param fileName：纹理的文件名
     */
    public Texture(String fileName) {
        textureID = Identifier.create().longValue();
        /** 纹理名称 */
        textureName = null;
        /** 纹理类型 */
        textureType = TEXTURE_IMAGE_BMP;
        /** 纹理内存数据 */
        data = null;
        /** 纹理WRAP模式 */
        wrapMode = WRAP_MODE_REPEAT;
        this.status = new Status();
        loadFile(fileName);
    }

    /**
     * 拷贝本对象内容到其他同类对象
     *
     * @return:Object
     */
    @Override
    public Object clone() {
        Texture t = new Texture();
        /** 纹理ID */
        t.textureID = this.textureID;
        /** 纹理名称 */
        if (textureName != null)
            t.textureName = new String(this.textureName);
        /** 纹理类型 */
        t.textureType = this.textureType;
        /** 纹理内存数据 */
        if (data != null)
            t.data = Arrays.copyOf(this.data, this.data.length);
        /** 纹理WRAP模式 */
        t.wrapMode = this.wrapMode;
        t.status = (Status) t.status.clone();
        return t;
    }

    /**
     * 从存储对象中加载数据，填充本对象
     *
     * @param in:表示可以读取的存储对象，可能是内存、文件、管道等
     * @throws IOException
     * @return:true or false
     */
    @Override
    public boolean load(DataInput in) throws IOException {
        /** 纹理ID */
        textureID = in.readLong();
        /** 纹理名称 */
        textureName = StringUtils.load(in);
        /** 纹理类型 */
        textureType = in.readInt();
        /** 纹理内存数据 */
        int s = in.readInt();
        if (s > 0) {
            data = new byte[s];
            in.readFully(data);
        }
        wrapMode = in.readInt();
        this.status.load(in);
        return true;
    }

    /**
     * 将本对象写入存储对象中，存储对象可能是内存、文件、管道等
     *
     * @param out:表示可以写入的存储对象，可能是内存、文件、管道等
     * @throws IOException
     * @return:true or false
     */
    @Override
    public boolean store(DataOutput out) throws IOException {
        /** 纹理ID */
        out.writeLong(textureID);
        /** 纹理名称 */
        StringUtils.store(textureName, out);
        /** 纹理类型 */
        out.writeInt(textureType);
        /** 纹理内存数据 */
        int s = getDataSize();
        out.writeInt(s);
        if (s > 0)
            out.write(data);
        out.writeInt(wrapMode);
        this.status.store(out);
        return true;
    }

    /**
     * 对象序列化后的字节数，
     * 默认实现为将其写入一个字节数组中，然后返回该字节数
     *
     * @return:字节数
     */
    @Override
    public long getByteArraySize() {
        long s = (this.data == null ? 0 : this.data.length);
        s += this.status.getByteArraySize();
        return s + 8 + StringUtils.getByteArraySize(textureName) + 4 + 4 + 4;
    }

    /**
     * 从存储文件中加载数据，填充本对象
     *
     * @param fileName:表示可以读取的存储文件名
     * @throws IOException
     * @return:true or false
     */
    private boolean loadFile(String fileName) {
        String szext = fileName.substring(fileName.lastIndexOf('.') + 1).toUpperCase();
        if (szext.equals("BMP")) {
            setTextureType(TEXTURE_IMAGE_BMP);
        } else if (szext.equals("DDS")) {
            setTextureType(TEXTURE_IMAGE_DDS);
        } else if (szext.equals("TGA")) {
            setTextureType(TEXTURE_IMAGE_TGA);
        } else if (szext.equals("TIF")) {
            setTextureType(TEXTURE_IMAGE_TIF);
        } else if (szext.equals("JPG")) {
            setTextureType(TEXTURE_IMAGE_JPG);
        } else if (szext.equals("PNG")) {
            setTextureType(TEXTURE_IMAGE_PNG);
        } else if (szext.equals("GIF")) {
            setTextureType(TEXTURE_IMAGE_GIF);
        } else if (szext.equals("MNG")) {
            setTextureType(TEXTURE_IMAGE_MNG);
        } else {
            return false;
        }
        String tname = fileName.substring(fileName.lastIndexOf('/') + 1);
        tname = tname.substring(tname.lastIndexOf('\\') + 1);
        tname = tname.substring(0, tname.lastIndexOf('.'));
        setTextureName(tname);
        try {
            File f = new File(fileName);
            long s = f.length();
            this.data = new byte[(int) s];
            FileInputStream fis = new FileInputStream(fileName);
            fis.read(this.data, 0, (int) s);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 获得纹理标识符
     *
     * @return：纹理标识符
     */
    @Override
    public Identifier getIdentifier() {
        return Identifier.create(this.textureID);
    }

    /**
     * 获得纹理名称
     *
     * @return：纹理名称
     */
    @Override
    public String getName() {
        return getTextureName();
    }

    /**
     * 获得纹理状态
     *
     * @return：纹理状态
     */
    @Override
    public Status getStatus() {
        return this.status;
    }
}
