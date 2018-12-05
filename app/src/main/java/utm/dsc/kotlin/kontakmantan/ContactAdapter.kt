package utm.dsc.kotlin.kontakmantan

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.list_block.view.*

class ContactAdapter(con:Context, arrList: ArrayList<ContactData>): BaseAdapter() {

    var arrayList = ArrayList<ContactData>()
    var context:Context? = null
    var myInflater: LayoutInflater? = null


    init{
        this.context = con
        this.myInflater = LayoutInflater.from(context)
        this.arrayList = arrList
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var myView = myInflater!!.inflate(R.layout.list_block,null)
        var ConObj = arrayList[position]

        var full_name : String = ConObj.FirstName.toString()+ " " + ConObj.LastName.toString()
        myView.contact_name.text = full_name
        return myView
    }

    override fun getItem(position: Int): Any {
        return arrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return arrayList.size
    }

}
