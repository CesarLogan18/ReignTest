package com.reign.test.ui.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.reign.test.R
import com.reign.test.data.model.StoryModel
import com.reign.test.data.repository.StoryRepositoryListener
import com.reign.test.databinding.ActivityMainBinding
import com.reign.test.ui.detail.DetailActivity

class MainActivity : AppCompatActivity(), CellClickListener, StoryRepositoryListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var vm: MainViewModel
    private lateinit var storyAdapter: MainAdapter

    override fun onCellClickListener(itemClicked: StoryModel) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.PARAM_URL, itemClicked.story_url)
        startActivity(intent)
    }

    override fun onStoriesUpdated() {
        storyAdapter.notifyDataSetChanged()
        binding.refresher.isRefreshing = false

        if (storyAdapter.itemCount == 0)
            prepareFeedBack()
        else
            hideFeedBack()
    }

    override fun onNetworkFailed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.dialog_offline_title))
        builder.setMessage(getString(R.string.dialog_offline_msg))

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
        }
        builder.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vm = ViewModelProvider(this).get(MainViewModel::class.java)
        vm.listener = this;

        storyAdapter = MainAdapter(vm.getAllStories(), this, this)

        setupList()
        configSwipeFeature()
        configPullToRefreshFeature()

        vm.updateStories()
    }

    private fun setupList() {
        val layoutManager = LinearLayoutManager(applicationContext)
        binding.list.layoutManager = layoutManager
        binding.list.itemAnimator = DefaultItemAnimator()
        binding.list.adapter = storyAdapter
    }

    private fun configSwipeFeature() {
        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = binding.list.adapter as MainAdapter
                vm.removeStory(viewHolder.adapterPosition)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                if (storyAdapter.itemCount == 0)
                    prepareFeedBack()

            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.list)
    }

    private fun configPullToRefreshFeature() {
        binding.refresher.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                this,
                R.color.yellow_800
            )
        )
        binding.refresher.setColorSchemeColors(Color.WHITE)

        binding.refresher.setOnRefreshListener {
            vm.updateStories()
        }
    }

    private fun prepareFeedBack() {
        binding.feedbackMsg.visibility = View.VISIBLE
        binding.feedbackMsg.text = getString(R.string.feedBack_msg)
    }

    private fun hideFeedBack() {
        binding.feedbackMsg.visibility = View.GONE
    }

}