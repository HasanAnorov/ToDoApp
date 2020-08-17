package com.example.todoapp

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class SwipeToDelete(var adapter:TaskAdapter,var context:Context,var recycler:MainActivity):ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {

    private val db = FirebaseFirestore.getInstance()
    var taskName:String=""
    private val taskCollectionRef = FirebaseFirestore.getInstance().collection("task")
    lateinit var documentReference:DocumentReference
    private val noteRef =
        db.document("Notebook/My First Note")


    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        var positionItem=viewHolder.adapterPosition
        var    mRecentlyDeletedItem =adapter.models[positionItem]
        var   mRecentlyDeletedItemPosition = positionItem

        when(direction){
            ItemTouchHelper.LEFT -> {
                noteRef.delete()

                taskName= adapter.models[positionItem].taskName
                adapter.models.removeAt(positionItem)
                adapter.notifyItemRemoved(positionItem)

                Snackbar.make(recycler.recyclerView,taskName,Snackbar.LENGTH_LONG)

                    .setAction("Undo") {

                        adapter.models.add(mRecentlyDeletedItemPosition,mRecentlyDeletedItem)
                        adapter.notifyItemInserted(mRecentlyDeletedItemPosition)
                        //   recycler.recyclerView.scrollToPosition(mAdapterPosition)

                    }.show()
            }
            ItemTouchHelper.RIGHT -> {
                val intent = Intent(context, EditingTask::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            
            }

        }

    }

}




