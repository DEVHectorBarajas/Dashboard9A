
package Models;

import Exceptions.RecordNotFoundException;
import MySql.MySqlConnection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.json.JSONException;
import org.json.JSONObject;

public class Plant {
    //objetos
    private static PreparedStatement command;
    private static ResultSet result;
    
    private int id;
    private double temperature;
    private double moisture;
    private Timestamp date;

    public int getId() {
        return id;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getMoisture() {
        return moisture;
    }

    public void setMoisture(double moisture) {
        this.moisture = moisture;
    }
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
    public String getFormatedDate() { return new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(this.date); }

    public Plant() {
        this.id = 0;
        this.temperature = 0;
        this.moisture = 0;
    }

    public Plant(int id, double temperature, double moisture, Timestamp date) {
        this.id = id;
        this.temperature = temperature;
        this.moisture = moisture;
        this.date = date;
    }
    public Plant(int id) throws RecordNotFoundException{
        try
        {
            //query 
            String query = "select pla_id, pla_temperature, pla_moisture, pla_date from plant where pla_id = ?";
            //prepare statement
            command = MySqlConnection.getConnection().prepareStatement(query);
            //parameters
            command.setInt(1, id);
            //execute query
            result = command.executeQuery();
            //check if found data
            result.first();
            if (result.getRow() > 0) //found data
            {
                //read values, pass them to attributes
                this.id = result.getInt("pla_id");
                this.temperature = result.getDouble("pla_temperature");
                this.moisture = result.getDouble("pla_moisture");
                this.date = result.getTimestamp("pla_date");
            }
            else
                throw new RecordNotFoundException(this.getClass().getName(), String.valueOf(id));
            
        }
        catch(SQLException ex)
        {
            System.out.println("Error: "+ ex);
        }
      
    }
    public Plant(String data) throws ParseException, SQLException{
        try
        {
            JSONObject object = new JSONObject(data);
            this.temperature = object.getDouble("temperature");
            this.moisture = object.getDouble("moisture"); 
            this.date  = Timestamp.valueOf(object.getString("date"));
 
        }
        catch(JSONException ex){
             System.out.println(ex);
        } 
    }
    
    public Boolean add(){
        String dateNow = ""+getHora();
        String query = "INSERT INTO plant(name, temperature, illumination, humidity, time) VALUES "
            + "(Coco,?,?,?,CURRENT_TIMESTAMP)";
            try{
            command = MySqlConnection.getConnection().prepareStatement(query);
            command.setDouble(1, this.temperature);
            command.setDouble(2, this.moisture);
            command.setDouble(3, this.illumination);
            //command.setTimestamp(4, getHora());
            //command.setTimestamp(5, getFecha());
            
            command.executeUpdate(); 
            
            
            }catch(SQLException ex){
                ex.printStackTrace();
            }
            return true;
        
    }
    private static java.sql.Timestamp getHora() {
	java.util.Date today = new java.util.Date();
	return new java.sql.Timestamp(today.getTime());
    }
    
}
