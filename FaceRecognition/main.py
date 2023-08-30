from flask import Flask, request, jsonify
import face_recognition
import joblib
from sklearn import svm
import numpy as np
from datetime import datetime
import threading
import base64
import os
from flask import send_file
from flask_cors import CORS

app = Flask(__name__)
CORS(app)

model_lock = threading.Lock()  # Lock for model access


def create_faces_dict(folder_path):
    faces_list = []
    for filename in os.listdir(folder_path):
        name, ext = os.path.splitext(filename)
        if ext.lower() in (".jpg", ".png", ".jpeg"):
            face_info = {"name": name, "image": os.path.join(folder_path, filename)}
            faces_list.append(face_info)
    return faces_list


def train_svm_model():
    known_faces = create_faces_dict("data")

    known_face_encodings = []
    known_face_names = []

    for known_face in known_faces:
        image = face_recognition.load_image_file(known_face["image"])
        face_locations = face_recognition.face_locations(image)
        face_encodings = face_recognition.face_encodings(image, face_locations)
        known_face_encodings.extend(face_encodings)
        known_face_names.extend([known_face["name"]] * len(face_encodings))

    # Model
    svm = svm.SVC()

    X = np.array(known_face_encodings)
    y = known_face_names
    
    svm.fit(X, y)
  
    joblib.dump(svm, 'svm_model.pkl')
    print('[Done] Save Model')


@app.route('/process-attendance', methods=['GET', 'POST'])
def process_attendance():
    try:
        # Get image on Spring
        data = request.get_json()
        image_data_base64 = data['image']
  
        image_path = "captured_image.jpg"
        with open(image_path, "wb") as f:
            f.write(base64.b64decode(image_data_base64.split(",")[1]))

        # face recognition
        unknown_image = face_recognition.load_image_file(image_path)
        unknown_face_locations = face_recognition.face_locations(unknown_image)
        unknown_face_encodings = face_recognition.face_encodings(unknown_image, unknown_face_locations)

        # load model
        svc = joblib.load('svm_model.pkl')

        if len(unknown_face_encodings) > 0:
            name = svc.predict(unknown_face_encodings)[0]
            timestamp = datetime.now().strftime('%Y-%m-%d %H:%M:%S')
            response_data = {"name": name, "timestamp": timestamp}
        else:
            response_data = {"name": "Unknown", "timestamp": "Unknown"}

        print("Received Image:", image_path)
        print("Response Data:", response_data)

        return jsonify(response_data)


    except Exception as e:
        error_response = {"error": str(e)}
        print("error: ", error_response)
        return jsonify(error_response), 500

@app.route('/display-captured-image', methods=['GET'])
def display_captured_image():
    return send_file('captured_image.jpg', mimetype='image/jpeg')

def schedule_model_training(interval_in_seconds):
    train_svm_model()  # Initial training
    threading.Timer(interval_in_seconds, schedule_model_training, [interval_in_seconds]).start()

if __name__ == '__main__':
    # Load initial SVM model and start scheduling (e.g., every 24 hours)
    interval_in_seconds = 1 * 60 * 30 
    schedule_model_training(interval_in_seconds)

    app.run(host='0.0.0.0', port=5001)
