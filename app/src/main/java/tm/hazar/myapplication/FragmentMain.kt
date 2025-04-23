package tm.hazar.myapplication

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tm.hazar.myapplication.databinding.FragmentMainBinding


class FragmentMain : Fragment() {

    private lateinit var binding : FragmentMainBinding
    private  var sharedPreference : SharedPreference?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreference = SharedPreference.newInstance(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.btnCheck.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer,FragmentHome())
                .addToBackStack(null)
                .commit()
        }
        binding.btnAbout.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer,FragmentAbout())
                .addToBackStack(null)
                .commit()
        }
        binding.themeSwitch.isChecked = sharedPreference?.theme!!
        binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
           sharedPreference?.theme = true
            } else {
            sharedPreference?.theme = false
            }
            restartActivity(activity)

        }
        return binding.root
    }
    fun restartActivity(activity: Activity?) {
        val intent = activity?.getIntent()
       // intent?.putExtra("destination_id", R.id.fragmentLogin)
        activity?.finish()
        activity?.startActivity(intent)
    }

}