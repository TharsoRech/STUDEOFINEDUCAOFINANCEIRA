package com.studeofin_educaofinanceira;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DicasInvestimentoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DicasInvestimentoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View myView = null;
    ListView Lista = null;
    WebView webview = null;
    TableLayout ll = null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DicasInvestimentoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DicasInvestimentoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DicasInvestimentoFragment newInstance(String param1, String param2) {
        DicasInvestimentoFragment fragment = new DicasInvestimentoFragment();
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
        myView = inflater.inflate(R.layout.fragment_dicas_investimento ,container, false);
        webview = (WebView) myView.findViewById(R.id.webView1);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        ll = (TableLayout) myView.findViewById(R.id.TabelaDicas);
        AdicionarItem("https://cmcapital.com.br/blog/dicas-de-investimento-para-iniciantes/","Investimento para Iniciantes","cmcapital.com.br");
        AdicionarItem("https://einvestidor.estadao.com.br/educacao-financeira/dicas-ganhar-dinheiro-bolsa-valores","Dicas da Bolsa de Valores","einvestidor.estadao.com.br");
        AdicionarItem("https://www.youtube.com/watch?v=IR63GWaGmes&t=2s","COMO FAZER A RESERVA DE EMERGÊNCIA?","www.youtube.com");
        // Inflate the layout for this fragment
       return myView;
    }

    public void AdicionarItem(String url,String descricao,String urlinicial){
        TableRow row= new TableRow(this.getActivity());
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);
        TextView qty = new TextView(this.getActivity());;
        qty.setText(descricao);
        qty.setTypeface(null, Typeface.BOLD);
        row.addView(qty);
        ll.addView(row,ll.getChildCount());
        TableRow row2= new TableRow(this.getActivity());
        row2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                ll.setVisibility(View.INVISIBLE);
                webview.setVisibility(View.VISIBLE);
                webview.loadUrl(url);
            }
        });
        TextView qty2 = new TextView(this.getActivity());
        qty2.setText(urlinicial);
        row2.setLayoutParams(lp);
        qty2.setTextColor(Color.parseColor("#0000EE"));
        row2.addView(qty2);
        ll.addView(row2,ll.getChildCount());

    }


}