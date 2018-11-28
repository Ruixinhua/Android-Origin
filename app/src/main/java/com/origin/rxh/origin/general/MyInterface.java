package com.origin.rxh.origin.general;

public class MyInterface {

    DialogReturn dialogReturn;

    public interface DialogReturn {

        void onDialogCompleted(boolean answer);
    }

    public void setListener(DialogReturn dialogReturn) {
        this.dialogReturn = dialogReturn;
    }

    public DialogReturn getListener() {
        return dialogReturn;

    }
}
