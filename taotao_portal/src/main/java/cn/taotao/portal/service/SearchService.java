package cn.taotao.portal.service;


import cn.taotao.portal.pojo.SearchResult;

public interface SearchService {
    SearchResult search(String queryString, int page);
}
