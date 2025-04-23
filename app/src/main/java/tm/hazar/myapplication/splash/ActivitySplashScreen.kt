package tm.hazar.myapplication.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tm.hazar.myapplication.MainActivity


@SuppressLint("CustomSplashScreen")
class ActivitySplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startNewActivity()
    }

    private fun startNewActivity() {
        val intent: Intent
        intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


}