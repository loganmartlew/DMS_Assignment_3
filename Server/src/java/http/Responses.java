/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package http;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;

/**
 *
 * @author Logan
 */
public class Responses {
    public static String getUnauthorisedResponse() {
        JsonObjectBuilder object = Json.createObjectBuilder();
        object.add("error", "You are unauthorised, please send authorisation to access this resource");
        
        String jsonString;
        try(Writer writer = new StringWriter()) {
            Json.createWriter(writer).write(object.build());
            jsonString = writer.toString();
        } catch (IOException e) {
            jsonString = "{}";
        }
        
        return jsonString;
    }
    
    public static String getMissingDataResponse() {
        JsonObjectBuilder object = Json.createObjectBuilder();
        object.add("error", "Request was missing a field and was unable to be handled");
        
        String jsonString;
        try(Writer writer = new StringWriter()) {
            Json.createWriter(writer).write(object.build());
            jsonString = writer.toString();
        } catch (IOException e) {
            jsonString = "{}";
        }
        
        return jsonString;
    }
}
