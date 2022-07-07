package com.studeofin_educaofinanceira;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.studeofin_educaofinanceira.data.model.GerirDespesaClass;
import com.studeofin_educaofinanceira.data.model.ListaDespesa;
import com.studeofin_educaofinanceira.data.model.ListaMetas;
import com.studeofin_educaofinanceira.data.model.MetasClass;
import com.studeofin_educaofinanceira.data.model.YourPreference;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MetasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MetasFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Context context;
    View myView = null;
    TableLayout ll = null;
    EditText Descricao = null;
    EditText Valor =  null;
    EditText DataMeta = null;
    public YourPreference yourPrefrence;
    final Calendar myCalendar= Calendar.getInstance();
    int RowSelectndex = 0;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MetasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MetasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MetasFragment newInstance(String param1, String param2) {
        MetasFragment fragment = new MetasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_metas ,container, false);
        Button SalvarButton = (Button) myView.findViewById(R.id.salvarMeta);
        Button ExcluirButton = (Button) myView.findViewById(R.id.excluirMeta);
        Button AdicionarrButton = (Button) myView.findViewById(R.id.adicionarMeta);
        Descricao = (EditText) myView.findViewById(R.id.descricaoMetaText);
        Valor = (EditText) myView.findViewById(R.id.valorMetaText);
        DataMeta = (EditText) myView.findViewById(R.id.DataMeta);
        yourPrefrence = YourPreference.getInstance(this.getActivity());
        ll = (TableLayout) myView.findViewById(R.id.TabelaMeta);
        AtualizarListaRow();
        AdicionarrButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Boolean PodeAdd = true;
                if(Descricao.getText().length() <= 0){
                    PodeAdd = false;
                    msgbox("Informação Faltante","Campo Descrição em Branco");
                }
                if(Valor.getText().length() <= 0 && PodeAdd){
                    PodeAdd = false;
                    msgbox("Informação Faltante","Campo Valor em Branco");
                }
                if(DataMeta.getText().length() <= 0 && PodeAdd){
                    PodeAdd = false;
                    msgbox("Informação Faltante","Campo Data em Branco");
                }
                if(PodeAdd){
                    AdicionarRow(Descricao.getText().toString(),Valor.getText().toString(),DataMeta.getText().toString());
                    Descricao.setText("");
                    Valor.setText("");
                    DataMeta.setText("");
                    msgbox("Inserção de Meta","Meta Adicionada com sucesso");

                }
            }
        });
        ExcluirButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Boolean PodeExcluir = true;
                if(RowSelectndex <= 0){
                    PodeExcluir = false;
                    msgbox(" Erro na Exclusão","Nenhum item Selecionado");
                }
                if(PodeExcluir){
                    ExcluirRow(RowSelectndex);
                    msgbox("Exclusão de Meta","Meta Excluida com sucesso");
                }
            }
        });
        SalvarButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Boolean PodeEditar = true;
                if(RowSelectndex <= 0){
                    PodeEditar = false;
                    msgbox(" Erro na Edição","Nenhum item Selecionado");
                }
                if(PodeEditar){
                    EditarRow(Descricao.getText().toString(),Valor.getText().toString(),DataMeta.getText().toString());
                    msgbox("Edição de Meta","Meta Editada com sucesso");
                }
            }
        });
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        DataMeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(context,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        // Inflate the layout for this fragment
        return myView;
    }

    public void AdicionarRow(String descricao,String valor,String tipo){

        TableRow row= new TableRow(this.getActivity());
        row.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                RowSelectndex = ll.indexOfChild(v);
                for(int i = 0, j = ll.getChildCount(); i < j; i++) {
                    View view = ll.getChildAt(i);
                    if (view instanceof TableRow) {
                        // then, you can remove the the row you want...
                        // for instance...
                        TableRow row = (TableRow) view;
                        if(RowSelectndex != i){
                            row.setBackgroundColor(Color.TRANSPARENT);
                        }

                    }
                }
                ((TableRow)ll.getChildAt(RowSelectndex)).setBackgroundColor(Color.GRAY);
                TextView descText = ((TextView)((TableRow)ll.getChildAt(RowSelectndex)).getChildAt(0));
                TextView valorText = ((TextView)((TableRow)ll.getChildAt(RowSelectndex)).getChildAt(1));
                TextView dataMeta = ((TextView)((TableRow)ll.getChildAt(RowSelectndex)).getChildAt(2));
                Descricao.setText(descText.getText().toString());
                Valor.setText(valorText.getText().toString());
                DataMeta.setText(dataMeta.getText().toString());
            }
        });;
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);
        TextView qty = new TextView(this.getActivity());
        qty.setText( descricao);
        qty.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        row.addView(qty);
        TextView qty1 = new TextView(this.getActivity());
        qty1.setText(valor);
        qty1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        row.addView(qty1);
        TextView qty2 = new TextView(this.getActivity());
        qty2.setText(tipo);
        qty2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        row.addView(qty2);
        ll.addView(row,ll.getChildCount());
        AtualizarLista();

    }

    public void ExcluirRow(int index){
        ll.removeViewAt(index);
        Valor.setText("");
        Descricao.setText("");
        DataMeta.setText("");
        AtualizarLista();
    }

    public void EditarRow(String descricao,String valor,String tipo){

        TextView descText = ((TextView)((TableRow)ll.getChildAt(RowSelectndex)).getChildAt(0));
        descText.setText(descricao);
        TextView valorText = ((TextView)((TableRow)ll.getChildAt(RowSelectndex)).getChildAt(1));
        valorText.setText(valor);
        TextView tipoText = ((TextView)((TableRow)ll.getChildAt(RowSelectndex)).getChildAt(2));
        tipoText.setText(tipo);
        AtualizarLista();
    }

    public void AtualizarLista(){
        ListaMetas lista = new ListaMetas();
        lista.data = new ArrayList<>();
        for(int i = 0, j = ll.getChildCount(); i < j; i++) {
            View view = ll.getChildAt(i);
            if (view instanceof TableRow && i > 0) {
                // then, you can remove the the row you want...
                // for instance...
                TableRow row = (TableRow) view;
                TextView descText = ((TextView)((TableRow)ll.getChildAt(i)).getChildAt(0));
                TextView valorText = ((TextView)((TableRow)ll.getChildAt(i)).getChildAt(1));
                TextView tipoText = ((TextView)((TableRow)ll.getChildAt(i)).getChildAt(2));

                MetasClass meta = new MetasClass();
                meta.Objetivo = descText.getText().toString();
                meta.Valor = valorText.getText().toString();
                meta.Data = tipoText.getText().toString();
                lista.data.add(meta);
            }
        }
        if(lista.data.size() > 0){
            String str = new Gson().toJson(lista);
            yourPrefrence.saveData("ListaMeta",str);
        }
    }

    public void AtualizarListaRow(){
        String lista = yourPrefrence.getData("ListaMeta");
        if(lista != null  && lista.length() >0 ){
            Type userListType = new TypeToken<ListaMetas>(){}.getType();
            Gson gson = new Gson();
            ListaMetas outputList = gson.fromJson(lista, userListType);
            for (MetasClass var : outputList.data)
            {
                AdicionarRow(var.Objetivo,var.Valor, var.Data);
            }
        }

    }

    public void msgbox(String str,String str2)
    {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this.getActivity());
        dlgAlert.setTitle(str);
        dlgAlert.setMessage(str2);
        dlgAlert.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    private void updateLabel(){
        String myFormat="dd/MM/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        DataMeta.setText(dateFormat.format(myCalendar.getTime()));
    }
}