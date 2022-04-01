
package com.sd4.repository;

import com.sd4.model.Brewery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BreweryRepository extends CrudRepository<Brewery, Long> {
    
}
