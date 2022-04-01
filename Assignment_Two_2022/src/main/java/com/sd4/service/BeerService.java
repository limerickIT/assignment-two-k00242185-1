
package com.sd4.service;

import com.sd4.model.Beer;
import com.sd4.repository.BeerRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BeerService {
    @Autowired
    private BeerRepository BeerRepo;
    
    //pager
    public List<Beer> findAll(int number, int pagesize){
        
    List<Beer> b = (List<Beer>) BeerRepo.findAll();
    b.size();
        Pageable p = PageRequest.of(number,pagesize);
        Page<Beer> beerpage = BeerRepo.findAll(p);
        return beerpage.toList();
    }
    public Optional<Beer> findbyid(long id){
    return BeerRepo.findById(id);
    }
    public boolean deleteOne(long id){
       
        Optional<Beer> beer = findbyid(id);
        Beer b = beer.orElse(new Beer());
    BeerRepo.delete(b);
        return BeerRepo.findById(id).isPresent();
         
    }

    public void addbeer(Beer b) {
    if(BeerRepo.findById(b.getId()).isPresent()){
    return ;}
      BeerRepo.save(b);
            
      
      
    }
}
