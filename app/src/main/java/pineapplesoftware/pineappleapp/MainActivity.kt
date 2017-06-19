package pineapplesoftware.pineappleapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
{
    private var mHelloWorldTextView : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getViews()
    }

    fun getViews() {
        mHelloWorldTextView = findViewById(R.id.txt_hello_world) as TextView?
        mHelloWorldTextView?.text = "City of Stars"
    }

}
