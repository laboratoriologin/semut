package com.login.android.semut.adapter;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.login.android.semut.DefaultActivity;
import com.login.android.semut.R;
import com.login.android.semut.model.GridItem;

public class GridItensSucomAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<GridItem> listIitens;
	private DefaultActivity context;

	public GridItensSucomAdapter(DefaultActivity _context, List<GridItem> _listItens) {
		this.listIitens = _listItens;
		this.mInflater = LayoutInflater.from(_context);
		this.context = _context;
	}

	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		GridItem item = listIitens.get(position);
		convertView = mInflater.inflate(R.layout.item_list_sucom, null);
		
		ImageView imgViewItem = (ImageView) convertView.findViewById(R.sucom.imagemItem);
		TextView txtItem = (TextView) convertView.findViewById(R.sucom.textoItem);
		
		imgViewItem.setBackgroundResource(item.getImagem());
		txtItem.setText(item.getTextoItem());

		return convertView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listIitens.size();
	}

	@Override
	public Object getItem(int position) {
		return listIitens.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
}
