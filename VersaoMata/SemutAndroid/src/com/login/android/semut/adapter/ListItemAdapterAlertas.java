/**
 * 
 */
package com.login.android.semut.adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
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

import com.login.android.semut.R;
import com.login.android.semut.R.color;
import com.login.android.semut.SucomActivity;
import com.login.android.semut.model.CategoriaOcorrencia;
import com.login.android.semut.model.Grupo;
import com.login.android.semut.model.Noticia;
import com.login.android.semut.model.Ocorrencia;
import com.login.android.semut.util.Constantes;
import com.login.android.semut.util.DrawableManager;
import com.login.android.semut.util.LoadImage;

/**
 * @author Ricardo
 * 
 */
public class ListItemAdapterAlertas extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<Ocorrencia> lstOcorrencia;
	private Context context;

	public ListItemAdapterAlertas(Context _context, List<Ocorrencia> lstOcorrencia) {
		this.lstOcorrencia = lstOcorrencia;
		mInflater = LayoutInflater.from(_context);
		context = _context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lstOcorrencia.size();
	}

	@Override
	public Object getItem(int position) {
		return lstOcorrencia.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Ocorrencia item = lstOcorrencia.get(position);

		convertView = mInflater.inflate(R.layout.item_list_alertas, null);

		/*for (CategoriaOcorrencia categoriaOcorrencia : listaCategoriasOcorrencias) {
			if (item.getCategoriaId().equals(categoriaOcorrencia.getId()) ){*/
				((TextView) convertView.findViewById(R.item_list_alerta.categoria)).setText(item.getCategoria().getDescricao());
		/*	}
		}*/
		
		((TextView) convertView.findViewById(R.item_list_alerta.data_publicacao)).setText(DateFormat.format("dd/MM/yyyy kk:mm", ((Ocorrencia) item).getData()));

		ImageView image = (ImageView) convertView.findViewById(R.item_list_alerta.imgAlerta);

		if (!item.getCaminhoImagem().equals("")) {
			Drawable img = DrawableManager.getDrawableManager().getDrawable(Constantes.URL_IMG + item.getCaminhoImagem());

			if (img == null) {

				new LoadImage(image, mInflater.getContext()).execute(Constantes.URL_IMG + item.getCaminhoImagem());

			} else {

				image.setImageDrawable(img);

			}
		} else {
			image.setVisibility(View.GONE);
		}

		((TextView) convertView.findViewById(R.item_list_alerta.txtAlerta)).setText(item.getDescricao());

		return convertView;
	}

	private View getSimpleView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

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

	public List<Ocorrencia> getLstOcorrencia() {
		return lstOcorrencia;
	}

	public void setLstOcorrencia(List<Ocorrencia> lstOcorrencia) {
		this.lstOcorrencia = lstOcorrencia;
	}

}
