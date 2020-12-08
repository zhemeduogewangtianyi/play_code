package com.opencode.code.spring.data.elasticsearch.crud;

import com.opencode.code.common.Student;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface StudentCrudRepository extends CrudRepository<Student,String>, PagingAndSortingRepository<Student,String>, ElasticsearchRepository<Student,String> {

    List<Student> findByName(String name);

}
