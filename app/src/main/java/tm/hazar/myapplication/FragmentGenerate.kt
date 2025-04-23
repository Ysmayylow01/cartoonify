package tm.hazar.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import tm.hazar.myapplication.databinding.FragmentGeneratorBinding
import java.io.File

class FragmentGenerate : Fragment() {
    private var imageUrl: String? = null


    private lateinit var  b : FragmentGeneratorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageUrl = it.getString("url")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentGeneratorBinding.inflate(inflater,container,false)

        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(this)
                .load(imageUrl)
                .into(b.imageView)
        }

        b.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        b.btnDownload.setOnClickListener {
            imageUrl?.let { url ->
                downloadImage(url)
            }
        }
        return b.root
    }
    private fun downloadImage(url: String) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : com.bumptech.glide.request.target.CustomTarget<android.graphics.Bitmap>() {
                override fun onResourceReady(
                    resource: android.graphics.Bitmap,
                    transition: com.bumptech.glide.request.transition.Transition<in android.graphics.Bitmap>?
                ) {
                    saveImageToGallery(resource)
                }

                override fun onLoadCleared(placeholder: android.graphics.drawable.Drawable?) {}
            })
    }
    private fun saveImageToGallery(bitmap: android.graphics.Bitmap) {
        val filename = "cartoonified_${System.currentTimeMillis()}.jpg"
        val fos = requireContext().openFileOutput(filename, android.content.Context.MODE_PRIVATE)
        bitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, 100, fos)
        fos.close()

        val file = File(requireContext().filesDir, filename)
        val uri = androidx.core.content.FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.provider",
            file
        )

        val intent = android.content.Intent(android.content.Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        intent.data = uri
        requireContext().sendBroadcast(intent)

        android.widget.Toast.makeText(requireContext(), "Surat ýatda saklandy", android.widget.Toast.LENGTH_SHORT).show()
    }


    companion object{
        fun newInstance(url : String) : FragmentGenerate{
            val args = Bundle().apply {
                putString("url",url)
            }
            return FragmentGenerate().apply {
                arguments = args
            }
        }

    }
}