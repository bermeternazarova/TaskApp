package com.example.taskapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskapp.databinding.ItemTaskBinding

class AdapterTask:RecyclerView.Adapter<AdapterTask.ViewHolderTask>(){

    private var tasklist= arrayListOf<TaskModel>()

    fun addTask(taskModel: TaskModel){
        tasklist.add(0,taskModel)
       // notifyDataSetChanged()  обновляет весь список
        notifyItemChanged(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTask {
        return ViewHolderTask(ItemTaskBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolderTask, position: Int) {
      holder.bind(tasklist[position])
    }

    override fun getItemCount(): Int {
        return tasklist.size
    }

    inner class ViewHolderTask (private  var binding: ItemTaskBinding):RecyclerView.ViewHolder(binding.root){
      fun bind(taskModel: TaskModel){
          binding.tvItemTitle.text= taskModel.title
          binding.tvItemDescription.text = taskModel.description
      }}}