package com.example.smart_city.ui.person;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.smart_city.R;

public class PersonFragment extends Fragment implements View.OnClickListener {

    private PersonViewModel personViewModel;
    private ImageView img_avatar;
    private TextView tv_account;
    private TextView tv_message;
    private TextView tv_order;
    private TextView tv_psd;
    private TextView tv_advice;
    private Button btn_quit;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        personViewModel =
//                new ViewModelProvider(this).get(PersonViewModel.class);
        View view = inflater.inflate(R.layout.fragment_person, container, false);
//        final TextView textView = view.findViewById(R.id.text_person);
//        personViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        Toolbar toolbar = view.findViewById(R.id.app_bar);
        toolbar.setTitle("个人中心");
        img_avatar = view.findViewById(R.id.img_avatar);
        tv_account = view.findViewById(R.id.tv_account);
        tv_message = view.findViewById(R.id.tv_message);
        tv_order = view.findViewById(R.id.tv_order);
        tv_psd = view.findViewById(R.id.tv_psd);
        tv_advice = view.findViewById(R.id.tv_advice);
        btn_quit = view.findViewById(R.id.btn_quit);
        btn_quit.setOnClickListener(this);
        tv_message.setOnClickListener(this);
        tv_order.setOnClickListener(this);
        tv_psd.setOnClickListener(this);
        tv_advice.setOnClickListener(this);
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_message:
                startActivity(new Intent(getActivity(), Message.class));
                break;
            case R.id.tv_order:
                startActivity(new Intent(getActivity(), OrderList.class));
                break;
            case R.id.tv_psd:
                startActivity(new Intent(getActivity(), PsdUpdate.class));
                break;
            case R.id.tv_advice:
                startActivity(new Intent(getActivity(), Advice.class));
                break;
            case R.id.btn_quit:
                getActivity().finish();
                break;
            default:
                break;
        }

    }
}