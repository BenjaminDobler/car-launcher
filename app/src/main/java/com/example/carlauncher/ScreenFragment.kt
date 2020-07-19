package com.example.carlauncher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


class ScreenFragment(): Fragment() {



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

        v = inflater.inflate(R.layout.screen_fragment, container, false);

        if (position %2 == 0) {
            v.setBackgroundResource(R.color.colorPrimary);
        } else {
            v.setBackgroundResource(R.color.colorAccent);
        }


        //
        return v;
    }


    companion object {
        fun getInstance(position: Int): ScreenFragment {
            val instance = ScreenFragment(position);
            // instance.position = position;
            return instance
        }
    }
}