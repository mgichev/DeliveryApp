package com.deliveryapp.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.deliveryapp.databinding.FragmentAccountBinding
import com.yandex.mapkit.search.SearchManager
import com.yandex.mapkit.search.Session


class AccountFragment: Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchManager: SearchManager
    private lateinit var session: Session
    val vm by viewModels<AccountViewModel>()
    // private val booksViewModel: ShopBooksViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        searchManager = SearchFactory.getInstance().createSearchManager(SearchManagerType.ONLINE)
//        binding.btn.setOnClickListener {
//            session = searchManager.submit(
//                Point(
//                    54.339067005693984, 41.90954273678974),
//                16,
//                SearchOptions(),
//                this
//            )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    override fun onSearchResponse(p0: Response) {
//        Log.d("Test", "Worked")
//    }
//
//    override fun onSearchError(p0: Error) {
//        Log.d("Test", "no Worked")
//    }
}