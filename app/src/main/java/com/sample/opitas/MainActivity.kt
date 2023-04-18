package com.sample.opitas

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.sample.opitas.databinding.ActivityMainBinding
import com.sample.opitas.models.LanguagesListModel
import com.sample.opitas.utils.SearchManager
import com.sample.opitas.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    val languageMap = hashMapOf<String, Pair<String, ArrayList<String>>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel.getLanguagesList()
        addObservers()
        addListener()
    }

    private fun addListener() {
        val searchManager = SearchManager(::performSearch)
        binding.tvInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isEmpty() == true){
                    binding.tvOutput.text = ""
                }
                searchManager.search(s?.toString() ?: "")
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun performSearch(query: String) {
        if (query.isEmpty()) return
        val source = if (binding.sourceSpinner.selectedItem.toString()
                .contains("auto")
        ) "auto" else languageMap[binding.sourceSpinner.selectedItem.toString()]?.first.toString()
        val target = languageMap[binding.outputSpinner.selectedItem.toString()]?.first.toString()
        mainViewModel.getSearchTranslations(query, source, target)
    }

    private fun addObservers() {
        mainViewModel.languagesListModel.observe(this) { response ->
            if (response.isSuccessful) {
                response.body()?.forEach { languageModel ->
                    languageMap[languageModel.name] =
                        Pair(languageModel.code, languageModel.targets)
                }
                inflateSpinner(response.body())
            } else {
                Toast.makeText(this, response.errorBody().toString(), Toast.LENGTH_LONG).show()
            }
        }

        mainViewModel.searchResponseModel.observe(this) {
            if (it.isSuccessful) {
                binding.tvOutput.text = it.body()?.translatedText.toString()
            } else {
                Toast.makeText(this, it.errorBody().toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    @Suppress("warnings")
    private fun inflateSpinner(languagesListModel: ArrayList<LanguagesListModel>?) {
        val languageList = languageMap.keys.toMutableList()
        languageList.add(0, "auto")

        binding.sourceSpinner.adapter =
            ArrayAdapter(this, R.layout.custom_spinner_view, languageList)

        binding.sourceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = languageList.get(position)

                val targetCodeList = languageMap[selectedItem]?.second
                var targetNameList = mutableListOf<String>()
                if (selectedItem.contains("auto")) {
                    targetNameList = languageMap.keys.toMutableList()
                } else {
                    languageMap.forEach {
                        if (targetCodeList?.contains(it.value.first) == true) {
                            targetNameList.add(it.key)
                        }
                    }
                }
                updateDestinationSpinner(targetNameList)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.outputSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                performSearch(binding.tvInput.text.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun updateDestinationSpinner(languageList: List<String>) {
        binding.outputSpinner.adapter =
            ArrayAdapter(this, R.layout.custom_spinner_view, languageList)
    }
}