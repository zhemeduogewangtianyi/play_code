package com.opencode.carrot.csql.test;

import com.opencode.carrot.csql.entity.User;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.IOUtils;
import org.apache.lucene.util.NumericUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

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

    public static void main(String[] args) throws IOException, ParseException {

        //把数据填充到JavaBean对象中
        User user = new User();
        user.setId(2L);
        user.setName("week");
        user.setAge(17);

        Analyzer analyzer = new StandardAnalyzer();

        File file = new File("/Users/wty/Downloads/temp");
        if(!file.exists()){
            file.mkdir();
        }
        Path indexPath = file.toPath();

        FSDirectory directory = FSDirectory.open(indexPath);

        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        IndexWriter iWriter = new IndexWriter(directory,config);

        Document doc = new Document();
        doc.add(new NumericDocValuesField("id",user.getId()));
        doc.add(new Field("name",user.getName(), TextField.TYPE_STORED));
        doc.add(new NumericDocValuesField("age", user.getAge()));

        iWriter.addDocument(doc);

        iWriter.close();

        DirectoryReader iReader = DirectoryReader.open(directory);
        IndexSearcher iSearcher = new IndexSearcher(iReader);

//        QueryParser parser = new QueryParser("id", analyzer);
//        Query query = parser.parse("1");
//        TopDocs search = iSearcher.search(query, 10);

        Query id = LongPoint.newExactQuery("id", 2);
        TopDocs search = iSearcher.search(id, 10);

        for(ScoreDoc hit : search.scoreDocs){
            Document hitDoc = iSearcher.doc(hit.doc);
            System.out.println(hitDoc.get("id"));
            System.out.println(hitDoc.get("name"));
            System.out.println(hitDoc.get("age"));
        }
        iReader.close();
        directory.close();
//        IOUtils.rm(indexPath);

    }

}
