package com.example.cartrack.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.cartrack.R
import com.example.cartrack.SingleUserActivity
import com.example.cartrack.response.Users


class UserAdapter(private val nameList: List<Users>?, private val context: Context?) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onBindViewHolder(holder: UserViewHolder, position: Int)
    {
        holder.bindData(
            this.nameList!![position].name.toString(),
            nameList[position].email.toString(),
            nameList[position].company!!.name.toString()
        )
        holder.cartView?.setOnClickListener(View.OnClickListener {
            val idd = this.nameList[position].id
            val intent = Intent(context,SingleUserActivity::class.java)
            intent.putExtra("ID", idd)
            context?.startActivity(intent)
        })
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
        var cartView = itemView?.findViewById<CardView>(R.id.clickCard)
        fun bindData(name: String, email: String, phone: String) {
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