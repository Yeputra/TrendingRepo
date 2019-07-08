package id.gojek.trendingrepo

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import id.gojek.trendingrepo.model.Repo
import org.jetbrains.anko.sdk27.coroutines.onClick

class MainAdapter(private val context: Context, private val repoCollection: List<Repo>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_trending_repo, parent, false))
    }

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.llParent.onClick {
            var expanded = repoCollection.get(position).isExpanded
            repoCollection.get(position).isExpanded = !expanded
            notifyItemChanged(position)
        }
        var expanded = repoCollection.get(position).isExpanded
        holder.llRepo.setVisibility(if (expanded) View.VISIBLE else View.GONE)

        Glide.with(context)
            .load(repoCollection.get(position).avatar)
            .into(holder.ivAvatar)

        holder.tvAuthor.text = repoCollection.get(position).author
        holder.tvRepo.text = repoCollection.get(position).name
        holder.tvDescription.text = repoCollection.get(position).description
        holder.tvLanguage.text = repoCollection.get(position).language
        if(repoCollection.get(position).languageColor != null){
            holder.ivLanguage.setColorFilter(Color.parseColor(repoCollection.get(position).languageColor))
        }
        holder.tvStarCount.text = repoCollection.get(position).stars.toString()
        holder.tvForkCount.text = repoCollection.get(position).forks.toString()
    }

    override fun getItemCount(): Int {
        return repoCollection.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivAvatar: CircleImageView = itemView.findViewById(R.id.civ_avatar)
        val tvAuthor: TextView = itemView.findViewById(R.id.tv_author_name)
        val tvRepo: TextView = itemView.findViewById(R.id.tv_repo_name)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_repo_description)
        val tvLanguage: TextView = itemView.findViewById(R.id.tv_language)
        val tvStarCount: TextView = itemView.findViewById(R.id.tv_star_count)
        val tvForkCount: TextView = itemView.findViewById(R.id.tv_fork_count)
        val ivLanguage: ImageView = itemView.findViewById(R.id.iv_language_color)
        val llRepo: LinearLayout = itemView.findViewById(R.id.ll_repo_item)
        val llParent: LinearLayout = itemView.findViewById(R.id.ll_parent)
    }
}

