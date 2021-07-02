#include <SPI.h>
#include <Ethernet.h>

// variables ethernet shield
byte mac[] = {  0x90, 0xA2, 0xDA, 0x0D, 0x9A, 0x47 };
IPAddress ip(192,168,1,11);
IPAddress serverIP(192,168,1,10);
EthernetClient client;
int serverPort=8080;
int msg;



void setup() {
  Serial.begin(9600);

  pinMode(4,OUTPUT);
  digitalWrite(4,HIGH);
  
  if (Ethernet.begin(mac) == 0) {
    Serial.println("No se pudo configurar, cambiando a ip statica");
    Ethernet.begin(mac, ip);
  }
  Serial.println(Ethernet.localIP());

  delay(100);
  Serial.println("Connectando al servidor ...");
  
  if (client.connect(serverIP, serverPort)) {
    Serial.println("Se pudo conectar con ip statica mensaje de prueba");
  }
  else {
   Serial.println("Coneccion fallida");
  }
}

void loop() {
  
    float moisture1 = analogRead(A1) ; 
    float m = moisture1 / 10; 
    
    float tempC = analogRead(A0); 
    float t = (5.0 * tempC * 100.0)/1024.0; 
     
    delay(100);

  client.stop();
  
  if(client.connect(serverIP, serverPort)) {
    String json = "{\"temperature\" :"+ String(t)+", \"moisture\" : "+String(m)+ ", \"date\" :"+ "\"2016-11-26 12:12:12\"" + "}";
    //String json = "{\"temperature\" :"+ String(t)+", \"moisture\" : "+String(m)+"}";
    Serial.println(json);
    client.println(json); 
    client.flush();
  }
  else {
   Serial.println("Coneccion fallida");
   Serial.println("Reintentando");
    if (client.connect(serverIP, serverPort)) {
      Serial.println("Conectado");
    }
    else {
      Serial.println("Coneccion fallida");
    }
  }
  delay(100);       
}



