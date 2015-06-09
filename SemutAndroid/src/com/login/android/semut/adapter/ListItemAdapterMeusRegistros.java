/**
 * 
 */
package com.login.android.semut.lauro.adapter;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.login.android.semut.lauro.MeusRegistrosActivity;
import com.login.android.semut.lauro.R;
import com.login.android.semut.lauro.model.Noticia;
import com.login.android.semut.lauro.model.Ocorrencia;
import com.login.android.semut.lauro.util.Constantes;
import com.login.android.semut.lauro.util.DrawableManager;
import com.login.android.semut.lauro.util.LoadImage;

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

		setResultado(new ArrayList<Object>());

		getResultado().addAll(lstNoticia);

		getResultado().addAll(lstOcorrencia);

		// BeanComparator comparator = new BeanComparator("texto");
		//
		// Collections.sort(resultado, comparator);

		mInflater = LayoutInflater.from(context);

		this.context = context;

		act = (MeusRegistrosActivity) context;
	}

	@Override
	public int getCount() {
		return getResultado().size();
	}

	@Override
	public Object getItem(int position) {
		return getResultado().get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		convertView = mInflater.inflate(R.layout.item_list_meus_registros, null);

		if (act.getGrupo().getId() == 2) {
			convertView.findViewById(R.id.item_list_noticia_borda).setBackgroundResource(R.drawable.borda_item_list_sucom);
			convertView.findViewById(R.id.item_list_noticia_data_publicacao).setBackgroundColor(act.getResources().getColor(R.color.sucom));
		}

		Object item = getResultado().get(position);

		convertView.findViewById(R.id.item_list_noticia_categoria).setVisibility(View.VISIBLE);

		convertView.findViewById(R.id.item_list_noticia_imgCabecalho).setVisibility(View.VISIBLE);

		if (item instanceof Ocorrencia) {

			((RelativeLayout) convertView.findViewById(R.id.item_list_noticia_relative_layout_np)).setVisibility(RelativeLayout.VISIBLE);

			if ("-1".equals(((Ocorrencia) item).getNumeroProtocolo())) {
				((TextView) convertView.findViewById(R.id.item_list_noticia_text_view_np)).setText("NP:");
				((ImageView) convertView.findViewById(R.id.item_list_noticia_img_upload)).setImageDrawable(context.getResources().getDrawable(R.drawable.icone_enviando));	
			} else {
				((ImageView) convertView.findViewById(R.id.item_list_noticia_img_upload)).setImageDrawable(context.getResources().getDrawable(R.drawable.icone_check_enviado));
				((TextView) convertView.findViewById(R.id.item_list_noticia_text_view_np)).setText("NP:" + ((Ocorrencia) item).getNumeroProtocolo());
			}

			((TextView) convertView.findViewById(R.id.item_list_noticia_categoria)).setText("Ocorrência");

			((ImageView) convertView.findViewById(R.id.item_list_noticia_imgCabecalho)).setImageDrawable(context.getResources().getDrawable(R.drawable.icon_bttrans_ocor));

			((TextView) convertView.findViewById(R.id.item_list_noticia_data_publicacao)).setText(DateFormat.format("dd/MM/yyyy kk:mm", ((Ocorrencia) item).getData()));

			((TextView) convertView.findViewById(R.id.item_list_noticia_descricao)).setText(((Ocorrencia) item).getDescricao());

			String path = Environment.getExternalStorageDirectory().toString();

			File file = new File(path, "oc" + DateFormat.format("ddMMyyyhhmmss", ((Ocorrencia) item).getData()) + ".jpg");

			ImageView image = (ImageView) convertView.findViewById(R.id.item_list_noticia_imgNoticia);

			if (file.exists()) {

				Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

				image.setImageBitmap(bitmap);

			} else {

				image.setVisibility(View.GONE);
			}

		} else if (item instanceof Noticia) {
			
			((RelativeLayout) convertView.findViewById(R.id.item_list_noticia_relative_layout_np)).setVisibility(RelativeLayout.GONE);

			if (((Noticia) item).getTipoNoticia() == 2 || ((Noticia) item).getTipoNoticia() == 4) {
				((ImageView) convertView.findViewById(R.id.item_list_noticia_imgCabecalho)).setImageDrawable(context.getResources().getDrawable(R.drawable.icon_bttrans_noticias));

				if (((Noticia) item).getTipoNoticia() == 2)
					((TextView) convertView.findViewById(R.id.item_list_noticia_categoria)).setText("Notícia");
				else if (((Noticia) item).getTipoNoticia() == 4) {
					((ImageView) convertView.findViewById(R.id.item_list_noticia_imgCabecalho)).setImageDrawable(context.getResources().getDrawable(R.drawable.icon_btsucom_qua_aviso));
					((TextView) convertView.findViewById(R.id.item_list_noticia_categoria)).setText("Aviso");
				}

			} else if (((Noticia) item).getTipoNoticia() == 5) {
				((ImageView) convertView.findViewById(R.id.item_list_noticia_imgCabecalho)).setImageDrawable(context.getResources().getDrawable(R.drawable.icon_bttrans_edu_no_tran));

				((TextView) convertView.findViewById(R.id.item_list_noticia_categoria)).setText("Educação");
			}

			if (((Noticia) item).getTipoNoticia() == 4) {
				convertView.findViewById(R.id.item_list_noticia_borda).setBackgroundResource(R.drawable.borda_item_list_sucom);
				convertView.findViewById(R.id.item_list_noticia_data_publicacao).setBackgroundColor(act.getResources().getColor(R.color.sucom));
			}

			((TextView) convertView.findViewById(R.id.item_list_noticia_data_publicacao)).setText(DateFormat.format("dd/MM/yyyy kk:mm", ((Noticia) item).getData_publicacao()));

			((TextView) convertView.findViewById(R.id.item_list_noticia_descricao)).setText(((Noticia) item).getTitulo());

			ImageView image = (ImageView) convertView.findViewById(R.id.item_list_noticia_imgNoticia);

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

		return convertView;
	}

	private View getSimpleView(int position, View convertView, ViewGroup parent) {

		Object item = getResultado().get(position);

		Ocorrencia ocorrencia = null;
		Noticia noticia = null;

		if (item instanceof Ocorrencia) {

			((TextView) convertView.findViewById(R.id.item_list_noticia_categoria)).setText("Ocorrência");
			((ImageView) convertView.findViewById(R.id.item_list_noticia_imgCabecalho)).setImageDrawable(context.getResources().getDrawable(R.drawable.icon_bttrans_noticias));
			((TextView) convertView.findViewById(R.id.item_list_noticia_descricao)).setText(((Ocorrencia) item).getDescricao());

		} else if (item instanceof Noticia) {

			((TextView) convertView.findViewById(R.id.item_list_noticia_categoria)).setText("Notícia");
			((ImageView) convertView.findViewById(R.id.item_list_noticia_imgCabecalho)).setImageDrawable(context.getResources().getDrawable(R.drawable.icon_bttrans_meus_regis));
			((TextView) convertView.findViewById(R.id.item_list_noticia_descricao)).setText(((Noticia) item).getDescricao());

		}

		convertView = mInflater.inflate(R.layout.item_list_meus_registros, null);

		return convertView;
	}

	public List<Object> getResultado() {
		return resultado;
	}

	public void setResultado(List<Object> resultado) {
		this.resultado = resultado;
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
