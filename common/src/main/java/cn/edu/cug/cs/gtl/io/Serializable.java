package cn.edu.cug.cs.gtl.io;

import cn.edu.cug.cs.gtl.common.Variant;

import java.io.*;

/**
 * Created by ZhenwenHe on 2016/12/8.
 * <p>
 * Serializable接口，实现对象的序列化与反序列化;
 * 这个接口的名字与java.io.Serializable同名，容易混淆
 * 因此后将废除，采用Storable替换
 */
@Deprecated
public interface Serializable extends Storable {

}
