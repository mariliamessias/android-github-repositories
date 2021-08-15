package br.com.githubrepositories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.githubrepositories.databinding.ListItemsBinding
import br.com.githubrepositories.models.Items
import com.bumptech.glide.Glide

class MainActivityAdapter() : RecyclerView.Adapter<MainActivityAdapter.MainActivityAdapterHolder>() {

    private var repositoriesList = ArrayList<Items>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainActivityAdapter.MainActivityAdapterHolder {
        return MainActivityAdapterHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_items, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: MainActivityAdapter.MainActivityAdapterHolder,
        position: Int
    ) {
        holder.binding.repositoryName.text = "${repositoriesList[position].name}"
        holder.binding.stars.text = "${repositoriesList[position].startsCount}"
        holder.binding.author.text = "${repositoriesList[position].owner.name}"
        holder.binding.forks.text = "${repositoriesList[position].forksCount}"

        Glide
            .with(holder.itemView)
            .load(repositoriesList[position].owner.avatarUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.binding.thumbnail)
    }

    override fun getItemCount(): Int {
        return repositoriesList.size
    }

    class MainActivityAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ListItemsBinding.bind(itemView)
    }
    
    fun updateList(repositoriesList: ArrayList<Items>, oldCount: Int, repositoriesListSize: Int) {
        this.repositoriesList = repositoriesList
        notifyItemRangeInserted(oldCount, repositoriesListSize)
    }
}

