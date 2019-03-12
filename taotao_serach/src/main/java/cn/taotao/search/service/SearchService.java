package cn.taotao.search.service;

import cn.taotao.search.pojo.SearchResult;

public interface SearchService {
    SearchResult search(String queryString, int page, int rows) throws Exception;
}
