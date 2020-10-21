package com.example.cartrack.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cartrack.R
import com.example.cartrack.response.Users


class UserAdapter(val nameList: List<Users>?) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onBindViewHolder(holder: UserViewHolder, position: Int)
    {
        holder.bindData(
            this.nameList!![position].name.toString(),
            nameList[position].email.toString(),
            nameList[position].company!!.name.toString())
    }

    override fun getItemCount(): Int
    {
        return nameList!!.size
    }

    class UserViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)
    {
        var textView = itemView?.findViewById<TextView>(R.id.nameOf)
        var date = itemView?.findViewById<TextView>(R.id.emailA)
        var shortDec = itemView?.findViewById<TextView>(R.id.comName)
        fun bindData(name: String, email: String, phone: String ) {
            textView?.text = name
            date?.text =email
            shortDec?.text =phone
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent?.context)
            .inflate(R.layout.user_row, parent, false);

        //return ViewHolder
        return UserViewHolder(view)
    }
}