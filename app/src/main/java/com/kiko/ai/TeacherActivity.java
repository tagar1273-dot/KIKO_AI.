package com.kiko.ai;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;

public class TeacherActivity extends Activity {
    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        LinearLayout l = new LinearLayout(this); l.setOrientation(1); l.setPadding(22,28,22,22);
        TextView title = new TextView(this); title.setText("KIKO AI Teacher"); title.setTextSize(24); l.addView(title);
        TextView intro = new TextView(this);
        intro.setText("اردو اور English میں سیکھیں۔ موضوع منتخب کریں؛ یہ ابتدائی آف لائن سبق ہیں۔\\n\\n" +
          "1) Programming: variable ڈیٹا محفوظ کرتا ہے۔ مثال: int age = 18;\\n\\n" +
          "2) Android App Development: Activity ایپ کی ایک اسکرین ہوتی ہے۔ XML/Java یا Kotlin سے UI بناتے ہیں۔\\n\\n" +
          "3) Website Development: HTML ڈھانچہ، CSS ڈیزائن، JavaScript تعامل کے لیے ہے۔\\n\\n" +
          "4) Ethical Hacking: صرف اپنی یا تحریری اجازت والی لیب پر کام کریں۔ پہلے Linux، networking، HTTP اور محفوظ coding سیکھیں۔\\n\\n" +
          "اگلے مرحلے میں اسباق، کوئز، پریکٹس پروجیکٹس اور cloud AI tutor شامل کیے جا سکتے ہیں۔");
        intro.setTextSize(16); l.addView(intro);
        Button quiz = new Button(this); quiz.setText("چھوٹا کوئز"); quiz.setOnClickListener(v ->
            new android.app.AlertDialog.Builder(this).setTitle("HTML کس کام آتی ہے؟")
              .setItems(new String[]{"ویب صفحے کا ڈھانچہ", "فون چارج کرنا", "وائی فائی بند کرنا"},
                (d,w) -> android.widget.Toast.makeText(this, w==0?"درست جواب!":"دوبارہ کوشش کریں۔",0).show()).show());
        l.addView(quiz); setContentView(l);
    }
}
