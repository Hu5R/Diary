package com.example.noteapp281;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.noteapp281.bean.Note;
import com.example.noteapp281.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    private EditText etTitle,etContent;

    private NoteDbOpenHelper mNoteDbOpenHelper;
    RadioButton sunny_radio_button,rainy_radio_button,overcast_radio_button,cloudy_radio_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etTitle = findViewById(R.id.et_title);
        etContent = findViewById(R.id.et_content);
        mNoteDbOpenHelper = new NoteDbOpenHelper(this);
        sunny_radio_button = findViewById(R.id.sunny_radio_button);
        rainy_radio_button = findViewById(R.id.rainy_radio_button);
        overcast_radio_button = findViewById(R.id.overcast_radio_button);
        cloudy_radio_button = findViewById(R.id.cloudy_radio_button);
    }

    public void add(View view) {
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();
        if (TextUtils.isEmpty(title)) {
            ToastUtil.toastShort(this, "标题不能为空！");
            return;
        }

        Note note = new Note();

        note.setTitle(title);
        note.setContent(content);
        note.setCreatedTime(getCurrentTimeFormat());
        if (sunny_radio_button.isChecked()){
            note.setWeather(0);
        }else if (rainy_radio_button.isChecked()){
            note.setWeather(1);
        }else if (overcast_radio_button.isChecked()){
            note.setWeather(2);
        }else if (cloudy_radio_button.isChecked()){
            note.setWeather(3);
        }else {
            note.setWeather(0);
        }
        long row = mNoteDbOpenHelper.insertData(note);
        if (row != -1) {
            ToastUtil.toastShort(this,"添加成功！");
            this.finish();
        }else {
            ToastUtil.toastShort(this,"添加失败！");
        }

    }

    private String getCurrentTimeFormat() {
        SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("yyyy年MM月dd HH:mm:ss");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }
}