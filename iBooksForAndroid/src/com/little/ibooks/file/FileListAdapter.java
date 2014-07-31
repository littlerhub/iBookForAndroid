/*
 * Copyright (c) 2010-2011, The MiCode Open Source Community (www.micode.net)
 *
 * This file is part of FileExplorer.
 *
 * FileExplorer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FileExplorer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SwiFTP.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.little.ibooks.file;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import com.little.ibooks.R;

public class FileListAdapter extends ArrayAdapter<FileInfo> {
    private static final String LOG_TAG = "FileListAdapter";

    private LayoutInflater mInflater;

    private FileViewInteractionHub mFileViewInteractionHub;

    private FileIconHelper mFileIcon;

    public FileListAdapter(Context context, int resource, List<FileInfo> objects,
            FileViewInteractionHub f, FileIconHelper fileIcon) {
        super(context, resource, objects);
        mInflater = LayoutInflater.from(context);
        mFileViewInteractionHub = f;
        mFileIcon = fileIcon;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView != null) {
            view = convertView;
        } else {
            view = mInflater.inflate(R.layout.file_browse_item, parent, false);
        }

        FileListItem listItem = (FileListItem) view;
        FileInfo lFileInfo = mFileViewInteractionHub.getItem(position);
        listItem.bind(lFileInfo, mFileViewInteractionHub, mFileIcon);

        return view;
    }
}