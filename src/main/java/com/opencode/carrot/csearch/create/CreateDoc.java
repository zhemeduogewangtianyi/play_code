package com.opencode.carrot.csearch.create;

import com.opencode.carrot.csearch.annotation.CField;
import com.opencode.carrot.csearch.config.CSearchConfig;
import com.opencode.carrot.csearch.context.CFieldContext;
import com.opencode.carrot.csearch.entity.User;
import com.opencode.carrot.csearch.enums.CFieldTypeEnum;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;

public class CreateDoc {

    public static void main(String[] args) {

        User user = new User();
        user.setId(2L);
        user.setName("周童童");
        user.setAge(17);
        user.setDesc("周童童 是一个好学生，太好了，真的是太好了！￥%……");
        user.setUrl("http://www.baidu.com");
        user.setBirthDay(new Date());

        new CreateDoc().createDoc(user);
    }

    public boolean createDoc(Object obj) {

        CSearchConfig searchConfig = CSearchConfig.getConfig();

        Directory directory = null;
        IndexWriter iWriter = null;

        try {
            directory = FSDirectory.open(searchConfig.getPath());

            IndexWriterConfig config = new IndexWriterConfig(searchConfig.getAnalyzer());

            iWriter = new IndexWriter(directory, config);

            Document doc = new Document();

            Class<?> aClass = obj.getClass();
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field objField : declaredFields) {

                CField annotation = objField.getAnnotation(CField.class);
                if(annotation != null){
                    CFieldTypeEnum enums = annotation.enums();

                    boolean store = annotation.store();
                    boolean analyzer = annotation.analyzer();
                    boolean date = annotation.isDate();

                    String dateFormat = annotation.dateFormat();

                    System.out.println(store + " : " + analyzer + " : " + date + " : " + dateFormat);


                    Class<?> type = objField.getType();
                    String name = objField.getName();
                    objField.setAccessible(true);
                    Object o = objField.get(obj);

                    CFieldContext context = new CFieldContext(doc);
                    context.setField(objField);
                    context.setCField(annotation);
                    context.setO(obj);

//                    Class<?>[] parameter = {String.class,long[].class};
//                    Object[] args = {name,new long[]{Long.parseLong(o.toString())}};
//                    IndexableField clsField = enums.getClsField(parameter,args);

                    System.out.println(type + " : " + name + " : " + o);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (iWriter != null) {
                try {
                    iWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (directory != null) {
                try {
                    directory.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return true;
    }

}
