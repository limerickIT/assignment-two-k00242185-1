
package com.sd4.controller;

import com.sd4.model.Beer;
import com.sd4.service.BeerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/beer")
public class BeerController {
    @Autowired
    private BeerService beerService;
    
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<List<Beer>> index(){
    List<Beer> beerlist =  beerService.findAll();
    if(beerlist.isEmpty()){
    return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
    else{
        //Link selflink = new Link("http://localhost:8888/beer/"+ beerlist.get()).withSelfRel();
        //beerlist.get().add(selfLink);
    return ResponseEntity.ok(beerlist);
    }
    }
    @GetMapping(value="link", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<Beer> getAllBeers(){
        List<Beer> beerlist = beerService.findAll();
        for(final Beer beer:beerlist){
        long id = beer.getId();
        Link selfLink = linkTo(BeerController.class).slash(id).withSelfRel();
        beer.add(selfLink);
        
        
        
        }
        Link link = linkTo(BeerController.class).withSelfRel();
        return CollectionModel.of(beerlist, link);
    
    }
    
    
    
    
}
