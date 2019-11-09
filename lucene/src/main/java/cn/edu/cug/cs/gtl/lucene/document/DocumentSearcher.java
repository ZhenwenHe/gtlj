package cn.edu.cug.cs.gtl.lucene.document;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.xml.builders.BooleanQueryBuilder;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

public class DocumentSearcher {
    private String indexDirectory="index";
    private Analyzer analyzer=new StandardAnalyzer();
    private IndexSearcher searcher = null;
    private int topNumber=5;

    /**
     * 词条查询
     * @param fieldName
     * @param queryString
     * @return
     */
    public TopDocs termQuery(String fieldName, String  queryString)throws Exception{

        TermQuery q = new TermQuery(new Term(fieldName,queryString));
        return searcher.search(q, topNumber);
    }

    /**
     *通配符查询
     * @param fieldName
     * @param queryString
     * @return
     * @throws Exception
     */
    public TopDocs wildcardQuery(String fieldName, String  queryString)throws Exception{
        WildcardQuery q = new WildcardQuery(new Term(fieldName,queryString));
        return searcher.search(q,topNumber);
    }

    /**
     *前缀查询
     * @param fieldName
     * @param queryString
     * @return
     * @throws Exception
     */
    public TopDocs prefixQuery(String fieldName, String  queryString)throws Exception{
        PrefixQuery q = new PrefixQuery(new Term(fieldName,queryString));
        return searcher.search(q,topNumber);
    }

    /**
     *模糊查询
     * @param fieldName
     * @param queryString
     * @param maxEdits
     * @param prefixLength
     * @return
     * @throws Exception
     */
    public TopDocs fuzzyQuery(String fieldName, String  queryString,int maxEdits, int prefixLength)throws Exception{
        FuzzyQuery q = new FuzzyQuery(new Term(fieldName,queryString),maxEdits, prefixLength);
        return searcher.search(q,topNumber);
    }

    /**
     *
     * @param fieldName1
     * @param queryString1
     * @param not_and_or
     * @param fieldName2
     * @param queryString2
     * @return
     * @throws Exception
     */
    public TopDocs booleanQuery(String fieldName1, String  queryString1,
                                String not_and_or, //not and or
                                String fieldName2, String  queryString2)throws Exception{
        BooleanQuery.Builder builder  = new BooleanQuery.Builder();
        String op = not_and_or.trim().toLowerCase();
        TermQuery tq1= new TermQuery(new Term(fieldName1,queryString1));
        TermQuery tq2= new TermQuery(new Term(fieldName2,queryString2));
        if(op.equals("not")){
            builder.add(new BooleanClause(tq1, BooleanClause.Occur.MUST));
            builder.add(new BooleanClause(tq2, BooleanClause.Occur.MUST_NOT));
        }
        else if(op.equals("or")){
            builder.add(new BooleanClause(tq1, BooleanClause.Occur.SHOULD));
            builder.add(new BooleanClause(tq2, BooleanClause.Occur.SHOULD));
        }
        else if(op.equals("and")){
            builder.add(new BooleanClause(tq1, BooleanClause.Occur.MUST));
            builder.add(new BooleanClause(tq2, BooleanClause.Occur.MUST));
        }
        else {
            throw new Exception("booleanQuery not_and_or parameter is not right");
        }
        return searcher.search(builder.build(),topNumber);
    }



    public static DocumentSearcher of (String indexDirectory) {
        try {
            DocumentSearcher documentSearcher=  new DocumentSearcher(indexDirectory);
            IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexDirectory)));
            documentSearcher.searcher = new IndexSearcher(reader);
            return documentSearcher;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public int getTopNumber() {
        return topNumber;
    }

    public void setTopNumber(int topNumber) {
        this.topNumber = topNumber;
    }

    public void output(TopDocs results){
        try {
            ScoreDoc[] hits = results.scoreDocs;
            int numTotalHits = Math.toIntExact(results.totalHits.value);
            System.out.println(numTotalHits + " total matching documents");
            int start=0;
            int end=Math.min(numTotalHits,hits.length);
            boolean raw=true;

            for (int i = start; i < end; i++) {
                if (raw) {                              // output raw format
                    System.out.println("doc=" + hits[i].doc + " score=" + hits[i].score);
                }

                Document doc = getSearcher().doc(hits[i].doc);
                String path = doc.get("path");
                if (path != null) {
                    System.out.println((i + 1) + ". " + path);
                    String title = doc.get("text");
                    if (title != null) {
                        System.out.println("   text: " + doc.get("text"));
                    }
                } else {
                    System.out.println((i + 1) + ". " + "No path for this document");
                }

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public Analyzer getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(Analyzer analyzer) {
        this.analyzer = analyzer;
    }

    public IndexSearcher getSearcher() {
        return searcher;
    }

    private DocumentSearcher(String indexDirectory) {
        this.indexDirectory = indexDirectory;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    public void execute(String queryString) throws Exception{
        String [] args = new String[4];
        args[0]="-index";
        args[1]=indexDirectory;
        args[2]="-query";
        args[3]=queryString;
        run(args);
    }
    /**
     * Simple command-line based search demo.
     */
    public static void run(String[] args) throws Exception {
        String usage =
                "Usage:\tjava SearchFiles [-index dir] [-field f] [-repeat n] [-queries file] [-query string] [-raw] [-paging hitsPerPage]\n\nSee http://lucene.apache.org/core/4_1_0/demo/ for details.";
        if (args.length > 0 && ("-h".equals(args[0]) || "-help".equals(args[0]))) {
            System.out.println(usage);
            System.exit(0);
        }

        String index = "index";
        String field = "contents";
        String queries = null;
        int repeat = 0;
        boolean raw = false;
        String queryString = null;
        int hitsPerPage = 10;

        for (int i = 0; i < args.length; i++) {
            if ("-index".equals(args[i])) {
                index = args[i + 1];
                i++;
            } else if ("-field".equals(args[i])) {
                field = args[i + 1];
                i++;
            } else if ("-queries".equals(args[i])) {
                queries = args[i + 1];
                i++;
            } else if ("-query".equals(args[i])) {
                queryString = args[i + 1];
                i++;
            } else if ("-repeat".equals(args[i])) {
                repeat = Integer.parseInt(args[i + 1]);
                i++;
            } else if ("-raw".equals(args[i])) {
                raw = true;
            } else if ("-paging".equals(args[i])) {
                hitsPerPage = Integer.parseInt(args[i + 1]);
                if (hitsPerPage <= 0) {
                    System.err.println("There must be at least 1 hit per page.");
                    System.exit(1);
                }
                i++;
            }
        }

        IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
        IndexSearcher searcher = new IndexSearcher(reader);
        Analyzer analyzer = new StandardAnalyzer();

        BufferedReader in = null;
        if (queries != null) {
            in = Files.newBufferedReader(Paths.get(queries), StandardCharsets.UTF_8);
        } else {
            in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        }
        QueryParser parser = new QueryParser(field, analyzer);
        while (true) {
            if (queries == null && queryString == null) {                        // prompt the user
                System.out.println("Enter query: ");
            }

            String line = queryString != null ? queryString : in.readLine();

            if (line == null || line.length() == -1) {
                break;
            }

            line = line.trim();
            if (line.length() == 0) {
                break;
            }

            Query query = parser.parse(line);
            System.out.println("Searching for: " + query.toString(field));

            if (repeat > 0) {                           // repeat & time as benchmark
                Date start = new Date();
                for (int i = 0; i < repeat; i++) {
                    searcher.search(query, 100);
                }
                Date end = new Date();
                System.out.println("Time: " + (end.getTime() - start.getTime()) + "ms");
            }

            doPagingSearch(in, searcher, query, hitsPerPage, raw, queries == null && queryString == null);

            if (queryString != null) {
                break;
            }
        }
        reader.close();
    }

    /**
     * This demonstrates a typical paging search scenario, where the search engine presents
     * pages of size n to the user. The user can then go to the next page if interested in
     * the next hits.
     * <p>
     * When the query is executed for the first time, then only enough results are collected
     * to fill 5 result pages. If the user wants to page beyond this limit, then the query
     * is executed another time and all hits are collected.
     */
    public static void doPagingSearch(BufferedReader in, IndexSearcher searcher, Query query,
                                      int hitsPerPage, boolean raw, boolean interactive) throws IOException {

        // Collect enough docs to show 5 pages
        TopDocs results = searcher.search(query, 5 * hitsPerPage);
        ScoreDoc[] hits = results.scoreDocs;

        int numTotalHits = Math.toIntExact(results.totalHits.value);
        System.out.println(numTotalHits + " total matching documents");

        int start = 0;
        int end = Math.min(numTotalHits, hitsPerPage);

        while (true) {
            if (end > hits.length) {
                System.out.println("Only results 1 - " + hits.length + " of " + numTotalHits + " total matching documents collected.");
                System.out.println("Collect more (y/n) ?");
                String line = in.readLine();
                if (line.length() == 0 || line.charAt(0) == 'n') {
                    break;
                }

                hits = searcher.search(query, numTotalHits).scoreDocs;
            }

            end = Math.min(hits.length, start + hitsPerPage);

            for (int i = start; i < end; i++) {
                if (raw) {                              // output raw format
                    System.out.println("doc=" + hits[i].doc + " score=" + hits[i].score);
                    continue;
                }

                Document doc = searcher.doc(hits[i].doc);
                String path = doc.get("path");
                if (path != null) {
                    System.out.println((i + 1) + ". " + path);
                    String title = doc.get("text");
                    if (title != null) {
                        System.out.println("   text: " + doc.get("text"));
                    }
                } else {
                    System.out.println((i + 1) + ". " + "No path for this document");
                }

            }

            if (!interactive || end == 0) {
                break;
            }

            if (numTotalHits >= end) {
                boolean quit = false;
                while (true) {
                    System.out.print("Press ");
                    if (start - hitsPerPage >= 0) {
                        System.out.print("(p)revious page, ");
                    }
                    if (start + hitsPerPage < numTotalHits) {
                        System.out.print("(n)ext page, ");
                    }
                    System.out.println("(q)uit or enter number to jump to a page.");

                    String line = in.readLine();
                    if (line.length() == 0 || line.charAt(0) == 'q') {
                        quit = true;
                        break;
                    }
                    if (line.charAt(0) == 'p') {
                        start = Math.max(0, start - hitsPerPage);
                        break;
                    } else if (line.charAt(0) == 'n') {
                        if (start + hitsPerPage < numTotalHits) {
                            start += hitsPerPage;
                        }
                        break;
                    } else {
                        int page = Integer.parseInt(line);
                        if ((page - 1) * hitsPerPage < numTotalHits) {
                            start = (page - 1) * hitsPerPage;
                            break;
                        } else {
                            System.out.println("No such page");
                        }
                    }
                }
                if (quit) break;
                end = Math.min(numTotalHits, start + hitsPerPage);
            }
        }
    }
}
