package com.example.miis200;

import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Eyeimage_MyAdapter extends RecyclerView.Adapter<Eyeimage_MyAdapter.ViewHolder> {
    //动态数组
    public List<Eyeimage_ItemRecycler> itemRecyclers;

    private String ImagePath;
    //构造
    public Eyeimage_MyAdapter(List<Eyeimage_ItemRecycler> itemRecyclers) {
        this.itemRecyclers = itemRecyclers;
    }

    // 刪除項目
    public void removeItem(int position){
        itemRecyclers.remove(0);
        notifyItemRemoved(position);
        Log.i("PPPPPPPOSSSITION",String.valueOf(position));
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //绑定行布局
        View view = View.inflate(parent.getContext(),R.layout.eyeimage_item_recycler,null);
        //实例化ViewHolder
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    //设置数据
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //获取当前实体类对象
        Eyeimage_ItemRecycler irs = itemRecyclers.get(position);
        holder.img.setImageBitmap(irs.getmImg());
        System.out.println("iiiiiirrrrrsss : " + irs.getmImg());
    }

    //数量
    @Override
    public int getItemCount() {
        return itemRecyclers.size();
    }

    //内部类
    class ViewHolder extends RecyclerView.ViewHolder{
        //行布局中的控件
        ImageView img;
        TextView text;
        ImageButton delete;

        public ViewHolder(View itemView) {
            super(itemView);
            //绑定控件
            img = (ImageView) itemView.findViewById(R.id.item_image);
            text = (TextView) itemView.findViewById(R.id.item_text);
            delete = (ImageButton) itemView.findViewById( R.id.item_delete );

            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),"click " +getAdapterPosition(),Toast.LENGTH_SHORT).show();
                    ImagePath = getData(getAdapterPosition()).getmName();
                    Log.v("ddddddddddddddddd", ImagePath);

                    if (ImagePath.indexOf("/") != 0){
                        ImagePath = "/storage/emulated/0/1.MiiS/2.IMAGE/" + ImagePath;
                    }

                    Intent Image_path = new Intent(v.getContext(), Addexamination_examination_viewphoto.class);
                    Image_path.putExtra("Image_path",ImagePath);
                    v.getContext().startActivity(Image_path);
                }
            } );


        }
    }


    public void addData(int position, Eyeimage_ItemRecycler recyclerItem) {
        itemRecyclers.add(position, recyclerItem);
    }


    // 刪除數據
    public void removeData(int position) {
        removeItem(0);
    }


    public Eyeimage_ItemRecycler getData(int position) {
        return itemRecyclers.get(position);
    }

    public Eyeimage_ItemRecycler getRemoveData(int position) {
        return itemRecyclers.remove(position);
    }

}
