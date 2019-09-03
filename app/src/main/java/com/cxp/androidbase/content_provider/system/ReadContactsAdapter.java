package com.cxp.androidbase.content_provider.system;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cxp.androidbase.R;

import java.util.List;
import java.util.Map;

/**
 * 文 件 名: ReadContactsAdapter
 * 创 建 人: CXP
 * 创建日期: 2019-08-30 14:25
 * 描    述: 适配器
 * 修 改 人:
 * 修改时间：
 * 修改备注：
 */
public class ReadContactsAdapter extends RecyclerView.Adapter<ReadContactsAdapter.ReadContactsViewHolder> {

    private Context mContext;
    private List<Map<String, String>> mDatas;

    public ReadContactsAdapter(Context context, List<Map<String, String>> datas) {
        mContext = context;
        mDatas = datas;
    }

    @NonNull
    @Override
    public ReadContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReadContactsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReadContactsViewHolder holder, int position) {
        holder.mTitle.setText(mDatas.get(position).get("title"));
        holder.mContent.setText(mDatas.get(position).get("content"));
    }


    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    static class ReadContactsViewHolder extends RecyclerView.ViewHolder {

        TextView mTitle;
        TextView mContent;

        public ReadContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.item_title);
            mContent = itemView.findViewById(R.id.item_content);
        }
    }
}
