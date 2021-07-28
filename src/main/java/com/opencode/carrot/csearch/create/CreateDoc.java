package com.opencode.carrot.csearch.create;

import com.opencode.carrot.csearch.annotation.CField;
import com.opencode.carrot.csearch.config.CSearchConfig;
import com.opencode.carrot.csearch.context.CFieldContext;
import com.opencode.carrot.csearch.entity.User;
import com.opencode.carrot.csearch.enums.CFieldTypeEnum;
import com.opencode.carrot.csearch.interfaces.CFieldHandle;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class CreateDoc {

    private static final List<CFieldHandle> C_FIELD_HANDLES = new ArrayList<>();

    private static final String[] names = {"int","long","string","text","store"};

    static{
        ExtensionLoader<CFieldHandle> extensionLoader = ExtensionLoader.getExtensionLoader(CFieldHandle.class);
        for(String name : names){
            C_FIELD_HANDLES.add(extensionLoader.getExtension(name));
        }
    }

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

                    Class<?> type = objField.getType();
                    String name = objField.getName();
                    objField.setAccessible(true);
                    Object o = objField.get(obj);

                    C_FIELD_HANDLES.forEach(extendsion -> {

                        CFieldContext context = new CFieldContext(doc);
                        context.setField(objField);
                        context.setCField(annotation);
                        context.setO(o);
                        context.setName(name);
                        context.setType(type);

                        if(extendsion.support(context)){
                            Object handle = extendsion.handle(context);
                            if(handle != null){
                                IndexableField res = (IndexableField) handle;
                                doc.add(res);
                            }
                        }

                    });
                }
            }

            iWriter.addDocument(doc);

            iWriter.close();

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
