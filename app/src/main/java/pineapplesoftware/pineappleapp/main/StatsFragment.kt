package pineapplesoftware.pineappleapp.main;

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.components.Description
import kotlinx.android.synthetic.main.fragment_stats.*
import pineapplesoftware.pineappleapp.R
import java.util.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry


/**
 * Created by root on 2017-09-17.
 */

class StatsFragment : Fragment() {

    //region Attributes

    private val TAG : String = "StatsFragment"
    private val yData = floatArrayOf(5f, 10f, 15f, 30f, 40f)
    private val xData = arrayOf("Sony", "Huawei", "LG", "Apple", "Samsung")

    //endregion

    //region Overridden Methods

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_stats, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        configureChart()
        loadData()
    }

    //endregion

    //region Private Methods

    private fun loadData() {
        val yVals1 = ArrayList<PieEntry>()

        for (i in 0..yData.size - 1)
            yVals1.add(PieEntry(yData[i], i.toFloat()))

        val xVals = ArrayList<String>()

        for (i in 0..xData.size - 1)
            xVals.add(xData[i])

        // create pie data set
        val dataSet = PieDataSet(yVals1, "Market Share")
        dataSet.sliceSpace = 8f
        dataSet.selectionShift = 7f

        // add many colors
        val colors = ArrayList<Int>()

//        for (c in ColorTemplate.VORDIPLOM_COLORS)
//            colors.add(c)
//
//        for (c in ColorTemplate.JOYFUL_COLORS)
//            colors.add(c)
//
//        for (c in ColorTemplate.COLORFUL_COLORS)
//            colors.add(c)
//
//        for (c in ColorTemplate.LIBERTY_COLORS)
//            colors.add(c)
//
//        for (c in ColorTemplate.PASTEL_COLORS)
//            colors.add(c)

        colors.add(ContextCompat.getColor(context, R.color.colorAccent))
        colors.add(ContextCompat.getColor(context, R.color.colorEarning))
        colors.add(ContextCompat.getColor(context, R.color.colorPieChart1))
        colors.add(ContextCompat.getColor(context, R.color.colorPieChart3))
        colors.add(ContextCompat.getColor(context, R.color.colorPieChart2))
        dataSet.colors = colors

        // instantiate pie data object now
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.WHITE)
        data.setValueTypeface(ResourcesCompat.getFont(context, R.font.roboto_light))

        statsPieChart.data = data

        // undo all highlights
        statsPieChart.highlightValues(null)

        // update pie chart
        statsPieChart.invalidate()
    }

    private fun configureChart() {
        val description  = Description()
        description.text = ""
        description.textColor = ContextCompat.getColor(context, R.color.colorPrimaryExtraLight)
        description.typeface = ResourcesCompat.getFont(context, R.font.roboto_light)
        description.textSize = 15f
        description.textAlign = Paint.Align.CENTER

        statsPieChart.setUsePercentValues(true)
        statsPieChart.description = description
        statsPieChart.isDrawHoleEnabled = true
        statsPieChart.holeRadius = 30f
        statsPieChart.transparentCircleRadius = 10f
        statsPieChart.setHoleColor(ContextCompat.getColor(context, android.R.color.transparent))
        statsPieChart.rotationAngle = 0f
        statsPieChart.isRotationEnabled = true
    }

    //endregion
}
