package com.futureworkshops.codetest.android.presentation.utils;

import com.futureworkshops.codetest.android.domain.model.ErrorParser;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class ErrorHandler {

    public static String getErrorMessage(Throwable throwable) {
        if (throwable instanceof HttpException) {
            ResponseBody body = ((HttpException) throwable).response().errorBody();
            Gson gson = new Gson();
            TypeAdapter<ErrorParser> adapter = gson.getAdapter
                    (ErrorParser
                            .class);
            try {
                ErrorParser errorParser =
                        adapter.fromJson(body.string());
                return errorParser.getError();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "error fail";
    }
}
