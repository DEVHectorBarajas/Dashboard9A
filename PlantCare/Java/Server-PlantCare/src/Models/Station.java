package Models;

import MySql.MySqlConnection;
import Exceptions.RecordNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

public class Station {
    
     //objetos
    private static PreparedStatement command;
    private static ResultSet result;
    //atributos
    private String id;
    private String description;
    //getters y setters
    public String getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
    //constructores
     public Station() {
        this.id = "";
        this.description = "";
    }
    public void Estacion(String data){
        try
        {
            JSONObject object = new JSONObject(data);
            this.id = object.getString("id");
            this.description = object.getString("description");
        }
        catch(JSONException ex){
             System.out.println(ex);
        } 
    }
     public Station(String id) throws RecordNotFoundException{
        try
        {
            //query 
            String query = "select sta_id, sta_description from station where sta_id = ?";
            //prepare statement
            command = MySqlConnection.getConnection().prepareStatement(query);
            //parameters
            command.setString(1, id);
            //execute query
            result = command.executeQuery();
            //check if found data
            result.first();
            if (result.getRow() > 0) //found data
            {
                //read values, pass them to attributes
                this.id = result.getString("sta_id");
                this.description = result.getString("sta_description");
            }
            else
                throw new RecordNotFoundException(this.getClass().getName(), String.valueOf(id));
            
        }
        catch(SQLException ex)
        {
            
        }
      
    }
    public Station(String id, String description) {
        this.id = id;
        this.description = description;
    }
    //metodos
    public ArrayList<Plant> getPlants() {
         //list
       ArrayList<Plant> list = new ArrayList<Plant>();
       //query
       String query = "select pla_id, pla_temperature, pla_moisture, pla_date from lecturas where pla_station_id = ?";
       try
       {
           //prepare statemnet 
           command = MySqlConnection.getConnection().prepareStatement(query);
           //parameters
           command.setString(1, id);
           //execute query
           result = command.executeQuery();
           //read data
           while(result.next()){
               //add concepts to list
               list.add(new Plant(result.getInt(1), result.getDouble(2), result.getDouble(3), result.getTimestamp(5)));
           }
       }
       catch(SQLException ex)
       {
           
       }
       return list;
    }
    
}
