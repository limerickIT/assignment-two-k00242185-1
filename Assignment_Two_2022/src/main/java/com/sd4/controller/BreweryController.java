
package com.sd4.controller;

import com.google.gwt.user.client.ui.Image;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.sd4.model.Brewery;
import com.sd4.service.BreweryService;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import javax.imageio.ImageIO;
import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/brewery")
public class BreweryController {
    @Autowired
    private BreweryService breweryservice;
   
//    @GetMapping(" ")
//    public ResponseEntity<Brewery> getallBrewery(){
//   // breweryservice.getall();
//    
//    }
//    
    
      @GetMapping("/{id}")
    public ResponseEntity<String> getbyID(@PathVariable long id)
    {
       Optional<Brewery> brewery = breweryservice.findone(id);
       
        Brewery b = brewery.orElse(new Brewery());
        String bstring = "Brewery Name = " + b.getName()
                + "\n Brewery Address 1 = " + b.getAddress1()
                + "\n Brewery Address 2 = " + b.getAddress2()
                + "\n Brewery City = " + b.getCity()
                + "\n Brewery State = " + b.getState()
                + "\n Brewery Country = " + b.getCountry()
                + "\n Brewery Description = " + b.getDescription()
                + "\n Brewery Code = " + b.getCode()
                + "\n Brewery Email = " + b.getEmail()
                + "\n Brewery Website = " + b.getWebsite();
    if(b.getId() != id){
    return new ResponseEntity(HttpStatus.NOT_FOUND);
    
    }
    
    
    
    return ResponseEntity.ok(bstring);
       }
    
    @GetMapping("map/{id}")
    public ResponseEntity<String> getmap(@PathVariable long id) throws ApiException, InterruptedException, IOException{
    Optional<Brewery> brewery = breweryservice.findone(id);
    Brewery b = brewery.orElse(new Brewery());
    String html = "<h2>" + b.getName() + ",  " + b.getAddress1() + ", " + b.getAddress2() + " " + b.getCity() + " " + b.getState()
            + "</h2>";
    String address =  b.getAddress1() + ", " + b.getAddress2() + " " + b.getCity() + " " + b.getState();
    //testing out the mapping
   // GeoApiContext apicontext = new GeoApiContext.Builder().apiKey("AIzaSyBx3o8rX4-s5ce_IBKS8QE1URLRP-7m6bQ").build();
   // GeocodingResult[] coderesults = GeocodingApi.geocode(apicontext, html).await();
   // String show = coderesults[0].addressComponents.toString();
    
    
    
//    
//    String addresstest = "<div id=\"map\" style=\"width:400px;height:400px;background:grey\"></div>"
//            + "<script>\n" +
//"function myMap() {\n" +
//"var mapOptions = {\n" +
//"    center: new google.maps.LatLng(51.5, -10.12),\n" +
//"    zoom: 100,\n" +
//"    mapTypeId: google.maps.MapTypeId.HYBRID\n" +
//"}\n" +
//"var map = new google.maps.Map(document.getElementById(\"map\"), mapOptions);\n" +
//"}\n" +
//"</script>\n" +
//"<script src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyBx3o8rX4-s5ce_IBKS8QE1URLRP-7m6bQ&callback=myMap\"></script>\n" +
//"</body>";
   //MapView mv = new MapView();
    return ResponseEntity.ok(html);
    }
    
    //QR 
    @GetMapping(value = "/{id}/qr", produces = MediaType.TEXT_HTML_VALUE)
    public Object  getqrbyid(@PathVariable long id) throws WriterException, IOException 
    {
        Optional<Brewery> brewery = breweryservice.findone(id);
       String path = "C:\\Users\\Xging\\Documents\\Mgn\\GitHub\\assignment-two-k00242185-1\\QRcode.png";
        Brewery b = brewery.orElse(new Brewery());

        String headerqrcode = "BEGIN:VCARD";
        String contactName ="\nN:"+b.getName();
        String contactOrgan = "\nORG:"+b.getName();
        String contactNumber = "\nTEL:" + b.getPhone();
        String website = "\nURL" + b.getWebsite();
        String email = "\nEMAIL" + b.getEmail();
        String address = "\nADR" + b.getAddress1() + b.getAddress2();
        String footer ="\nEND:VCARD";
        
        String final_Vcard =  headerqrcode + contactName + contactOrgan + contactNumber + website + email + address + footer;

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix code =  new MultiFormatWriter().encode(new String(final_Vcard.getBytes("UTF-8"), "UTF-8"), BarcodeFormat.QR_CODE, 250, 250);  
                    //qrCodeWriter.encode(final_Vcard, BarcodeFormat.QR_CODE, 250, 250);
            
         MatrixToImageWriter.writeToFile(code, path.substring(path.lastIndexOf('.') + 1), new File(path));
       //  return path;
       String width = "width:250;";
       String height = "height:250;";
       
    String displayqr = "<img src= 'QRcode.png'  style='"+ width + height +"' />";
        
          return displayqr;
       
    }
   
    }

  

