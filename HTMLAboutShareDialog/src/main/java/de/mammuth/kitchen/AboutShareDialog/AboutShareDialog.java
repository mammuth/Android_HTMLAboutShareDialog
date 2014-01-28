package de.mammuth.kitchen.AboutShareDialog;

/**
 * Class that provides the possibility to display a remote HTML File within a Dialog. Used for
 * giving the user a beautiful and remote changable about-screen. Sharing options, aswell as following
 * options (Facebook, Twitter) are included);
 *
 * Created by Maximilian Muth | mail@maxi-muth.de
 * Date: 28.01.14
 * Time: 09:44
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class AboutShareDialog {
    private static Activity activity = null;
    private static String version = "";
    public static final String aboutURL = "http://www.maxi-muth.de/android/tankstellen-sparfuchs/about.html";
    // For a local html-file (in the assets-folder) use "file:///android_asset/offline-about.html"
    public static final String facebookID = "";
    public static final String twitterID = "";

    public AboutShareDialog(Activity a) {
        activity = a;
    }


    /**
     * ****************************************************************************************************************
     * * Webview for within the Dialog:
     * *****************************************************************************************************************
     */

    private WebView getRemoteDialog() {
        WebView webView2 = new WebView(activity);
        webView2.loadUrl(aboutURL);
        webView2.setWebViewClient(new WebViewClient() {

            // Open external links with the browser
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                try {

                    // Pass it to the system, doesn't match your domain
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    activity.startActivity(intent);
                    // Tell the WebView you took care of it.
                    return true;

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                Toast.makeText(
                        activity.getApplicationContext(),
                        "Couldnt load Data from the server...\nWorking Connection?",
                        Toast.LENGTH_LONG).show();

            }
        });
        return webView2;
    }


    /**
     * ****************************************************************************************************************
     * * About Dialog:
     * *****************************************************************************************************************
     */

    void aboutDialog() {
        try {
            version = activity.getPackageManager().getPackageInfo(
                    activity.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
        }
        final AlertDialog.Builder ad_rate = new AlertDialog.Builder(activity);
        ad_rate.setTitle("About & Info Version " + version);

        ad_rate.setView(getRemoteDialog());
        ad_rate.setPositiveButton("Rate Now!",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface ad_rate, int which) {
                        Intent it_rate = new Intent(Intent.ACTION_VIEW);
                        it_rate.setData(Uri
                                .parse("market://details?id=" + activity.getPackageName()));
                        activity.startActivity(it_rate);

                        Toast.makeText(activity, "Thanks!", Toast.LENGTH_SHORT)
                                .show();
                    }
                });

        ad_rate.setNegativeButton("Feedback",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        sentmail(null);
                    }
                });
        // ad_rate.setIcon(R.drawable.ic_rate);
        ad_rate.show();
    }

    public void sentmail(View view) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_EMAIL,
                new String[]{"mail@test.de"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Feedback Android App");
        i.putExtra(Intent.EXTRA_TEXT, "\n\nApp Version: " + version);
        i.putExtra(Intent.EXTRA_BUG_REPORT, true);
        activity.startActivity(Intent.createChooser(i, "Choose an Email-App"));
        Toast.makeText(activity, "Deutsch or English pls!", Toast.LENGTH_SHORT)
                .show();

    }

    /**
     * ****************************************************************************************************************
     * * Sharing:
     * *****************************************************************************************************************
     */
    public void shareWithFriends(View view) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");

        i.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        i.putExtra(
                Intent.EXTRA_TEXT,
                "content");
        i.putExtra(Intent.EXTRA_BUG_REPORT, true);
        activity.startActivity(Intent.createChooser(i, "Share with ..."));

    }

    public static Intent getOpenFacebookIntent(Context context) {

        try {
            context.getPackageManager()
                    .getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("fb://profile/" + facebookID));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/" + facebookID + ">"));
        }
    }

    void openTwitterIntent() {
        try {
            // Check if the Twitter app is installed on the phone.
            activity.getPackageManager().getPackageInfo("com.twitter.android",
                    0);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setClassName("com.twitter.android",
                    "com.twitter.android.ProfileActivity");
            // Don't forget to put the "L" at the end of the id.
            intent.putExtra("user_id", twitterID);
            activity.startActivity(intent);
        } catch (NameNotFoundException e) {
            // If Twitter app is not installed, start browser.
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri
                    .parse("https://twitter.com/123xyz")));
        }
    }
}
