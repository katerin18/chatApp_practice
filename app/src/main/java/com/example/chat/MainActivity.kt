package com.example.chat

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var oneTapClient: SignInClient
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val reqCode: Int = 123


    //private val REQ_ONE_TAP = 2  // Can be any integer unique to the Activity
        //  private var showOneTapUI = true

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == reqCode) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }
//        val googleCredential = oneTapClient.getSignInCredentialFromIntent(data)
//        val idToken = googleCredential.googleIdToken
//        when {
//            idToken != null -> {
//                // Got an ID token from Google. Use it to authenticate
//                // with Firebase.
//                val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
//                auth.signInWithCredential(firebaseCredential)
//                    .addOnCompleteListener(this) { task ->
//                        if (task.isSuccessful) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success")
//                            val user = auth.currentUser
//                            updateUI(user)
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.exception)
//                            updateUI(null)
//                        }
//                    }
//            }
//            else -> {
//                // Shouldn't happen.
//                Log.d(TAG, "No ID token!")
//            }
//        }


    private fun UpdateUI(account: GoogleSignInAccount){
        val credential= GoogleAuthProvider.getCredential(account.idToken,null)
        auth.signInWithCredential(credential).addOnCompleteListener {task->
            if(task.isSuccessful) {
                SavedPreference.setEmail(this,account.email.toString())
                SavedPreference.setUsername(this,account.displayName.toString())
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
    // we update the UI after Google signin takes place
    private fun handleResult(completedTask: Task<GoogleSignInAccount>){
        try{
            val account: GoogleSignInAccount? =completedTask.getResult(ApiException::class.java)
            if (account != null) {
                UpdateUI(account)
            }
        } catch (e:ApiException){
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show()
        }
    }


    /////////////  START  //////////////////////////
    private fun signInGoogle(){
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, reqCode)
    }
    /////////////  END  //////////////////////////

    override fun onCreate(savedInstanceState: Bundle?) {
/////////////  START  //////////////////////////
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("39311531823-8pvfcc7jsja8rrl9id2vit4l13sn3uap.apps.googleusercontent.com")
            .requestEmail()
            .build()
        // getting the value of gso inside the GoogleSigninClient
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso)
////////////////  END  //////////////////////

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth

        sign_up.setOnClickListener{
            startActivity(Intent(this, SignScreen::class.java))
        }

        log_in.setOnClickListener{
            val email_log = user_email.text.toString().trim()
            val pass_log = user_pass0.text.toString().trim()
            val succes =  check_1(email_log, pass_log)

            if(succes.isEmpty()){
                startActivity(Intent(this, ProfileScreen::class.java))
            }

        }

        // Authorization with Google
        im_google.setOnClickListener { view: View? ->
//            val signInRequest = BeginSignInRequest.builder()
//                .setGoogleIdTokenRequestOptions(
//                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                        .setSupported(true)
//                        // Your server's client ID, not your Android client ID.
//                        .setServerClientId("805449957097-ssiq28ntqgkk6f45khjgf5bg36feie06.apps.googleusercontent.com")
//                        // Only show accounts previously used to sign in.
//                        .setFilterByAuthorizedAccounts(true)
//                        .build()
//                )
//                .build()
//
//            oneTapClient.beginSignIn(signInRequest)
//                .addOnSuccessListener(this) { result ->
//                    try {
//                        startIntentSenderForResult(
//                            result.pendingIntent.intentSender, 2,
//                            null, 0, 0, 0, null)
//                    } catch (e: IntentSender.SendIntentException) {
//                        Log.e(TAG, "Couldn't start One Tap UI: ${e.localizedMessage}")
//                    }
//                }
//                .addOnFailureListener(this) { e ->
//                    // No saved credentials found. Launch the One Tap sign-up flow, or
//                    // do nothing and continue presenting the signed-out UI.
//                    Log.d(TAG, e.localizedMessage)
//                }
//            val googleCredential = oneTapClient.getSignInCredentialFromIntent(data)
//            val idToken = googleCredential.googleIdToken
//            when {
//                idToken != null -> {
//                    // Got an ID token from Google. Use it to authenticate
//                    // with Firebase.
//                    val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
//                    auth.signInWithCredential(firebaseCredential)
//                        .addOnCompleteListener(this) { task ->
//                            if (task.isSuccessful) {
//                                // Sign in success, update UI with the signed-in user's information
//                                Log.d(TAG, "signInWithCredential:success")
//                                val user = auth.currentUser
//                                updateUI(user)
//                            } else {
//                                // If sign in fails, display a message to the user.
//                                Log.w(TAG, "signInWithCredential:failure", task.exception)
//                                updateUI(null)
//                            }
//                        }
//                }
//                else -> {
//                    // Shouldn't happen.
//                    Log.d(TAG, "No ID token!")
//                }
//            }
            signInGoogle()
        }
    }
//            var signInRequest = BeginSignInRequest.builder()
//                .setGoogleIdTokenRequestOptions(
//                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                        .setSupported(true)
//                        // Your server's client ID, not your Android client ID.
//                        .setServerClientId("39311531823-8pvfcc7jsja8rrl9id2vit4l13sn3uap.apps.googleusercontent.com")
//                        // Only show accounts previously used to sign in.
//                        .setFilterByAuthorizedAccounts(true)
//                        .build())
//                .build()
//            oneTapClient = com.google.android.gms.auth.api.identity.Identity.getSignInClient(this)
//            oneTapClient.beginSignIn(signInRequest)
//                .addOnSuccessListener(this) { result ->
//                    try {
//                        startIntentSenderForResult(
//                            result.pendingIntent.intentSender, REQ_ONE_TAP,
//                            null, 0, 0, 0, null)
//                    } catch (e: IntentSender.SendIntentException) {
//                        Log.e(TAG, "Couldn't start One Tap UI: ${e.localizedMessage}")
//                    }
//                }
//                .addOnFailureListener(this) { e ->
//                    // No saved credentials found. Launch the One Tap sign-up flow, or
//                    // do nothing and continue presenting the signed-out UI.
//                    Log.d(TAG, e.localizedMessage)
//                }



    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        if(GoogleSignIn.getLastSignedInAccount(this) != null){
            startActivity(Intent(this, ProfileScreen::class.java))
            finish()
        }
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
//////////////  START /////////////////
//    fun update_UI(account: GoogleSignInAccount){
//        val credential= GoogleAuthProvider.getCredential(account.idToken,null)
//        auth.signInWithCredential(credential).addOnCompleteListener {task->
//            if(task.isSuccessful) {
//                SavedPreference.setEmail(this,account.email.toString())
//                SavedPreference.setUsername(this,account.displayName.toString())
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//        }
//    }
////////////// END //////////////////
    fun updateUI(currentUser: FirebaseUser?){

    }

///////////////  START  //////////////////
    object SavedPreference {

        const val EMAIL= "email"
        const val USERNAME="username"

        private  fun getSharedPreference(ctx: Context?): SharedPreferences? {
            return PreferenceManager.getDefaultSharedPreferences(ctx)
        }

        private fun  editor(context: Context, const:String, string: String){
            getSharedPreference(
                context
            )?.edit()?.putString(const,string)?.apply()
        }

        fun getEmail(context: Context)= getSharedPreference(
            context
        )?.getString(EMAIL,"")

        fun setEmail(context: Context, email: String){
            editor(
                context,
                EMAIL,
                email
            )
        }

        fun setUsername(context: Context, username:String){
            editor(
                context,
                USERNAME,
                username
            )
        }

        fun getUsername(context: Context) = getSharedPreference(
            context
        )?.getString(USERNAME,"")

    }
///////////////  END  //////////////////


    fun check_1(email_log: String, pass_log: String): String{
        var success = ""
        if (email_log.isNotEmpty() && pass_log.isNotEmpty()){
            auth.signInWithEmailAndPassword(email_log, pass_log)
                .addOnCompleteListener(this) { task ->
                    Toast.makeText(this, "OK", Toast.LENGTH_LONG).show()
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        Toast.makeText(this, "Authentication was successfully.",
                            Toast.LENGTH_SHORT).show()

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                        success = "Invalid email or password"

                    }
                }


        }
        else if(email_log.isEmpty() && pass_log.isEmpty()){
            user_email.error = "Input your email, please."
            user_pass0.error = "Input your email, please."
        }
        else{
            Toast.makeText(this, "Input required", Toast.LENGTH_LONG).show()
        }
        return success
    }
}