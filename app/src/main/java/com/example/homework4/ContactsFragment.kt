package com.example.homework4

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy.*

class ContactsFragment : Fragment(R.layout.fragment_contacts) {

    private val requestPermissionLauncher =
        registerForActivityResult(RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                showContacts()
            } else {
                Toast.makeText(context,
                    "Без этого разрешения вы не сможете пользоваться основной и единственной функцией приложения",
                    Toast.LENGTH_LONG).show()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = view.findViewById<Button>(R.id.buttonGetContacts)
        button.setOnClickListener { buttonClicked() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

    private fun buttonClicked() {
        when {
            checkSelfPermission(context as MainActivity, Manifest.permission.READ_CONTACTS)
                    == PackageManager.PERMISSION_GRANTED -> {
                showContacts()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                val alertDialogBuilder = AlertDialog.Builder(context as MainActivity)
                alertDialogBuilder.setMessage("Ну очень нужно")
                alertDialogBuilder.setNegativeButton("CANCEL") { dialog, _ -> dialog.cancel() }
                alertDialogBuilder.setPositiveButton("OK") { _, _ ->
                    requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
                }
                alertDialogBuilder.show()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
            }
        }
    }

    @SuppressLint("Recycle", "Range")
    fun showContacts() {
        val contactList = mutableListOf<Contact>()
        val cursor =
            context?.contentResolver?.query(ContactsContract.Contacts.CONTENT_URI, null, null,
                null, ContactsContract.Contacts.DISPLAY_NAME + " ASC")
        if (cursor != null) {
            if (cursor.count > 0) {
                cursor.moveToFirst()
                while (cursor.moveToNext()) {
                    val name =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    val contact = Contact()
                    contact.name = name
                    contactList.add(contact)
                }
            }
        }
        val recyclerView: RecyclerView? = view?.findViewById(R.id.recyclerViewId)
        val adapter = ContactAdapter(contactList)
        adapter.stateRestorationPolicy = PREVENT_WHEN_EMPTY
        val layoutManager = LinearLayoutManager(context)
        if (recyclerView != null) {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = layoutManager
        }
    }
}