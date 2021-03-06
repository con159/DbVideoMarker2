package dev1team.vmpackage.dbvideomarker.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import dev1team.vmpackage.dbvideomarker.R;
import dev1team.vmpackage.dbvideomarker.activity.PlayListEditViewModel;
import dev1team.vmpackage.dbvideomarker.adapter.util.MyItemView;
import dev1team.vmpackage.dbvideomarker.adapter.util.ViewCase;
import dev1team.vmpackage.dbvideomarker.adapter.viewholder.PlaylistViewHolderBottom;
import dev1team.vmpackage.dbvideomarker.adapter.viewholder.PlaylistViewHolderNormal;
import dev1team.vmpackage.dbvideomarker.adapter.viewholder.PlaylistViewHolderSelect;
import dev1team.vmpackage.dbvideomarker.database.entitiy.PlayList;
import dev1team.vmpackage.dbvideomarker.listener.OnItemSelectedListener;
import dev1team.vmpackage.dbvideomarker.listener.OnPlaylistClickListener;

import java.util.List;

public class PlayListAdapter extends RecyclerView.Adapter<MyItemView> {

    private SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);
    private SparseBooleanArray mSelectedItemIds = new SparseBooleanArray(0);
    private OnItemSelectedListener onItemSelectedListener;
    private OnPlaylistClickListener onPlaylistClickListener;
    private List<PlayList> playListList;
    private ViewCase sel_type;
    private PlayListEditViewModel playListEditViewModel;

    public PlayListAdapter(Context context, ViewCase sel_type, OnPlaylistClickListener onPlaylistClickListener, OnItemSelectedListener onItemSelectedListener) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        this.sel_type = sel_type;
        this.onItemSelectedListener = onItemSelectedListener;
        this.onPlaylistClickListener = onPlaylistClickListener;
    }


    @Override
    public MyItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (sel_type == ViewCase.NORMAL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist, parent, false);
            return new PlaylistViewHolderNormal(view);
        } else if (sel_type == ViewCase.SELECT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist, parent, false);
            return new PlaylistViewHolderSelect(view);
        } else if (sel_type == ViewCase.BOTTOM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist_bottomsheet, parent, false);
            return new PlaylistViewHolderBottom(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyItemView holder, int position) {
        if (holder instanceof PlaylistViewHolderNormal) {
            PlaylistViewHolderNormal playlistViewHolderNormal = (PlaylistViewHolderNormal) holder;
            if (playListList != null) {
                PlayList current = playListList.get(position);
                playlistViewHolderNormal.pName.setText(current.getpName());
                playlistViewHolderNormal.pvCount.setText(String.valueOf(current.getVcount()));
                playlistViewHolderNormal.pmCount.setText(String.valueOf(current.getMcount()));

                playlistViewHolderNormal.view.setOnClickListener(view -> {
                    int pid = current.getPid();
                    onPlaylistClickListener.clickPlaylist(pid);
                });
                playlistViewHolderNormal.view.setOnLongClickListener(view -> {
                    int pid = current.getPid();
                    onPlaylistClickListener.clickLongPlaylist(view, pid);
                    return false;
                });


            }
        } else if (holder instanceof PlaylistViewHolderSelect) {
            PlaylistViewHolderSelect playlistViewHolderSelect = (PlaylistViewHolderSelect) holder;
            if (playListList != null) {
                PlayList current = playListList.get(position);
                playlistViewHolderSelect._pName.setText(current.getpName());
                playlistViewHolderSelect._pvCount.setText(String.valueOf(current.getVcount()));
                playlistViewHolderSelect._pmCount.setText(String.valueOf(current.getMcount()));

                if (mSelectedItems.get(position, false)) {
                    playlistViewHolderSelect._view.setBackgroundColor(Color.parseColor("#A6A6A6"));
                } else {
                    playlistViewHolderSelect._view.setBackgroundColor(Color.parseColor("#5C5C5C"));
                }

                playlistViewHolderSelect._view.setOnClickListener(view -> {
                    if (mSelectedItems.get(position, false)) {
                        //GRAY
                        mSelectedItems.delete(position);
                        mSelectedItemIds.delete(current.getPid());
                        notifyItemChanged(position);
                    } else {
                        //WHITE
                        mSelectedItems.put(position, true);
                        mSelectedItemIds.put(current.getPid(), true);
                        notifyItemChanged(position);
                    }
                    Log.d("test", "parsed" + mSelectedItemIds.size());

                    onItemSelectedListener.onItemSelected(view, mSelectedItemIds);
                });


            }
        } else if (holder instanceof PlaylistViewHolderBottom) {
            PlaylistViewHolderBottom playlistViewHolderBottom = (PlaylistViewHolderBottom) holder;
            if (playListList != null) {
                PlayList current = playListList.get(position);
                playlistViewHolderBottom.pname.setText(current.getpName());
                playlistViewHolderBottom.pname.setTextColor(Color.parseColor("#FFFFFF"));
                if (mSelectedItems.get(position, false)) {
                    playlistViewHolderBottom.view.setBackgroundColor(Color.parseColor("#737373"));
                } else {
                    playlistViewHolderBottom.view.setBackgroundColor(Color.parseColor("#5C5C5C"));
                }

                playlistViewHolderBottom.view.setOnClickListener(view -> {
                    if (mSelectedItems.get(position, false)) {
                        //GRAY
                        mSelectedItems.delete(position);
                        mSelectedItemIds.delete(current.getPid());
                        notifyItemChanged(position);
                    } else {
                        //WHITE
                        mSelectedItems.put(position, true);
                        mSelectedItemIds.put(current.getPid(), true);
                        notifyItemChanged(position);
                    }
                    Log.d("test", "parsed" + mSelectedItemIds.size());

                    onItemSelectedListener.onItemSelected(view, mSelectedItemIds);
                });
            }
        }

    }

    public void setPlayLists(List<PlayList> playList) {
        playListList = playList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (playListList != null)
            return playListList.size();
        else return 0;
    }
}
