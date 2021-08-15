package br.com.githubrepositories

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.githubrepositories.databinding.ActivityMainBinding
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
    private lateinit var tvShowList: ArrayList<ItemsModel>

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
        tvShowList = ArrayList()
        mainActivityAdapter = MainActivityAdapter()
        binding.recyclerview.adapter = mainActivityAdapter
        binding.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!binding.recyclerview.canScrollVertically(1)) {
                    if (totalAvailablePages!= null) {
                    currentPage += 1
                    loadPageList()
                    }
                }
            }
        })
        loadPageList()
    }

    private fun loadPageList() {
        toogleLoading()
        apiClient.callRepositoriesListRequest(currentPage, object : Callback<RepositoriesModel> {
            override fun onResponse(call: Call<RepositoriesModel>, response: Response<RepositoriesModel>) {
                if (response.isSuccessful) {
                    val repositoryModel = response.body()
                    if (repositoryModel != null) {
                        val oldCount = tvShowList.size
                        totalAvailablePages = getTotalAvailablePages(repositoryModel)
                        tvShowList.addAll(repositoryModel.items)
                        mainActivityAdapter.updateList(tvShowList, oldCount, tvShowList.size)
                        Log.e(
                            TAG,
                            "oldCount $oldCount totalAvailablePages $totalAvailablePages tvShowList ${tvShowList.size}"
                        )
                    }
                }
                toogleLoading()
            }

            override fun onFailure(call: Call<RepositoriesModel>, t: Throwable) {
                t.printStackTrace()
                Log.e(TAG, "exception", t)
                toogleLoading()
            }
        })
    }

    private fun getTotalAvailablePages(repositoryModel: RepositoriesModel) : Int? {
        try{
            return repositoryModel.items.size
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