package cn.edu.cug.cs.gtl.index;


import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.io.Serializable;

/**
 * Created by ZhenwenHe on 2016/12/6.
 */
public interface QueryStrategy extends Serializable {
    /**
     * @param previouslyFetched 前一个获取的Entry，传入的值不会被改变
     * @param nextEntryToFetch  传入，并返回下一个ID
     * @param bFetchNextEntry   传入，并返回
     */
    void getNextEntry(Entry previouslyFetched, Identifier nextEntryToFetch, Boolean bFetchNextEntry);
}
