package com.studeofin_educaofinanceira;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GerirDespesasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GerirDespesasFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View myView = null;
    TableLayout ll = null;
    EditText Descricao = null;
    EditText Valor =  null;
    Spinner Tipos = null;
    int RowSelectndex = 0;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GerirDespesasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GerirDespesasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GerirDespesasFragment newInstance(String param1, String param2) {
        GerirDespesasFragment fragment = new GerirDespesasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        myView = inflater.inflate(R.layout.fragment_gerir_despesas ,container, false);
        Button SalvarButton = (Button) myView.findViewById(R.id.SalvarDespesa);
        Button ExcluirButton = (Button) myView.findViewById(R.id.ExcluirDespesa);
        Button AdicionarrButton = (Button) myView.findViewById(R.id.AdicionarDespesa);
        Descricao = (EditText) myView.findViewById(R.id.DescricaoText);
        Valor = (EditText) myView.findViewById(R.id.ValorText);
        Tipos = (Spinner) myView.findViewById(R.id.Tipo);
        ll = (TableLayout) myView.findViewById(R.id.Tabela);
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
                if(PodeAdd){
                    AdicionarRow(Descricao.getText().toString(),Valor.getText().toString(),Tipos.getSelectedItem().toString());
                    msgbox("Inserção de Despesa/Receita","Despesa/Receita Adicionada com sucesso");
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
                    msgbox("Exclusão de Despesa/Receita","Despesa/Receita Excluida com sucesso");
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
                    EditarRow(Descricao.getText().toString(),Valor.getText().toString(),Tipos.getSelectedItem().toString());
                    msgbox("Edição de Despesa/Receita","Despesa/Receita Editada com sucesso");
                }
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
                TextView tipoText = ((TextView)((TableRow)ll.getChildAt(RowSelectndex)).getChildAt(2));
                Descricao.setText(descText.getText().toString());
                Valor.setText(valorText.getText().toString());
                Tipos.setSelection(((ArrayAdapter)Tipos.getAdapter()).getPosition(tipoText.getText().toString()));
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

    }

    public void ExcluirRow(int index){
        ll.removeViewAt(index);
        Valor.setText("");
        Descricao.setText("");
    }

    public void EditarRow(String descricao,String valor,String tipo){

                TextView descText = ((TextView)((TableRow)ll.getChildAt(RowSelectndex)).getChildAt(0));
                descText.setText(descricao);
                TextView valorText = ((TextView)((TableRow)ll.getChildAt(RowSelectndex)).getChildAt(1));
                valorText.setText(valor);
                TextView tipoText = ((TextView)((TableRow)ll.getChildAt(RowSelectndex)).getChildAt(2));
                tipoText.setText(tipo);
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
}