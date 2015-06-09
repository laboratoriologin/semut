/**
 * 
 */
package com.login.android.semut.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.login.android.semut.MeusRegistrosActivity;
import com.login.android.semut.R;
import com.login.android.semut.model.Noticia;
import com.login.android.semut.model.Ocorrencia;
import com.login.android.semut.util.Constantes;
import com.login.android.semut.util.DrawableManager;
import com.login.android.semut.util.LoadImage;

/**
 * @author Ricardo
 * 
 */
public class ListItemAdapterMeusRegistros extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<Object> resultado;
	private Context context;
	private MeusRegistrosActivity act;

	public ListItemAdapterMeusRegistros(Context context, List<Noticia> lstNoticia, List<Ocorrencia> lstOcorrencia) {

		resultado = new ArrayList<Object>();

		resultado.addAll(lstNoticia);

		resultado.addAll(lstOcorrencia);

		// BeanComparator comparator = new BeanComparator("texto");
		//
		// Collections.sort(resultado, comparator);

		mInflater = LayoutInflater.from(context);

		this.context = context;

		act = (MeusRegistrosActivity) context;
	}

	@Override
	public int getCount() {
		return resultado.size();
	}

	@Override
	public Object getItem(int position) {
		return resultado.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		convertView = mInflater.inflate(R.layout.item_list_noticia, null);

		if (act.getGrupo().getId() == 2) {
			convertView.findViewById(R.item_list_noticia.borda).setBackgroundResource(R.drawable.borda_item_list_sucom);
			convertView.findViewById(R.item_list_noticia.data_publicacao).setBackgroundColor(act.getResources().getColor(R.color.sucom));
		}

		Object item = resultado.get(position);

		convertView.findViewById(R.item_list_noticia.categoria).setVisibility(View.VISIBLE);

		convertView.findViewById(R.item_list_noticia.imgCabecalho).setVisibility(View.VISIBLE);

		if (item instanceof Ocorrencia) {

			((TextView) convertView.findViewById(R.item_list_noticia.categoria)).setText("Ocorrência");

			((ImageView) convertView.findViewById(R.item_list_noticia.imgCabecalho)).setImageDrawable(context.getResources().getDrawable(R.drawable.icon_bttrans_ocor));

			((TextView) convertView.findViewById(R.item_list_noticia.data_publicacao)).setText(DateFormat.format("dd/MM/yyyy kk:mm", ((Ocorrencia) item).getData()));

			((TextView) convertView.findViewById(R.item_list_noticia.descricao)).setText(((Ocorrencia) item).getDescricao());

			String path = Environment.getExternalStorageDirectory().toString();

			File file = new File(path, "oc" + DateFormat.format("ddMMyyyhhmmss", ((Ocorrencia) item).getData()) + ".jpg");

			ImageView image = (ImageView) convertView.findViewById(R.item_list_noticia.imgNoticia);

			if (file.exists()) {

				Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

				image.setImageBitmap(bitmap);

			} else {

				image.setVisibility(View.GONE);
			}

		} else if (item instanceof Noticia) {

			if (((Noticia) item).getTipoNoticia() == 2 || ((Noticia) item).getTipoNoticia() == 4) {
				((ImageView) convertView.findViewById(R.item_list_noticia.imgCabecalho)).setImageDrawable(context.getResources().getDrawable(R.drawable.icon_bttrans_noticias));
				
				if (((Noticia) item).getTipoNoticia() == 2)
					((TextView) convertView.findViewById(R.item_list_noticia.categoria)).setText("Notícia");
				else if (((Noticia) item).getTipoNoticia() == 4){
					((ImageView) convertView.findViewById(R.item_list_noticia.imgCabecalho)).setImageDrawable(context.getResources().getDrawable(R.drawable.icon_btsucom_qua_aviso));
					((TextView) convertView.findViewById(R.item_list_noticia.categoria)).setText("Aviso");
				}

			} else if (((Noticia) item).getTipoNoticia() == 5) {
				((ImageView) convertView.findViewById(R.item_list_noticia.imgCabecalho)).setImageDrawable(context.getResources().getDrawable(R.drawable.icon_bttrans_edu_no_tran));

				((TextView) convertView.findViewById(R.item_list_noticia.categoria)).setText("Educação");
			}

			if (((Noticia) item).getTipoNoticia() == 4) {
				convertView.findViewById(R.item_list_noticia.borda).setBackgroundResource(R.drawable.borda_item_list_sucom);
				convertView.findViewById(R.item_list_noticia.data_publicacao).setBackgroundColor(act.getResources().getColor(R.color.sucom));
			}

			((TextView) convertView.findViewById(R.item_list_noticia.data_publicacao)).setText(DateFormat.format("dd/MM/yyyy kk:mm", ((Noticia) item).getData_publicacao()));

			((TextView) convertView.findViewById(R.item_list_noticia.descricao)).setText(((Noticia) item).getTitulo());

			ImageView image = (ImageView) convertView.findViewById(R.item_list_noticia.imgNoticia);

			if (!((Noticia) item).getImagem().equals("")) {
				Drawable img = DrawableManager.getDrawableManager().getDrawable(Constantes.URL_IMG + ((Noticia) item).getImagem());

				if (img == null) {

					new LoadImage(image, mInflater.getContext()).execute(Constantes.URL_IMG + ((Noticia) item).getImagem());

				} else {

					image.setImageDrawable(img);

				}
			} else {
				image.setVisibility(View.GONE);
			}

		}

		// Drawable img = DrawableManager.getDrawableManager().getDrawable(
		// itemOcorrencia.getImagem());
		//
		// convertView = mInflater.inflate(R.layout.item_list_noticia, null);
		//
		// ImageView image = (ImageView) convertView
		// .findViewById(R.item_list_noticia.imgNoticia);
		//
		// if (img == null) {
		//
		// new LoadImage(image, mInflater.getContext()).execute(itemOcorrencia
		// .getImagem());
		//
		// image.setBackgroundResource(R.anim.loading);
		//
		// AnimationDrawable frameAnimation = (AnimationDrawable) image
		// .getBackground();
		//
		// image.post(new StarterAnim(frameAnimation));
		//
		// } else {
		//
		// image.setImageDrawable(img);
		//
		// }

		// ((TextView) convertView.findViewById(R.item_list_noticia.categoria))
		// .setText(item.getTitulo());

		// ((TextView) convertView.findViewById(R.item_list_noticia.titulo))
		// .setText(item.getTitulo());

		return convertView;
	}

	private View getSimpleView(int position, View convertView, ViewGroup parent) {

		Object item = resultado.get(position);

		Ocorrencia ocorrencia = null;
		Noticia noticia = null;

		if (item instanceof Ocorrencia) {

			((TextView) convertView.findViewById(R.item_list_noticia.categoria)).setText("Ocorrência");
			((ImageView) convertView.findViewById(R.item_list_noticia.imgCabecalho)).setImageDrawable(context.getResources().getDrawable(R.drawable.icon_bttrans_noticias));
			((TextView) convertView.findViewById(R.item_list_noticia.descricao)).setText(((Ocorrencia) item).getDescricao());

		} else if (item instanceof Noticia) {

			((TextView) convertView.findViewById(R.item_list_noticia.categoria)).setText("Notícia");
			((ImageView) convertView.findViewById(R.item_list_noticia.imgCabecalho)).setImageDrawable(context.getResources().getDrawable(R.drawable.icon_bttrans_meus_regis));
			((TextView) convertView.findViewById(R.item_list_noticia.descricao)).setText(((Noticia) item).getDescricao());

		}

		convertView = mInflater.inflate(R.layout.item_list_noticia, null);

		// ((TextView)
		// convertView.findViewById(R.item_list_noticia.data_publicacao)).setText(SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT).format(itemOcorrencia.getData()));

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

}
