package com.example.androidxml

import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var contactAdapter: ContactAdapter
    private val contactList = mutableListOf<Contact>()
    private lateinit var addContactButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.contacts_recycler_view)
        addContactButton = findViewById(R.id.add_contact_button)

        // Настройка RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        contactAdapter = ContactAdapter(contactList) { contact -> deleteContact(contact) }
        recyclerView.adapter = contactAdapter

        // Обработчик для кнопки "Добавить контакт"
        addContactButton.setOnClickListener {
            showAddContactDialog()
        }
    }

    private fun showAddContactDialog() {
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_add_contact, null)
        builder.setView(view)

        val nameEditText = view.findViewById<EditText>(R.id.name_edit_text)
        val phoneEditText = view.findViewById<EditText>(R.id.phone_edit_text)

        builder.setTitle("Добавить контакт")
            .setPositiveButton("Добавить") { dialog, _ ->
                val name = nameEditText.text.toString().trim()
                val phone = phoneEditText.text.toString().trim()

                if (validateInput(name, phone)) {
                    addContact(Contact(name, phone))
                } else {
                    showValidationError()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun validateInput(name: String, phone: String): Boolean {
        return !(TextUtils.isEmpty(name) || TextUtils.isEmpty(phone))
    }

    private fun showValidationError() {
        AlertDialog.Builder(this)
            .setTitle("Ошибка ввода")
            .setMessage("Пожалуйста, заполните оба поля: имя и номер телефона.")
            .setPositiveButton("ОК") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun addContact(contact: Contact) {
        contactList.add(contact)
        contactAdapter.notifyItemInserted(contactList.size - 1)
    }

    private fun deleteContact(contact: Contact) {
        val position = contactList.indexOf(contact)
        if (position >= 0) {
            contactList.removeAt(position)
            contactAdapter.notifyItemRemoved(position)
        }
    }
}
