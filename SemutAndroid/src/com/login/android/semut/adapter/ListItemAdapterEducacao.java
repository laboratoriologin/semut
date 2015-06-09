/**
 * 
 */
package com.login.android.semut.lauro.adapter;

import java.util.List;

import com.login.android.semut.lauro.R;
import com.login.android.semut.lauro.R.color;
import com.login.android.semut.lauro.R.sucom;
import com.login.android.semut.lauro.SucomActivity;
import com.login.android.semut.lauro.model.CategoriaOcorrencia;
import com.login.android.semut.lauro.model.Educacao;
import com.login.android.semut.lauro.model.Grupo;
import com.login.android.semut.lauro.model.Noticia;
import com.login.android.semut.lauro.util.Constantes;
import com.login.android.semut.lauro.util.DrawableManager;
import com.login.android.semut.lauro.util.LoadImage;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author Ricardo
 * 
 */
public class ListItemAdapterEducacao extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<Noticia> lstEducacao;
	private Grupo grupo;
	private Context context;

	public ListItemAdapterEducacao(Context _context, List<Noticia> lstEducacao) {

		this.lstEducacao = lstEducacao;
		mInflater = LayoutInflater.from(_context);
		context = _context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lstEducacao.size();
	}

	@Override
	public Object getItem(int position) {
		return lstEducacao.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Noticia item = lstEducacao.get(position);
		
		convertView = mInflater.inflate(R.layout.item_list_educacao, null);


		((TextView) convertView.findViewById(R.item_list_educacao.descricao)).setText(item.getTitulo());
		((TextView) convertView.findViewById(R.item_list_educacao.data_publicacao)).setText(DateFormat.format("dd/MM/yyyy kk:mm", item.getData_publicacao()));

		((TextView) convertView.findViewById(R.item_list_educacao.descricao)).setTextColor(color.transalvador);
		((TextView) convertView.findViewById(R.item_list_educacao.data_publicacao)).setBackgroundResource(R.color.transalvador);
		
		ImageView image = (ImageView) convertView.findViewById(R.item_list_educacao.imgNoticia);
		
		if (!item.getImagem().equals("")) {
			Drawable img = DrawableManager.getDrawableManager().getDrawable(Constantes.URL_IMG + item.getImagem());

			if (img == null) {

				new LoadImage(image, mInflater.getContext()).execute(Constantes.URL_IMG + item.getImagem());

			} else {

				image.setImageDrawable(img);

			}
		}
		else{
			image.setVisibility(View.GONE);
		}

		return convertView;
	}

	private View getSimpleView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Noticia item = lstEducacao.get(position);

		convertView = mInflater.inflate(R.layout.item_list_educacao, null);

		// ((TextView) convertView.findViewById(R.item_list_educacao.categoria))
		// .setText(item.getTitulo());

		((TextView) convertView.findViewById(R.item_list_educacao.descricao)).setText(item.getDescricao());
		((TextView) convertView.findViewById(R.item_list_educacao.data_publicacao)).setText(DateFormat.format("dd/MM/yyyy kk:mm", item.getData_publicacao()));

		// ((TextView) convertView.findViewById(R.item_list_educacao.titulo))
		// .setText(item.getTitulo());
		return convertView;
	}

	class StarterAnim implements Runnable {
		private AnimationDrawable frameAnimation;

		public StarterAnim(AnimationDrawable ad) {
			frameAnimation = ad;
		}

		public void run() {
			frameAnimation.start();
		}

	}

	public List<Noticia> getLstEducacao() {
		return lstEducacao;
	}

	public void setLstEducacao(List<Noticia> lstEducacao) {
		this.lstEducacao = lstEducacao;
	}

}
