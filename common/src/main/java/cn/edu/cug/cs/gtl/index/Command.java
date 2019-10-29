package cn.edu.cug.cs.gtl.index;

/**
 * Created by ZhenwenHe on 2016/12/6.
 */
public interface Command extends java.io.Serializable {

    void execute(Node in);
}
