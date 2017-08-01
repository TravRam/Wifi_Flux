package trav.wifi_flux;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button wifi = (Button) findViewById(R.id.enable);
        Button wifi2 = (Button) findViewById(R.id.disable);


        final String netSSID = "TravelTab";                 //Change Final SSID to find Wifi Network
        final String passKey = "123456789";         //Change Final passkey to connect to network

        final WifiManager wifiManage = (WifiManager) getSystemService(Context.WIFI_SERVICE);  //Define wifi manager and config for future use.
        final WifiConfiguration conf = new WifiConfiguration();

        conf.SSID = "\"" + netSSID + "\"";          //Set SSID and Password to Connect
        conf.preSharedKey = "\"" + passKey + "\"";

        conf.hiddenSSID = true;         //Hidden SSID
        conf.status = WifiConfiguration.Status.ENABLED;

        conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);       //WPA Security to enable password

        conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);

        conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);

        conf.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        conf.allowedProtocols.set(WifiConfiguration.Protocol.RSN);


        wifi.setOnClickListener(new Button.OnClickListener(){       //enable WIfi

            @Override
            public void onClick(View v) {

                wifiManage.setWifiEnabled(true);                //Turn on Wifi
                if(wifiManage != null && wifiManage.isWifiEnabled()) {      //Test that null case is not considered and turned on correctly

                    wifiManage.addNetwork(conf);            //Add Hidden Network to list of Wifi networks

                    List<WifiConfiguration> list = wifiManage.getConfiguredNetworks();
                    for (WifiConfiguration i: list) {

                        if(i.SSID != null && i.SSID.equals("\"" + netSSID + "\"")){         //Compare SSIDs to confirm network

                            wifiManage.disconnect();                //disconnect from current network and reconnect to the Hidden SSID

                            wifiManage.enableNetwork(i.networkId, true);

                            wifiManage.reconnect();

                            wifiManage.saveConfiguration(); //Save final Configuration

                            break;
                        }
                    }
                }
            }

        });

        wifi2.setOnClickListener(new Button.OnClickListener() {     //Simply Disable Wifi
            @Override
            public void onClick(View v) {
                wifiManage.setWifiEnabled(false);
            }
        });
    }


}
