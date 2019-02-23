package test.com.roomnavigator.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import test.com.roomnavigator.R
import test.com.roomnavigator.entity.Name


class NameListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<NameListAdapter.NameViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var names = emptyList<Name>() // Cached copy of names

    inner class NameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameItemView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return NameViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NameViewHolder, position: Int) {
        val current = names[position]
        holder.nameItemView.text = current.name

    }

    internal fun setNames(names: List<Name>) {
        this.names = names
        notifyDataSetChanged()
    }

    override fun getItemCount() = names.size
}
