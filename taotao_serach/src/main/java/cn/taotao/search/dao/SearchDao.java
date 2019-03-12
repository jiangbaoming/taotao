package cn.taotao.search.dao;

import cn.taotao.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;

public interface SearchDao {

    SearchResult search(SolrQuery query) throws Exception;
}
