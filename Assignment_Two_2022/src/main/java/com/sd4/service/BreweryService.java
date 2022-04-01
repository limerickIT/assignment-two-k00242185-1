
package com.sd4.service;

import com.sd4.model.Brewery;
import com.sd4.repository.BreweryRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BreweryService {
    @Autowired
    private BreweryRepository BreweryRepo;
    
    public Optional<Brewery> findone(long id){
    return BreweryRepo.findById(id);
    }
    
    
}
