package com.opencode.carrot.csearch.test;

import com.opencode.carrot.csearch.entity.User;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.IOUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

/**

 l 通过项进行搜索 TermQuery类

 l 在指定的项范围内搜索 TermRangeQuery类

 l 通过字符串搜索 PrefixQuery类

 l 组合查询 BooleanQuery类

 l 通过短语搜索 PhraseQuery类

 l 通配符查询 WildcardQuery类

 l 搜索类似项 FuzzyQuery类

 l 匹配所有文档 MatchAllDocsQuery类

 l 不匹配文档 MatchNoDocsQuery类

 l 解析查询表达式 QueryParser类

 l 多短语查询 MultiPhraseQuery类

 l 查询所有 MatchAllDocsQuery类

 l 不匹配所有文档 MatchNoDocsQuery类

 * */
public class TestCreateIndex {

    private static final String path = "/Users/wty/Downloads/temp";
//    private static final String path = "D:\\del";


    public static void main(String[] args) throws IOException, ParseException {

        //把数据填充到JavaBean对象中
        User user = new User();
        user.setId(2L);
        user.setName("周童童");
        user.setAge(17);
        user.setDesc("周童童 是一个好学生，太好了，真的是太好了！￥%……");
        user.setUrl("http://www.baidu.com");
        user.setBirthDay(new Date());

        Analyzer analyzer = new StandardAnalyzer();

        File file = new File(path);
        if(!file.exists()){
            file.mkdir();
        }
        Path indexPath = file.toPath();

        FSDirectory directory = FSDirectory.open(indexPath);

        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        IndexWriter iWriter = new IndexWriter(directory,config);

        Document doc = new Document();

        //建立 Long 索引
        LongPoint id_idx = new LongPoint("id", user.getId());
        doc.add(id_idx);

        //存储ID
        Field id = new StoredField("id", user.getId());
        doc.add(id);

        //设置一个文本类型，对内容不分词，并存在文档中储存
        Field name = new StringField("name", user.getName(), Field.Store.YES);
        doc.add(name);

        //设置一个文本类型，对内容分词
        Field desc = new TextField("desc", user.getDesc(), Field.Store.YES);
        doc.add(desc);

        //建立一个 Int 类型
        Field age_idx = new IntPoint("age", user.getAge());
        doc.add(age_idx);

        //存储
        Field age = new StoredField("age", user.getAge());
        doc.add(age);

        //建立一个文本类型，不分词，不建立索引，在文档中存储
        StoredField url = new StoredField("url", user.getUrl());
        doc.add(url);

        //建立一个日期类型 - 用 long 表示
        doc.add(new LongPoint("birthDay",user.getBirthDay().getTime()));
        doc.add(new StoredField("birthDay",user.getBirthDay().getTime()));

        iWriter.addDocument(doc);

        iWriter.close();

        DirectoryReader iReader = DirectoryReader.open(directory);
        IndexSearcher iSearcher = new IndexSearcher(iReader);

//        QueryParser parser = new QueryParser("id", analyzer);
//        Query query = parser.parse("1");
//        TopDocs search = iSearcher.search(query, 10);

        Query queryById = LongPoint.newExactQuery("id", 2);
        TopDocs search = iSearcher.search(queryById, 10);

        for(ScoreDoc hit : search.scoreDocs){
            Document hitDoc = iSearcher.doc(hit.doc);
            List<IndexableField> fields = hitDoc.getFields();
            for(IndexableField idxField : fields){
                System.out.println(idxField.name() + " : " + idxField.getCharSequenceValue());
            }
        }
        iReader.close();
        directory.close();
        IOUtils.rm(indexPath);

    }

}
