package com.keysoft.mongodb.repositories;

import com.keysoft.mongodb.model.Application;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends MongoRepository<Application, String>{
}
