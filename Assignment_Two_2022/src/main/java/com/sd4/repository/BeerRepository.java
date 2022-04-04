
package com.sd4.repository;

import com.sd4.model.Beer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeerRepository extends CrudRepository<Beer, Long>, PagingAndSortingRepository<Beer, Long>{
    
    
    
}
