package com.opencode.carrot.csearch.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

import java.io.File;
import java.nio.file.Path;

@Getter
public class CSearchConfig {

    private Path path;

    @Setter
    private Analyzer analyzer;

    public void setPath(String path) {
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        this.path = file.toPath();
    }

    public static CSearchConfig getConfig(){
        CSearchConfig cSearchConfig = new CSearchConfig();
        cSearchConfig.setPath("/Users/wty/Downloads/temp");
        cSearchConfig.setAnalyzer(new StandardAnalyzer());
        return cSearchConfig;
    }

}
