package id.gojek.trendingrepo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import id.gojek.trendingrepo.model.Repo
import org.jetbrains.anko.sdk27.coroutines.onClick

class MainAdapter(private val repo: List<Repo>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_trending_repo, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.llParent.onClick {
            var expanded = repo.get(position).isExpanded
            repo.get(position).isExpanded = !expanded
            notifyItemChanged(position)
        }
        var expanded = repo.get(position).isExpanded
        holder.llRepo.setVisibility(if (expanded) View.VISIBLE else View.GONE)
        holder.tvAuthor.text = repo.get(position).author
        holder.tvRepo.text = repo.get(position).name
        holder.tvDescription.text = repo.get(position).description
        holder.tvLanguage.text = repo.get(position).language
//        holder.ivLanguage.backgroundColor = Color.parseColor(repo.get(position).languageColor)
        holder.tvStarCount.text = repo.get(position).stars.toString()
        holder.tvForkCount.text = repo.get(position).forks.toString()
    }

    override fun getItemCount(): Int {
        return repo.size
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

