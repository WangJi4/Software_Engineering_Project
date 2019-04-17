package com.example.coursetable;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangeCourseInfo extends AppCompatActivity {
    protected final static int result_Code = 17372;
    protected final static int delete_Code = 88;
    private DatabaseHelper databaseHelper = new DatabaseHelper(this, "database.db", null, 1);

    Course course;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_course_info);
        setFinishOnTouchOutside(false);

        final EditText inputCourseCode = (EditText) findViewById(R.id.course_code);
        final EditText inputCourseName = (EditText) findViewById(R.id.course_name);
        final EditText inputTeacher = (EditText) findViewById(R.id.teacher_name);
        final EditText inputClassRoom = (EditText) findViewById(R.id.class_room);
        final EditText inputDay = (EditText) findViewById(R.id.week);
        final EditText inputStart = (EditText) findViewById(R.id.classes_begin);
        final EditText inputEnd = (EditText) findViewById(R.id.classes_ends);

        course = (Course)getIntent().getSerializableExtra("course");

        inputCourseCode.setText(course.getCourseCode());
        inputCourseName.setText(course.getCourseName());
        inputTeacher.setText(course.getTeacher());
        inputClassRoom.setText(course.getClassRoom());
        inputDay.setText(String.valueOf(course.getDay()));
        inputStart.setText(String.valueOf(course.getStart()));
        inputEnd.setText(String.valueOf(course.getEnd()));

        Button button_save_info = (Button)findViewById(R.id.button_save_info);
        button_save_info.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(ChangeCourseInfo.this, "基本信息没有填写", Toast.LENGTH_SHORT).show();
                }else {
                    course = new Course(courseCode,courseName, teacher, classRoom, Integer.valueOf(day), Integer.valueOf(start), Integer.valueOf(end));
                    Intent intent = new Intent(ChangeCourseInfo.this, MainActivity.class);
                    intent.putExtra("course1", course);
                    setResult(RESULT_OK, intent);
//                    startActivityForResult(intent, 1);
//                    startActivity(intent);
                    finish();
                }
            }
        });

        Button button_delete = (Button)findViewById(R.id.button_delete);
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangeCourseInfo.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

//    private void loadCourse(){
//
//    }

    //可能会存在
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if(resultCode == Activity.RESULT_OK && requestCode > 0 && data != null){
//            Log.d("Course Code",course.getCourseCode());
//            course = (Course)data.getSerializableExtra("course");
//        }
//    }

//    protected void saveData(){
//        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
//
//    }
}
