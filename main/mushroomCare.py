import Adafruit_DHT
import time
import RPi.GPIO as GPIO
import pyrebase


#for DHT11 sensor----------------------------------------------------------------------------------------------
sensor = Adafruit_DHT.DHT11

#DHT pin connects to GPIO 4-----------------------------------------------------------------------------------
sensor_pin = 4

#set pins to gpio numbers for relay
GPIO.setmode(GPIO.BCM)

#define pins to relay
#for temperature
GPIO.setup(23 , GPIO.OUT)
#for humidity
GPIO.setup(24 , GPIO.OUT)


#key data for firebase configuration--------------------------------------------------------------------------
firebaseConfig = {
    "apiKey": "AIzaSyDCb7rT65PwleQ3fOnrY-NFaU5iHDpcX-4",
    "authDomain": "mushroomcare-raspberry-pi.firebaseapp.com",
    "databaseURL": "https://mushroomcare-raspberry-pi-default-rtdb.asia-southeast1.firebasedatabase.app",
    "projectId": "mushroomcare-raspberry-pi",
    "storageBucket": "mushroomcare-raspberry-pi.appspot.com",
    "messagingSenderId": "95156600036",
    "appId": "1:95156600036:web:bb0d53e207e344d87b40f1",
    "measurementId": "G-VBC6JB312Y"
};

try:
    #loop forever--------------------------------------------------------------------------------------------------------
    while True:


            #read the humidity and temperature----------------------------------------------------------------------------
            humidity, temperature = Adafruit_DHT.read_retry(sensor, sensor_pin)
            

            #uncomment the line below to convert to Fahrenheit
            
            #temperature = temperature * 9/5.0 + 32

            if humidity is not None and temperature is not None:

                #print temperature and humidity
                print('Temperature = ' + str(temperature) +('\t')+'Humidity = ' + str(humidity))
                
                if(temperature<=28):
                    print('Temperature Normal & spinkles off')
                    GPIO.output(23 , GPIO.LOW)

                else:
                    print('Temperature level is High ! & sprinkles on')
                    GPIO.output(23 , GPIO.HIGH)
                    
                if(humidity>=85):
                    print('Humidity level is Normal & humidifier off')
                    GPIO.output(24 , GPIO.LOW)
                    
                else:
                    print('Humidity level is Low ! & humidifier on')
                    GPIO.output(24 , GPIO.HIGH)
                           
                print('')    
                time.sleep(0.5)

            else:
                print('Failed to get reading. Try again!')
                time.sleep(0.5)
                
                
            #initialize firebase    
            firebase = pyrebase.initialize_app(firebaseConfig)
            
            #send data to the database
            storage = firebase.storage()
            database = firebase.database()
            database.child("Mushroom Growing House Condition")
            data = {"Tempurature":temperature,"Humidity":humidity}
            database.set(data)
        
except KeyboardInterrupt:
    print("Program interrupted.")
finally:
    GPIO.cleanup()
