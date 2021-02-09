package io.quadrant.beaconlib.util;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.RemoteException;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.List;

public class BeaconUtil {

    private final BeaconManager beaconManager;
    private final BeaconConsumer beaconConsumer;

    public BeaconUtil(Context context, BeaconReceiver beaconReceiver){
        beaconManager = BeaconManager.getInstanceForApplication(context);
        beaconManager.getBeaconParsers()
                .add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));//iBeacon
        beaconManager.getBeaconParsers()
                .add(new BeaconParser().setBeaconLayout(BeaconParser.ALTBEACON_LAYOUT));
        beaconManager.getBeaconParsers()
                .add(new BeaconParser().setBeaconLayout(BeaconParser.EDDYSTONE_TLM_LAYOUT));
        beaconManager.getBeaconParsers()
                .add(new BeaconParser().setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT));
        beaconManager.getBeaconParsers()
                .add(new BeaconParser().setBeaconLayout(BeaconParser.EDDYSTONE_URL_LAYOUT));
        beaconManager.getBeaconParsers()
                .add(new BeaconParser().setBeaconLayout(BeaconParser.URI_BEACON_LAYOUT));

        beaconConsumer = new BeaconConsumer() {
            @Override
            public void onBeaconServiceConnect() {
                Region region = new Region("GeolancerBeacon", null, null, null);
                RangeNotifier rangeNotifier = (collection, region1) -> {
                    if(collection.size()>0){
                        beaconReceiver.onBeaconStatus((List<Beacon>) collection);
                    }
                };

                try {
                    beaconManager.startRangingBeaconsInRegion(region);
                    beaconManager.addRangeNotifier(rangeNotifier);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public Context getApplicationContext() {
                return null;
            }

            @Override
            public void unbindService(ServiceConnection serviceConnection) {}

            @Override
            public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
                return false;
            }
        };
    }

    public void beaconBind(){
        beaconManager.bind(beaconConsumer);
    }

    public void beaconUnbind(){
        beaconManager.unbind(beaconConsumer);
    }

    public interface BeaconReceiver{
        void onBeaconStatus(List<Beacon> beacons);
    }
}
