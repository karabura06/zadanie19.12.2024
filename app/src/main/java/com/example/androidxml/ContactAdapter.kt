package com.example.androidxml

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(
    private val contactList: List<Contact>,
    private val onDeleteClick: (Contact) -> Unit
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contactList[position]
        holder.nameTextView.text = contact.name
        holder.phoneTextView.text = contact.phone
        holder.deleteButton.setOnClickListener {
            onDeleteClick(contact)
        }
    }

    override fun getItemCount(): Int = contactList.size

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.contact_name)
        val phoneTextView: TextView = itemView.findViewById(R.id.contact_phone)
        val deleteButton: Button = itemView.findViewById(R.id.delete_button)
    }
}
