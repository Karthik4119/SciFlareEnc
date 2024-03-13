package com.example.sciflaretask.view

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sciflaretask.R
import com.example.sciflaretask.adapter.SenderReceiverAdapter
import com.example.sciflaretask.model.MessageResp
import com.example.sciflaretask.viewmodel.MyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReceiverActivity:AppCompatActivity() {
    private var rvReceiver: RecyclerView?=null
    private var msgreceiveList: ArrayList<MessageResp>? = null
    private var sender: TextView?=null
    val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)
        initView()
    }

    private fun initView() {
        sender=findViewById(R.id.sender)
        rvReceiver=findViewById(R.id.rvReceiver)
        viewModel.readAllMsg()
        sender?.setOnClickListener {
           finish()
        }

        viewModel.readAllMsgList.observe(this , Observer {
            it.body()?.let { it1 ->
                msgreceiveList?.clear()
                msgreceiveList= it.body() as ArrayList<MessageResp>?
            }
            rvReceiver?.layoutManager = LinearLayoutManager(this)
            rvReceiver?.adapter = SenderReceiverAdapter(this,msgreceiveList!!){ pos->
                shareMsg(pos)
            }
        })
    }

    private fun shareMsg(pos: Int) {
        val msg=msgreceiveList?.get(pos)?.encMes
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT,msg )
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }


}