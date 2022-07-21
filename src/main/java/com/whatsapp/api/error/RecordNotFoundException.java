package com.whatsapp.api.error;

public class RecordNotFoundException extends RuntimeException{
    public RecordNotFoundException(String type, int id){
        super(String.format("%s with id %s not found", type, id));
    }
}
