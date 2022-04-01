
package com.sd4.controller;

import com.google.gwt.user.client.ui.Image;
import com.google.zxing.BarcodeFormat;
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
   
      @GetMapping("/{id}")
    public ResponseEntity<Brewery> getbyID(@PathVariable long id)
    {
       Optional<Brewery> brewery = breweryservice.findone(id);
       
        Brewery b = brewery.orElse(new Brewery());
    if(b.getId() != id){
    return new ResponseEntity(HttpStatus.NOT_FOUND);
    
    }
    
    
    
    return ResponseEntity.ok(b);
       }
    
    
    
    //QR 
//    @GetMapping(value = "/{id}/qr", produces = )
//    public Object  getqrbyid(@PathVariable long id) throws WriterException, IOException 
//    {
//        Optional<Brewery> brewery = breweryservice.findone(id);
//       String path = "C:\\Users\\Xging\\Documents";
//        Brewery b = brewery.orElse(new Brewery());
//    if(b.getId() != id){
//        String headerqrcode = "BEGIN:VCARD";
//        String contactName ="\nN:"+b.getName();
//        String contactOrgan = "\nORG:"+b.getName();
//        String contactNumber = "\nTEL:" + b.getPhone();
//        String website = "\nURL" + b.getWebsite();
//        String email = "\nEMAIL" + b.getEmail();
//        String address = "\nADR" + b.getAddress1();
//        String footer ="\nEND:VCARD";
//        
//        String final_Vcard =  headerqrcode + contactName + contactOrgan + contactNumber + website + email + address + footer;
//    
//        QRCodeWriter qrCodeWriter = new QRCodeWriter();
//            BitMatrix code =  qrCodeWriter.encode(final_Vcard, BarcodeFormat.QR_CODE, 250, 250);
//        // MatrixToImageWriter.writeToFile(code, path.substring(path.lastIndexOf('.') + 1), new File(path));
//        // return path;
//    }
//        
//          return b;
//       
//    }
   
    }

  

