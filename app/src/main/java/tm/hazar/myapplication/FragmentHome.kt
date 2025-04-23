package tm.hazar.myapplication

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tm.hazar.myapplication.databinding.FragmentHomeBinding
import java.io.File

class FragmentHome : Fragment() {
    private lateinit var spinnerType: Spinner
    private lateinit var selectedType: String
    private lateinit var apiKeySpinner : Spinner
    private lateinit var selectedApiKey : String

    private val types = arrayOf("pixar_plus", "anime", "avatar", "3d_cartoon")
    private val apiKeys = arrayOf("CY2VGLRAqdoaM1zbhS3BGKsOX9JvtWKe8RgLUyAFokX9PIYJZS0uE6jCk57s4TH6","E9eGpKQ4TgnxrhOzCp7EomY3Fqgf2JjiWt4u9oO6K7xNRlI6aJzDSAk1LeIcwBuN")
    private var imageUrl: String? = null
    private lateinit var b: FragmentHomeBinding
    private lateinit var fileImage  : File
    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val uri: Uri = result.data!!.data!!
                var file = File(requireContext().cacheDir, "picked_image.jpg")
                requireContext().contentResolver.openInputStream(uri)?.use { input ->
                    file.outputStream().use { output -> input.copyTo(output) }
                }
                Glide.with(requireContext())
                    .load(uri)
                    .into(b.imageView)
              //  cartoonifyImage(file)
                fileImage = file
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentHomeBinding.inflate(inflater, container, false)
        spinnerType = b.root.findViewById(R.id.spinnerType)
        apiKeySpinner = b.root.findViewById(R.id.spinnerApiKey)

        setSpinnerType()
        setSpinnerApiKey()
        b.btnSelectImage.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .galleryOnly()
                .createIntent { imagePickerLauncher.launch(it) }
        }
        b.btnUpload.setOnClickListener {
            if (fileImage!=null){
                cartoonifyImage(fileImage)
            }

        }
        b.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return b.root
    }

    private fun setSpinnerApiKey() {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, apiKeys)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            apiKeySpinner.adapter = adapter

        apiKeySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                selectedApiKey = apiKeys[position]
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
            }
        }
    }

    private fun setSpinnerType() {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, types)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerType.adapter = adapter

        spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                selectedType = types[position]
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
            }
        }
    }

    private fun cartoonifyImage(imageFile: File) {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        b.progressBar.visibility = View.VISIBLE
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.ailabapi.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(CartoonApi::class.java)

        val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("image", imageFile.name, requestFile)
        val typeBody = selectedType.toRequestBody("text/plain".toMediaTypeOrNull())

        Log.d("qwerty", "cartoonifyImage: $typeBody")
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = service.cartoonify(selectedApiKey, imagePart, typeBody)
                if (response.isSuccessful) {
                    imageUrl = response.body()?.data?.image_url
                    Log.d("qwerty", "cartoonifyImage: $imageUrl")
                    withContext(Dispatchers.Main) {
                        b.progressBar.visibility = View.GONE
                        val fragment = FragmentGenerate.newInstance(imageUrl ?: "")
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainer, fragment)
                            .addToBackStack(null)
                            .commit()
                    }
                }
            } catch (e: Exception) {
                b.progressBar.visibility = View.GONE
                e.printStackTrace()
            }
        }
    }
}
