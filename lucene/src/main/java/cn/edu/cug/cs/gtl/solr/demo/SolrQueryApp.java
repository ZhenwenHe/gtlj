package cn.edu.cug.cs.gtl.solr.demo;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.MapSolrParams;
import org.apache.solr.common.params.ModifiableSolrParams;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SolrQueryApp {
    public static void main(String[] args){
        try {
            String serverURL = "http://120.24.168.173:8983/solr";
            SolrClient solrClient = new HttpSolrClient
                    .Builder()
                    .withBaseSolrUrl(serverURL)
                    .withConnectionTimeout(10000)
                    .withSocketTimeout(60000)
                    .build();

            final Map<String, String> queryParamMap = new HashMap<>();
            queryParamMap.put("q", "*:*");
            queryParamMap.put("fq", "(contents:beam OR contents:lucene) AND (title:beam)");

            queryParamMap.put("fl", "id, title, contents");
            queryParamMap.put("sort", "id asc");
            MapSolrParams queryParams = new MapSolrParams(queryParamMap);

            final QueryResponse response = solrClient.query("gtl", queryParams);
            final SolrDocumentList documents = response.getResults();

            System.out.println("Found " + documents.getNumFound() + " documents");
            for(SolrDocument document : documents) {
                final String id = (String) document.getFirstValue("id");
                final String name = (String) document.getFirstValue("title");

                System.out.println("id: " + id + "; title: " + name);
                final String contents = (String) document.getFirstValue("contents");
                System.out.println("contents: " + contents);
            }


//
//            The example snippet below shows an annotated TechProduct class that can be used to represent results from Solr’s "techproducts" example collection.
//
//            public static class TechProduct {
//                @Field public String id;
//                @Field public String name;
//
//                public TechProduct(String id, String name) {
//                    this.id = id;  this.name = name;
//                }
//
//                public TechProduct() {}
//            }
//            Application code with access to the annotated TechProduct class above can index TechProduct objects directly without any conversion, as in the example snippet below:
//
//            final SolrClient client = getSolrClient();
//
//            final TechProduct kindle = new TechProduct("kindle-id-4", "Amazon Kindle Paperwhite");
//            final UpdateResponse response = client.addBean("techproducts", kindle);
//
//            client.commit("techproducts");
//            Similarly, search results can be converted directly into bean objects using the getBeans() method on QueryResponse:
//
//            final SolrClient client = getSolrClient();
//
//            final SolrQuery query = new SolrQuery("*:*");
//            query.addField("id");
//            query.addField("name");
//            query.setSort("id", ORDER.asc);
//
//            final QueryResponse response = client.query("techproducts", query);
//            final List<TechProduct> products = response.getBeans(TechProduct.class);


        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
