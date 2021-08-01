package com.example.content_realm

import android.icu.text.MessageFormat.format
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter
import java.lang.String.format
import java.text.MessageFormat.format
import android.text.format.DateFormat
import android.widget.TextView



class TodoListAdapter(realmResult: OrderedRealmCollection<Content>) :
    RealmBaseAdapter<Content>(realmResult) {
    override fun getItemId(position: Int): Long {
        if (adapterData != null) {
            return adapterData!![position].id
        }
        return super.getItemId(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val vh: ViewHolder
        val view: View


        if (convertView == null) {
            view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_todo, parent, false)
            vh = ViewHolder(view)
            view.tag = vh
        }
        else {
            view = convertView
            vh = view.tag as ViewHolder
        }

        if (adapterData != null) {
            val item = adapterData!![position]
            vh.textTextView.text = "장비명 : " + item.name
            vh.countTextView.text = "개수 : " + item.count.toString()
            vh.locationTextView.text = "배송지 : " + item.location
            vh.dateTextView.text = "날짜 : " + DateFormat.format("MM/dd", item.date)
        }

        return view
    }
}

class ViewHolder(view: View) {
    val dateTextView: TextView = view.findViewById(R.id.dateTextView)
    val textTextView: TextView = view.findViewById(R.id.textTextView)
    val countTextView: TextView = view.findViewById(R.id.countTextView)
    val locationTextView: TextView = view.findViewById(R.id.locationTextView)
}