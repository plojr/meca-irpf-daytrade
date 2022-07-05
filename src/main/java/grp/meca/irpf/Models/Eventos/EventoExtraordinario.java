/*
 * Esta classe será a classe base para qualquer evento extraordinário.
 * Lista de eventos extraordinários: cisão, fusão, grupamento e desdobramento.
 * Os campos em comum a todos eles são: o id, a data do evento e a empresa,
 * representada pelo ticker, responsável pelo evento.
 * Quando o evento envolver outra empresa, como nos casos de cisão e fusão,
 * a variável responsável por ela estará na classe filha.
 * Quanto às sobras:
 * 
 * Desdobramento:
 *	- Não gera fração já que o valor de desdobramento é inteiro.
 *
 * Grupamento:
 * 	- Se a venda do mês + fração > 20k: soma-se a fração à venda e deve-se pagar IR sobre lucro; deve ser declarado em "Renda Variável".
 * 	- Caso contrário: a fração deve ser declarada em "Rendimentos Isentos e Não Tributáveis", no código "Outros".
 * 
 * Bonificação:
 *  - Deve ser declarado em "Rendimentos Isentos e Não Tributáveis", no código "Outros".
 * 
 * Cisão:
 * 	- Deve ser declarado em "Rendimentos Isentos e Não Tributáveis", no código "Outros".
 * 
 * Fusão:
 *  - ?
 */

package grp.meca.irpf.Models.Eventos;

import java.time.LocalDate;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import org.springframework.data.util.Pair;

import grp.meca.irpf.Models.Basico.Ticker;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class EventoExtraordinario implements Comparable<EventoExtraordinario> {

	public EventoExtraordinario() {}
	
	public EventoExtraordinario(Ticker ticker1, LocalDate dataEvento) {
		this.ticker1 = ticker1;
		this.dataEvento = dataEvento;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ticker_id_1", nullable = false)
	private Ticker ticker1;
	
	@Column(nullable = false)
	private LocalDate dataEvento;
	
	public abstract void aplicarEvento(Map<String, Pair<Integer, Double>> carteira);
	
	public int compareTo(EventoExtraordinario evento) {
		return this.getDataEvento().compareTo(evento.getDataEvento());
	}
}
