package com.kazantsev.coder.view.mainfragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.kazantsev.coder.view.listfragment.UserListFragment


class ViewPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var data: MutableList<Department> = mutableListOf()

    fun setData(data: List<Department>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()

    }

    private fun createFragment(department: Int): Fragment =
        UserListFragment.newInstance(data[department].apiDepartment)

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Fragment {
       return createFragment(position)
    }

    override fun getPageTitle(position: Int) =data[position].tabDepartment
    }






