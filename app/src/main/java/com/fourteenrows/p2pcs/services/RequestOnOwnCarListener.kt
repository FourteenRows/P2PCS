package com.fourteenrows.p2pcs.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.fourteenrows.p2pcs.R
import com.fourteenrows.p2pcs.model.database.ModelFirebase
import com.fourteenrows.p2pcs.model.utility.ModelDates
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


class RequestOnOwnCarListener(private val context: Context) {
    private val appContext = context.applicationContext

    fun initialize(cids: ArrayList<String>) {
        FirebaseFirestore.getInstance()
            .collection("Requests")
            .addSnapshotListener { querySnapshot, _ ->

                if (!querySnapshot!!.metadata.isFromCache) {
                    querySnapshot.documents.forEach { req ->
                        if (req.id in cids) {
                            FirebaseFirestore.getInstance()
                                .collection("Requests")
                                .document(req.id)
                                .addSnapshotListener { documentSnapshot, _ ->

                                    if (documentSnapshot != null && documentSnapshot["accepted"] == null && documentSnapshot["model"] != null) {
                                        notifyRequestPermission(
                                            documentSnapshot.id,
                                            documentSnapshot["model"] as String,
                                            documentSnapshot["applicant"] as String,
                                            (documentSnapshot["startDate"] as Timestamp).toDate(),
                                            (documentSnapshot["endDate"] as Timestamp).toDate(),
                                            documentSnapshot["username"] as String
                                        )
                                    }
                                }
                        }
                    }
                }
            }
    }

    private fun notifyRequestPermission(
        cid: String,
        model: String,
        owner: String,
        startDate: Date,
        endDate: Date,
        username: String
    ) {
        val mNotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val acceptIntent = Intent(appContext, NotificationAcceptedReceiver::class.java)
        acceptIntent.putExtra("cid", cid)
        acceptIntent.putExtra("model", model)
        acceptIntent.putExtra("uid", owner)
        acceptIntent.putExtra("startDate", startDate.time)
        acceptIntent.putExtra("endDate", endDate.time)
        val pendingAcceptIntent =
            PendingIntent.getBroadcast(context, 0, acceptIntent, 0)

        val rejectedIntent = Intent(appContext, NotificationRejectedReceiver::class.java)
        rejectedIntent.putExtra("cid", cid)
        val pendingRejectIntent =
            PendingIntent.getBroadcast(context, 0, rejectedIntent, 0)

        val bigText = NotificationCompat.BigTextStyle()
        bigText.setSummaryText("Prenotazione")
        bigText.setBigContentTitle("Richiesta di prenotazione da $username")

        val mBuilder = NotificationCompat.Builder(appContext, "notify_001")
            .setStyle(bigText)
            .setContentText(
                "Modello: $model \nIl ${ModelDates.toLocaleTimeFormat(startDate)}\nSlot: ${ModelDates.toTinyTimeSpanFormat(
                    Date(endDate.time - startDate.time)
                )}"
            )
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setPriority(Notification.PRIORITY_HIGH)
            .addAction(R.drawable.background_red, "Accetta", pendingAcceptIntent)
            .addAction(R.drawable.background_blue, "Rifiuta", pendingAcceptIntent)
            .setVibrate(longArrayOf(500, 0, 500, 500))
            .setOngoing(true)

        if (Build.VERSION.SDK_INT >= 26) {
            val channelId = "Your_channel_id"
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_HIGH
            )
            mNotificationManager.createNotificationChannel(channel)
            mBuilder.setChannelId(channelId)
        }

        if (owner != ModelFirebase().getUid()) {
            mNotificationManager.notify(0, mBuilder.build())
        }
    }
}
