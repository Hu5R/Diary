package com.example.noteapp281;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.noteapp281.bean.Note;
import com.example.noteapp281.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditActivity extends AppCompatActivity {

    private Note note;
    private EditText etTitle,etContent;

    private NoteDbOpenHelper mNoteDbOpenHelper;
    RadioButton sunny_radio_button,rainy_radio_button,overcast_radio_button,cloudy_radio_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etTitle = findViewById(R.id.et_title);
        etContent = findViewById(R.id.et_content);
        sunny_radio_button = findViewById(R.id.sunny_radio_button);
        rainy_radio_button = findViewById(R.id.rainy_radio_button);
        overcast_radio_button = findViewById(R.id.overcast_radio_button);
        cloudy_radio_button = findViewById(R.id.cloudy_radio_button);

        initData();

    }

    private void initData() {
        Intent intent = getIntent();
        note = (Note) intent.getSerializableExtra("note");
        if (note != null) {
            etTitle.setText(note.getTitle());
            etContent.setText(note.getContent());
            switch (note.getWeather()) {
                case 0: sunny_radio_button.setChecked(true);break;
                case 1: rainy_radio_button.setChecked(true);break;
                case 2: overcast_radio_button.setChecked(true);break;
                case 3: cloudy_radio_button.setChecked(true);break;

            }
        }
        mNoteDbOpenHelper = new NoteDbOpenHelper(this);
    }

    public void save(View view) {
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();
        if (TextUtils.isEmpty(title)) {
            ToastUtil.toastShort(this, "标题不能为空！");
            return;
        }

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
        long rowId = mNoteDbOpenHelper.updateData(note);
        if (rowId != -1) {
            ToastUtil.toastShort(this, "修改成功！");
            this.finish();
        }else{
            ToastUtil.toastShort(this, "修改失败！");
        }

    }

    private String getCurrentTimeFormat() {
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy年MM月dd HH:mm:ss");
        Date date = new Date();
        return sdf.format(date);
    }
}