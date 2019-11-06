package com.android.internal.telephony;

/**
 * Created by Yeshveer on 10/1/2018.
 */

        public interface ITelephony {
    boolean endCall();
    void answerRingingCall();
    void silenceRinger();
}