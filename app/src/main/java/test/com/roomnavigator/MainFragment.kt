package test.com.roomnavigator


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_main.*
import test.com.roomnavigator.adapter.NameListAdapter
import test.com.roomnavigator.providers.NameViewModel


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    val action = MainFragmentDirections.actionMainFragmentToDetailFragment()
    private lateinit var nameViewModel: NameViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Sending data from one fragment to another fragment
        buttonAdd.setOnClickListener {
            findNavController().navigate(action)
        }
        rvNames.layoutManager = LinearLayoutManager(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = activity?.let { NameListAdapter(it) }
        rvNames.adapter = adapter


        nameViewModel = ViewModelProviders.of(this).get(NameViewModel::class.java)

        nameViewModel.allnames.observe(this, Observer { names ->
            names?.let {
                adapter?.setNames(names)
            }
        })

    }

}
