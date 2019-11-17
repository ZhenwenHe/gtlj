package cn.edu.cug.cs.gtl.lucene.demo;

import cn.edu.cug.cs.gtl.config.Config;
import cn.edu.cug.cs.gtl.io.File;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.pipeline.TextAnnotationCreator;
import edu.stanford.nlp.util.CoreMap;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class AnalyzerTest {



    @Test
    public void analyzerStandard(){
        String enText = "Analysis is one of the main causes of slow indexing. Simply put, the more you analyze the slower analyze the indexing (in most cases).";
        String cnText = "何珍文说的话很好理解";

        try (Analyzer a = new StandardAnalyzer();){
            TokenStream ts = a.tokenStream("standard_en",enText);
            doToken(ts);
            ts = a.tokenStream("standard_cn",cnText);
            doToken(ts);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void analyzerSmartCN(){
        String enText = "Analysis is one of the main causes of slow indexing. Simply put, the more you analyze the slower analyze the indexing (in most cases).";
        String cnText = "何珍文说的话很好理解。如果你还不明白，那就去查相关文档。";

        try (Analyzer a = new SmartChineseAnalyzer();){
            TokenStream ts = a.tokenStream("SmartCN_en",enText);
            doToken(ts);
            ts = a.tokenStream("SmartCN_cn",cnText);
            doToken(ts);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void analyzerCJK(){
        String enText = "Analysis is one of the main causes of slow indexing. Simply put, the more you analyze the slower analyze the indexing (in most cases).";
        String cnText = "何珍文说的话很好理解。如果你还不明白，那就去查相关文档。";

        try (Analyzer a = new CJKAnalyzer();){
            TokenStream ts = a.tokenStream("CJK_en",enText);
            doToken(ts);
            ts = a.tokenStream("CJK_cn",cnText);
            doToken(ts);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void analyzerStandfordNLP(){
        String enText = "Analysis is one of the main causes of slow indexing. Simply put, the more you analyze the slower analyze the indexing (in most cases).";
        String cnText = "何珍文说的话很好理解。如果你还不明白，那就去查相关文档。";



        try {
            Properties props = new Properties();
            props.load(IOUtils.readerFromString("StanfordCoreNLP-chinese.properties"));
            StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
            //创建一个解析器，传入的是需要解析的文本
            TextAnnotationCreator textAnnotationCreator = new TextAnnotationCreator();
            Annotation cna = textAnnotationCreator.createFromText(cnText);
            pipeline.annotate(cna);

            //根据标点符号，进行句子的切分，每一个句子被转化为一个CoreMap的数据结构，保存了句子的信息()
            List<CoreMap> sentences = cna.get(CoreAnnotations.SentencesAnnotation.class);
            //从CoreMap 中取出CoreLabel List ,打印
            for (CoreMap sentence : sentences){
                for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)){
                    String word = token.get(CoreAnnotations.TextAnnotation.class);
                    System.out.println(word);
                }
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void doToken(TokenStream ts) throws IOException {
        ts.reset();
        CharTermAttribute cta = ts.getAttribute(CharTermAttribute.class);
        while (ts.incrementToken()) {
            System.out.print(cta.toString() + "|");
        }
        System.out.println();
        ts.end();
        ts.close();
    }

}