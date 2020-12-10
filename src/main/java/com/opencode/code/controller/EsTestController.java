package com.opencode.code.controller;

import com.opencode.code.common.Student;
import com.opencode.code.spring.data.elasticsearch.crud.StudentCrudRepository;
import com.opencode.code.spring.data.elasticsearch.template.ElasticSearchTemplateTest;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.ParsedAvg;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchScrollHits;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@RequestMapping(value = "/es")
@RestController
public class EsTestController {

    @Autowired
    private StudentCrudRepository studentCrudRepository;

    @Autowired
    private ElasticSearchTemplateTest elasticSearchTemplateTest;

    @RequestMapping(value = "/testCreateIndex")
    public Object testCreateIndex(){
        return elasticSearchTemplateTest.testCreate();
    }

    @RequestMapping(value = "/testPutMapping")
    public Object testPutMapping(){
        return elasticSearchTemplateTest.testPutMapping();
    }

    @RequestMapping(value = "/testDelIndex")
    public Object testDelIndex(){
        return elasticSearchTemplateTest.testDel();
    }

    @RequestMapping(value = "/saveStudent")
    public Object saveStudent() throws ParseException {

        Student stu = new Student();

        stu.setAge((byte) 18);
        stu.setBirthDay(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2000-01-01 12:00:00"));
        stu.setBodyWeight(8.88F);
        stu.setHeight(8.88F);
        stu.setGender('男');
        stu.setIsFool(false);
        stu.setMaxScore((short)120);
        stu.setNumber(1001);
        stu.setSalary(0.1D);
        stu.setName("二狗");

        return studentCrudRepository.save(stu);
    }

    @RequestMapping(value = "/batchSaveStudent")
    public Object batchSaveStudent() {

        List<Student> list = new ArrayList<>();

        for(int i = 0 ; i < 500 ; i++){

            Student stu = new Student();

            stu.setAge((byte) i);
            stu.setBirthDay(new Date());
            stu.setBodyWeight(ThreadLocalRandom.current().nextFloat());
            stu.setHeight(ThreadLocalRandom.current().nextFloat());
            stu.setGender((i&1) == 0 ? '男' : '女');
            stu.setIsFool((i&1) == 0);
            stu.setMaxScore((short)i);
            stu.setNumber(1002 + i);
            stu.setSalary(0.1D + i);
            stu.setName("二狗");

            list.add(stu);
        }

        return studentCrudRepository.saveAll(list);
    }

    @RequestMapping(value = "/delStudent")
    public Object delStudent(@RequestParam String id) {

        studentCrudRepository.deleteById(id);

        return id;
    }

    @RequestMapping(value = "/delAll")
    public Object delAll() {

        studentCrudRepository.deleteAll();

        return "true";
    }

    @RequestMapping(value = "/queryById")
    public Object queryById(@RequestParam String id) {

        return studentCrudRepository.findById(id);

    }

    @RequestMapping(value = "/queryAll")
    public Object queryAll() {

        Iterable<Student> all = studentCrudRepository.findAll(Sort.by(Sort.Direction.DESC,"salary"));
        return all;

    }

    @RequestMapping(value = "/customQuery")
    public Object customQuery(@RequestParam String name) {

        Iterable<Student> all = studentCrudRepository.findByName(name);
        return all;

    }

    @RequestMapping(value = "/queryBuilderBaseQuery")
    public Object queryBuilderBaseQuery(@RequestParam String name) {

        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("name", name);

        Iterable<Student> search = studentCrudRepository.search(matchQueryBuilder);
        return search;

    }

    @RequestMapping(value = "/customQueryBuilder")
    public Object customQueryBuilder() {

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        queryBuilder.withQuery(QueryBuilders.matchQuery("name","二丫"));

        queryBuilder.withPageable(PageRequest.of(0,3));

        NativeSearchQuery build = queryBuilder.build();

        Page<Student> search = studentCrudRepository.search(build);

        //总条数
        long totalElements = search.getTotalElements();

        //总页数
        int totalPages = search.getTotalPages();

        return search;

    }

    @RequestMapping(value = "/pageQuery")
    public Object pageQuery() {

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        queryBuilder.withQuery(QueryBuilders.matchQuery("isFool",false));

        queryBuilder.withPageable(PageRequest.of(0,3));

        queryBuilder.withSort(SortBuilders.fieldSort("age").order(SortOrder.ASC));

        NativeSearchQuery build = queryBuilder.build();

        Page<Student> search = studentCrudRepository.search(build);

        //总条数
        long totalElements = search.getTotalElements();

        //总页数
        int totalPages = search.getTotalPages();

        return search;

    }


    @RequestMapping(value = "/aggregationQuery")
    public Object aggregationQuery() {

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        //不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""},null));

        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为 names，聚合字段为 name
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("ages").field("age");
        queryBuilder.addAggregation(termsAggregationBuilder);

        NativeSearchQuery build = queryBuilder.build();

        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<Student> search = (AggregatedPage<Student>) studentCrudRepository.search(build);

        //3、解析 -> 取出名为 names 的那个聚合
        Aggregation ages = search.getAggregation("ages");

//        List<StringTerms.Bucket> buckets = ages.getBuckets();

        // 4、遍历
//        for (StringTerms.Bucket bucket : buckets) {
//            // 5、获取桶中的key，即品牌名称
//            System.out.println(bucket.getKeyAsString());
//            // 6、获取桶中的文档数量
//            System.out.println(bucket.getDocCount());
//        }

        //总条数
        long totalElements = search.getTotalElements();

        //总页数
        int totalPages = search.getTotalPages();

        return ages;

    }

    @RequestMapping(value = "/aggregationQueryAvg")
    public Object aggregationQueryAvg() {

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        //不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""},null));

        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为 names，聚合字段为 name
        AvgAggregationBuilder field = AggregationBuilders.avg("avgAges").field("age");
        queryBuilder.addAggregation(field);

        NativeSearchQuery build = queryBuilder.build();

        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<Student> search = (AggregatedPage<Student>) studentCrudRepository.search(build);

        //3、解析 -> 取出名为 names 的那个聚合
        Aggregation names = search.getAggregation("avgAges");


        return ((ParsedAvg) names).getValue();

    }

    @RequestMapping(value = "/scrollQuery")
    public Object scrollQuery(@RequestParam String scrollId) {

        QueryBuilder queryBuilder= QueryBuilders.rangeQuery("age").gte(1);

        NativeSearchQuery nativeSearchQuery=new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withPageable(PageRequest.of(0,2))
                .build();

        long scrollTimeout = 10000;
        SearchScrollHits<Student> searchHits;


        if(StringUtils.isEmpty(scrollId)){
            searchHits = elasticSearchTemplateTest.scrollQuery(scrollTimeout, nativeSearchQuery, Student.class, new String[]{"student"});
        }else{
            try{
                searchHits = elasticSearchTemplateTest.searchScrollContinue(scrollId,scrollTimeout,Student.class, new String[]{"student"});
            }catch(Exception e){
                searchHits = null;
            }
        }

        Map<String,Object> resultMap = new HashMap<>();

        if(searchHits == null){
            resultMap.put("data",new ArrayList<>());
            resultMap.put("total",null);
            resultMap.put("scrollId",null);
            return resultMap;
        }

        long totalHits = searchHits.getTotalHits();
        System.out.println("总记录数为：" + totalHits);

        boolean empty = searchHits.isEmpty();
        if(empty){
            elasticSearchTemplateTest.searchScrollClear(Collections.singletonList(scrollId));
            List<SearchHit<Student>> searchHitList = searchHits.getSearchHits();
            resultMap.put("data",searchHitList);
            resultMap.put("total",null);
            resultMap.put("scrollId",null);
            return resultMap;
        }else{
            List<SearchHit<Student>> searchHitList = searchHits.getSearchHits();
            String scroll = searchHits.getScrollId();
            resultMap.put("data",searchHitList);
            resultMap.put("total",totalHits);
            resultMap.put("scrollId",scroll);
            return resultMap;
        }

    }

    @RequestMapping(value = "/updateStudent")
    public Object changeStudent() throws ParseException {

        Student stu = new Student();
        stu.setId("nNHlQXYBU32GP44v-cZu");

        int i = ThreadLocalRandom.current().nextInt(100) + 1;

        if((i & 1) == 0){

            stu.setAge((byte) 16);
            stu.setBirthDay(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2002-01-01 12:00:00"));
            stu.setBodyWeight(6.66F);
            stu.setHeight(6.66F);
            stu.setGender('女');
            stu.setIsFool(false);
            stu.setMaxScore((short)120);
            stu.setNumber(1002);
            stu.setSalary(0.2D);
            stu.setName("二丫");

        }else{

            stu.setAge((byte) 18);
            stu.setBirthDay(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2000-01-01 12:00:00"));
            stu.setBodyWeight(8.88F);
            stu.setHeight(8.88F);
            stu.setGender('男');
            stu.setIsFool(false);
            stu.setMaxScore((short)120);
            stu.setNumber(1001);
            stu.setSalary(0.1D);
            stu.setName("二狗");

        }

        return studentCrudRepository.save(stu);
    }


}
