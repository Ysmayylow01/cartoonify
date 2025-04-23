package tm.hazar.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import tm.hazar.myapplication.databinding.FragmentAboutUsBinding

class FragmentAbout : Fragment() {
    private lateinit var b: FragmentAboutUsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentAboutUsBinding.inflate(inflater,container,false)

        b.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        return b.root
    }
}