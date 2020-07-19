package com.example.carlauncher

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MenuScreenFragment(): Fragment() {



    lateinit var v: View;

    var position: Int = 0

    constructor(p: Int):this() {
        position = p;
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.menu_screen_fragment, container, false);

        if (position %2 == 0) {
            v.setBackgroundResource(R.color.colorPrimary);
        } else {
            v.setBackgroundResource(R.color.colorAccent);
        }

        val apps = getAppList();

        //

        val listView: RecyclerView = v.findViewById(R.id.list_view);
        listView.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = AppListAdapter(apps)
        }

        val adapter: AppListAdapter = listView.adapter as AppListAdapter;

        adapter.onItemClick = { appInfo ->

            // do something with your item
            Log.d("TAG", appInfo.packageName)
            val launchIntent =
                this.context?.packageManager?.getLaunchIntentForPackage(appInfo.packageName)
            startActivity(launchIntent)
        }

        return v;
    }


    companion object {
        fun getInstance(position: Int): MenuScreenFragment {
            val instance = MenuScreenFragment(position);
            // instance.position = position;
            return instance
        }
    }


    fun getActivityIcon(
        context: Context,
        packageName: String?,
        activityName: String?
    ): Drawable? {
        val pm: PackageManager = context.packageManager
        val intent = Intent()
        intent.component = ComponentName(packageName!!, activityName!!)
        val resolveInfo = pm.resolveActivity(intent, 0)
        return resolveInfo.loadIcon(pm)
    }


    fun getAppList(): ArrayList<AppInfo> {
        val pm: PackageManager = this.context!!.packageManager;
        var appsList = ArrayList<AppInfo>()

        val i = Intent(Intent.ACTION_MAIN, null)
        i.addCategory(Intent.CATEGORY_LAUNCHER)

        val allApps = pm.queryIntentActivities(i, 0)
        for (ri in allApps) {
            val label = ri.loadLabel(pm).toString();
            val packageName = ri.activityInfo.packageName
            val icon = ri.activityInfo.loadIcon(pm)
            val app = AppInfo(label, packageName, icon)
            appsList.add(app)
        }
        return appsList;
    }
}