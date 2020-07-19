package com.example.carlauncher

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


data class AppInfo(var label: String, var packageName: String, var icon: Drawable)


class AppListAdapter(private val list: List<AppInfo>)
    : RecyclerView.Adapter<AppListAdapter.AppInfoViewHolder>(){

    var onItemClick: ((AppInfo) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppInfoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AppInfoViewHolder(inflater, parent);
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AppInfoViewHolder, position: Int) {
        val appInfo: AppInfo = list[position]
        holder.bind(appInfo)
    }


    inner class AppInfoViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.app_info_list_item, parent, false)) {

        private var labelView: TextView? = null
        private var iconView: ImageView? = null;

        init {
            labelView = itemView.findViewById(R.id.label)
            iconView = itemView.findViewById(R.id.icon)

            itemView.setOnClickListener {
                onItemClick?.invoke(list[adapterPosition])
            }
        }

        fun bind(appInfo: AppInfo) {
            labelView?.text = appInfo.label
            iconView?.setImageDrawable(appInfo.icon)
        }

    }

}


