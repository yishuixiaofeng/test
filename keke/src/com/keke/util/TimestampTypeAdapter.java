package com.keke.util;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class TimestampTypeAdapter implements JsonSerializer<Timestamp>, JsonDeserializer<Timestamp>{  
	private String datePattern;
	private DateFormat format = null;
	
    public String getDatePattern() {
		return datePattern;
	}
	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}
	
	public TimestampTypeAdapter() {
		format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
	}

	public TimestampTypeAdapter(String datePattern) {
		this.datePattern = datePattern;
		format = new SimpleDateFormat(datePattern);      
	}

	public JsonElement serialize(Timestamp ts, Type t, JsonSerializationContext jsc) {      
        String dfString = format.format(new Date(ts.getTime()));      
        return new JsonPrimitive(dfString);      
    }      
    public Timestamp deserialize(JsonElement json, Type t, JsonDeserializationContext jsc) throws JsonParseException {      
        if (!(json instanceof JsonPrimitive)) {      
            throw new JsonParseException("The date should be a string value");      
        }      
     
        try {      
            Date date = format.parse(json.getAsString());      
            return new Timestamp(date.getTime());      
        } catch (ParseException e) {      
            throw new JsonParseException(e);      
        }      
    }      
} 
