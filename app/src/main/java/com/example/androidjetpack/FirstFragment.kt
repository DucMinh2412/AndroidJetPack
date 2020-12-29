package com.example.androidjetpack

import android.os.Bundle
import android.util.Log.e
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.work.*
import com.example.androidjetpack.DoingWork.Companion.DUC_NO_PRO_2
import com.example.androidjetpack.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    companion object {
        const val DUC_NO_PRO = "DucNoPro"
    }

    private lateinit var binding: FragmentFirstBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = Navigation.findNavController(view)

        /*
        navigation component
         */
        binding.tvPro.setOnClickListener {
            //  setOneTimeWorkRequest()
            //  navController.navigate(R.id.action_firstFragment_to_secondFragment)
        }

        /**
        WorkManager : thực thi dưới background, bất đồng bộ, có thể lên lịch thực thi
        OneTimeWorkRequest : thực thi 1 lần
        Constraints : cài đặt thêm trong quá trình thực thi
        Setting output and input data
         */
        setOneTimeWorkRequest()
    }

    private fun setOneTimeWorkRequest() {
        val constraints = Constraints.Builder()
                .setRequiresCharging(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        val data = Data.Builder()
                .putInt(DUC_NO_PRO, 1)
                .build()

        val task = OneTimeWorkRequest.Builder(DoingWork::class.java)
                .setConstraints(constraints)
                .setInputData(data)
                .build()

        val workManager = WorkManager.getInstance(requireContext().applicationContext)
        workManager.enqueue(task)
        workManager.getWorkInfoByIdLiveData(task.id).observe(
                viewLifecycleOwner, Observer { workInfo ->

            if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                binding.tvPro.text = workInfo.state.name
            }

            if (workInfo.state.isFinished) {
                val dataDoingWork = workInfo.outputData.getString(DUC_NO_PRO_2)
                e("af", dataDoingWork + "")
            }
        })
    }

}