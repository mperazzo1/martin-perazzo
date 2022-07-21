package com.whatsapp.api.error;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class RecordNotFoundException extends RuntimeException{
    public RecordNotFoundException(String type, int id){
        super(String.format("%s with id %s not found", type, id));
    }

    public RecordNotFoundException(String type, List<Integer> ids){
        super(String.format("%s with ids %s not found", type, String.join(", ", ids.stream().map(id -> id.toString()).collect(Collectors.toUnmodifiableList()))));
    }
}
