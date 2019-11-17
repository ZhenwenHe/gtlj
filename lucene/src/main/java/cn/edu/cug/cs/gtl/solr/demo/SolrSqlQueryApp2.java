package cn.edu.cug.cs.gtl.solr.demo;

import cn.edu.cug.cs.gtl.protos.SqlQueryStatement;
import cn.edu.cug.cs.gtl.protoswrapper.SqlWrapper;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.MapSolrParams;

public class SolrSqlQueryApp2 {
    public static void main(String[] args) {
        try {
            String serverURL = "http://120.24.168.173:8983/solr";
            SolrClient solrClient = new HttpSolrClient
                    .Builder()
                    .withBaseSolrUrl(serverURL)
                    .withConnectionTimeout(10000)
                    .withSocketTimeout(60000)
                    .build();

            String sql = "select id, title, contents from gtl where (contents='beam' OR contents='lucene') AND (title='beam') OR (input_date<='2019-10-03T10:15:30Z') OR (input_date>='2011-12-03T10:15:30Z')";
            MapSolrParams queryParams = new MapSolrParams(
                    SqlWrapper.toSolrQueryParameterMap(sql));

            final QueryResponse response = solrClient.query("gtl", queryParams);
            final SolrDocumentList documents = response.getResults();

            System.out.println("Found " + documents.getNumFound() + " documents");
            for (SolrDocument document : documents) {
                final String id = (String) document.getFirstValue("id");
                final String name = (String) document.getFirstValue("title");

                System.out.println("id: " + id + "; title: " + name);
                final String contents = (String) document.getFirstValue("contents");
                System.out.println("contents: " + contents);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
