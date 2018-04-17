package me.aungkooo.geologist;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.database.FirebaseDatabase;

import io.fabric.sdk.android.Fabric;
import me.aungkooo.geologist.rx.RxBus;

/**
 * Created by root on 11/21/17.
 */

public class MyApplication extends Application {

    private RxBus bus;

    @Override
    public void onCreate() {
        super.onCreate();
        bus = new RxBus();

        Fabric.with(this, new Crashlytics());

    }

    public RxBus bus() {
        return bus;
    }

}
