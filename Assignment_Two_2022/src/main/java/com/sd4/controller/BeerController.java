
package com.sd4.controller;

import com.sd4.model.Beer;
import com.sd4.model.Brewery;
import com.sd4.service.BeerService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/beer")
public class BeerController {
    @Autowired
    private BeerService beerService;
    
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Link> index(){
    Link link = linkTo(BeerController.class).slash("link").slash(1).slash(10).withSelfRel();
  
  
    return ResponseEntity.ok(link);
    }
    
    @GetMapping(value="link/{pageNo}/{pageSize}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<Beer> getAllBeers(@PathVariable int pageNo, @PathVariable int pageSize){
        List<Beer> beerlist = beerService.findAll(pageNo, pageSize);
        for(final Beer beer:beerlist){
        long id = beer.getId();
        Link selfLink = linkTo(BeerController.class).slash(id).withSelfRel();
        beer.add(selfLink);
        
        
        
        }
      String  value = "link";
        Link backlink;
      if(pageNo == 1){
          backlink = linkTo(BeerController.class).withSelfRel();
      }
      else{
       backlink = linkTo(BeerController.class).slash(value).slash(pageNo - 1).slash(pageSize).withSelfRel();}
        Link forwardlink = linkTo(BeerController.class).slash(value).slash(pageNo + 1).slash(pageSize).withSelfRel();
        return CollectionModel.of(beerlist,backlink, forwardlink);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Beer> getOneBeer(@PathVariable long id ){
    Optional<Beer> beer = beerService.findbyid(id);
    Beer b = beer.orElse(new Beer());
    if(b.getId() != id){
    return new ResponseEntity(HttpStatus.NOT_FOUND);
    
    }
    
    Link brewerylink = linkTo(BreweryController.class).slash(b.getBrewery_id()).withSelfRel();
    b.add(brewerylink);
    Link linkback = linkTo(BeerController.class).withSelfRel();
    b.add(linkback);
    return ResponseEntity.ok(b);
    
    }
    
    @DeleteMapping("/{id}")
    public boolean deleteOne(@PathVariable long id){
    boolean deletion = false;
    deletion = beerService.deleteOne(id);
    return deletion;
    }
    
   @PostMapping(value = "/beers", produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity Addbeer(@RequestBody Beer b){
      
    beerService.addbeer(b);
    return new ResponseEntity(HttpStatus.CREATED);
   
   }
    
    
}
