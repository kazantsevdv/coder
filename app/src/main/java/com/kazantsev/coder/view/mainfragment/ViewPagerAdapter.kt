package com.kazantsev.coder.view.mainfragment

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kazantsev.coder.view.listfragment.UserListFragment


class ViewPagerAdapter(fragment: Fragment) :
        FragmentStateAdapter(fragment) {

    private var data: MutableList<Department> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Department>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()

    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun getTabName(pos: Int) = data[pos].tabDepartment

    override fun createFragment(department: Int): Fragment =
            UserListFragment.newInstance(data[department].apiDepartment)
}









