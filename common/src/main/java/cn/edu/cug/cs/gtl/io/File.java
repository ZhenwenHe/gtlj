package cn.edu.cug.cs.gtl.io;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class File extends java.io.File {


    private static final long serialVersionUID = 572161537787747783L;

    /**
     * 判断文件或目录是否存在
     *
     * @param fileName
     * @return
     */
    public static boolean exists(final String fileName) {
        return new File(fileName).exists();
    }

    /**
     * @param fullPathName 完全名称，例如： usr/he/datafile.csv
     * @return 返回不含路径的文件，datafile.csv
     */
    public static String getFileName(String fullPathName) {
        if (fullPathName == null) return "";
        if (fullPathName.isEmpty()) return "";

        int i = fullPathName.lastIndexOf(java.io.File.separatorChar);
        if (i >= 0)
            return fullPathName.substring(i + 1);
        else {
            if (java.io.File.separator.equals("\\")) {
                i = fullPathName.lastIndexOf("/");
            } else {
                i = fullPathName.lastIndexOf("\\");
            }
            if (i >= 0)
                return fullPathName.substring(i + 1);
            else
                return "";
        }
    }

    /**
     * @param fullPathName 完全名称，例如： usr/he/datafile.csv
     * @return 返回不含路径、不含后缀的文件，datafile
     */
    public static String getFileNameWithoutSuffix(String fullPathName) {
        String fileName = getFileName(fullPathName);

        int i = fileName.lastIndexOf('.');
        if (i >= 0)
            return fileName.substring(0, i);
        else
            return fileName;
    }

    /**
     * @param fullPathName 完全名称，例如： /usr/he/datafile.csv
     * @return 返回
     * /usr/he
     * datafile
     * csv
     */
    public static String[] splitFileName(String fullPathName) {
        if (fullPathName == null) return null;
        if (fullPathName.isEmpty()) return null;
        String[] rs = new String[3];
        rs[0] = getDirectory(fullPathName);
        rs[1] = getFileNameWithoutSuffix(fullPathName);
        rs[2] = getSuffixName(fullPathName);
        return rs;
    }

    /**
     * @param fullPathName 完全名称，例如： usr/he/datafile.csv
     * @return 返回目录 /usr/he
     */
    public static String getDirectory(String fullPathName) {
        if (fullPathName == null) return "";
        if (fullPathName.isEmpty()) return "";

        int i = fullPathName.lastIndexOf(java.io.File.separatorChar);
        if (i >= 0)
            return fullPathName.substring(0, i);
        else {
            if (java.io.File.separator.equals("\\")) {
                i = fullPathName.lastIndexOf("/");
            } else {
                i = fullPathName.lastIndexOf("\\");
            }
            if (i >= 0)
                return fullPathName.substring(0, i);
            else
                return "";
        }
    }

    /**
     * 返回文件后缀名
     *
     * @param fullPathName 完全名称，例如： usr/he/datafile.csv
     * @return 返回不含路径的文件，csv
     */
    public static String getSuffixName(String fullPathName) {
        if (fullPathName == null) return "";
        if (fullPathName.isEmpty()) return "";

        int i = fullPathName.lastIndexOf('.');
        if (i >= 0)
            return fullPathName.substring(i + 1);
        else
            return "";
    }

    /**
     * @param fullPathName 完全名称，例如： usr/he/datafile.csv
     * @param newSuffix    新的文件名后缀，svm
     * @return usr/he/datafile.svm
     */
    public static String replaceSuffixName(String fullPathName, String newSuffix) {
        if (fullPathName == null) return "";
        if (fullPathName.isEmpty()) return "";

        int i = fullPathName.lastIndexOf('.');
        if (i >= 0)
            return fullPathName.substring(0, i + 1) + newSuffix;
        else
            return "";
    }

    /**
     * Creates a new <code>File</code> instance by converting the given
     * pathname string into an abstract pathname.  If the given string is
     * the empty string, then the result is the empty abstract pathname.
     *
     * @param pathname A pathname string
     * @throws NullPointerException If the <code>pathname</code> argument is <code>null</code>
     */
    public File(@NotNull String pathname) {
        super(pathname);
    }

    /**
     * Creates a new <code>File</code> instance from a parent pathname string
     * and a child pathname string.
     * <p>
     * <p> If <code>parent</code> is <code>null</code> then the new
     * <code>File</code> instance is created as if by invoking the
     * single-argument <code>File</code> constructor on the given
     * <code>child</code> pathname string.
     * <p>
     * <p> Otherwise the <code>parent</code> pathname string is taken to denote
     * a directory, and the <code>child</code> pathname string is taken to
     * denote either a directory or a file.  If the <code>child</code> pathname
     * string is absolute then it is converted into a relative pathname in a
     * system-dependent way.  If <code>parent</code> is the empty string then
     * the new <code>File</code> instance is created by converting
     * <code>child</code> into an abstract pathname and resolving the result
     * against a system-dependent default directory.  Otherwise each pathname
     * string is converted into an abstract pathname and the child abstract
     * pathname is resolved against the parent.
     *
     * @param parent The parent pathname string
     * @param child  The child pathname string
     * @throws NullPointerException If <code>child</code> is <code>null</code>
     */
    public File(String parent, @NotNull String child) {
        super(parent, child);
    }

    /**
     * Creates a new <code>File</code> instance from a parent abstract
     * pathname and a child pathname string.
     * <p>
     * <p> If <code>parent</code> is <code>null</code> then the new
     * <code>File</code> instance is created as if by invoking the
     * single-argument <code>File</code> constructor on the given
     * <code>child</code> pathname string.
     * <p>
     * <p> Otherwise the <code>parent</code> abstract pathname is taken to
     * denote a directory, and the <code>child</code> pathname string is taken
     * to denote either a directory or a file.  If the <code>child</code>
     * pathname string is absolute then it is converted into a relative
     * pathname in a system-dependent way.  If <code>parent</code> is the empty
     * abstract pathname then the new <code>File</code> instance is created by
     * converting <code>child</code> into an abstract pathname and resolving
     * the result against a system-dependent default directory.  Otherwise each
     * pathname string is converted into an abstract pathname and the child
     * abstract pathname is resolved against the parent.
     *
     * @param parent The parent abstract pathname
     * @param child  The child pathname string
     * @throws NullPointerException If <code>child</code> is <code>null</code>
     */
    public File(java.io.File parent, @NotNull String child) {
        super(parent, child);
    }

    /**
     * Creates a new <tt>File</tt> instance by converting the given
     * <tt>file:</tt> URI into an abstract pathname.
     * <p>
     * <p> The exact form of a <tt>file:</tt> URI is system-dependent, hence
     * the transformation performed by this constructor is also
     * system-dependent.
     * <p>
     * <p> For a given abstract pathname <i>f</i> it is guaranteed that
     * <p>
     * <blockquote><tt>
     * new File(</tt><i>&nbsp;f</i><tt>.{@link #toURI() toURI}()).equals(</tt><i>&nbsp;f</i><tt>.{@link #getAbsoluteFile() getAbsoluteFile}())
     * </tt></blockquote>
     * <p>
     * so long as the original abstract pathname, the URI, and the new abstract
     * pathname are all created in (possibly different invocations of) the same
     * Java virtual machine.  This relationship typically does not hold,
     * however, when a <tt>file:</tt> URI that is created in a virtual machine
     * on one operating system is converted into an abstract pathname in a
     * virtual machine on a different operating system.
     *
     * @param uri An absolute, hierarchical URI with a scheme equal to
     *            <tt>"file"</tt>, a non-empty path component, and undefined
     *            authority, query, and fragment components
     * @throws NullPointerException     If <tt>uri</tt> is <tt>null</tt>
     * @throws IllegalArgumentException If the preconditions on the parameter do not hold
     * @see #toURI()
     * @see URI
     * @since 1.4
     */
    public File(@NotNull URI uri) {
        super(uri);
    }


    /**
     * read all text lines from files and write into the string list
     *
     * @param files
     * @return
     */
    public static List<String> readTextLines(java.io.File[] files) {
        try {
            ArrayList<String> ss = new ArrayList<>();
            for (java.io.File f : files) {
                if (f.isFile()) {
                    //System.out.println(f.getAbsolutePath());
                    BufferedReader r = new BufferedReader(new FileReader(f));
                    Object[] objects = r.lines().toArray();
                    for (Object o : objects) {
                        ss.add((String) o);
                    }
                }
            }
//            for(String s : ss){
//                System.out.println(s);
//            }
            return ss;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * read all text lines from file and write into the string list
     *
     * @param file
     * @return
     */
    public static List<String> readTextLines(java.io.File file) {
        try {
            ArrayList<String> ss = new ArrayList<>();
            if (file.isFile()) {
                BufferedReader r = new BufferedReader(new FileReader(file));
                Object[] objects = r.lines().toArray();
                for (Object o : objects) {
                    ss.add((String) o);
                }
            }
            return ss;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * read all text files in tha path,and write into a string list
     *
     * @param path
     * @return
     */
    public static List<String> readTextLines(String path) {
        java.io.File[] files = new java.io.File(path).listFiles();
        return readTextLines(files);
    }

    /**
     * 返回目录下所有的后缀名为suffix的文件名（全名）
     *
     * @param inputPath suffix
     *                  null 返回所有的文件和目录
     *                  *.*  返回所有的文件（不含目录）
     *                  *.doc  返回所有的doc文档
     *                  *.ppt  返回所有的ppt文档
     * @param suffix    文件后缀名
     * @return 返回目录下所有的后缀名为suffix的文件名（全名）
     */
    public static List<String> listFiles(String inputPath, String suffix) {
        List<java.io.File> files = new ArrayList<>();
        if (suffix == null) {
            getAllFiles(new java.io.File(inputPath), files, true);
            List<String> ls = new ArrayList<>();
            for (java.io.File f : files) {
                ls.add(f.getAbsolutePath());
            }
            return ls;
        } else if (suffix.equals("*.*")) {
            getAllFiles(new java.io.File(inputPath), files, false);
            List<String> ls = new ArrayList<>();
            for (java.io.File f : files) {
                ls.add(f.getAbsolutePath());
            }
            return ls;
        } else {
            final String suffixLower = getSuffixName(suffix);
            getAllFiles(new java.io.File(inputPath), files, false);
            List<String> ls = new ArrayList<>();
            for (java.io.File f : files) {
                ls.add(f.getAbsolutePath());
            }

            return ls.stream()
                    .filter((s) -> getSuffixName(s).toLowerCase().equals(suffixLower))
                    .collect(Collectors.toList());

        }
    }

    /**
     * CALL BY PUBLIC FUNCTION getAllFiles()
     *
     * @param inputPath  目录
     * @param includeDir 是否包含目录
     * @param ls
     */
    private static void getAllFiles(java.io.File inputPath, List<java.io.File> ls, boolean includeDir) {
        java.io.File[] fs = inputPath.listFiles();
        for (java.io.File f : fs) {
            if (f.isDirectory()) {//若是目录，则递归打印该目录下的文件
                if (includeDir)
                    ls.add(f);
                getAllFiles(f, ls, includeDir);
            }
            if (f.isFile()) {        //若是文件，直接打印
                ls.add(f);
            }
        }
    }

}
