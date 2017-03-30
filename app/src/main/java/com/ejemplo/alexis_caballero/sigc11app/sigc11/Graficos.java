package com.ejemplo.alexis_caballero.sigc11app.sigc11;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.ejemplo.alexis_caballero.sigc11app.DAO.DAOEmpresasTotales;
import com.ejemplo.alexis_caballero.sigc11app.R;
import com.ejemplo.alexis_caballero.sigc11app.items.ItemEmpresastotales;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.PercentFormatter;

import java.util.ArrayList;

public class Graficos extends AppCompatActivity {

    private PieChart pieChart;
    private DAOEmpresasTotales myDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graficos);

        pieChart = (PieChart) findViewById(R.id.pieChart);
        myDAO = new DAOEmpresasTotales(this);
        setDataChart();//Grafico
    }

    public void setDataChart(){
        myDAO.openConnection();
        ArrayList<ItemEmpresastotales> empresas = myDAO.getAllEmpresas();
        myDAO.closeConnection();

        //propiedades del grafico

        pieChart.setRotationEnabled(true);
        pieChart.animateXY(1000,1000);
        pieChart.setHoleRadius(36f);
        pieChart.setDescription("");

        //valores

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        ArrayList<String>xVals = new ArrayList<String>();

        //Colores de la grafica

        ArrayList<Integer>colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        myDAO.openConnection();
        float total_solicitudes = myDAO.getTotalSolicitudes();
        myDAO.closeConnection();

        //Ingresar datos al grafico


        for (int i=0; i<4;i++) {
            float solicitudes = (empresas.get(i).getSolicitudes()/total_solicitudes)*100f;
            yVals.add(new Entry(solicitudes, i));
            xVals.add(empresas.get(i).getEmpresa());
        }

        PieDataSet dataSet = new PieDataSet(yVals, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(colors);

        PieData data = new PieData(xVals,  dataSet);
        data.setValueFormatter( new PercentFormatter());
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.highlightValues(null);
        pieChart.invalidate();
    }
}
