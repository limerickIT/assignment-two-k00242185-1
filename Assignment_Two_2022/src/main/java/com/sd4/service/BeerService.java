
package com.sd4.service;

import com.sd4.model.Beer;
import com.sd4.repository.BeerRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BeerService {
    @Autowired
    private BeerRepository BeerRepo;
    
    public List<Beer> findAll(){
    return(List<Beer>) BeerRepo.findAll();
    }
}
