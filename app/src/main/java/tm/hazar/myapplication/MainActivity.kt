package tm.hazar.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (SharedPreference.newInstance(this).theme){
            StaticMethods.onActivityCreateSetTheme(this, true)
        }else{
            StaticMethods.onActivityCreateSetTheme(this, false)
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, FragmentMain())
            .commit()
    }
}