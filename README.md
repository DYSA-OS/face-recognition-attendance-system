# Face Recognition Attendance System
An integrated management system to manage student attendance and grades. Facial recognition is performed by webcam in the professor's classroom and attendance is verified. Students can enter each classroom and check their attendance in real-time and also can check there grades.

## Model
### Diagram
![image](https://github.com/DYSA-OS/face-recognition-attendance-system/assets/93754504/f0c1e008-3fac-446c-9869-90d7eaebdf47)

### 1. Face Recognition
[main.py](https://github.com/4th-Oasis-Hackathon/kimkangjeong/blob/main/FaceRecognition/main.py) is **python server** that predicts the username from the captured image with SVM. The model retrains at intervals to learn about new users coming into the data folder. So you have to set interval times on ```interval_in_seconds```

```
if __name__ == '__main__':
    interval_in_seconds = 1 * 60 * 30 
    schedule_model_training(interval_in_seconds)

    app.run(host='0.0.0.0', port=5001)
```
#### structure
![image](https://github.com/DYSA-OS/face-recognition-attendance-system/assets/93754504/009bc588-32f5-4f85-8d9a-dc77cdff47d8)

#### setting
- install libraries ```pip install -r requirements.txt```
- delete files in [📁 data](https://github.com/4th-Oasis-Hackathon/kimkangjeong/tree/main/FaceRecognition/data) for reset datasets
- run the code while using website ```python main.py```
  
### 2. Website
```
📁AttendanceApp
├── HELP.md
├── build
├── build.gradle
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── settings.gradle
└── src
    └── main
        ├── generated
        ├── java
        │   └── FaceAuto
        │       └── AttendanceApp
        │           ├── AttendanceAppApplication.java 
        │           ├── CORSConfig.java
        │           ├── WebMvcConfiguration.java
        │           ├── domain
        │           │   ├── Attendance.java
        │           │   ├── AttendanceStatus.java
        │           │   ├── Course.java
        │           │   ├── Grade.java
        │           │   ├── Professor.java
        │           │   ├── Student.java
        │           │   └── StudentCourse.java
        │           ├── repository
        │           │   ├── AttendanceRepository.java
        │           │   ├── CourseRepository.java
        │           │   ├── GradeRepository.java
        │           │   ├── ProfessorRepository.java
        │           │   ├── StudentCourseRepository.java
        │           │   └── StudentRepository.java
        │           ├── service
        │           │   ├── AttendanceService.java
        │           │   ├── CourseService.java
        │           │   ├── FileStorageService.java
        │           │   ├── ProfessorService.java
        │           │   ├── StudentCourseService.java
        │           │   └── StudentService.java
        │           └── web
        │               ├── AttendanceRequest.java
        │               ├── CourseController.java
        │               ├── CourseForm.java
        │               ├── GradeRequest.java
        │               ├── ProfessorController.java
        │               ├── ProfessorForm.java
        │               ├── StudentController.java
        │               └── StudentForm.java
        └── resources
            ├── application.properties
            ├── application.yml
            ├── static
            └── templates
                ├── fragments
                │   ├── bodyHeader.html
                │   ├── footer.html
                │   └── header.html
                ├── professor
                │   ├── add-course.html
                │   ├── attendance.html
                │   ├── attendance_result.html
                │   ├── course-details.html
                │   ├── grade-form.html
                │   ├── index.html
                │   ├── professor-details.html
                │   └── sign-up.html
                └── student
                    ├── index.html
                    ├── sign-up.html
                    ├── student-details.html
                    ├── student-grade.html
                    ├── student-grades-attendances.html
                    └── table.html        
```

#### Function
- sign-up
    - professor
    - student
- professor
    - add course
    - give grades
    - mark Attendance(by.face recognition)
- student
    - check course
    - check grades
    - check attendance

