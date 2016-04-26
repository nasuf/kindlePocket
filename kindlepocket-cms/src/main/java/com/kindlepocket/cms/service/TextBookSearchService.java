package com.kindlepocket.cms.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Test;
import org.springframework.stereotype.Service;

import com.kindlepocket.cms.pojo.Item;
import com.kindlepocket.cms.pojo.SearchResult;

@Service
public class TextBookSearchService {

    @Test
    public void testAddData() {
        HttpSolrServer solrServer = new HttpSolrServer("http://localhost:8983/kindlePocket");
        List<Item> items = new ArrayList<Item>();
        for (int i = 1; i <= 200; i++) {
            Item item = new Item();
            item.setDownloadTimes(new Date().getTime());
            item.setAuthor("nasuf_" + i);
            item.setId((long) i);
            item.setKindleMailTimes((long) i);
            item.setMailTimes((long) i);
            item.setTitle("nasuf's No." + i);
            item.setUploadDate(new Date().getTime());
            item.setUploaderName("nasuf_" + i);
            items.add(item);
        }
        try {
            solrServer.addBeans(items);
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HttpSolrServer httpSolrServer = new HttpSolrServer("http://localhost:8983/kindlePocket");

    public void addInfos(List<Item> items) throws Exception {
        // add data to solr server
        this.httpSolrServer.addBeans(items);
        this.httpSolrServer.commit();
    }

    public void deleteInfos(List<String> ids) throws Exception {
        this.httpSolrServer.deleteById(ids);
        this.httpSolrServer.commit();
    }

    @Test
    public void testDeleteInfos() throws SolrServerException, IOException {
        HttpSolrServer solrServer = new HttpSolrServer("http://localhost:8983/kindlePocket");
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0; i <= 100; i++) {
            arrayList.add(i + "");
        }
        solrServer.deleteById(arrayList);
        solrServer.commit();
    }

    public SearchResult search(String keyWords, Integer page, Integer rows) throws Exception {
        // contruct query conditions
        SolrQuery solrQuery = new SolrQuery();
        // key words
        solrQuery.setQuery("title:" + keyWords);
        // paging settings. 'rows' means the records returned.
        // when page changes the only value need to change is the 'start'
        solrQuery.setStart((Math.max(page, 1) - 1) * rows);
        solrQuery.setRows(rows);

        // whether need highlight
        boolean isHighlighting = !StringUtils.equals("*", keyWords) && StringUtils.isNotEmpty(keyWords);

        if (isHighlighting) {
            // highlight config
            // enable highlight
            solrQuery.setHighlight(true);
            // highlight field
            solrQuery.addHighlightField("title");
            // highlight prefix
            solrQuery.setHighlightSimplePre("<em>");
            // highlight suffix
            solrQuery.setHighlightSimplePost("</em>");
        }

        // excute query
        QueryResponse queryResponse = this.httpSolrServer.query(solrQuery);
        List<Item> items = queryResponse.getBeans(Item.class);
        if (null == items) {
            // no records returned
            return new SearchResult();
        }
        if (isHighlighting) {
            // write back the highlighted contents into the data object
            Map<String, Map<String, List<String>>> map = queryResponse.getHighlighting();
            for (Map.Entry<String, Map<String, List<String>>> highlighting : map.entrySet()) {
                for (Item item : items) {
                    if (!highlighting.getKey().equals(item.getId().toString())) {
                        continue;
                    }
                    // item.setId((long) 1);
                    item.setTitle(StringUtils.join(highlighting.getValue().get("title"), ""));
                    break;
                }
            }
        }
        SearchResult searchResult = new SearchResult();
        searchResult.setRows(items);
        // total counts
        searchResult.setTotal(queryResponse.getResults().getNumFound());
        return searchResult;
    }
}
