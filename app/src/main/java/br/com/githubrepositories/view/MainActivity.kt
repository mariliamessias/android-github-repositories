package br.com.githubrepositories.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.githubrepositories.R
import br.com.githubrepositories.adapter.MainActivityAdapter
import br.com.githubrepositories.databinding.ActivityMainBinding
import br.com.githubrepositories.models.Items
import br.com.githubrepositories.models.Repositories
import br.com.githubrepositories.services.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainActivityAdapter: MainActivityAdapter
    private var apiClient = ApiClient()
    private var currentPage = 1
    private var totalAvailablePages : Int? = 1
    private lateinit var itemsList: ArrayList<Items>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        binding.recyclerview.setHasFixedSize(true)
        binding.recyclerview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        itemsList = ArrayList()
        mainActivityAdapter = MainActivityAdapter()
        binding.recyclerview.adapter = mainActivityAdapter
        binding.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!binding.recyclerview.canScrollVertically(1)) {
                    if (totalAvailablePages!= null) {
                    currentPage ++
                    loadPageList()
                    }
                }
            }
        })
        loadPageList()
    }

    private fun loadPageList() {
        toogleLoading()
        apiClient.callRepositoriesListRequest(currentPage, object : Callback<Repositories> {
            override fun onResponse(call: Call<Repositories>, response: Response<Repositories>) {
                if (response.isSuccessful) {
                    val repositoryModel = response.body()
                    if (repositoryModel != null) {
                        val oldCount = itemsList.size
                        totalAvailablePages = getTotalAvailablePages(repositoryModel)
                        itemsList.addAll(repositoryModel.items)
                        mainActivityAdapter.updateList(itemsList, oldCount, itemsList.size)
                        Log.e(
                            TAG,
                            "oldCount $oldCount totalAvailablePages $totalAvailablePages tvShowList ${itemsList.size}"
                        )
                    }
                }
                toogleLoading()
            }

            override fun onFailure(call: Call<Repositories>, t: Throwable) {
                t.printStackTrace()
                Log.e(TAG, "exception", t)
                toogleLoading()
            }
        })
    }

    private fun getTotalAvailablePages(repository: Repositories) : Int? {
        try{
            return repository.items.size
        }catch (ex : Exception){
            return null
        }
    }

    private fun toogleLoading() {
        if (currentPage == 1) {
            if (binding.defaultProgress.isShown) {
                binding.defaultProgress.visibility = View.GONE
            } else {
                binding.defaultProgress.visibility = View.VISIBLE
            }
        } else {
            if (binding.loadMoreProgress.isShown) {
                binding.loadMoreProgress.visibility = View.GONE
            } else {
                binding.loadMoreProgress.visibility = View.VISIBLE
            }
        }
    }
}