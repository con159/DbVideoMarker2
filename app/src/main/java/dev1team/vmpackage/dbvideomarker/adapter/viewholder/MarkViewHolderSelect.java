package dev1team.vmpackage.dbvideomarker.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import dev1team.vmpackage.dbvideomarker.R;
import dev1team.vmpackage.dbvideomarker.adapter.util.MyItemView;

public class MarkViewHolderSelect extends MyItemView {
    public View _view;
    public TextView _mMemo;
    public TextView _mStart;
    public ImageView _mThumb;

    public MarkViewHolderSelect(@NonNull View view) {
        super(view);
        this._view = view;
        _mMemo = view.findViewById(R.id._mMemo);
        _mStart = view.findViewById(R.id._mstart);
        _mThumb = view.findViewById(R.id._mthumb);
    }
}