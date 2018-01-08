package com.example.saeko.currencyapp;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saeko.currencyapp.network.RetrofitBuilder;
import com.example.saeko.currencyapp.network.model.Aud;
import com.example.saeko.currencyapp.network.model.Cad;
import com.example.saeko.currencyapp.network.model.Cny;
import com.example.saeko.currencyapp.network.model.Eur;
import com.example.saeko.currencyapp.network.model.Gbp;
import com.example.saeko.currencyapp.network.model.Inr;
import com.example.saeko.currencyapp.network.model.Jpy;
import com.example.saeko.currencyapp.network.model.Nzd;
import com.example.saeko.currencyapp.network.model.Usd;
import com.example.saeko.currencyapp.network.model.Zar;
import com.example.saeko.currencyapp.network.response.MyCurrencyResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by saeko on 2018-01-07.
 */

public class ConversionDialogFragment extends DialogFragment {

    int mNum;
    int mPos;
    double resultValue;
    double rate;

    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    static ConversionDialogFragment newInstance(int num, int pos) {
        ConversionDialogFragment f = new ConversionDialogFragment();
        Bundle args = new Bundle();
        args.putInt("num", num);
        args.putInt("pos", pos);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments().getInt("num");

        // Pick a style based on the num.
        int style = DialogFragment.STYLE_NORMAL, theme = 0;
        switch ((mNum-1)%6) {
            case 1: style = DialogFragment.STYLE_NO_TITLE; break;
            case 2: style = DialogFragment.STYLE_NO_FRAME; break;
            case 3: style = DialogFragment.STYLE_NO_INPUT; break;
            case 4: style = DialogFragment.STYLE_NORMAL; break;
            case 5: style = DialogFragment.STYLE_NORMAL; break;
            case 6: style = DialogFragment.STYLE_NO_TITLE; break;
            case 7: style = DialogFragment.STYLE_NO_FRAME; break;
            case 8: style = DialogFragment.STYLE_NORMAL; break;
        }
        switch ((mNum-1)%6) {
            case 4: theme = android.R.style.Theme_Holo; break;
            case 5: theme = android.R.style.Theme_Holo_Light_Dialog; break;
            case 6: theme = android.R.style.Theme_Holo_Light; break;
            case 7: theme = android.R.style.Theme_Holo_Light_Panel; break;
            case 8: theme = android.R.style.Theme_Holo_Light; break;
        }
        setStyle(style, theme);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Dialog dialog = getDialog();

        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int dialogWidth = (int) (metrics.widthPixels * 0.8);
        int dialogHeight = (int) (metrics.heightPixels * 0.25);

        lp.width = dialogWidth;
        lp.height = dialogHeight;
        dialog.getWindow().setAttributes(lp);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_conversion, container, false);
        View tv = v.findViewById(R.id.text);
        final Spinner baseSpinner = (Spinner) v.findViewById(R.id.base_currency_conversion_spinner);
        final Spinner targetSpinner = (Spinner) v.findViewById(R.id.target_currency_conversion_spinner);
        final EditText amountEdit = (EditText) v.findViewById(R.id.amount_currency_conversion_edit);
        final TextView targetText = (TextView) v.findViewById(R.id.target_currency_conversion_text);


        mPos = getArguments().getInt("pos");

        baseSpinner.setSelection(mPos);

        amountEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // when user press enter
                final String baseCurrency = baseSpinner.getSelectedItem().toString();
                final String targetCurrency = targetSpinner.getSelectedItem().toString();
                final int amount = Integer.parseInt(amountEdit.getText().toString());

                Call<MyCurrencyResponse> call = RetrofitBuilder.getCoineyApi().getMyCurrencies();
                try {
                    call.enqueue(new Callback<MyCurrencyResponse>() {
                        @Override
                        public void onResponse(Call<MyCurrencyResponse> call, Response<MyCurrencyResponse> response) {
                            if (response.isSuccessful()) {
                                MyCurrencyResponse body = response.body();
                                setConvertData(body, baseCurrency, targetCurrency, amount, targetText);
                            }
                        }

                        @Override
                        public void onFailure(Call<MyCurrencyResponse> call, Throwable t) {
                            Toast.makeText(getActivity().getApplicationContext(),"request failure",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                }

                return false;
            }
        });

        baseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // when user change spinner value
                final String baseCurrency = baseSpinner.getSelectedItem().toString();
                final String targetCurrency = targetSpinner.getSelectedItem().toString();
                final int amount = Integer.parseInt(amountEdit.getText().toString());

                if (amount == 0) {
                    return;
                }

                Call<MyCurrencyResponse> call = RetrofitBuilder.getCoineyApi().getMyCurrencies();
                try {
                    call.enqueue(new Callback<MyCurrencyResponse>() {
                        @Override
                        public void onResponse(Call<MyCurrencyResponse> call, Response<MyCurrencyResponse> response) {
                            if (response.isSuccessful()) {
                                MyCurrencyResponse body = response.body();
                                setConvertData(body, baseCurrency, targetCurrency, amount, targetText);
                            }
                        }

                        @Override
                        public void onFailure(Call<MyCurrencyResponse> call, Throwable t) {
                            Toast.makeText(getActivity().getApplicationContext(),"request failure",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), "exception", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        targetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String baseCurrency = baseSpinner.getSelectedItem().toString();
                final String targetCurrency = targetSpinner.getSelectedItem().toString();
                final int amount = Integer.parseInt(amountEdit.getText().toString());

                if (amount == 0) {
                    return;
                }

                Call<MyCurrencyResponse> call = RetrofitBuilder.getCoineyApi().getMyCurrencies();
                try {
                    call.enqueue(new Callback<MyCurrencyResponse>() {
                        @Override
                        public void onResponse(Call<MyCurrencyResponse> call, Response<MyCurrencyResponse> response) {
                            if (response.isSuccessful()) {
                                MyCurrencyResponse body = response.body();
                                setConvertData(body, baseCurrency, targetCurrency, amount, targetText);
                            }
                        }

                        @Override
                        public void onFailure(Call<MyCurrencyResponse> call, Throwable t) {
                            Toast.makeText(getActivity().getApplicationContext(),"request failure",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), "exception", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return v;
    }

    private void setConvertData(MyCurrencyResponse body,String baseCurrency, String targetCurrency, int amount, TextView targetText) {
        switch(baseCurrency) {
            case "aud":
                Aud aud = body.getAud();
                switch (targetCurrency) {
                    case "cad":
                        rate = aud.getCad();
                        break;
                    case "cny":
                        rate = aud.getCny();
                        break;
                    case "eur":
                        rate = aud.getEur();
                        break;
                    case "gbp":
                        rate = aud.getGbp();
                        break;
                    case "inr":
                        rate = aud.getInr();
                        break;
                    case "jpy":
                        rate = aud.getJpy();
                        break;
                    case "nzd":
                        rate = aud.getNzd();
                        break;
                    case "usd":
                        rate = aud.getUsd();
                        break;
                    case "zar":
                        rate = aud.getZar();
                        break;
                    default:
                        rate = 1.0;
                        break;
                }
                resultValue = rate * amount;
                targetText.setText(String.valueOf(resultValue));
                break;
            case "cad":
                Cad cad = body.getCad();
                switch (targetCurrency) {
                    case "aud":
                        rate = cad.getAud();
                        break;
                    case "cny":
                        rate = cad.getCny();
                        break;
                    case "eur":
                        rate = cad.getEur();
                        break;
                    case "gbp":
                        rate = cad.getGbp();
                        break;
                    case "inr":
                        rate = cad.getInr();
                        break;
                    case "jpy":
                        rate = cad.getJpy();
                        break;
                    case "nzd":
                        rate = cad.getNzd();
                        break;
                    case "usd":
                        rate = cad.getUsd();
                        break;
                    case "zar":
                        rate = cad.getZar();
                        break;
                    default:
                        rate = 1.0;
                        break;
                }
                resultValue = rate * amount;
                targetText.setText(String.valueOf(resultValue));
                break;
            case "cny":
                Cny cny = body.getCny();
                switch (targetCurrency) {
                    case "aud":
                        rate = cny.getAud();
                        break;
                    case "cad":
                        rate = cny.getCad();
                        break;
                    case "eur":
                        rate = cny.getEur();
                        break;
                    case "gbp":
                        rate = cny.getGbp();
                        break;
                    case "inr":
                        rate = cny.getInr();
                        break;
                    case "jpy":
                        rate = cny.getJpy();
                        break;
                    case "nzd":
                        rate = cny.getNzd();
                        break;
                    case "usd":
                        rate = cny.getUsd();
                        break;
                    case "zar":
                        rate = cny.getZar();
                        break;
                    default:
                        rate = 1.0;
                        break;
                }
                resultValue = rate * amount;
                targetText.setText(String.valueOf(resultValue));
                break;
            case "eur":
                Eur eur = body.getEur();
                switch (targetCurrency) {
                    case "aud":
                        rate = eur.getAud();
                        break;
                    case "cad":
                        rate = eur.getCad();
                        break;
                    case "cny":
                        rate = eur.getCny();
                        break;
                    case "gbp":
                        rate = eur.getGbp();
                        break;
                    case "inr":
                        rate = eur.getInr();
                        break;
                    case "jpy":
                        rate = eur.getJpy();
                        break;
                    case "nzd":
                        rate = eur.getNzd();
                        break;
                    case "usd":
                        rate = eur.getUsd();
                        break;
                    case "zar":
                        rate = eur.getZar();
                        break;
                    default:
                        rate = 1.0;
                        break;
                }
                resultValue = rate * amount;
                targetText.setText(String.valueOf(resultValue));
                break;
            case "gbp":
                Gbp gbp = body.getGbp();
                switch (targetCurrency) {
                    case "aud":
                        rate = gbp.getAud();
                        break;
                    case "cad":
                        rate = gbp.getCad();
                        break;
                    case "cny":
                        rate = gbp.getCny();
                        break;
                    case "eur":
                        rate = gbp.getEur();
                        break;
                    case "inr":
                        rate = gbp.getInr();
                        break;
                    case "jpy":
                        rate = gbp.getJpy();
                        break;
                    case "nzd":
                        rate = gbp.getNzd();
                        break;
                    case "usd":
                        rate = gbp.getUsd();
                        break;
                    case "zar":
                        rate = gbp.getZar();
                        break;
                    default:
                        rate = 1.0;
                        break;
                }
                resultValue = rate * amount;
                targetText.setText(String.valueOf(resultValue));
                break;
            case "inr":
                Inr inr = body.getInr();
                switch (targetCurrency) {
                    case "aud":
                        rate = inr.getAud();
                        break;
                    case "cad":
                        rate = inr.getCad();
                        break;
                    case "cny":
                        rate = inr.getCny();
                        break;
                    case "eur":
                        rate = inr.getEur();
                        break;
                    case "gbp":
                        rate = inr.getGbp();
                        break;
                    case "jpy":
                        rate = inr.getJpy();
                        break;
                    case "nzd":
                        rate = inr.getNzd();
                        break;
                    case "usd":
                        rate = inr.getUsd();
                        break;
                    case "zar":
                        rate = inr.getZar();
                        break;
                    default:
                        rate = 1.0;
                        break;
                }
                resultValue = rate * amount;
                targetText.setText(String.valueOf(resultValue));
                break;
            case "jpy":
                Jpy jpy = body.getJpy();
                switch (targetCurrency) {
                    case "aud":
                        rate = jpy.getAud();
                        break;
                    case "cad":
                        rate = jpy.getCad();
                        break;
                    case "cny":
                        rate = jpy.getCny();
                        break;
                    case "eur":
                        rate = jpy.getEur();
                        break;
                    case "gbp":
                        rate = jpy.getGbp();
                        break;
                    case "inr":
                        rate = jpy.getInr();
                        break;
                    case "nzd":
                        rate = jpy.getNzd();
                        break;
                    case "usd":
                        rate = jpy.getUsd();
                        break;
                    case "zar":
                        rate = jpy.getZar();
                        break;
                    default:
                        rate = 1.0;
                        break;
                }
                resultValue = rate * amount;
                targetText.setText(String.valueOf(resultValue));
                break;
            case "nzd":
                Nzd nzd = body.getNzd();
                switch (targetCurrency) {
                    case "aud":
                        rate = nzd.getAud();
                        break;
                    case "cad":
                        rate = nzd.getCad();
                        break;
                    case "cny":
                        rate = nzd.getCny();
                        break;
                    case "eur":
                        rate = nzd.getEur();
                        break;
                    case "gbp":
                        rate = nzd.getGbp();
                        break;
                    case "inr":
                        rate = nzd.getInr();
                        break;
                    case "jpy":
                        rate = nzd.getJpy();
                        break;
                    case "usd":
                        rate = nzd.getUsd();
                        break;
                    case "zar":
                        rate = nzd.getZar();
                        break;
                    default:
                        rate = 1.0;
                        break;
                }
                resultValue = rate * amount;
                targetText.setText(String.valueOf(resultValue));
                break;
            case "usd":
                Usd usd = body.getUsd();
                switch (targetCurrency) {
                    case "aud":
                        rate = usd.getAud();
                        break;
                    case "cad":
                        rate = usd.getCad();
                        break;
                    case "cny":
                        rate = usd.getCny();
                        break;
                    case "eur":
                        rate = usd.getEur();
                        break;
                    case "gbp":
                        rate = usd.getGbp();
                        break;
                    case "inr":
                        rate = usd.getInr();
                        break;
                    case "jpy":
                        rate = usd.getJpy();
                        break;
                    case "nzd":
                        rate = usd.getNzd();
                        break;
                    case "zar":
                        rate = usd.getZar();
                        break;
                    default:
                        rate = 1.0;
                        break;
                }
                resultValue = rate * amount;
                targetText.setText(String.valueOf(resultValue));
                break;
            case "zar":
                Zar zar = body.getZar();
                switch (targetCurrency) {
                    case "aud":
                        rate = zar.getAud();
                        break;
                    case "cad":
                        rate = zar.getCad();
                        break;
                    case "cny":
                        rate = zar.getCny();
                        break;
                    case "eur":
                        rate = zar.getEur();
                        break;
                    case "gbp":
                        rate = zar.getGbp();
                        break;
                    case "inr":
                        rate = zar.getInr();
                        break;
                    case "jpy":
                        rate = zar.getJpy();
                        break;
                    case "nzd":
                        rate = zar.getNzd();
                        break;
                    case "usd":
                        rate = zar.getUsd();
                        break;
                    default:
                        rate = 1.0;
                        break;
                }
                resultValue = rate * amount;
                targetText.setText(String.valueOf(resultValue));
                break;
        }
    }
}
