package com.example.albaalza.P_MyPage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.albaalza.R;

public class License extends AppCompatActivity {

    TextView l_weekView, l_Progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);

        l_weekView=(TextView)findViewById(R.id.l_weekview);
        l_Progressbar=(TextView)findViewById(R.id.l_progressbar);

        l_weekView.setText("Copyright 2014 Raquib-ul-Alam\n" +
                "\n" +
                "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                "you may not use this file except in compliance with the License.\n" +
                "You may obtain a copy of the License at\n" +
                "\n" +
                "   http://www.apache.org/licenses/LICENSE-2.0\n" +
                "\n" +
                "Unless required by applicable law or agreed to in writing, software\n" +
                "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                "See the License for the specific language governing permissions and\n" +
                "limitations under the License.\n");

        l_Progressbar.setText(" LICENSE Version 2, December 2004\n" +
                "Copyright (C) 2014 Bruce Lee <bruceinpeking#gmail.com>");
    }
}
