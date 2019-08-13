package com.core.customer.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import com.core.customer.document.Gps;
import com.core.customer.interfaz.GpsRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.core.customer.exception.GpsNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
class GpsController{

    @Autowired(required=true)
    private GpsRepository repository;

    private static final String UriRaspiLogerReport = "http://104.155.172.194/RaspiLogerReport/rep/inc/personReport";
    private static final String UriHeaderKey = "STK-KEY";
    private static final String UriHeaderValue = "OK";

    /**
     * curl localhost:8080
     */
    @RequestMapping("/")
    public void raspiLogerReport(){
        RestTemplate customerSitrack = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        headers.set(UriHeaderKey, UriHeaderValue);
        
        final HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<String> response = customerSitrack.exchange(UriRaspiLogerReport, HttpMethod.GET, entity, String.class);   

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            System.out.println("response: "+ root.toString());
            Iterator<JsonNode> iter=root.iterator();
            while(iter.hasNext()){
                JsonNode parameterNode=iter.next(); 
                System.out.println("parameterNode : " + parameterNode);
                //Jul 24, 1983 3:41:21 AM
                //calcular edad 
                System.out.println("dateOfBirth: "+ parameterNode.get("fechaNacimiento").toString());
                //ordenar
            }
        } catch (IOException e) {
            e.printStackTrace();
	    }
    }

    /**
     * curl localhost:8080/geographicalcoordinates/5d4ef92b66dca14d45fdffbd
     * @param id
     * @return
     */
    @GetMapping("/geographicalcoordinates/{id}")
    Gps getGeographicalCoordinatesById(@PathVariable ObjectId id){
        return repository.findById(id)
			.orElseThrow(() -> new GpsNotFoundException(id));
    }

    /**
     * curl -X POST localhost:8080/geographicalcoordinates -H 'Content-type:application/json' -d '{"latitude": "0.10", "length": "0.10", "ratio": "0.44"}'
     */
    @PostMapping("/geographicalcoordinates")
    Gps createGeographicalCoordinates(@RequestBody Gps newGps){
        return repository.save(newGps);
    }

    /**
     * curl localhost:8080/geographicalcoordinates/   
     */
    @GetMapping("/geographicalcoordinates")
    List<Gps> getGeographicalCoordinates(){
        return repository.findAll();
    }

    /**
     * curl -X PUT localhost:8080/geographicalcoordinates/5d4f5e6f36f6a3049689e2fa -H 'Content-type:application/json' -d '{"latitude": "0.09", "length": "0.1", "ratio": "0.2"}'
     * @param newCustomer
     * @param id
     * @return
     */
    @PutMapping("/geographicalcoordinates/{id}")
    Gps updateGeographicalCoordinatesById(@RequestBody Gps newGps, @PathVariable ObjectId id){
        return repository.findById(id)
            .map(gps -> {
                gps.setLatitude(newGps.getLatitude());  
                gps.setLength(newGps.getLength());
                return repository.save(gps);
            })
            .orElseGet(() -> {
                newGps.setId(id);
                return repository.save(newGps);
            });
    }

    /**
     * curl -X DELETE localhost:8080/geographicalcoordinates/5d4efd19f5e5480b32bd1339
     */
    @DeleteMapping("/geographicalcoordinates/{id}")
    void deleteGeographicalCoordinatesById(@PathVariable ObjectId id){
        repository.deleteById(id);
    }
}