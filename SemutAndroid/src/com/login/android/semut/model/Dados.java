package com.login.android.semut.lauro.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import org.droidpersistence.util.DroidUtils;

@SuppressWarnings("serial")
public class Dados implements Serializable {

	private ArrayList<Noticia> noticias;
	private ArrayList<Noticia> quadroDeAvisos;
	private ArrayList<Ocorrencia> alertas;
	private ArrayList<Educacao> lstEducacao;

	public Dados() {
		super();

		noticias = new ArrayList<Noticia>();
		quadroDeAvisos = new ArrayList<Noticia>();
		alertas = new ArrayList<Ocorrencia>();
		lstEducacao = new ArrayList<Educacao>();

		criarNoticias();
		criarAvisos();
		criarAlertas();
		criarEducacao();
	}

	public ArrayList<Noticia> criarNoticias() {
		for (int i = 0; i < 10; i++) {
			Noticia noticia = new Noticia();
			noticia.setData_publicacao(DroidUtils
					.convertStringToDate("2013-11-12 10:10:12"));
			noticia.setImagem("");
			noticia.setTitulo("Juracy Magalhães e ACM terão faixas exclusivas de ônibus fiscalizadas a partir da próxima semana");
			noticia.setDescricao("A partir da próxima semana, a Transalvador dará início à fiscalização das faixas exclusivas para ônibus das avenidas ACM e Juracy Magalhães, recentemente recapeadas, agora em fase de sinalização."
					+

					"O princípio utilizado pela engenharia de tráfego da Transalvador é a MULV - Melhor Utilização do Leito Viário - que consiste no balanceamento do espaço da pista, a partir do redimensionamento da largura das faixas somente com pintura, sem realizar obras."
					+

					"De acordo com o superintendente Fabrizzio Muller, a orientação da administração é “priorizar o transporte público”. “A faixa será, inicialmente, fiscalizada por agentes de trânsito até que a licitação de equipamentos eletrônicos seja concluída”, complementou Fabrizzio."
					+

					"Já estão avançados os estudos para a MULV em outras importantes vias da cidade, como a Orla e a Av. Paralela e da Orla, com reativação das faixas exclusivas também nestas vias. Trafegar por faixa exclusiva para ônibus é infração de trânsito, que gera multa de R$ 53,21 e três pontos na carteira de habilitação.");

			noticias.add(noticia);
		}

		return noticias;
	}

	public ArrayList<Noticia> criarAvisos() {
		
		for (int i = 0; i < 10; i++) {
			Noticia noticia = new Noticia();
			noticia.setData_publicacao(DroidUtils
					.convertStringToDate("2013-11-05 13:42:18"));
			noticia.setImagem("");
			noticia.setTitulo("Movimento Salvador Vai de Bike faz campanha junto a rodoviários");
			noticia.setDescricao("Para estimular o respeito ao ciclista no trânsito, o Movimento Salvador Vai de Bike criou uma campanha de divulgação, utilizando outdoor, jornal, TV, internet e panfleto. O foco da campanha está no uso compartilhado das vias públicas, de forma a garantir mais segurança para todos que circulam pela cidade. Lançado em setembro pela Prefeitura, o movimento ganha cada dia mais adeptos, com quase 11,5 mil pessoas credenciadas através do site www.bikesalvador.com e 18 mil viagens realizadas, estimulando a população a usar a bicicleta como meio de transporte alternativo, mais saudável e menos poluente."+ 

"O movimento teve início com a disponibilização do sistema público de bicicletas compartilhadas, com estações em locais estratégicos da cidade, conectadas a uma central de operações via wireless, alimentadas por energia sola. O usuário paga apenas o credenciamento anual, no valor de R$10, valendo-se do serviço ao longo de 12 meses, ou seja, sem a cobrança de tarifas diárias ou mensais, diferente do que ocorre em outras capitais onde o programa já funciona."+

"Segundo o secretário municipal da Copa, Isaac Edington, coordenador do movimento, com a boa adesão ao projeto e duas ciclofaixas já abertas no Campo Grande e Comércio, o objetivo agora é investir na conscientização da população sobre a importância do compartilhamento das vias. “Nossa meta é que as ciclofaixas sejam o ponto de partida para que as pessoas adotem o uso da bicicleta como meio de transporte no seu dia-a-dia. Para isso, a Prefeitura já começa a investir em infraestrutura. Um exemplo é a ciclofaixa permanente que a Transalvador já instalou no trecho Roma/Ribeira”, explica o secretário."+

"Boa convivência - Isaac Edignton destaca que o trabalho de conscientização da população engloba várias ações. Uma delas é a campanha que está nas ruas da cidade. O conteúdo da campanha disponibiliza dicas e informações sobre as principais questões que promovam uma boa convivência entre ciclistas e motoristas, a exemplo da obrigatoriedade de se manter a distância de 1,5 metro ao ultrapassar quem está na bike e o direito das bicicletas de compartilhar as vias com os veículos. É infração gravíssima colar na traseira e ameaçar o ciclista, por exemplo."+

"Ainda dentro das ações de mobilização, o secretário da Ecopa reuniu-se na última segunda-feira (4) com os dirigentes do Sindicato dos Rodoviários da Bahia. No encontro com o presidente da entidade, Hélio Ferreira, o vice-presidente Fábio Primo e o presidente metropolitano, Walter Lopes, foram disponibilizados materiais da campanha de conscientização do Movimento Salvador Vai de Bike, com os principais pontos de interesse para serem distribuídos entre a categoria."+

"\"A partir deste contato inicial, nossa meta é promover ações continuadas. Isso será realizado não só como os rodoviários, que formam uma parcela importante das pessoas que circulam no trânsito da cidade, como com outras categorias e representantes de grupos da sociedade civil\", afirmou Isaac Edignton.");
			quadroDeAvisos.add(noticia);
		}

		return quadroDeAvisos;
	}

	public ArrayList<Ocorrencia> criarAlertas() {
		
		for (int i = 0; i < 10; i++) {
			Ocorrencia alerta = new Ocorrencia();
			alerta.setDescricao("Acidente na Avenida ACM com vítima. Colisão entre dois automóveis.");
			alerta.setData(DroidUtils
					.convertStringToDate("2013-11-13 17:32:18"));

			alertas.add(alerta);
		}

		return alertas;
	}
	
	public ArrayList<Educacao> criarEducacao(){
		for (int i = 0; i < 10; i++) {
			Educacao educacao = new Educacao();
			educacao.setTitulo("Mudança na orla só garante trânsito bom na pista com mão invertida");

			educacao.setData_publicacao("22/08/2013 07:53");
			educacao.setDescricao("Como o próprio nome já diz, a faixa solidária, que estreou ontem na orla de Salvador, entre a Boca do Rio e o Sesc Piatã, deveria estimular a carona e beneficiar motoristas que seguiam em direção ao Centro – desde que o carro fosse ocupado por mais de uma pessoa."+
			"Mas, no mundo dos espertalhões, não foi bem o que aconteceu. Apesar de ter facilitado a vida de quem a usou, o benefício de atravessar o trecho em cerca de cinco minutos, por conta do tráfego livre, atraiu também quem não tinha direito."
			
					);

			lstEducacao.add(educacao);

		}
		
		return lstEducacao;
	}

	public ArrayList<Noticia> getNoticias() {
		return noticias;
	}

	public void setNoticias(ArrayList<Noticia> noticias) {
		this.noticias = noticias;
	}

	public ArrayList<Noticia> getQuadroDeAvisos() {
		return quadroDeAvisos;
	}

	public void setQuadroDeAvisos(ArrayList<Noticia> quadroDeAvisos) {
		this.quadroDeAvisos = quadroDeAvisos;
	}

	public ArrayList<Ocorrencia> getAlertas() {
		return alertas;
	}

	public void setAlertas(ArrayList<Ocorrencia> alertas) {
		this.alertas = alertas;
	}

	public ArrayList<Educacao> getEducacao() {
		return lstEducacao;
	}

	public void setEducacao(ArrayList<Educacao> educacao) {
		this.lstEducacao = educacao;
	}
}
