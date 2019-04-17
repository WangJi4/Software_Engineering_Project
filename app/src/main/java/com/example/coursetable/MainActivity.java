package com.example.coursetable;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout day;

    //创建数据库
    private DatabaseHelper databaseHelper = new DatabaseHelper(this, "database.db", null, 1);


    int currentCoursesNumber = 0;
    int maxCoursesNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setContentView(R.layout.activity_main);
        loadData();
    }

    private void loadData(){
        ArrayList<Course> coursesList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM courses", null);
        if(cursor.moveToFirst()){
            do{
                coursesList.add(new Course(
                        cursor.getString(cursor.getColumnIndex("course_code")),
                        cursor.getString(cursor.getColumnIndex("course_name")),
                        cursor.getString(cursor.getColumnIndex("teacher")),
                        cursor.getString(cursor.getColumnIndex("class_room")),
                        cursor.getInt(cursor.getColumnIndex("day")),
                        cursor.getInt(cursor.getColumnIndex("class_start")),
                        cursor.getInt(cursor.getColumnIndex("class_end"))
                ));
            }while(cursor.moveToNext());
            cursor.close();
        }
        for(Course course: coursesList){
            creatLeftView(course);
            creatItemCourseView(course);
        }
    }

    private void saveData(Course course){
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.execSQL
                ("insert into courses(course_code, course_name, teacher, class_room, day, class_start, class_end) " + "values(?,?, ?, ?, ?, ?, ?)",
                        new String[] {
                                course.getCourseCode(),
                                course.getCourseName(),
                                course.getTeacher(),
                                course.getClassRoom(),
                                course.getDay()+"",
                                course.getStart()+"",
                                course.getEnd()+""}
                );
    }

    private void updateData(Course course){
        deleteData(course);
        saveData(course);
    }

    public void deleteData(Course course){
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from courses where course_code = ?", new String[]{course.getCourseCode()});
    }

    private void creatLeftView(Course course){
        int endNumber = course.getEnd();
        if(endNumber > maxCoursesNumber){
            for(int i = 0; i < endNumber-maxCoursesNumber; i++){
                View view = LayoutInflater.from(this).inflate(R.layout.left_view, null);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(110, 180);
                view.setLayoutParams(params);

                TextView text = view.findViewById(R.id.class_number_text);
                text.setText(String.valueOf(++currentCoursesNumber));
                LinearLayout leftViewLayout = findViewById(R.id.left_view_layout);
                leftViewLayout.addView(view);
            }
            maxCoursesNumber = endNumber;
        }
    }

    private void creatItemCourseView(final Course course){
        int getDay = course.getDay();
        if((getDay<1 || getDay>7)||course.getStart() > course.getEnd()){
            Toast.makeText(this, "Date is invalid", Toast.LENGTH_SHORT);
        }else {
            int dayId = 0;
            switch (getDay) {
                case 1: dayId = R.id.monday; break;
                case 2: dayId = R.id.tuesday; break;
                case 3: dayId = R.id.wednesday; break;
                case 4: dayId = R.id.thursday; break;
                case 5: dayId = R.id.friday; break;
                case 6: dayId = R.id.saturday; break;
                case 7: dayId = R.id.weekday; break;
            }
            day = findViewById(dayId);

            int height = 180;
            final View v = LayoutInflater.from(this).inflate(R.layout.course_card, null);
            v.setY(height * (course.getStart()-1));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,(course.getEnd()-course.getStart()+1)*height - 8
            );
            v.setLayoutParams(params);
            TextView text = v.findViewById(R.id.text_view);
            text.setText(course.getCourseName() + "\n" +course.getTeacher() + "\n" + course.getClassRoom()); //显示课程名
            day.addView(v);
            v.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {
//                    return true;
                    Intent intent = new Intent(MainActivity.this, ChangeCourseInfo.class);
//                    Log.d("Course Code", course.getCourseCode());
                    intent.putExtra("course", course);
//                    setResult(Activity.RESULT_OK, intent);
                    startActivityForResult(intent, 1);
                    deleteData(course);
                    v.setVisibility(View.GONE);
                    day.removeView(v);
                    return true;
                }
            });
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_courses:
                Intent intent = new Intent(MainActivity.this, AddCourseActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.menu_about:
                Intent intent1 = new Intent(this, AboutActivity.class);
                startActivity(intent1);
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //接受别的活动的反馈
        //目前没有反馈
        Log.d("SB", "NMSL" );
        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            Course course = (Course) data.getSerializableExtra("course");
            Log.d("Course Code", course.getCourseCode());
            creatLeftView(course);
            creatItemCourseView(course);
            saveData(course);
        }
        if(requestCode == 1 && resultCode == Activity.RESULT_OK && data != null){
            Course course = (Course) data.getSerializableExtra("course1");
            Log.d("Course Code", course.getCourseCode());
            creatItemCourseView(course);
            updateData(course);
            creatLeftView(course);
        }
    }
}
