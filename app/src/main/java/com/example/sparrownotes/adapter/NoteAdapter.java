package com.example.sparrownotes.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sparrownotes.R;
import com.example.sparrownotes.bean.NoteBean;
import com.example.sparrownotes.dao.NoteDao;
import com.example.sparrownotes.util.Constant;
import com.example.sparrownotes.util.DialogUtil;
import com.example.sparrownotes.util.SharedPreferenceUtil;

import java.lang.reflect.Field;
import java.util.List;

/**
 * RecyclerView适配器
 * @author Horsen
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private List<NoteBean> notesList;
    private Context context;
    private NoteDao noteDao;
    private static final String TAG = "NoteAdapter";
    SharedPreferenceUtil spu;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View noteView;
        View itemLayout;
        ImageView itemCateImage;
        ImageView itemMenu;
        TextView itemTitle;
        TextView itemId;
        TextView itemContent;
        TextView itemTime;
        TextView itemCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteView = itemView;
            itemId = itemView.findViewById(R.id.item_id);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemContent = itemView.findViewById(R.id.item_content);
            itemCategory = itemView.findViewById(R.id.item_category);
            itemTime = itemView.findViewById(R.id.item_time);
            itemMenu = itemView.findViewById(R.id.item_menu);
            itemLayout = itemView.findViewById(R.id.item_layout);
            itemCateImage = itemView.findViewById(R.id.item_cate_img);
        }
    }

    public NoteAdapter(List<NoteBean> notesList, Context context) {
        this.notesList = notesList;
        this.context = context;
        noteDao = new NoteDao(context);
        spu = SharedPreferenceUtil.getInstance(context);
    }

    @SuppressLint("RestrictedApi")
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.itemMenu.setOnClickListener(v -> {
            // 根据页面显示不同的菜单
            if ("all".equals(Constant.PAGE_STATE)) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v, Gravity.RIGHT);
                popupMenu.getMenuInflater().inflate(R.menu.item_menu_all, popupMenu.getMenu());
                // 弹出式菜单的菜单项点击事件
                popupMenu.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.delete_all:
                            // 删除
                            removeData(holder.getAdapterPosition());
                            Toast.makeText(v.getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.top_all:
                            //置顶
                            setTop(holder.getAdapterPosition());
                            Toast.makeText(v.getContext(), "已置顶", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.edit_all:
                            //编辑
                            DialogUtil.editNoteDialog(context, notesList.get(holder.getAdapterPosition()).getNoteID());
                            break;
                        default:
                            break;
                    }
                    return false;
                });
                setIconsVisible(popupMenu, true);
                popupMenu.show();
            } else {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v, Gravity.RIGHT);
                popupMenu.getMenuInflater().inflate(R.menu.item_menu_top, popupMenu.getMenu());
                // 弹出式菜单的菜单项点击事件
                popupMenu.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.delete_top:
                            // 删除
                            removeData(holder.getAdapterPosition());
                            Toast.makeText(v.getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.top_top:
                            // 取消置顶
                            notSetTop(holder.getAdapterPosition());
                            Toast.makeText(v.getContext(), "取消置顶", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.edit_top:
                            // 编辑
                            DialogUtil.editNoteDialog(context, notesList.get(holder.getAdapterPosition()).getNoteID());
                            break;
                        default:
                            break;
                    }
                    return false;
                });
                // 使弹出菜单的图标可见
                setIconsVisible(popupMenu, true);
                popupMenu.show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NoteBean noteItem = notesList.get(position);
        holder.itemId.setText(String.valueOf(noteItem.getNoteID()));
        holder.itemTitle.setText(noteItem.getTitle());
        holder.itemContent.setText(noteItem.getContent());
        holder.itemCategory.setText(noteItem.getCategory());
        holder.itemTime.setText(noteItem.getTime());
        int imgResource = noteItem.getImgResource();
        /*
         * 通过Glide获取图片资源
         * 这里本来做的是获取tomcat中的图片url, 考虑到每次运行都要启动tomcat
         * 就改成了本地的方式
         * 等到交作业的时候再弄一个网络的版本
         */
        Glide.with(context).load(imgResource).into(holder.itemCateImage);
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    /**
     * 删除一条数据
     *
     * @param position
     */
    public void removeData(int position) {
        // 从数据库删除
        noteDao.delete(notesList.get(position).getNoteID());
        // 从List中删除
        notesList.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 修改一条数据(置顶)
     *
     * @param position
     */
    public void setTop(int position) {
        NoteBean noteItem = noteDao.queryById(notesList.get(position).getNoteID());
        noteItem.setTop("1");
        noteDao.update(noteItem);
        notifyDataSetChanged();
    }

    /**
     * 修改一条数据(取消置顶)
     *
     * @param position
     */
    public void notSetTop(int position) {
        NoteBean noteItem = noteDao.queryById(notesList.get(position).getNoteID());
        noteItem.setTop("0");
        noteDao.update(noteItem);
        notesList.remove(position);
        notifyDataSetChanged();
    }

    /**
     * menu显示图标
     *
     * @param popupMenu
     * @param flag
     */
    @SuppressLint("RestrictedApi")
    private void setIconsVisible(PopupMenu popupMenu, boolean flag) {
        //判断menu是否为空
        if (popupMenu != null) {
            try {
                Field field = popupMenu.getClass().getDeclaredField("mPopup");
                field.setAccessible(true);
                MenuPopupHelper mHelper = (MenuPopupHelper) field.get(popupMenu);
                mHelper.setForceShowIcon(flag);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }
}
