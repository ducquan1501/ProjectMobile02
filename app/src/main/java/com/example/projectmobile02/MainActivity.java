package com.example.projectmobile02;


import android.graphics.Color;
import android.os.Bundle;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText editText1, editText2, editText3, editText4, editText5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1. Gọi các thành phần từ Layout, thiết lập tham số
        LineChart lineChart = findViewById(R.id.chart);
        editText1 = findViewById(R.id.parameter1);
        editText2 = findViewById(R.id.parameter2);
        editText3 = findViewById(R.id.parameter3);
        editText4 = findViewById(R.id.parameter4);
        editText5 = findViewById(R.id.parameter5);
        Button button = (Button) findViewById(R.id.executeB);
        //2. Tạo đường trục tung, trục hoành
        ArrayList<Entry> zeroLineEntriesX = new ArrayList<>();
        for(float x0 = -15; x0 <= 15; x0+=0.1)
        {
            float y0 = 0;
            zeroLineEntriesX.add(new Entry(x0, y0));
        }

        ArrayList<Entry> zeroLineEntriesY = new ArrayList<>();
        zeroLineEntriesY.add(new Entry(0f, 100f));
        zeroLineEntriesY.add(new Entry(0f, -100f));

        //2. Tạo DataSet cho các đường trục tung, trục hoành
        LineDataSet zeroLineDataSetX = new LineDataSet(zeroLineEntriesX, "Ox");
        zeroLineDataSetX.setDrawCircles(false);
        zeroLineDataSetX.setColor(Color.BLACK);
        zeroLineDataSetX.setLineWidth(3f);

        LineDataSet zeroLineDataSetY = new LineDataSet(zeroLineEntriesY, "Oy");
        zeroLineDataSetY.setDrawCircles(false);
        zeroLineDataSetY.setColor(Color.BLACK);
        zeroLineDataSetY.setLineWidth(3f);

        LineData lineData = new LineData(zeroLineDataSetX, zeroLineDataSetY);

        //3. Cấu hình đồ thị
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMaximum(15f);
        xAxis.setAxisMinimum(-15f);
        xAxis.setDrawGridLines(true);
        xAxis.setGranularity(0.1f);
        xAxis.setLabelCount(10);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return String.valueOf(value);
            }
        });

        YAxis yAxis = lineChart.getAxisLeft();
        YAxis yAxisR = lineChart.getAxisRight();
        yAxisR.setEnabled(false);
        yAxis.setDrawLabels(true);
        yAxis.setAxisMaximum(100f);
        yAxis.setAxisMinimum(-100f);
        yAxis.setGranularity(0.1f);
        yAxis.setLabelCount(16);

        lineChart.setData(lineData);
        Legend lg = lineChart.getLegend();
        lg.setEnabled(false);
        Description dp = new Description();
        dp.setText("Đồ thị hàm số");
        dp.setTextColor(Color.RED);
        dp.setTextSize(10f);
        lineChart.setDescription(dp);
        lineChart.invalidate();

        //4. Thiết lập chức năng cho nút Button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //4.1. Xử lý các editText trống
                String et1 = editText1.getText().toString();
                if(et1.isEmpty()) {
                    editText1.setText("0");
                }
                String et2 = editText2.getText().toString();
                if(et2.isEmpty()){
                    editText2.setText("0");
                }
                String et3 = editText3.getText().toString();
                if(et3.isEmpty()){
                    editText3.setText("0");
                }
                String et4 = editText4.getText().toString();
                if(et4.isEmpty()){
                    editText4.setText("0");
                }
                String et5 = editText5.getText().toString();
                if(et5.isEmpty()){
                    editText5.setText("0");
                }
                Float p1 = Float.parseFloat(editText1.getText().toString());
                Float p2 = Float.parseFloat(editText2.getText().toString());
                Float p3 = Float.parseFloat(editText3.getText().toString());
                Float p4 = Float.parseFloat(editText4.getText().toString());
                Float p5 = Float.parseFloat(editText5.getText().toString());

                //4.2. Tạo mảng chứa tập hợp các điểm hình thành nên đồ thị
                List<Entry> entries = new ArrayList<>();
                for (float x = -15; x <= 15; x += 0.02)
                {
                    float y = p1*x*x*x*x + p2*x*x*x + p3*x*x + p4*x + p5;
                    entries.add(new Entry(x, y));
                }
                //4.3 Khai báo dataSet
                LineDataSet dataSet = new LineDataSet(entries, "y = "+p1+"x^4 +" +p2+"x^3 + " +p3+"x^2 + "+p4+"x+ "+p5);
                dataSet.setColor(Color.RED);
                dataSet.setDrawValues(false);
                dataSet.setDrawCircles(false);
                dataSet.setLineWidth(2f);

                //4.4. Tạo một LineData chứa LineDataSet của đường kẻ
                lineData.addDataSet(dataSet);
                lineChart.setData(lineData);

                //4.5. Vẽ đồ thị
                lineChart.invalidate();
            }
        });
    }
}
