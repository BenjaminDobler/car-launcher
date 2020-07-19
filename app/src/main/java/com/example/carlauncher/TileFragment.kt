import android.app.Activity
import android.appwidget.AppWidgetHost
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.carlauncher.R


class TileFragment():Fragment() {

    lateinit var v: View;

    lateinit var mAppWidgetManager: AppWidgetManager;
    lateinit var mAppWidgetHost: AppWidgetHost;
    private val APPWIDGET_HOST_ID = 1
    private val REQUEST_PICK_APPWIDGET = 2
    private val REQUEST_CREATE_APPWIDGET = 3

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mAppWidgetManager = AppWidgetManager.getInstance(context!!.applicationContext)
        mAppWidgetHost = AppWidgetHost(context!!.applicationContext, APPWIDGET_HOST_ID)

        v = inflater.inflate(R.layout.tile_fragment, container, false);


        val button: FrameLayout =  v.findViewById(R.id.frame_layout)
        button.setOnClickListener {
            selectWidget(it)
        }


        //
        return v;
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_PICK_APPWIDGET) {
                configureWidget(data)
                println("configure app widget")
            } else if (requestCode == REQUEST_CREATE_APPWIDGET) {
                createWidget(data)
                println("create app widget")
            }
        } else if (resultCode == Activity.RESULT_CANCELED && data != null) {
            val appWidgetId = data.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1)
            if (appWidgetId != -1) {
                mAppWidgetHost.deleteAppWidgetId(appWidgetId)
            }
        }
    }

    private fun configureWidget(data: Intent?) {
        val extras = data?.extras
        val appWidgetId = extras!!.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, -1)
        val appWidgetInfo = mAppWidgetManager.getAppWidgetInfo(appWidgetId)
        if (appWidgetInfo.configure != null) {
            val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE)
            intent.component = appWidgetInfo.configure
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            startActivityForResult(intent, REQUEST_CREATE_APPWIDGET)
        } else {
            createWidget(data)
        }
    }

    fun createWidget(data: Intent?) {
        val extras = data?.extras
        val appWidgetId = extras!!.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, -1)
        println("App Widget ID $appWidgetId")
        val appWidgetInfo = mAppWidgetManager.getAppWidgetInfo(appWidgetId)
        val hostView = mAppWidgetHost.createView(
            context!!.applicationContext,
            appWidgetId,
            appWidgetInfo
        )
        hostView.setAppWidget(appWidgetId, appWidgetInfo)
        val frameLayout: FrameLayout = v.findViewById(R.id.frame_layout)
        frameLayout.removeAllViews()
        frameLayout.addView(hostView)
        // Log.i(TAG, "The widget size is: " + appWidgetInfo.minWidth + "*" + appWidgetInfo.minHeight)
    }



    fun selectWidget(view: View) {
        val appWidgetId: Int = this.mAppWidgetHost.allocateAppWidgetId()
        val pickIntent = Intent(AppWidgetManager.ACTION_APPWIDGET_PICK)
        pickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        startActivityForResult(pickIntent, REQUEST_PICK_APPWIDGET)
    }
}