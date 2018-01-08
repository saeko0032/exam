package com.example.saeko.currencyapp;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saeko.currencyapp.network.RetrofitBuilder;
import com.example.saeko.currencyapp.network.model.Aud;
import com.example.saeko.currencyapp.network.model.Cad;
import com.example.saeko.currencyapp.network.model.Cny;
import com.example.saeko.currencyapp.network.model.CurrencyInfo;
import com.example.saeko.currencyapp.network.model.Eur;
import com.example.saeko.currencyapp.network.model.Gbp;
import com.example.saeko.currencyapp.network.model.Inr;
import com.example.saeko.currencyapp.network.model.Jpy;
import com.example.saeko.currencyapp.network.model.Nzd;
import com.example.saeko.currencyapp.network.model.Usd;
import com.example.saeko.currencyapp.network.model.Zar;
import com.example.saeko.currencyapp.network.response.MyCurrencyResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RateActivity extends AppCompatActivity {

    private List<CurrencyInfo> currencyResponseList;
    private Spinner spinner;
    private Button refreshButton;
    private TextView timeTextView;
    private Button convertButton;
    private RecyclerView recyclerView;
    private RateAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        timeTextView = (TextView) findViewById(R.id.timestamp);

        spinner = (Spinner) findViewById(R.id.order_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                // when user select spinner value, connect to server to get data
                loadData();
                if (!currencyResponseList.isEmpty()) {
                    // when request has some data, update current time
                    adapter.notifyDataSetChanged();
                    updateTime();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // when user select spinner value, connect to server to get data
                loadData();
                if (!currencyResponseList.isEmpty()) {
                    // when request has some data, update current time
                    adapter.notifyDataSetChanged();
                    updateTime();
                }
            }
        });

        refreshButton = (Button) findViewById(R.id.refresh_button);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // when user press refresh button, connect to server to get data
                loadData();
                if (!currencyResponseList.isEmpty()) {
                    // when request has some data, update current time
                    adapter.notifyDataSetChanged();
                    updateTime();
                }
            }
        });

        convertButton = (Button) findViewById(R.id.conversion_button);
        convertButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // when user press convert button, show dialog
                showDialog();
            }
        });

        // initialize list
        currencyResponseList = new ArrayList<>();

        // make Recycler List
        recyclerView = (RecyclerView) findViewById(R.id.rate_recycler);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, 0));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RateAdapter(currencyResponseList, this.getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    public void loadData() {
        Call<MyCurrencyResponse> call = RetrofitBuilder.getCoineyApi().getMyCurrencies();
        try {
            call.enqueue(new Callback<MyCurrencyResponse>() {
                @Override
                public void onResponse(Call<MyCurrencyResponse> call, Response<MyCurrencyResponse> response) {
                    if (response.isSuccessful()) {
                        MyCurrencyResponse body = response.body();

                        // clear current list to avoid duplicate data
                        currencyResponseList.clear();
                        currencyResponseList.addAll(makeResponseList(body));
                        adapter.notifyDataSetChanged();
                    } else {
                        switch ((response.code())) {
                            case 404:
                                Toast.makeText(getApplicationContext(), "the request was not found", Toast.LENGTH_SHORT).show();
                                break;
                            case 500:
                                Toast.makeText(getApplicationContext(), "server is not working", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(getApplicationContext(), "unknown error", Toast.LENGTH_SHORT).show();
                                break;
                        }

                    }
                }

                @Override
                public void onFailure(Call<MyCurrencyResponse> call, Throwable t) {
                    new AlertDialog.Builder(RateActivity.this)
                            .setTitle("No Internet Connection")
                            .setPositiveButton(
                                    "Yes",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    }).show();
                    Toast.makeText(getApplicationContext(), "failure", Toast.LENGTH_LONG).show();;
                }
            });
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "exception", Toast.LENGTH_LONG).show();
        }
    }

    public List<CurrencyInfo> makeResponseList(MyCurrencyResponse body) {
        List<CurrencyInfo> latestList = new ArrayList<>();
        switch ((String)spinner.getSelectedItem()) {
            case "aud":
                Aud jsonCurrency1 = body.getAud();
                latestList.add(new CurrencyInfo("cad", jsonCurrency1.getCad()));
                latestList.add(new CurrencyInfo("cny", jsonCurrency1.getCny()));
                latestList.add(new CurrencyInfo("eur", jsonCurrency1.getEur()));
                latestList.add(new CurrencyInfo("gbp", jsonCurrency1.getGbp()));
                latestList.add(new CurrencyInfo("inr", jsonCurrency1.getInr()));
                latestList.add(new CurrencyInfo("jpy", jsonCurrency1.getJpy()));
                latestList.add(new CurrencyInfo("nzd", jsonCurrency1.getNzd()));
                latestList.add(new CurrencyInfo("usd", jsonCurrency1.getUsd()));
                latestList.add(new CurrencyInfo("zar", jsonCurrency1.getZar()));
                break;
            case "cad":
                Cad jsonCurrency2 = body.getCad();
                latestList.add(new CurrencyInfo("aud", jsonCurrency2.getAud()));
                latestList.add(new CurrencyInfo("cny", jsonCurrency2.getCny()));
                latestList.add(new CurrencyInfo("eur", jsonCurrency2.getEur()));
                latestList.add(new CurrencyInfo("gbp", jsonCurrency2.getGbp()));
                latestList.add(new CurrencyInfo("inr", jsonCurrency2.getInr()));
                latestList.add(new CurrencyInfo("jpy", jsonCurrency2.getJpy()));
                latestList.add(new CurrencyInfo("nzd", jsonCurrency2.getNzd()));
                latestList.add(new CurrencyInfo("usd", jsonCurrency2.getUsd()));
                latestList.add(new CurrencyInfo("zar", jsonCurrency2.getZar()));
                break;
            case "cny":
                Cny jsonCurrency3 = body.getCny();
                latestList.add(new CurrencyInfo("aud", jsonCurrency3.getAud()));
                latestList.add(new CurrencyInfo("cad", jsonCurrency3.getCad()));
                latestList.add(new CurrencyInfo("eur", jsonCurrency3.getEur()));
                latestList.add(new CurrencyInfo("gbp", jsonCurrency3.getGbp()));
                latestList.add(new CurrencyInfo("inr", jsonCurrency3.getInr()));
                latestList.add(new CurrencyInfo("jpy", jsonCurrency3.getJpy()));
                latestList.add(new CurrencyInfo("nzd", jsonCurrency3.getNzd()));
                latestList.add(new CurrencyInfo("usd", jsonCurrency3.getUsd()));
                latestList.add(new CurrencyInfo("zar", jsonCurrency3.getZar()));
                break;
            case "eur":
                Eur jsonCurrency4 = body.getEur();
                latestList.add(new CurrencyInfo("aud", jsonCurrency4.getAud()));
                latestList.add(new CurrencyInfo("cad", jsonCurrency4.getCad()));
                latestList.add(new CurrencyInfo("cny", jsonCurrency4.getCny()));
                latestList.add(new CurrencyInfo("gbp", jsonCurrency4.getGbp()));
                latestList.add(new CurrencyInfo("inr", jsonCurrency4.getInr()));
                latestList.add(new CurrencyInfo("jpy", jsonCurrency4.getJpy()));
                latestList.add(new CurrencyInfo("nzd", jsonCurrency4.getNzd()));
                latestList.add(new CurrencyInfo("usd", jsonCurrency4.getUsd()));
                latestList.add(new CurrencyInfo("zar", jsonCurrency4.getZar()));
                break;
            case "gbp":
                Gbp jsonCurrency5 = body.getGbp();
                latestList.add(new CurrencyInfo("aud", jsonCurrency5.getAud()));
                latestList.add(new CurrencyInfo("cad", jsonCurrency5.getCad()));
                latestList.add(new CurrencyInfo("cny", jsonCurrency5.getCny()));
                latestList.add(new CurrencyInfo("eur", jsonCurrency5.getEur()));
                latestList.add(new CurrencyInfo("inr", jsonCurrency5.getInr()));
                latestList.add(new CurrencyInfo("jpy", jsonCurrency5.getJpy()));
                latestList.add(new CurrencyInfo("nzd", jsonCurrency5.getNzd()));
                latestList.add(new CurrencyInfo("usd", jsonCurrency5.getUsd()));
                latestList.add(new CurrencyInfo("zar", jsonCurrency5.getZar()));
                break;
            case "inr":
                Inr jsonCurrency6 = body.getInr();
                latestList.add(new CurrencyInfo("aud", jsonCurrency6.getAud()));
                latestList.add(new CurrencyInfo("cad", jsonCurrency6.getCad()));
                latestList.add(new CurrencyInfo("cny", jsonCurrency6.getCny()));
                latestList.add(new CurrencyInfo("eur", jsonCurrency6.getEur()));
                latestList.add(new CurrencyInfo("gbp", jsonCurrency6.getGbp()));
                latestList.add(new CurrencyInfo("jpy", jsonCurrency6.getJpy()));
                latestList.add(new CurrencyInfo("nzd", jsonCurrency6.getNzd()));
                latestList.add(new CurrencyInfo("usd", jsonCurrency6.getUsd()));
                latestList.add(new CurrencyInfo("zar", jsonCurrency6.getZar()));
                break;
            case "jpy":
                Jpy jsonCurrency7 = body.getJpy();
                latestList.add(new CurrencyInfo("aud", jsonCurrency7.getAud()));
                latestList.add(new CurrencyInfo("cad", jsonCurrency7.getCad()));
                latestList.add(new CurrencyInfo("cny", jsonCurrency7.getCny()));
                latestList.add(new CurrencyInfo("eur", jsonCurrency7.getEur()));
                latestList.add(new CurrencyInfo("gbp", jsonCurrency7.getGbp()));
                latestList.add(new CurrencyInfo("inr", jsonCurrency7.getInr()));
                latestList.add(new CurrencyInfo("nzd", jsonCurrency7.getNzd()));
                latestList.add(new CurrencyInfo("usd", jsonCurrency7.getUsd()));
                latestList.add(new CurrencyInfo("zar", jsonCurrency7.getZar()));
                break;
            case "nzd":
                Nzd jsonCurrency8 = body.getNzd();
                latestList.add(new CurrencyInfo("aud", jsonCurrency8.getAud()));
                latestList.add(new CurrencyInfo("cad", jsonCurrency8.getCad()));
                latestList.add(new CurrencyInfo("cny", jsonCurrency8.getCny()));
                latestList.add(new CurrencyInfo("eur", jsonCurrency8.getEur()));
                latestList.add(new CurrencyInfo("gbp", jsonCurrency8.getGbp()));
                latestList.add(new CurrencyInfo("inr", jsonCurrency8.getInr()));
                latestList.add(new CurrencyInfo("jpy", jsonCurrency8.getJpy()));
                latestList.add(new CurrencyInfo("usd", jsonCurrency8.getUsd()));
                latestList.add(new CurrencyInfo("zar", jsonCurrency8.getZar()));
                break;
            case "usd":
                Usd jsonCurrency9 = body.getUsd();
                latestList.add(new CurrencyInfo("aud", jsonCurrency9.getAud()));
                latestList.add(new CurrencyInfo("cad", jsonCurrency9.getCad()));
                latestList.add(new CurrencyInfo("cny", jsonCurrency9.getCny()));
                latestList.add(new CurrencyInfo("eur", jsonCurrency9.getEur()));
                latestList.add(new CurrencyInfo("gbp", jsonCurrency9.getGbp()));
                latestList.add(new CurrencyInfo("inr", jsonCurrency9.getInr()));
                latestList.add(new CurrencyInfo("jpy", jsonCurrency9.getJpy()));
                latestList.add(new CurrencyInfo("nzd", jsonCurrency9.getNzd()));
                latestList.add(new CurrencyInfo("zar", jsonCurrency9.getZar()));
                break;
            case "zar":
                Zar jsonCurrency10 = body.getZar();
                latestList.add(new CurrencyInfo("aud", jsonCurrency10.getAud()));
                latestList.add(new CurrencyInfo("cad", jsonCurrency10.getCad()));
                latestList.add(new CurrencyInfo("cny", jsonCurrency10.getCny()));
                latestList.add(new CurrencyInfo("eur", jsonCurrency10.getEur()));
                latestList.add(new CurrencyInfo("gbp", jsonCurrency10.getGbp()));
                latestList.add(new CurrencyInfo("inr", jsonCurrency10.getInr()));
                latestList.add(new CurrencyInfo("jpy", jsonCurrency10.getJpy()));
                latestList.add(new CurrencyInfo("nzd", jsonCurrency10.getNzd()));
                latestList.add(new CurrencyInfo("usd", jsonCurrency10.getUsd()));
                break;
            default:
                break;
        }
        return latestList;
    }

    private void showDialog() {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        int selectedIndex = spinner.getSelectedItemPosition();
        DialogFragment newFragment = ConversionDialogFragment.newInstance(7, selectedIndex);
        newFragment.show(ft, "dialog");
    }

    private void updateTime() {
        final DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        final Date date = new Date(System.currentTimeMillis());
        timeTextView.setText(df.format(date).toString());
    }
}
