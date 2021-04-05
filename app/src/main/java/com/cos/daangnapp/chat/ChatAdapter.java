package com.cos.daangnapp.chat;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.daangnapp.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private static final String TAG = "UserAdapter";

    // 4번 컬렉션 생성
    private final List<ChatData> chats;
    private String nickname;

    public ChatAdapter(List<ChatData> chats, String nickname) {
        Log.d(TAG, "ChatAdapter: 챗어뎁터" + chats + nickname);
        this.chats = chats;
        this.nickname = nickname;
    }

    // 5번 addItem, removeItem
    public void addItem(ChatData chat){
        Log.d(TAG, "addItem: 에드아이테" + chat);
        chats.add(chat);
        notifyItemInserted(chats.size()-1);
        //notifyDataSetChanged();
    }

    public void removeItem(int position){
        chats.remove(position);
    }

    // 7번 getView랑 똑같음
    // 차이점이 있다면 listView는 화면에 3개가 필요 최초 로딩시에 3개를 그려야하니까 getView가 3번 호출됨
    // 그 다음에 스크롤을 해서 2개가 추가되야 될때, 다시 getView를 호출함.
    // 하지만 recyclerView는 스크롤을 해서 2개가 추가되어야 할 때 onBindViewHolder를 호출함
    // onCreateViewHolder는 해당 activity 실행시에만 호출 됨 / 최초 로딩시에만 호출되고 더이상 호출안됨
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // 메인 엑티비티에 연결할 객체를 만듬
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE); // 고정이다.
        View view = inflater.inflate(R.layout.chat_item, parent, false); // 객체만 만들어져있음 뿌려져있지않음

        return new MyViewHolder(view); // view가 리스트뷰에 하나 그려짐
    }

    @Override // 최초 로딩끝나고 그 뒤부터는 얘가 호출됨, 데이터 추가
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");
        holder.setItem(chats.get(position), nickname);

    }

    // 6번 컬렉션 크기 알려주기 (화면에 몇개 그려야할지를 알아야 하기 때문)
    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: 체팅 사이즈" + chats.size());
        return chats.size();
    }

    // 1번 ViewHolder 만들기
    // ViewHolder란 하나의 View를 가지고 있는 Holder이다.
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // 2번 user_item이 가지고 있는 위젯들을 선언
        private TextView tvmsg, tvname;
        private String nickname;
        private ImageView ivperson;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvmsg = itemView.findViewById(R.id.tv_msg);
            tvname = itemView.findViewById(R.id.tv_name);
            ivperson = itemView.findViewById(R.id.iv_person);
        }

        public void setItem(ChatData chat, String nickname){
            Log.d(TAG, "setItem: 셋아이템" + chat.getNickname());
            Log.d(TAG, "setItem: 셋아티템 닉네임" + nickname);
            tvmsg.setText(chat.getMsg());
            tvname.setText(chat.getNickname());

            if (chat.getNickname().equals(nickname)){
                ivperson.setVisibility(View.INVISIBLE);
                tvmsg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                tvname.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            }else{
                tvmsg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                tvname.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            }
        }
    }

}