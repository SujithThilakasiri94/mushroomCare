import cv2
import numpy as np
from tflite_support.task import processor
import RPi.GPIO as GPIO
import time

_MARGIN = 10  # pixels
_ROW_SIZE = 10  # pixels
_FONT_SIZE = 1
_FONT_THICKNESS = 1
_TEXT_COLOR = (0, 0, 255)  # red


GPIO.setmode(GPIO.BCM)
GPIO.setup(23 , GPIO.OUT)


try:
    def visualize(
        image: np.ndarray,
        detection_result: processor.DetectionResult,
    ) -> np.ndarray:
      
      main = []
      for detection in detection_result.detections:
        # Draw bounding_box
        bbox = detection.bounding_box
        start_point = bbox.origin_x, bbox.origin_y
        end_point = bbox.origin_x + bbox.width, bbox.origin_y + bbox.height
        cv2.rectangle(image, start_point, end_point, _TEXT_COLOR, 3)

        # Draw label and score
        category = detection.classes[0]
        class_name = category.class_name
        main.append(class_name)
        
        #class_name = category.class_name
        probability = round(category.score, 2)
        result_text = class_name + ' (' + str(probability) + ')'
        text_location = (_MARGIN + bbox.origin_x,
                         _MARGIN + _ROW_SIZE + bbox.origin_y)
        cv2.putText(image, result_text, text_location, cv2.FONT_HERSHEY_PLAIN,
                    _FONT_SIZE, _TEXT_COLOR, _FONT_THICKNESS)
        
        if "rat" in main:
            print("Warning on !")
            GPIO.output(23,GPIO.HIGH)
            
        if "cockroach" in main:
            print("Warning on !")
            GPIO.output(23,GPIO.HIGH)
            
        if "" in main:
            print("Warning off")
            GPIO.output(23,GPIO.LOW)

      return image

except KeyboardInterrupt:
    print("Program interrupted.")
finally:
    GPIO.cleanup()
