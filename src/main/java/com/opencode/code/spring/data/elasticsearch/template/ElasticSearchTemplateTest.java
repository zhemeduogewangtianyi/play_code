package com.opencode.code.spring.data.elasticsearch.template;

import com.opencode.code.common.Student;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.TimeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.AbstractElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchScrollHits;
import org.springframework.data.elasticsearch.core.document.SearchDocumentResponse;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import org.springframework.data.elasticsearch.core.query.Query;

@Component
public class ElasticSearchTemplateTest {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    public boolean testCreate(){
        // 创建索引，会根据Item类的@Document注解信息来创建
        boolean index = elasticsearchRestTemplate.createIndex(Student.class);
//        template.indexOps(Student.class);

        return index;
    }

    public boolean testPutMapping(){
        // 配置映射，会根据Item类中的id、Field等字段来自动完成映射
        boolean b = elasticsearchRestTemplate.putMapping(Student.class);
        return b;
    }

    public boolean testDel(){
        // 删除索引，会根据Item类的@Document注解信息来创建
        return elasticsearchRestTemplate.deleteIndex(Student.class);
    }


    public <T> SearchScrollHits<T> scrollQuery(long scrollTimeInMillis,Query query, Class<T> clazz,String... indexs){
        return elasticsearchRestTemplate.searchScrollStart(scrollTimeInMillis, query, clazz, IndexCoordinates.of(indexs));
    }

    public <T> SearchScrollHits<T> searchScrollContinue(String scrollId, long scrollTimeInMillis, Class<T> clazz, String ... indexs) {

        return elasticsearchRestTemplate.searchScrollContinue(scrollId,scrollTimeInMillis,clazz,IndexCoordinates.of(indexs));

    }

    public void searchScrollClear(List<String> scrollIds) {
        elasticsearchRestTemplate.searchScrollClear(scrollIds);
    }

}
