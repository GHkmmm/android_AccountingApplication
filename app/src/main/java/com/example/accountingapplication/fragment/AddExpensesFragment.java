package com.example.accountingapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.accountingapplication.R;

public class AddExpensesFragment extends Fragment implements View.OnClickListener {
    private ImageView currentImage;
    private int currentIndex;
    private TextView type;
    private ImageView traffic, eat, clothes, house, shopping, repayment, rent, phonebill, electricbill, other;
    private int image[];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.expenses, container, false);
        initView(view);
        initData();
        return view;
    }
    private void initView(View view){
        type = getActivity().findViewById(R.id.type);

        traffic = view.findViewById(R.id.traffic);
        eat = view.findViewById(R.id.eat);
        clothes = view.findViewById(R.id.clothes);
        house= view.findViewById(R.id.house);
        shopping = view.findViewById(R.id.shopping);
        repayment = view.findViewById(R.id.repayment);
        rent = view.findViewById(R.id.rent);
        phonebill = view.findViewById(R.id.phonebill);
        electricbill = view.findViewById(R.id.electricbill);
        other = view.findViewById(R.id.other);

        traffic.setOnClickListener(this);
        eat.setOnClickListener(this);
        clothes.setOnClickListener(this);
        house.setOnClickListener(this);
        shopping.setOnClickListener(this);
        repayment.setOnClickListener(this);
        rent.setOnClickListener(this);
        phonebill.setOnClickListener(this);
        electricbill.setOnClickListener(this);
        other.setOnClickListener(this);
    }

    private void initData(){
        currentIndex = 0;
        currentImage = traffic;
        image = new int[]{
                R.drawable.traffic,
                R.drawable.eat,
                R.drawable.clothes,
                R.drawable.house,
                R.drawable.shopping,
                R.drawable.repayment,
                R.drawable.rent,
                R.drawable.phonebill,
                R.drawable.electricbill,
                R.drawable.other
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.traffic:
                traffic.setImageResource(R.drawable.traffic_actived);
                currentImage.setImageResource(image[currentIndex]);
                type.setText("交通");
                currentIndex = 0;
                currentImage = traffic;
                break;
            case R.id.eat:
                eat.setImageResource(R.drawable.eat_actived);
                currentImage.setImageResource(image[currentIndex]);
                type.setText("餐饮");
                currentIndex = 1;
                currentImage = eat;
                break;
            case R.id.clothes:
                clothes.setImageResource(R.drawable.clothes_actived);
                currentImage.setImageResource(image[currentIndex]);
                type.setText("衣服");
                currentIndex = 2;
                currentImage = clothes;
                break;
            case R.id.house:
                house.setImageResource(R.drawable.house_actived);
                currentImage.setImageResource(image[currentIndex]);
                type.setText("房子");
                currentIndex = 3;
                currentImage = house;
                break;
            case R.id.shopping:
                shopping.setImageResource(R.drawable.shopping_actived);
                currentImage.setImageResource(image[currentIndex]);
                type.setText("购物");
                currentIndex = 4;
                currentImage = shopping;
                break;
            case R.id.repayment:
                repayment.setImageResource(R.drawable.repayment_actived);
                currentImage.setImageResource(image[currentIndex]);
                type.setText("还款");
                currentIndex = 5;
                currentImage = repayment;
                break;
            case R.id.rent:
                rent.setImageResource(R.drawable.rent_actived);
                currentImage.setImageResource(image[currentIndex]);
                type.setText("借钱");
                currentIndex = 6;
                currentImage = rent;
                break;
            case R.id.phonebill:
                phonebill.setImageResource(R.drawable.phonebill_actived);
                currentImage.setImageResource(image[currentIndex]);
                type.setText("话费");
                currentIndex = 7;
                currentImage = phonebill;
                break;
            case R.id.electricbill:
                electricbill.setImageResource(R.drawable.electricbill_actived);
                currentImage.setImageResource(image[currentIndex]);
                type.setText("电费");
                currentIndex = 8;
                currentImage = electricbill;
                break;
            case R.id.other:
                other.setImageResource(R.drawable.other_actived);
                currentImage.setImageResource(image[currentIndex]);
                type.setText("other_actived");
                currentIndex = 9;
                currentImage = other;
                break;
        }
    }
}
