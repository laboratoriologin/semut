package com.login.android.semut.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.login.android.semut.AlertasActivity;
import com.login.android.semut.HomeActivity;
import com.login.android.semut.MostraMensagemActivity;
import com.login.android.semut.OcorrenciaActivity;
import com.login.android.semut.R;

/**
 * Classe utilitária para mostrar notificações.
 * 
 * @author lucasfreitas
 * 
 */
public class Notificacao {

	private static int notificationCount;

	/**
	 * Método responsável por disparar uma notificação na barra de status.
	 * 
	 * @param titulo
	 * @param mensagem
	 * @param context
	 */
	public static void mostraNotificacao(String titulo, String mensagem, Context context) {

		notificationCount = context.getSharedPreferences(Constantes.SHARED_PREFS, 0).getInt("count", 1);
		SharedPreferences.Editor editor = context.getSharedPreferences(Constantes.SHARED_PREFS, 0).edit();

		// Tempo em que a Notificação será disparada
		long tempoDefinido = System.currentTimeMillis();

		// Objeto Notification
		Notification notification = new Notification(R.drawable.ic_launcher, titulo, tempoDefinido);

		if (notificationCount > 1)
			notification.number = notificationCount;
		notificationCount++;
		
		editor.putInt("count", notificationCount);
		editor.commit();
		
		

		// Intent que será disparada quando o usuário clicar sobre a Notificação
		Intent intent = new Intent(context, HomeActivity.class);
		intent.putExtra("mensagem_recebida", mensagem);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		// Configurando os dados da Notificação
		notification.setLatestEventInfo(context, titulo, mensagem, pendingIntent);

		// Oculta a notificação após o usuário clicar sobre ela
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		// Define alertas de acordo com o padrão definido no dispositivo
		notification.defaults = Notification.DEFAULT_ALL;

		// Agenda a Notificação
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(0, notification);
	}

}
