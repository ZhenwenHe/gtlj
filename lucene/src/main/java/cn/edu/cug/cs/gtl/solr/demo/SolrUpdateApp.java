package cn.edu.cug.cs.gtl.solr.demo;

import cn.edu.cug.cs.gtl.config.Config;
import cn.edu.cug.cs.gtl.filter.FileFilter;
import cn.edu.cug.cs.gtl.protoswrapper.DocumentMapperWrapper;
import cn.edu.cug.cs.gtl.solr.document.DocumentCreator;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class SolrUpdateApp {

    static String rawDir = Config.getTestInputDirectory()+ cn.edu.cug.cs.gtl.io.File.separator+"lucene"+ cn.edu.cug.cs.gtl.io.File.separator
            +"dat"+ cn.edu.cug.cs.gtl.io.File.separator+"raw";

    static String idxDir = Config.getTestInputDirectory()+ cn.edu.cug.cs.gtl.io.File.separator+"lucene"+ cn.edu.cug.cs.gtl.io.File.separator
            +"dat"+ cn.edu.cug.cs.gtl.io.File.separator+"inx";


    public static void main(String [] args){

        String serverURL = "http://120.24.168.173:8983/solr";
        SolrClient solrClient = new HttpSolrClient
                .Builder()
                .withBaseSolrUrl(serverURL)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();

        try {
            List<SolrInputDocument> docs = DocumentCreator
                    .of(rawDir, FileFilter.textsFileFilter(), DocumentMapperWrapper.fileMapper())
                    .execute();

            for(SolrInputDocument s: docs){
                final UpdateResponse updateResponse = solrClient.add("gtl", s);
                // Indexed documents must be committed
                solrClient.commit("gtl");
                System.out.println(updateResponse.jsonStr());
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }


    }
}
