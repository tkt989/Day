package info.tkt989.day.ui

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import info.tkt989.day.MyApplication

import info.tkt989.day.R
import info.tkt989.day.model.Day
import kotlinx.android.synthetic.main.fragment_day_list.*
import javax.inject.Inject

class DayListFragment : Fragment() {
    private var listener: DayListFragmentListener? = null
    @Inject
    lateinit var dayListViewModel: DayListViewModel
    lateinit var adapter: ArrayAdapter<Day>
    var editMode: Boolean = true
        set(value) {
            addButton?.visibility = if (value) View.VISIBLE else View.GONE
            field = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            editMode = it.getBoolean("editMode")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_day_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1)
        dayListView.adapter = adapter

        dayListViewModel = ViewModelProviders.of(this).get(DayListViewModel::class.java)
        dayListViewModel.init((activity?.application as MyApplication).dateStore.dao)
        dayListViewModel.dayList.observe(this) {
            adapter.clear()
            adapter.addAll(it)
        }

        addButton.setOnClickListener {
            listener?.onAdd()
        }

        dayListView.setOnItemClickListener { _, _, pos, _ ->
            val day = adapter.getItem(pos)
            listener?.onDayClick(day)
        }

        editMode = editMode
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DayListFragmentListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface  DayListFragmentListener {
        fun onDayClick(day: Day)
        fun onAdd()
    }

    companion object {
        @JvmStatic
        fun newInstance(editMode: Boolean) =
                DayListFragment().apply {
                    arguments = Bundle().apply {
                        putBoolean("editMode", editMode)
                    }
                }
    }
}
