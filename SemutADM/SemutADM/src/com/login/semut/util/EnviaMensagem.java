package com.login.audit.laurofreitas.util;

import java.io.IOException;
import java.util.List;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.login.audit.laurofreitas.model.Token;

/**
 * Classe resposável por enviar mensagens para um dispositivo 
 * através do GCM.
 * 
 * @author lucasfreitas
 *
 */
public class EnviaMensagem {
	
	// Variável com o ID do dispositivo registrado no GCM
	//private static final String ID_DISPOSITIVO_GCM = "APA91bGjWby7EHJDyiO1tq-7HCoCN7M53mk0TVr6T-ltQGur-dTfE-gvQGfkGYhjamEMCKbcpBua6iOIggofOfPozi3fVFM5mkdCRcT3321Rh-D5ybca5a90F1R7gM-HXSWMu9BUOFhkygElGJyPzlXNPigyX4onvSg96f19Mcsyll4zjIvulpo";
	// Variável com a chave obtida em API ACCESS no Google APIs
	private static final String API_KEY = "AIzaSyAO8OggWOv_eCygTyXMSfLcBN4VsTtloqc";
	
	public static void enviar(List<String> token, String msg) {
		
		/**
		 * ID do Sender (Enviador)
		 */
		Sender sender = new Sender(API_KEY);
		
		/**
		 * Mensagem a ser enviada
		 */
		Message message = new Message.Builder()
		   .collapseKey("1")
		   .timeToLive(1000)
		   .delayWhileIdle(true)
		   .addData("mensagem", // identificador da mensagem
		     msg)
		   .build();
		
		MulticastResult result = null;
		
		/**
		 * Envia a mensagem para o dispositivo
		 */
		try {
			result = sender.send(message,token, 1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Imprime o resultado do envio na saída padrão
		if (result != null)
			System.out.println(result.toString());
		
	}

}
