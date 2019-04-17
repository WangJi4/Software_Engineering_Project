package com.example.coursetable;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddCourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        setFinishOnTouchOutside(false); //点击外部窗口时不销毁

        final EditText inputCourseCode = (EditText) findViewById(R.id.course_code);
        final EditText inputCourseName = (EditText) findViewById(R.id.course_name);
        final EditText inputTeacher = (EditText) findViewById(R.id.teacher_name);
        final EditText inputClassRoom = (EditText) findViewById(R.id.class_room);
        final EditText inputDay = (EditText) findViewById(R.id.week);
        final EditText inputStart = (EditText) findViewById(R.id.classes_begin);
        final EditText inputEnd = (EditText) findViewById(R.id.classes_ends);

        Button okButton = (Button) findViewById(R.id.button1);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseCode = inputCourseCode.getText().toString();
                String courseName = inputCourseName.getText().toString();
                String teacher = inputTeacher.getText().toString();
                String classRoom = inputClassRoom.getText().toString();
                String day = inputDay.getText().toString();
                String start = inputStart.getText().toString();
                String end = inputEnd.getText().toString();

                if(courseCode.equals("")||courseName.equals("") || day.equals("") || start.equals("") || end.equals("")){
                    Toast.makeText(AddCourseActivity.this, "基本信息没有填写", Toast.LENGTH_SHORT).show();
                }else {
                    Course course = new Course(courseCode,courseName, teacher, classRoom, Integer.valueOf(day), Integer.valueOf(start), Integer.valueOf(end));
                    Intent intent = new Intent(AddCourseActivity.this, MainActivity.class);
                    intent.putExtra("course", course);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });
        Button cancelButton = (Button) findViewById(R.id.button2);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCourseActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
