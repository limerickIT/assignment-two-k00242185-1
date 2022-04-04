
package com.sd4.controller;

import com.google.gwt.user.client.ui.Image;
import com.sd4.model.Beer;
import com.sd4.model.Brewery;
import com.sd4.service.BeerService;
import com.sd4.service.BreweryService;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.imageio.ImageIO;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/beer")
public class BeerController {
    @Autowired
    private BeerService beerService;
    @Autowired
    private BreweryService breweryservice;
    
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
        Link breweryLink = linkTo(BeerController.class).slash(id).slash("brewery").withSelfRel();
        beer.add(selfLink);
        beer.add(breweryLink);
        Link imageLinkthumb = linkTo(BeerController.class).slash(id).slash("thumb").withSelfRel();
        Link imageLinklarge= linkTo(BeerController.class).slash(id).slash("large").withSelfRel();
        beer.add(imageLinkthumb);
        beer.add(imageLinklarge);
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
    
    @GetMapping( value = "/{id}",  produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Beer> getOneBeer(@PathVariable long id ){
    Optional<Beer> beer = beerService.findbyid(id);
    Beer b = beer.orElse(new Beer());
   // Optional<Brewery> brewery = breweryservice.findone(b.getBrewery_id());
   // Brewery bwery = brewery.orElse(new Brewery());
    
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
    
   @GetMapping("/{id}/brewery")
   public ResponseEntity<String> getbeer_Brewery(@PathVariable long id){
       Optional<Beer> beer = beerService.findbyid(id);
    Beer b = beer.orElse(new Beer());
    Optional<Brewery> brewery = breweryservice.findone(b.getBrewery_id());
    Brewery bwery = brewery.orElse(new Brewery());
    String info = "<center> Beer ID : " + b.getId()
            + "\nBeer Description: " + b.getDescription()
            + "\nBrewery Name: " + bwery.getName()
            + "\nBrewery Address " + bwery.getAddress1()
            + "</center>";
            
   return ResponseEntity.ok(info);
   }
   
   @PutMapping("/{id}")
   public ResponseEntity<Beer> editbeerbyid(@RequestBody Beer b){
   Beer beer =  beerService.edit(b);
   
       return ResponseEntity.ok(beer);
   }
    @GetMapping("/{id}/{imagesize}")
    public ResponseEntity<String> getimagesize(@PathVariable long id, @PathVariable String imagesize) throws IOException{
    Optional<Beer> beer = beerService.findbyid(id);
    Beer b = beer.orElse(new Beer());
    String imag =  b.getImage();
   // File pathToFile = new File(imag);
   // BufferedImage im = ImageIO.read(pathToFile);
    String image = "<img src = '"+ imagesize + "\\" + imag +"' />";
    
    return ResponseEntity.ok(image);
    }
}
