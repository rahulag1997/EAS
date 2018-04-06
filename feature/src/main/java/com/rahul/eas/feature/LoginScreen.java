package com.rahul.eas.feature;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class LoginScreen extends AppCompatActivity {

    //    Private Variables
    // Views
    private EditText password_et;
    private ImageView fingerprint_iv;
    private TextView instruction_tv;

    //    Actual Password
    private String actual_password="1821";

    // Fingerprint STUFF
    private KeyguardManager keyguardManager;
    private FingerprintManager fingerprintManager;
    private static final String KEY_NAME = "yourKey";
    private Cipher cipher;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private FingerprintManager.CryptoObject cryptoObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        setup();

        boolean flag = true;
        boolean useFingerprint = true;
        if(useFingerprint) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                // Get an instance of KeyguardManager and FingerprintManager
                keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
                fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

                //Check hardware presence
                if (fingerprintManager.isHardwareDetected()) {

                    // Check if permission Granted
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) == PackageManager.PERMISSION_GRANTED) {

                        //Check that the user has registered at least one fingerprint//
                        if (fingerprintManager.hasEnrolledFingerprints()) {

                            //Check that the lockscreen is secured//
                            if (keyguardManager.isKeyguardSecure()) {

                                try {
                                    generateKey();
                                } catch (FingerprintException e) {
                                    e.printStackTrace();
                                }


                                if (initCipher()) {
                                    //If the cipher is initialized successfully, then create a CryptoObject instance//
                                    cryptoObject = new FingerprintManager.CryptoObject(cipher);

                                    // Here, I’m referencing the com.rahul.eas.feature.FingerprintHandler class that we’ll create in the next section. This class will be responsible
                                    // for starting the authentication process (via the startAuth method) and processing the authentication process events//
                                    FingerprintHandler helper = new FingerprintHandler(this);
                                    helper.startAuth(fingerprintManager, cryptoObject);
                                }
                                flag = false;
                            }
                        }
                    }
                }

            }
        }
        if (flag) {
            fingerprint_iv.setVisibility(View.INVISIBLE);
            instruction_tv.setText(getString(R.string.enter_password_to_continue));
        }


    }

    private void generateKey() throws FingerprintException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        try {
            // Obtain a reference to the Keystore using the standard Android keystore container identifier (“AndroidKeystore”)//
            keyStore = KeyStore.getInstance("AndroidKeyStore");

            //Generate the key//
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            //Initialize an empty KeyStore//
            keyStore.load(null);

            //Initialize the KeyGenerator//
            keyGenerator.init(new

                    //Specify the operation(s) this key can be used for//
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)

                    //Configure this key so that the user has to confirm their identity with a fingerprint each time they want to use it//
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());

            //Generate the key//
            keyGenerator.generateKey();

        } catch (KeyStoreException
                | NoSuchAlgorithmException
                | NoSuchProviderException
                | InvalidAlgorithmParameterException
                | CertificateException
                | IOException exc) {
            exc.printStackTrace();
            throw new FingerprintException(exc);
        }
        else
            throw new FingerprintException(null);
    }

    //Create a new method that we’ll use to initialize our cipher//
    public boolean initCipher() {
        try {
            //Obtain a cipher instance and configure it with the properties required for fingerprint authentication//
            cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //Return true if the cipher has been initialized successfully//
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {

            //Return false if cipher initialization failed//
            return false;
        } catch (KeyStoreException | CertificateException
                | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
        else
            throw new RuntimeException("Failed to init Cipher");
    }

    private class FingerprintException extends Exception {
        public FingerprintException(Exception e) {
            super(e);
        }
    }

    public void Login(View view) {
        validate();
    }

    private void validate() {
        String password = password_et.getText().toString();
        if(!password.equals(actual_password)){
            // Password incorrect
            password_et.setError("Incorrect Password");
            password_et.requestFocus();
        }
        else
            login();
    }

    private void login(){
        startActivity(new Intent(this,Home.class));
        finish();
    }

    private void setup() {
        // Initialise Views
        password_et = findViewById(R.id.login_password_et);
        fingerprint_iv = findViewById(R.id.login_fingerprint_iv);
        instruction_tv = findViewById(R.id.login_instruction_tv);

        // Shared Preferences
        SharedPreferences sharedPreferences;

        sharedPreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        actual_password = sharedPreferences.getString("Password",null);

        if(actual_password==null){
            startActivity(new Intent(this,SetupPassword.class));
            finish();
        }

        // On pressing Enter from Password field, Press login button
        password_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                validate();
                return false;
            }
        });
    }


}
