package com.example.homework4

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.homework4.databinding.ActivityMainBinding


const val BUTTON_VISIBILITY_KEY = "BUTTON_VISIBILITY_KEY"
const val ITEM_COUNT_KEY = "ITEM_COUNT_KEY"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonToFragment.setOnClickListener {
            addContactsFragment()
            it.visibility = View.GONE
        }
    }

    private fun addContactsFragment() {
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .add(R.id.fragmentContainerView, ContactsFragment())
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val buttonVisibility = binding.buttonToFragment.visibility
        outState.putInt(BUTTON_VISIBILITY_KEY, buttonVisibility)
        if (buttonVisibility == View.GONE) {
            val fragment = binding.fragmentContainerView.getFragment<ContactsFragment>()
            val recycler = fragment.view?.findViewById<RecyclerView>(R.id.recyclerViewId)
            val itemCount = recycler?.adapter?.itemCount
            if (itemCount != null) {
                outState.putInt(ITEM_COUNT_KEY, itemCount)
            }
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val buttonVisibility = savedInstanceState.getInt(BUTTON_VISIBILITY_KEY)
        val itemCount = savedInstanceState.getInt(ITEM_COUNT_KEY)
        binding.buttonToFragment.visibility = buttonVisibility
        if (buttonVisibility == View.GONE) {
            val fragment = binding.fragmentContainerView.getFragment<ContactsFragment>()
            val recycler = fragment.view?.findViewById<RecyclerView>(R.id.recyclerViewId)
            if (recycler != null && itemCount > 0) {
                fragment.showContacts()
            }
        }
    }
}