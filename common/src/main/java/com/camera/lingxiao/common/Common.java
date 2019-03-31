package com.camera.lingxiao.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class Common {
    private void showSelect(String[] urls, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog alertDialog = builder.create();
        alertDialog.setTitle("选择播放路径");
        builder.setItems(urls, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
    }
}
