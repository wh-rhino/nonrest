package com.core.customer.document;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Gps {    
    @Id
    private ObjectId id;
    private String latitude;
    private String length;
    private String ratio;

    public Gps() {
    }
        
    public Gps(String latitude, String length, String ratio,ObjectId id) {
        this.id = id; this.latitude = latitude; this.length = length; this.ratio = ratio; 
    }

    public String toString(){ 
        return  String.format("Identificador[id=%s, latitude='%s', length='%s', ratio='%s']", id, latitude, length, ratio); 
    }
     

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getId() {
        return id.toHexString();
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

}   