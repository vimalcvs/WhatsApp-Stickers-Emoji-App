package com.vimalcvs.stickers_app.ui;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vimalcvs.stickers_app.R;

public class InfosActivity extends AppCompatActivity {

    private String name;
    private String publisher;
    private String publisherEmail;
    private String publisherWebsite;
    private String privacyPolicyWebsite;
    private String licenseAgreementWebsite;

    private LinearLayout linear_layout_pack_publisher;
    private LinearLayout linear_layout_publisher_email;
    private LinearLayout linear_layout_publisher_website;
    private LinearLayout linear_layout_policy_privacy;
    private LinearLayout linear_layout_license_agreement;
    private TextView text_view_published;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos);


        Bundle bundle = getIntent().getExtras() ;
        this.name= bundle.getString( "name");
        this.publisher= bundle.getString( "publisher");
        this.publisherEmail= bundle.getString( "publisherEmail");
        this.publisherWebsite= bundle.getString( "publisherWebsite");
        this.privacyPolicyWebsite= bundle.getString( "privacyPolicyWebsite");
        this.licenseAgreementWebsite= bundle.getString( "licenseAgreementWebsite");
        initView();
        setAction();
        this.text_view_published.setText(publisher);
        if (publisher == null) {
            linear_layout_pack_publisher.setVisibility(View.GONE);
        }
        if (licenseAgreementWebsite == null){
            linear_layout_license_agreement.setVisibility(View.GONE);
        }
        if (publisherWebsite == null){
            linear_layout_publisher_website.setVisibility(View.GONE);
        }
        if (privacyPolicyWebsite ==  null ){
            linear_layout_policy_privacy.setVisibility(View.GONE);
        }
        if (publisherEmail == null){
            linear_layout_publisher_email.setVisibility(View.GONE);
        }
    }
    public void setAction(){
        this.linear_layout_publisher_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + publisherEmail));
                startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
            }
        });
        this.linear_layout_publisher_website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(publisherWebsite));
                startActivity(i);
            }
        });
        this.linear_layout_policy_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(privacyPolicyWebsite));
                startActivity(i);
            }
        });
        this.linear_layout_license_agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(licenseAgreementWebsite));
                startActivity(i);
            }
        });

    }
    private void initView() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.text_view_published= (TextView) findViewById(R.id.text_view_published);
        this.linear_layout_pack_publisher= (LinearLayout) findViewById(R.id.linear_layout_pack_publisher);
        this.linear_layout_publisher_email= (LinearLayout) findViewById(R.id.linear_layout_publisher_email);
        this.linear_layout_publisher_website= (LinearLayout) findViewById(R.id.linear_layout_publisher_website);
        this.linear_layout_policy_privacy= (LinearLayout) findViewById(R.id.linear_layout_policy_privacy);
        this.linear_layout_license_agreement= (LinearLayout) findViewById(R.id.linear_layout_license_agreement);

    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
        return;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
        }
        return super.onOptionsItemSelected(item);
    }
}
