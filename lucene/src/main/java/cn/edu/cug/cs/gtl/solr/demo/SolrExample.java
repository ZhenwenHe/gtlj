package cn.edu.cug.cs.gtl.solr.demo;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.util.UUID;

public class SolrExample {
    public static void main(String [] args){

        String serverURL = "http://120.24.168.173:8983/solr";
        SolrClient solrClient = new HttpSolrClient
                .Builder()
                .withBaseSolrUrl(serverURL)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();

        final SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", UUID.randomUUID().toString());
        doc.addField("name", "Amazon Kindle Paperwhite");
        doc.addField("title","solr 编程实践");


        try {
            final UpdateResponse updateResponse = solrClient.add("gtl", doc);
            // Indexed documents must be committed
            solrClient.commit("gtl");
            System.out.println(updateResponse.jsonStr());

            final SolrQuery solrQuery = new SolrQuery("*.*");
            solrQuery.addField("id");
            solrQuery.addField("title");
            solrQuery.addFilterQuery("title: solr");
            solrQuery.setRows(10);
            QueryResponse queryResponse = solrClient.query(solrQuery);
            System.out.println(queryResponse.jsonStr());

//            final SolrClient client = getSolrClient();
//
//            final Map<String, String> queryParamMap = new HashMap<String, String>();
//            queryParamMap.put("q", "*:*");
//            queryParamMap.put("fl", "id, name");
//            queryParamMap.put("sort", "id asc");
//            MapSolrParams queryParams = new MapSolrParams(queryParamMap);
//
//            final QueryResponse response = client.query("techproducts", queryParams);
//            final SolrDocumentList documents = response.getResults();
//
//            print("Found " + documents.getNumFound() + " documents");
//            for(SolrDocument document : documents) {
//                final String id = (String) document.getFirstValue("id");
//                final String name = (String) document.getFirstValue("name");
//
//                print("id: " + id + "; name: " + name);
//            }


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
