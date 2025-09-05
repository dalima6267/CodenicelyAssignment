package com.dalima.wikipedia_codenicely_assignment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dalima.wikipedia_codenicely_assignment.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val titles = listOf("Random", "Featured", "Categories")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        val toggle = androidx.appcompat.app.ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar, 0, 0
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val pagerAdapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, pos ->
            tab.text = titles[pos]
        }.attach()

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            if (menuItem.itemId == R.id.menu_settings) {
                // TODO: Implement SettingsFragment if required
            }
            binding.drawerLayout.closeDrawers()
            true
        }
    }
}
