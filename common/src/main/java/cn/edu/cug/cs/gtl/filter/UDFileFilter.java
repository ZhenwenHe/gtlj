package cn.edu.cug.cs.gtl.filter;

import cn.edu.cug.cs.gtl.filter.FileFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class UDFileFilter implements FileFilter {
    List<String> suffixList;
    boolean orOperation;


    public UDFileFilter(boolean orOperation,String... suffix) {
        this.suffixList = new ArrayList<>();
        for(String s: suffix){
            this.suffixList.add(s.trim().toLowerCase());
        }
        this.orOperation = orOperation;
    }
    /**
     * Tests whether or not the specified abstract pathname should be
     * included in a pathname list.
     *
     * @param pathname The abstract pathname to be tested
     * @return <code>true</code> if and only if <code>pathname</code>
     * should be included
     */
    @Override
    public boolean accept(File pathname) {
        String fileName = pathname.getName();
        if(orOperation){
            for(String f: this.suffixList){
                if(fileName.endsWith(f))
                    return true;
            }
            return false;
        }
        else {
            for(String f: this.suffixList){
                if(!fileName.endsWith(f))
                    return false;
            }
            return true;
        }
    }
}
